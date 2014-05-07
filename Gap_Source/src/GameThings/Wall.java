/**
 * 
 */
package GameThings;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Nemo Éú³ÉÇ½±Ú
 *
 */
public class Wall {

	/**
	 * 
	 */
	public Wall() {
		// TODO Auto-generated constructor stub
	}

	
	public Body getWall(World world,Vector2 V_1,Vector2 V_2){
		BodyDef edgeDef = new BodyDef();
		Body body = world.createBody(edgeDef);
		EdgeShape edgeShape = new EdgeShape();
		edgeShape.set(V_1, V_2);
		FixtureDef fixture = new FixtureDef();
		fixture.shape = edgeShape;
		body.createFixture(fixture);
		edgeShape.dispose();
		return body;
	}
}
