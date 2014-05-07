/**
 * 
 */
package GameResource;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * @author Nemo ��Դ�࣬�첽���أ�������ɺ�ֱ�ӵ���
 */
public class GameResource {

	/** ��Դ������ */
	public static AssetManager assetManager;
	/** �Ƿ������� */
	public static boolean isLoaded;
	
	/** ��Ϸ������Ч */
	public static Music M_playBackground;
	/** ��ť��Ч */
	public static Sound S_button;
	/** ��Ծ��Ч */
	public static Sound S_jump;
	/**ʧ����Ч */
	public static Sound S_fail;
	/** ��ť��Ч */
	public static Sound S_success;

	/**
	 * ��Ҫ�����������Դ
	 */
	public static void init() {
		assetManager = new AssetManager();
	}

	/**
	 * ����assetmanager����
	 */
	public static void load() {
		assetManager.load("Sound/success.ogg", Sound.class);
		assetManager.load("Sound/jump.ogg", Sound.class);
		assetManager.load("Sound/fail.wav", Sound.class);
		assetManager.load("Sound/button.wav", Sound.class);
		assetManager.load("Sound/playBackground.ogg", Music.class);
	}

	/**
	 * ��Դ����
	 */
	public static void update() {
		if (assetManager.update()) {
			/**
			 * Sound����
			 */
			S_success = assetManager.get("Sound/success.ogg", Sound.class);
			S_fail = assetManager.get("Sound/fail.wav", Sound.class);
			S_button = assetManager.get("Sound/button.wav", Sound.class);
			S_jump = assetManager.get("Sound/jump.ogg", Sound.class);
			M_playBackground = assetManager.get("Sound/playBackground.ogg",
					Music.class);
			M_playBackground.setLooping(true);
			isLoaded = true;
		}
	}
}
