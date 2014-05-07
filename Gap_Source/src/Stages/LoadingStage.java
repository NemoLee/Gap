/**
 * 
 */
package Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * @author Nemo
 * 
 */
public class LoadingStage extends Stage {

	/**
	 * 
	 */
	private ParticleEffect PE_loading;
	/**
	 * 标记particle左移还是右移
	 */
	private int i;

	public LoadingStage() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param width
	 * @param height
	 * @param keepAspectRatio
	 */
	public LoadingStage(float width, float height, boolean keepAspectRatio) {
		super(width, height, keepAspectRatio);
		// TODO Auto-generated constructor stub
		PE_loading = new ParticleEffect();
		PE_loading.load(Gdx.files.internal("Player/player.p"),
				Gdx.files.internal("Player/"));
		PE_loading.setPosition(Gdx.graphics.getWidth()/2-100, 100);
		i = 1;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		super.draw();
		getSpriteBatch().begin();
		PE_loading.draw(getSpriteBatch(), Gdx.graphics.getDeltaTime());
		getSpriteBatch().end();
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		moveParticle(delta);
	}

	/**
	 * 加载过程粒子移动
	 */
	private void moveParticle(float delta) {
		if (PE_loading.getEmitters().first().getX() > Gdx.graphics.getWidth()/2+100
				|| PE_loading.getEmitters().first().getX() < Gdx.graphics.getWidth()/2-100)
			i = -i;
		PE_loading.setPosition(PE_loading.getEmitters().first().getX() + i * 100
				* delta, 100);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		PE_loading.dispose();
	}
}
