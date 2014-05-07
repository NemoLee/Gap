/**
 * 
 */
package com.LittleN.Gap;

import GameLogic.GameLogic;
import GameResource.GameResource;
import Stages.LoadingStage;
import Stages.PlayStage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * @author Nemo
 * 
 */
public class Gap implements Screen {

	/**
	 * 
	 */
	Stage mainStage;
	Stage stage;
	LoadingStage loading_stage;
	GameLogic GL;
	boolean enter;

	public Gap() {
		// TODO Auto-generated constructor stub
		GL = new GameLogic();
		loading_stage = new LoadingStage(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(), false);
		stage = loading_stage;
		enter = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		GameResource.update();
		init();
		if (GL.getInitOver() && !enter) {
			stage = new PlayStage(Gdx.graphics.getWidth(),
					Gdx.graphics.getHeight(), false);
			setStage(stage, 5);
			enter = true;
		}
		stage.act(delta);
		stage.draw();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		// TODO Auto-generated method stub
		GL.setInitOver(false);
		GameResource.init();
		GameResource.load();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/** 判断资源加载 */
	public void init() {
		if (GL.getInitOver())
			return;
		if (GameResource.isLoaded) {
			GL.setInitOver(true);
		}
	}

	/**
	 * 切换场景
	 * 
	 * @param stage
	 *            目标舞台
	 * @param type
	 *            场景过度方式
	 */
	public void setStage(Stage newstage, int type) {
		mainStage = newstage;
		Gdx.input.setInputProcessor(mainStage);
		switch (type) {
		case 0:
			break;
		case 1:
			mainStage.getRoot().setPosition(800, 0);
			mainStage.getRoot().addAction(Actions.moveTo(0, 0, 0.4f));
			break;
		case 2:
			mainStage.getRoot().setPosition(-800, 0);
			mainStage.getRoot().addAction(Actions.moveTo(0, 0, 0.4f));
			break;
		case 3:
			mainStage.getRoot().setPosition(0, 480);
			mainStage.getRoot().addAction(Actions.moveTo(0, 0, 0.4f));
			break;
		case 4:
			mainStage.getRoot().setPosition(0, -480);
			mainStage.getRoot().addAction(Actions.moveTo(0, 0, 0.4f));
			break;
		case 5:
			mainStage.getRoot().setColor(0, 0, 0, 0);
			mainStage.getRoot().addAction(Actions.fadeIn(1f));
			break;
		}
	}
}
