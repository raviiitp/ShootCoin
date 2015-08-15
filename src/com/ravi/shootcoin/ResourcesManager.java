package com.ravi.shootcoin;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;


import android.graphics.Color;

import com.ravi.shootcoin.MainActivity;

public class ResourcesManager
{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final ResourcesManager INSTANCE = new ResourcesManager();
    
    public Engine engine;
    public MainActivity activity;
    public BoundCamera camera;
    public VertexBufferObjectManager vbom;
    
    public ITextureRegion splashRegion;
    private BitmapTextureAtlas splashTextureAtlas;
    
 // Game Texture
    public BuildableBitmapTextureAtlas gameTextureAtlas;
        
    public void loadMenuResources()
    {
        loadMenuGraphics();
        loadMenuAudio();
        loadMenuFonts();
    }
    
    public void loadGameResources()
    {
        loadGameGraphics();
        loadGameFonts();
        loadGameAudio();
    }
    
    public void loadResumeResources()
    {
    	loadResumeGraphics();
    	loadResumeFonts();
    	loadResumeAudio();
    }
    
    public void loadGameOverResources(){
    	loadGameOverGraphics();
    	loadGameOverFonts();
    	loadGameOverAudio();
    }
    
    public ITextureRegion menu_background_region;
    public ITextureRegion play_region;
    public ITextureRegion options_region, exit_region;
    public ITextureRegion game_Parallax_background_region;
    public ITextureRegion canon_region, bomb_region, pause_region, wall_region;
    public ITiledTextureRegion ball_region;
    
    private BuildableBitmapTextureAtlas menuTextureAtlas;
    private BuildableBitmapTextureAtlas resumeTextureAtlas;
    private BuildableBitmapTextureAtlas gameOverTextureAtlas;
    
    private void loadMenuGraphics()
    {
       	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
    	menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.png");
    	play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
    	options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "options.png");
    	exit_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "exit.png");
    	       
    	try 
    	{
    	    this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.menuTextureAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
    	        Debug.e(e);
    	}
    }
    
    private void loadMenuAudio()
    {
        
    }
    
    public Font font;
    private void loadMenuFonts()
    {
        FontFactory.setAssetBasePath("font/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        //import android.graphics.Color needed and not import org.andengine.util.color.Color;
        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
        font.load();
    }

    private void loadGameGraphics()
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
        gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 500, 1200, TextureOptions.BILINEAR);
        //game_Parallax_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "chessBoard.png");
        canon_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "canon.png");
        bomb_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "bomb.png");
        ball_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "ball.png", 1, 1);
        pause_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "pause.png");
        wall_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "wall.png");

        try 
        {
            this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.gameTextureAtlas.load();
        } 
        catch (final TextureAtlasBuilderException e)
        {
            Debug.e(e);
        }
    }
    
    private void loadGameFonts(){
    }
    
    private void loadGameAudio(){
    }
    
    public ITextureRegion resume_region, restart_region, toMenu_region;
    private void loadResumeGraphics()
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/resume/");
        resumeTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        resume_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(resumeTextureAtlas, activity, "resume.png");
        restart_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(resumeTextureAtlas, activity, "restart.png");
        toMenu_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(resumeTextureAtlas, activity, "toMenu.png");

        try 
        {
            this.resumeTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            //this.resumeTextureAtlas.load();
        } 
        catch (final TextureAtlasBuilderException e)
        {
            Debug.e(e);
        }
    }
    
    private void loadResumeFonts(){
    }
    
    private void loadResumeAudio(){
    }
    
    private void loadGameOverGraphics(){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/gameOver/");
        gameOverTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

        try 
        {
            this.gameOverTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.gameOverTextureAtlas.load();
        } 
        catch (final TextureAtlasBuilderException e)
        {
            Debug.e(e);
        }
    }
    
    private void loadGameOverFonts(){
    }
    
    private void loadGameOverAudio(){
    }
    
    public void loadSplashScreen()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
    	splashTextureAtlas.load();
    }
    
    public void unloadSplashScreen()
    {
    	splashTextureAtlas.unload();
    	splashRegion = null;
    }
    
    public void unloadMenuTextures()
    {
        menuTextureAtlas.unload();
    }
        
    public void loadMenuTextures()
    {
        menuTextureAtlas.load();
    }
    
    public void loadGameTextures(){
    	gameTextureAtlas.load();
    }
    
    public void unloadGameTextures()
    {
    	gameTextureAtlas.unload();
    }
    
    public void loadResumeTextures(){
    	resumeTextureAtlas.load();
    }
    
    public void unloadResumeTextures(){
    	resumeTextureAtlas.unload();
    }
    
    public void loadGameOverTextures(){
    }

    public void unloadGameOverTextures(){
    }
    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void prepareManager(Engine engine, MainActivity activity, BoundCamera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static ResourcesManager getInstance()
    {
        return INSTANCE;
    }

    
}
