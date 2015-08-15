package com.ravi.shootcoin;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;

import com.ravi.shootcoin.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
	}

	private void createBackground()
	{
		setBackground(new Background(0.8784f, 0.3274f, 0.09804f));
/*	    attachChild(new Sprite(CAMERA_WIDTH * 0.5f, CAMERA_HEIGHT * 0.5f, resourcesManager.menu_background_region, vbom)
	    {
	        @Override
	        protected void preDraw(GLState pGLState, Camera pCamera) 
	        {
	            super.preDraw(pGLState, pCamera);
	            pGLState.enableDither();
	        }
	    });*/
	}
	
	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		
	}
	
	// menu buttons
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;
	private final int MENU_EXIT = 2;

	private void createMenuChildScene()
	{
	    menuChildScene = new MenuScene(camera);
	    menuChildScene.setPosition(0, 0);
	    //setPosition((CAMERA_WIDTH - menuChildScene.getWidth()) * 0.5f, (CAMERA_HEIGHT - splash.getHeight()) * 0.5f);
	    
/*	    final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, vbom), 1.2f, 1);
	    final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourcesManager.options_region, vbom), 1.2f, 1);
	    final IMenuItem exitMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_EXIT, resourcesManager.exit_region, vbom), 1f, 1);
	    
	    menuChildScene.addMenuItem(playMenuItem);
	    menuChildScene.addMenuItem(optionsMenuItem);
	    menuChildScene.addMenuItem(exitMenuItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);
	    
	    playMenuItem.setPosition(playMenuItem.getX()+300, playMenuItem.getY() + 10);
	    optionsMenuItem.setPosition(optionsMenuItem.getX()+300, optionsMenuItem.getY());
	    exitMenuItem.setPosition(exitMenuItem.getX()+300, exitMenuItem.getY() - 10);
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);*/
	    
        TextMenuItem playMenuItem = new TextMenuItem(MENU_PLAY, resourcesManager.font, "play", vbom);
        TextMenuItem optionsMenuItem = new TextMenuItem(MENU_OPTIONS, resourcesManager.font, "options", vbom);
        TextMenuItem exitMenuItem = new TextMenuItem(MENU_EXIT, resourcesManager.font, "exit", vbom);
        menuChildScene.addMenuItem(playMenuItem);menuChildScene.addMenuItem(optionsMenuItem);menuChildScene.addMenuItem(exitMenuItem);
	    menuChildScene.buildAnimations();
	    playMenuItem.setPosition(playMenuItem.getX()*1.5f, playMenuItem.getY() + 50);
	    optionsMenuItem.setPosition(optionsMenuItem.getX()*1.5f, optionsMenuItem.getY());
	    exitMenuItem.setPosition(exitMenuItem.getX()*1.5f, exitMenuItem.getY() - 50);
	    menuChildScene.setBackgroundEnabled(false);
	    menuChildScene.setOnMenuItemClickListener(this);
	    setChildScene(menuChildScene);
	}
	
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
	{
	        switch(pMenuItem.getID())
	        {
	        case MENU_PLAY:
	        	SceneManager.getInstance().loadGameScene(engine);
	            return true;
	        case MENU_OPTIONS:
	            return true;
	        case MENU_EXIT:
	        	System.exit(0);
	        	return true;
	        default:
	            return false;
	    }
	}

}
