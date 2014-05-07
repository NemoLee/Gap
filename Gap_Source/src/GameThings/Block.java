/**
 * 
 */
package GameThings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * @author Nemo
 * 
 */
public class Block extends Image {

	Body body;

	public Block() {
		// TODO Auto-generated constructor stub

	}

	/**
	 * 
	 * @param TRD_block
	 *            显示纹理
	 * @param row
	 *            block所在列
	 * @param line
	 *            block 所在行
	 * @param flag
	 *            0:up 1:down 2:不可见
	 */
	public Block(World world, TextureRegionDrawable TRD_block, int row,
			int line, int flag) {
		setSize(80, 20);
		setDrawable(TRD_block);
		if (0 == flag)
			setPosition(row * 80, line * 20);
		else if (1 == flag) {
			setPosition(row * 80, line * 20 + 160);
			setName("block");
			setBox2d(world, row * 80+40, line * 20 + 170);//160+10 中心坐标系
		}
	}

	private void setBox2d(World world, float x, float y) {
		BodyDef box = new BodyDef();
		box.type = BodyType.StaticBody;
		box.position.set(x, y);
		body = world.createBody(box);
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(40f, 10f);
		FixtureDef fixture = new FixtureDef();
		fixture.shape = boxShape;
		fixture.density = 1f;
		fixture.friction = 0f;
		fixture.restitution = 0f;
		body.createFixture(fixture);
		boxShape.dispose();
//		BodyDef bd = new BodyDef();
//        bd.position.set(0, 0);
//        Body groundBody = world.createBody(bd);        
//        EdgeShape edge = new EdgeShape();
//        edge.set(new Vector2(0f, 0f), new Vector2(48f, 0f));
//        FixtureDef boxShapeDef = new FixtureDef();
//        boxShapeDef.shape = edge;
//        groundBody.createFixture(boxShapeDef);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
	}

	public void destoryBody(World world){
		world.destroyBody(body);
	}
	
}
