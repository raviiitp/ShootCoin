package com.ravi.shootcoin;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.engine.camera.Camera;

import com.ravi.shootcoin.BaseScene;
import com.ravi.shootcoin.SceneManager.SceneType;

public class SplashScene extends BaseScene{

	private Sprite splash;
	
	@Override
	public void createScene() {
		splash = new Sprite((CAMERA_WIDTH) * 0.5f, (CAMERA_HEIGHT) * 0.5f, resourcesManager.splashRegion, vbom)
		{
		    @Override
		    protected void preDraw(GLState pGLState, Camera pCamera) 
		    {
		       super.preDraw(pGLState, pCamera);
		       pGLState.enableDither();
		    }
		};
		        
		//splash.setScale(1.5f);
		//splash.setPosition((CAMERA_WIDTH) * 0.5f, (CAMERA_HEIGHT) * 0.5f);
		attachChild(splash);
	}

	@Override
	public void onBackKeyPressed() {
		return;
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();
	}

}
