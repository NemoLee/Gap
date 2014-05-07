/**
 * 
 */
package GameThings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * @author Nemo ·ÖÊý
 * 
 */
public class Num extends Label {

	int num = 0;

	/**
	 * @param text
	 * @param skin
	 */
	public Num(CharSequence text, Label.LabelStyle style) {
		super(text, style);
		style.font
				.getRegion()
				.getTexture()
				.setFilter(Texture.TextureFilter.Linear,
						Texture.TextureFilter.Linear);
	}

	public void addNum() {
		num++;
	}

	public void resetNum() {
		num = 0;
	}

	public int getNum() {
		return num;
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		setText("" + num);
		setPosition(400 - getTextBounds().width / 2, 30);
		super.act(delta);
	}

}
