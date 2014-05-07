/**
 * 
 */
package GameResource;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * @author Nemo 资源类，异步加载，加载完成后直接调用
 */
public class GameResource {

	/** 资源加载器 */
	public static AssetManager assetManager;
	/** 是否加载完成 */
	public static boolean isLoaded;
	
	/** 游戏背景音效 */
	public static Music M_playBackground;
	/** 按钮音效 */
	public static Sound S_button;
	/** 跳跃音效 */
	public static Sound S_jump;
	/**失败音效 */
	public static Sound S_fail;
	/** 按钮音效 */
	public static Sound S_success;

	/**
	 * 需要立即载入的资源
	 */
	public static void init() {
		assetManager = new AssetManager();
	}

	/**
	 * 存入assetmanager队列
	 */
	public static void load() {
		assetManager.load("Sound/success.ogg", Sound.class);
		assetManager.load("Sound/jump.ogg", Sound.class);
		assetManager.load("Sound/fail.wav", Sound.class);
		assetManager.load("Sound/button.wav", Sound.class);
		assetManager.load("Sound/playBackground.ogg", Music.class);
	}

	/**
	 * 资源载入
	 */
	public static void update() {
		if (assetManager.update()) {
			/**
			 * Sound部分
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
