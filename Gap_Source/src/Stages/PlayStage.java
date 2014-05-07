/**
 * 
 */
package Stages;

import GameLogic.GameData;
import GameResource.GameResource;
import GameThings.Block;
import GameThings.Num;
import GameThings.Player;
import GameThings.Wall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

/**
 * @author Nemo 游戏Stage
 */
public class PlayStage extends Stage {

	private Box2DDebugRenderer renderer;

	/**
	 * 上下地面
	 */
	private Image I_up;
	/**
	 * 行列标示
	 */
	private int[] i_row;
	private int[][] i_block;
	/**
	 * piece小图
	 */
	private TextureRegionDrawable TRD_up;

	private TextureAtlas TA_play;
	private Button B_jump, B_left, B_right, B_music;
	/**
	 * 活动部分
	 */
	private Group G_up;

	/************* Box2D *************************/
	private World world;

	private Player player;

	private Num num;

	private Array<Body> A_body;
	/** box2d light handler */
	/************* Box2D *************************/

	private Group G_dialog;
	private BitmapFont BF_num;
	private Label L_score;

	/**************** GameLogic ***********************/
	private GameData GD;
	private boolean Music, M_fail;
	private int highScore;

	/**************** GameLogic ***********************/
	public PlayStage() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param width
	 * @param height
	 * @param keepAspectRatio
	 *            只初始化固定部分， 可变部分在restart中
	 */
	public PlayStage(float width, float height, boolean keepAspectRatio) {
		super(width, height, keepAspectRatio);
		// TODO Auto-generated constructor stub

		GD = new GameData();
		Music = true;
		M_fail = false;
		highScore = GD.getScore();

		renderer = new Box2DDebugRenderer();
		A_body = new Array<Body>();
		initBox2D();
		player = new Player(world, 200, 400);
		initRes();
		addActor(player);
		Gdx.input.setInputProcessor(this);
		this.setViewport(800, 480, false);

	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++)
			world.step(1 / 60f, 6, 2);
		checkButton();
		musicPlay();
		super.act(delta);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		renderer.render(world, this.getCamera().combined);// for test
		super.draw();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		A_body.clear();
		player.dispose();
		BF_num.dispose();
		world.dispose();
		TA_play.dispose();
	}

	private void initRes() {

		TA_play = new TextureAtlas(Gdx.files.internal("Play/playstage.pack"));
		for (int i = 0; i < TA_play.getRegions().size; i++)
			TA_play.getRegions()
					.get(i)
					.getTexture()
					.setFilter(Texture.TextureFilter.Linear,
							Texture.TextureFilter.Linear);
		TRD_up = new TextureRegionDrawable(new TextureRegion(new Texture(
				"Play/up_piece.png")));

		// TRD_down = new TextureRegionDrawable(new TextureRegion(new Texture(
		// "Play/down_piece.png")));
		// TRD_down = new TextureRegionDrawable(new TextureRegion(new Texture(
		// "Play/down__.png")));
		G_up = new Group();
		G_up.setSize(800, 280);
		I_up = new Image(new Texture("Play/up.png"));

		I_up.setPosition(0, 60);// 相对group位置
		G_up.setPosition(0, 160);
		initButton();
		initNum();
		initDialog();
		reStart();

	}

	private void reStart() {
		this.getRoot().clearChildren();
		G_up.clearChildren();

		i_row = new int[2];
		i_block = new int[3][10];
		/** 生成无洞数组 */
		for (int row = 0; row < 10; row++) {
			int temp = MathUtils.random(0, 2);
			for (int i = temp; i >= 0; i--)
				i_block[i][row] = 1;
		}

		digHole();
		/**
		 * 遍历数组，初始化模型
		 */
		for (int line = 0; line < 3; line++)
			for (int row = 0; row < 10; row++) {
				if (0 == i_block[line][row]) {
					Block b = new Block(world, TRD_up, row, line, 0);
					G_up.addActor(b);
				}
			}

		G_up.addAction(Actions.sequence(Actions.moveTo(0, 360, 1f),
				Actions.delay(3f), Actions.moveTo(0, 160, 0.2f),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (checkPass())
							reStart();
						else {
							addDialog();
							System.out.println("gameover!");
						}
					}
				})));

		player.setposition(380, 240);
		G_up.addActor(I_up);
		addActor(G_up);
		addActor(B_jump);
		addActor(B_left);
		addActor(B_right);
		addActor(B_music);
		addActor(num);
		addActor(player);
		ArraytoVertices();
	}

	/**
	 * 生成分布数组中打洞
	 */
	private void digHole() {
		for (int i = 0; i < 2; i++) {
			i_row[i] = MathUtils.random(0, 9);
			if (i_block[0][i_row[i]] == i_block[1][i_row[i]]
					&& i_block[1][i_row[i]] == 1)
				i_block[2][i_row[i]] = 2;
			else if (i_block[0][i_row[i]] == i_block[1][i_row[i]]
					&& i_block[1][i_row[i]] == 0)
				i_block[0][i_row[i]] = 2;
			else
				i_block[1][i_row[i]] = 2;
		}
	}

	/**
	 * 初始化按钮
	 */
	private void initButton() {

		B_left = new Button(new TextureRegionDrawable(
				TA_play.findRegion("left")), new TextureRegionDrawable(
				TA_play.findRegion("left_p")));
		B_right = new Button(new TextureRegionDrawable(
				TA_play.findRegion("right")), new TextureRegionDrawable(
				TA_play.findRegion("right_p")));
		B_jump = new Button(new TextureRegionDrawable(
				TA_play.findRegion("jump")), new TextureRegionDrawable(
				TA_play.findRegion("jump_p")));
		B_music = new Button(new TextureRegionDrawable(
				TA_play.findRegion("music_on")), new TextureRegionDrawable(
				TA_play.findRegion("music_off")), new TextureRegionDrawable(
				TA_play.findRegion("music_off")));
		B_left.setSize(96, 96);
		B_right.setSize(96, 96);
		B_jump.setSize(96, 96);
		B_music.setSize(60, 60);
		B_left.setPosition(10, 10);
		B_right.setPosition(110, 10);
		B_jump.setPosition(670, 10);
		B_music.setPosition(4, 416);
		B_right.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub

				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				player.forceStop();
				super.touchUp(event, x, y, pointer, button);
			}
		});

		B_left.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				player.forceStop();
				super.touchUp(event, x, y, pointer, button);
			}
		});
		B_jump.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				if (Music)
					GameResource.S_jump.play(0.6f);
				player.forceJump();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		B_music.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub

				Music = !Music;
				// return super.touchDown(event, x, y, pointer, button);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				B_music.setChecked(B_music.isChecked());
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}

	private void initBox2D() {
		world = new World(new Vector2(0, -9.8f), true);
		setWall();
	}

	/**
	 * 监测按钮
	 */
	private void checkButton() {
		if (B_left.isPressed())
			player.forceLeft();
		if (B_right.isPressed())
			player.forceRight();
	}

	/**
	 * 监测过关事件 包括音效控制
	 */
	private boolean checkPass() {
		int temp = (int) (player.body.getPosition().x / 80);
		if (temp == i_row[0] || temp == i_row[1]) {
			num.addNum();
			if (Music)
				GameResource.S_success.play(0.6f);
			return true;
		}
		if (Music) {
			GameResource.S_fail.play(0.6f);
			M_fail = true;
		}
		return false;
	}

	/**
	 * 清楚下面方块
	 */
	private void cleanBlockBody() {
		for (Body body : A_body)
			world.destroyBody(body);
		A_body.clear();
	}

	/**
	 * 创建两侧墙
	 */
	private void setWall() {
		Wall wall = new Wall();
		wall.getWall(world, new Vector2(0, 0), new Vector2(0, 480));
		wall.getWall(world, new Vector2(800, 0), new Vector2(800, 480));
	}

	/**
	 * 初始化记分器
	 */
	private void initNum() {
		BF_num = new BitmapFont(Gdx.files.internal("Num/num.fnt"));
		num = new Num("1", new LabelStyle(BF_num, new Color(1, 1, 1, 1)));
		num.setPosition(getWidth() / 2, 300);

	}

	/**
	 * 数组转化为定点
	 */
	private void ArraytoVertices() {
		Vector2[] Vertices = new Vector2[22];
		Vertices[0] = new Vector2(0, 180);
		for (int i = 0; i < 10; i++)
			if (1 == i_block[1][i] && 1 == i_block[2][i])
				Vertices[2 * i + 1] = new Vector2(i * 80, 220);
			else if (1 == i_block[1][i])
				Vertices[2 * i + 1] = new Vector2(i * 80, 200);
			else
				Vertices[2 * i + 1] = new Vector2(i * 80, 180);
		for (int i = 1; i < 11; i++)
			Vertices[i * 2] = new Vector2(Vertices[2 * i - 1].x + 80,
					Vertices[2 * i - 1].y);
		Vertices[21] = new Vector2(800, 180);

		cleanBlockBody();
		creatBlock(Vertices);
	}

	/**
	 * 
	 * @param xxx
	 *            重置下层box2d墙
	 */
	private void creatBlock(Vector2[] xxx) {
		Wall wall = new Wall();
		for (int i = 0; i < 21; i++)
			A_body.add(wall.getWall(world, xxx[i], xxx[i + 1]));
	}

	/**
	 * 初始化dialog
	 */
	private void initDialog() {
		G_dialog = new Group();
		Texture T_dialog, T_back, T_backp, T_rank, T_rankp;
		T_dialog = new Texture("Dialog/dialog.png");
		T_back = new Texture("Dialog/back.png");
		T_backp = new Texture("Dialog/back_p.png");
		T_rank = new Texture("Dialog/rank.png");
		T_rankp = new Texture("Dialog/rank_p.png");
		T_dialog.setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);
		T_back.setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);
		T_backp.setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);
		T_rank.setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);
		T_rankp.setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);
		Image I_dialog = new Image(T_dialog);
		L_score = new Label("", num.getStyle());
		Button B_back = new Button(new TextureRegionDrawable(new TextureRegion(
				T_back)), new TextureRegionDrawable(new TextureRegion(T_backp)));
		Button B_rank = new Button(new TextureRegionDrawable(new TextureRegion(
				T_rank)), new TextureRegionDrawable(new TextureRegion(T_rankp)));
		B_back.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				System.out.println("back");
				if(Music)
					GameResource.S_button.play(.6f);
				G_dialog.addAction(Actions.sequence(
						Actions.moveTo(150, 480, 0.4f),
						Actions.run(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								highScore = GD.getScore();// 重新开始读取当前最高分
								reStart();
								num.resetNum();
								M_fail = false;
							}
						})));
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		B_rank.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				System.out.println("rank");
				if(Music)
					GameResource.S_button.play(.6f);
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		B_back.setPosition(275f, 50f);
		B_rank.setPosition(100f, 50f);
		G_dialog.addActor(I_dialog);
		G_dialog.addActor(B_back);
		G_dialog.addActor(B_rank);
	}

	/**
	 * 添加dialog到舞台
	 */
	private void addDialog() {
		G_dialog.setPosition(150, 480);
		G_dialog.addAction(Actions.sequence(Actions.moveTo(150, 150, 1f,
				Interpolation.bounceOut)));
		L_score.setText(num.getText());
		L_score.setPosition(250 - L_score.getTextBounds().width / 2, 150);
		L_score.setColor(1, 1, 1, 0);
		L_score.addAction(Actions.sequence(Actions.delay(1.0f),
				Actions.fadeIn(1f, Interpolation.bounceIn)));
		num.remove();
		G_dialog.addActor(L_score);
		addActor(G_dialog);
		if (highScore < num.getNum()) {
			GD.setScore(num.getNum()); // //新高分逻辑
		}
	}

	private void musicPlay() {
		if (Music && !M_fail && !GameResource.M_playBackground.isPlaying())
			GameResource.M_playBackground.play();
		else if (!Music && GameResource.M_playBackground.isPlaying())
			GameResource.M_playBackground.stop();
		else if(M_fail)
			GameResource.M_playBackground.stop();
	}
}
