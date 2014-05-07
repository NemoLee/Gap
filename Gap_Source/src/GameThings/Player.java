/**
 * 
 */
package GameThings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author Nemo
 * 
 */
public class Player extends Actor {

	/**
	 * 
	 */
	public Body body;
	BodyDef ballBodyDef;
	Texture T_player;

	ParticleEffect PE_player;


	public Player() {
		// TODO Auto-generated constructor stub
	}

	public Player(World world, float x, float y) {
		initBox2D(world, x, y);
		setSize(20, 20);
		T_player = new Texture("test/player.png");
		PE_player = new ParticleEffect();
		PE_player.load(Gdx.files.internal("Player/player.p"),
				Gdx.files.internal("Player/"));
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		// setPosition(body.getPosition().x, body.getPosition().y);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
//		batch.draw(T_player, body.getPosition().x, body.getPosition().y,
//				getWidth(), getHeight());
		PE_player.setPosition(body.getPosition().x, body.getPosition().y);
		PE_player.draw(batch, Gdx.graphics.getDeltaTime());
		super.draw(batch, parentAlpha);

	}

	private void initBox2D(World world, float x, float y) {
		ballBodyDef = new BodyDef();
		ballBodyDef.type = BodyType.DynamicBody;
		ballBodyDef.position.set(x, y);
		body = world.createBody(ballBodyDef);
//		PolygonShape playerShape = new PolygonShape();
		 CircleShape playerShape = new CircleShape();
//		playerShape.setAsBox(10, 10);
		 playerShape.setRadius(10);
		FixtureDef ballShapeDef = new FixtureDef();
		ballShapeDef.shape = playerShape;
		ballShapeDef.density = 1f;
		ballShapeDef.friction = 0f;
		ballShapeDef.restitution = 0f;
		body.createFixture(ballShapeDef);
		body.setFixedRotation(true);
		MassData md = body.getMassData();
		md.mass = 1;
		body.setMassData(md);
	}

	public void forceRight() {
		body.setLinearVelocity(20, body.getLinearVelocity().y);
	}

	public void forceLeft() {
		body.setLinearVelocity(-20, body.getLinearVelocity().y);
	}

	public void forceJump() {
			body.setLinearVelocity(body.getLinearVelocity().x, 30f);
	}

	public void forceStop() {
		body.setAwake(false);
		body.applyForceToCenter(new Vector2(0, -500), true);
	}

	public void setposition(float x, float y) {
		body.setTransform(x, y, 0);
	}

	public void dispose() {
		// TODO Auto-generated method stub
		PE_player.dispose();
	}
}
