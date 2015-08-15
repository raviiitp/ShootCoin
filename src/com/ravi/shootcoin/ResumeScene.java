package com.ravi.shootcoin;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import com.ravi.shootcoin.SceneManager.SceneType;

public class ResumeScene extends BaseScene{

	public Sprite resumeSprite, restartSprite, toMenuSprite;
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_RESUME;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	private void createBackground(){
		setBackground(new Background(0.8784f, 0.3274f, 0.09804f));
		resumeSprite = new Sprite(CAMERA_WIDTH*0.5f, CAMERA_HEIGHT*0.65f, resourcesManager.resume_region, vbom){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
            {
            	SceneManager.getInstance().loadGameSceneFromResumeScene_resume(engine);
                return true;
            };
		};
		
		restartSprite = new Sprite(CAMERA_WIDTH*0.5f, CAMERA_HEIGHT*0.5f, resourcesManager.restart_region, vbom){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
            {
            	SceneManager.getInstance().loadGameSceneFromResumeScene_restart(engine);
                return true;
            };
		};
		
		toMenuSprite = new Sprite(CAMERA_WIDTH*0.5f, CAMERA_HEIGHT*0.35f, resourcesManager.toMenu_region, vbom){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
            {
            	SceneManager.getInstance().loadMenuScene(engine);
                return true;
            };
		};
		ResumeScene.this.registerTouchArea(resumeSprite);
		ResumeScene.this.registerTouchArea(restartSprite);
		ResumeScene.this.registerTouchArea(toMenuSprite);
		this.attachChild(resumeSprite);
		this.attachChild(restartSprite);
		this.attachChild(toMenuSprite);
	}

}
