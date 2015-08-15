package com.ravi.shootcoin;


import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;

import com.ravi.shootcoin.ResourcesManager;
import com.ravi.shootcoin.SceneManager.SceneType;

public abstract class BaseScene extends Scene
{
	protected static final int CAMERA_WIDTH = 720, CAMERA_HEIGHT = 1200;
    protected Engine engine;
    protected Activity activity;
    protected ResourcesManager resourcesManager;
    protected VertexBufferObjectManager vbom;
    protected BoundCamera camera;
    
    public BaseScene()
    {
        this.resourcesManager = ResourcesManager.getInstance();
        this.engine = resourcesManager.engine;
        this.activity = resourcesManager.activity;
        this.vbom = resourcesManager.vbom;
        this.camera = resourcesManager.camera;
        createScene();
    }
    
    public abstract void createScene();
    
    public abstract void onBackKeyPressed();
    
    public abstract SceneType getSceneType();
    
    public abstract void disposeScene();
}
