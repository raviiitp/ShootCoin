package com.ravi.shootcoin;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import com.ravi.shootcoin.SceneManager.SceneType;

public class LoadingScene extends BaseScene{
	
	@Override
	public void createScene() {
		setBackground(new Background(Color.WHITE));
		attachChild(new Text(CAMERA_WIDTH*0.5f, CAMERA_HEIGHT*0.5f, resourcesManager.font, "loading...", vbom));
	}
	
	@Override
	public void onBackKeyPressed() {
		return;
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_LOADING;
	}

	@Override
	public void disposeScene() {
		
	}

}
