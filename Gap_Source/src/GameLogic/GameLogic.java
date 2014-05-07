/**
 * 
 */
package GameLogic;

/**
 * @author Nemo ×´Ì¬Êý¾Ý
 */
public class GameLogic {

	/**
	 * 
	 */
	public boolean F_pause;
	public boolean F_pause_V;

	public boolean init_over;

	public GameLogic() {
		// TODO Auto-generated constructor stub
		F_pause = true;
		F_pause_V = true;
	}

	public void setPause(boolean pause) {
		F_pause = pause;
	}

	public boolean getPause() {
		return F_pause;
	}

	public void setPauseV(boolean pause) {
		F_pause_V = pause;
	}

	public boolean getPauseV() {
		return F_pause_V;
	}

	public void setInitOver(boolean initover) {
		init_over = initover;
	}

	public boolean getInitOver() {
		return init_over;
	}

}
