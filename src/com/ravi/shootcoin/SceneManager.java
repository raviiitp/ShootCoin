package com.ravi.shootcoin;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.ravi.shootcoin.BaseScene;

public class SceneManager
{
    //---------------------------------------------
    // SCENES
    //---------------------------------------------
    
    private BaseScene splashScene;
    private BaseScene menuScene;
    private GameScene gameScene;
    private BaseScene loadingScene;
    private BaseScene resumeScene;
    private BaseScene gameOverScene;
    
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final SceneManager INSTANCE = new SceneManager();
    
    private SceneType currentSceneType = SceneType.SCENE_SPLASH;
    
    private BaseScene currentScene;
    
    private Engine engine = ResourcesManager.getInstance().engine;
    
    public enum SceneType
    {
        SCENE_SPLASH,
        SCENE_MENU,
        SCENE_GAME,
        SCENE_LOADING,
        SCENE_RESUME,
        SCENE_GAMEOVER,
    }
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
    
    public void setScene(BaseScene scene)
    {
        engine.setScene(scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
    }
    
    public void setScene(SceneType sceneType)
    {
        switch (sceneType)
        {
            case SCENE_MENU:
                setScene(menuScene);
                break;
            case SCENE_GAME:
                setScene(gameScene);
                break;
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_LOADING:
                setScene(loadingScene);
                break;
            case SCENE_RESUME:
            	setScene(resumeScene);
            	break;
            case SCENE_GAMEOVER:
            	setScene(gameOverScene);
            	break;
            default:
                break;
        }
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static SceneManager getInstance()
    {
        return INSTANCE;
    }
    
    public SceneType getCurrentSceneType()
    {
        return currentSceneType;
    }
    
    public BaseScene getCurrentScene()
    {
        return currentScene;
    }
    
    public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback)
    {
        ResourcesManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }
    
    private void disposeSplashScene()
    {
        ResourcesManager.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    }
    
    public void createMenuScene()
    {
        ResourcesManager.getInstance().loadMenuResources();
        menuScene = new MainMenuScene();
        loadingScene = new LoadingScene();
        SceneManager.getInstance().setScene(menuScene);
        disposeSplashScene();
    }
    
    public void createResumeScene(){
    	ResourcesManager.getInstance().loadResumeResources();
    	resumeScene = new ResumeScene();
    }
    
    public void loadGameScene(final Engine mEngine)
    {
        setScene(loadingScene);
        ResourcesManager.getInstance().unloadMenuTextures();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameResources();
                gameScene = new GameScene();
                gameScene.setTouchAreaBindingOnActionDownEnabled(true); //imp for smooth dragging movement
                setScene(gameScene);
            }
        }));
    }
    
    public void loadMenuScene(final Engine mEngine)
    {
        setScene(loadingScene);
        gameScene.disposeScene();
        ResourcesManager.getInstance().unloadResumeTextures();
        //ResourcesManager.getInstance().unloadGameTextures(); //needed or not
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadMenuTextures();
                setScene(menuScene);
            }
        }));
    }
    
    public void loadResumeScene(final Engine mEngine){
    	ResourcesManager.getInstance().unloadGameTextures(); //needed or not
    	ResourcesManager.getInstance().loadResumeTextures();
    	setScene(resumeScene);
    }
    
    public void loadGameSceneFromResumeScene_resume(final Engine mEngine){
    	ResourcesManager.getInstance().unloadResumeTextures();
    	ResourcesManager.getInstance().loadGameTextures(); //needed or not
    	setScene(gameScene);
    }
    
    public void loadGameSceneFromResumeScene_restart(final Engine mEngine){
    	ResourcesManager.getInstance().unloadResumeTextures();
    	ResourcesManager.getInstance().loadGameTextures(); //needed or not
    	gameScene.restartGame();
    	
    	setScene(gameScene);
    }
}
