package com.ravi.shootcoin;

import java.io.IOException;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.level.EntityLoader;
import org.xml.sax.Attributes;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ravi.shootcoin.SceneManager.SceneType;

/**
 * @author ravi
 *
 */


public class GameScene extends BaseScene implements IOnSceneTouchListener{

	//private static final GameScene INSTANCE = new GameScene();
	ButtonSprite pauseSprite;
	
	@Override
	public void createScene() {
		createBackground();
	    createHUD();
	    createPhysics();
	    initGame();
	    loadLevel(1);
	    createGameOverText();
	    setOnSceneTouchListener(this);
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadResumeScene(engine);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		camera.setHUD(null);
		camera.setCenter(CAMERA_WIDTH*0.5f, CAMERA_HEIGHT*0.5f);
		camera.setChaseEntity(null);
	}

	private void createBackground(){
		setBackground(new Background(0.8784f, 0.3274f, 0.09804f));
		//this.attachChild(new Sprite(CAMERA_WIDTH*0.5f, CAMERA_HEIGHT*0.5f, resourcesManager.game_Parallax_background_region, vbom));
	}
	
	private void initGame(){
	    pauseSprite = new ButtonSprite(CAMERA_WIDTH*0.8f, CAMERA_HEIGHT*0.9f, resourcesManager.pause_region, vbom){
	    	@Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
            {
	    		pauseButtonPressed();
                return true;
            };
	    };
	    GameScene.this.registerTouchArea(pauseSprite);
	    GameScene.this.attachChild(pauseSprite);
	    //GameScene.this.setAnchorCenter(0, 0);
//		gameHUD.attachChild(win);
	}
	
	public void restartGame(){
		loadLevel(1);
	}
	
	private HUD gameHUD;
	private Text scorePlayer;
	private float scorePlayerx, scorePlayery;
	
	private void createHUD() {
		
		gameHUD = new HUD();
		scorePlayerx = 0.0f; scorePlayery = CAMERA_HEIGHT*0.9f;
		scorePlayer = new Text(scorePlayerx, scorePlayery, resourcesManager.font, "player score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		//scorePlayer.setRotation(90);
		scorePlayer.setAnchorCenter(0, 0);
		scorePlayer.setText("player score: 0");
		gameHUD.attachChild(scorePlayer);
		
		camera.setHUD(gameHUD);
	}
	
	private float playerScore = 0;

	private void addToScore(float f)
	{
			playerScore = f;
		    scorePlayer.setText("player score: " + playerScore);
	}
	
	private PhysicsWorld physicsWorld;

	private void createPhysics()
	{
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -25), false);
	    //physicsWorld.setContactListener(contactListener());
	    registerUpdateHandler(physicsWorld);
	}
	
	private void pauseButtonPressed(){
		SceneManager.getInstance().loadResumeScene(engine);
	}
	
	
	private static final String TAG_ENTITY = "entity";
	private static final String TAG_ENTITY_ATTRIBUTE_POSITION = "position";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";
	    
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_CANON = "canon";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_BOMB = "bomb";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_BALL = "ball";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LEFTWALL = "leftWall";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_RIGHTWALL = "rightWall";
	private Sprite canonSprite, bombSprite, playerSprite, leftWallSprite, rightWallSprite;
	private Body bombBody, playerBody;
	
	private void loadLevel(int levelID)
	{
	    final SimpleLevelLoader levelLoader = new SimpleLevelLoader(vbom);
	    final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0, 0.5f);
	    final FixtureDef FIXTURE_DEF_BALL = PhysicsFactory.createFixtureDef(1f, 1f, 0.5f);
	    
	    levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL)
	    {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException 
	        {
	            //final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
	            //final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);
	            
	           camera.setBounds(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT); // here we set camera bounds
	            return GameScene.this;
	        }

	    });
	    
	    levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_ENTITY)
	    {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
	        {
	            final int position = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_POSITION);
	            final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);
	            
	            final Sprite levelObject;
	            if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_CANON))
	            {
	            	canonSprite = new Sprite(CAMERA_WIDTH*0.5f, CAMERA_HEIGHT*0.0f+(resourcesManager.canon_region.getHeight())/2, resourcesManager.canon_region, vbom){
	                    @Override
	                    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) 
	                    {
	                    	canonSpriteTouched(pSceneTouchEvent, X, Y);
	                        return true;
	                    };
	            	};
	                PhysicsFactory.createBoxBody(physicsWorld, canonSprite, BodyType.StaticBody, FIXTURE_DEF).setUserData("canon");
	                GameScene.this.registerTouchArea(canonSprite);
	                levelObject = canonSprite;
	            }
	            else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_BOMB))
	            {
	                bombSprite = new Sprite(CAMERA_WIDTH*0.5f, CAMERA_HEIGHT*0.0f+resourcesManager.canon_region.getHeight()-(resourcesManager.bomb_region.getHeight())/2, resourcesManager.bomb_region, vbom)
	                {
	                    @Override
	                    protected void onManagedUpdate(float pSecondsElapsed) 
	                    {
	                        super.onManagedUpdate(pSecondsElapsed);
	                        
	                        /** 
	                         * TODO
	                         * we will later check if player collide with this (coin)
	                         * and if it does, we will increase score and hide coin
	                         * it will be completed in next articles (after creating player code)
	                         */
	                    }
	                };
	                bombBody = PhysicsFactory.createCircleBody(physicsWorld, bombSprite, BodyType.DynamicBody, FIXTURE_DEF);
	                bombBody.setUserData("bomb");
	                physicsWorld.registerPhysicsConnector(new PhysicsConnector(bombSprite, bombBody, false, false)/*{
	                    @Override
	                    public void onUpdate(float pSecondsElapsed)
	                    {
	                        //super.onUpdate(pSecondsElapsed);
	                       // camera.onUpdate(0.1f);
	                        //shoot bomb continuously
	                    }
	                }*/);
	                levelObject = bombSprite;
	            }
	            else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_BALL))
	            {
	                /*player = new Player(CAMERA_WIDTH*0.5f, CAMERA_HEIGHT*0.65f, vbom, camera, physicsWorld)
	                {
	                    @Override
	                    public void onDie()
	                    {
	                        // TODO Latter we will handle it.
	                    	if (!gameOverDisplayed)
	                        {
	                            displayGameOverText();
	                        }
	                    }
	                };
	                

	                levelObject = player;*/
	            	
	                playerSprite = new Sprite(CAMERA_WIDTH*0.5f, CAMERA_HEIGHT*0.65f, resourcesManager.ball_region, vbom){
	                    @Override
	                    protected void onManagedUpdate(float pSecondsElapsed) 
	                    {
	                        super.onManagedUpdate(pSecondsElapsed);
	                        
	                        /** 
	                         * TODO
	                         * we will later check if player collide with this (coin)
	                         * and if it does, we will increase score and hide coin
	                         * it will be completed in next articles (after creating player code)
	                         */
	                        if (bombSprite.collidesWith(this))
	                        {
	                        	//playerBody.applyAngularImpulse(1);
	                        }
	                        if (leftWallSprite.collidesWith(this))
	                        {
	                        	//playerBody.applyAngularImpulse(1);
	                        }
	                        if (rightWallSprite.collidesWith(this))
	                        {
	                        	//playerBody.applyAngularImpulse(1);
	                        }
	                    }
	                };
	                playerBody = PhysicsFactory.createCircleBody(physicsWorld, playerSprite, BodyType.StaticBody, FIXTURE_DEF_BALL);
	                playerBody.setUserData("ball");
	                physicsWorld.registerPhysicsConnector(new PhysicsConnector(playerSprite, playerBody, true, false){
	                    @Override
	                    public void onUpdate(float pSecondsElapsed)
	                    {
	                        super.onUpdate(pSecondsElapsed);
	                        camera.onUpdate(0.1f);
	                        //shoot bomb continuously
	                        if (playerSprite.getY() <= 0)
	                        {                    
		                    	if (!gameOverDisplayed)
		                        {
		                            displayGameOverText();
		                        }
	                        }
	                    }
	                });
	                levelObject = playerSprite;
	            }
	            else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LEFTWALL)){
	        		leftWallSprite = new Sprite(CAMERA_WIDTH*0.1f, CAMERA_HEIGHT*0.5f, resourcesManager.wall_region, vbom);
	                final Body body = PhysicsFactory.createBoxBody(physicsWorld, leftWallSprite, BodyType.StaticBody, FIXTURE_DEF);
	                body.setUserData("leftWall");
	                leftWallSprite.setHeight(CAMERA_HEIGHT);
	                physicsWorld.registerPhysicsConnector(new PhysicsConnector(leftWallSprite, body, true, false){
	                    @Override
	                    public void onUpdate(float pSecondsElapsed)
	                    {
	                        //super.onUpdate(pSecondsElapsed);
	                       // camera.onUpdate(0.1f);
	                        //shoot bomb continuously
	                    }
	                });
	        		levelObject = leftWallSprite;
	            }
	            else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_RIGHTWALL)){
	        		rightWallSprite = new Sprite(CAMERA_WIDTH*0.9f, CAMERA_HEIGHT*0.5f, resourcesManager.wall_region, vbom);
	                final Body body = PhysicsFactory.createBoxBody(physicsWorld, rightWallSprite, BodyType.StaticBody, FIXTURE_DEF);
	                body.setUserData("rightWall");
	                rightWallSprite.setHeight(CAMERA_HEIGHT);
	                physicsWorld.registerPhysicsConnector(new PhysicsConnector(rightWallSprite, body, true, false){
	                    @Override
	                    public void onUpdate(float pSecondsElapsed)
	                    {
	                        //super.onUpdate(pSecondsElapsed);
	                       // camera.onUpdate(0.1f);
	                        //shoot bomb continuously
	                    }
	                });
	        		rightWallSprite.setFlippedHorizontal(true);
	        		levelObject = rightWallSprite;
	            }
	            else
	            {
	                throw new IllegalArgumentException();
	            }
	            levelObject.setCullingEnabled(true);
	            return levelObject;
	        }
	    });

	    levelLoader.loadLevelFromAsset(activity.getAssets(), "level/" + levelID + ".lvl");
	}
	
	private void canonSpriteTouched(TouchEvent pSceneTouchEvent, float X, float Y){
		if(pSceneTouchEvent.isActionMove()){
			addToScore(pSceneTouchEvent.getY());
			canonSprite.setX(pSceneTouchEvent.getX() - this.getWidth() / 2);
			bombSprite.setX(pSceneTouchEvent.getX() - this.getWidth() / 2);
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
	    if (pSceneTouchEvent.isActionDown())
	    {
	        if (!firstTouch)
	        {
	            //player.setFalling();
	        	playerBody.setType(BodyType.DynamicBody);
	            firstTouch = true;
	        }
	        else
	        {
	           // player.jump();
	        }
	    }
/*		if(pSceneTouchEvent.isActionMove()){
			addToScore(pSceneTouchEvent.getY());
			canonSprite.setX(pSceneTouchEvent.getX() - this.getWidth() / 20);
		}*/
		return false;
	}
	
	private Player player;
	   private boolean firstTouch = false;
	   
	   private Text gameOverText;
	   private boolean gameOverDisplayed = false;
	   
	   private void createGameOverText()
	   {
	       gameOverText = new Text(0, 0, resourcesManager.font, "Game Over!", vbom);
	   }

	   private void displayGameOverText()
	   {
	       camera.setChaseEntity(null);
	       gameOverText.setPosition(camera.getCenterX(), camera.getCenterY());
	       attachChild(gameOverText);
	       gameOverDisplayed = true;
	   }
	   
	private ContactListener contactListener() {
		ContactListener contactListener = new ContactListener() {
			
			public void beginContact(Contact contact) {
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();

				if (x1.getBody().getUserData() != null
						&& x2.getBody().getUserData() != null) {
					/*
					 * In practice, you can make an educated guess about which
					 * fixture is which, mainly based on the order that you
					 * created things in the world) In this case, for our code
					 * it should work just fine
					 */
					if (x2.getBody().getUserData().equals("ball")) {
						player.increaseFootContacts();
					}
				}

				if (x1.getBody().getUserData().equals("bomb")
						&& x2.getBody().getUserData().equals("ball")) {
					//x2.getBody().setLinearVelocity(new Vector2(x2.getBody().getLinearVelocity().x, 12));
				}

/*				if (x1.getBody().getUserData().equals("platform2")
						&& x2.getBody().getUserData().equals("player")) {
					engine.registerUpdateHandler(new TimerHandler(0.2f,
							new ITimerCallback() {
								public void onTimePassed(
										final TimerHandler pTimerHandler) {
									pTimerHandler.reset();
									engine.unregisterUpdateHandler(pTimerHandler);
									x1.getBody().setType(BodyType.DynamicBody);
								}
							}));
				}*/
			}

			public void endContact(Contact contact) {
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();

				if (x1.getBody().getUserData() != null
						&& x2.getBody().getUserData() != null) {
					if (x2.getBody().getUserData().equals("ball")) {
						player.decreaseFootContacts();
					}
				}
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub

			}

		};
		return contactListener;
	}
}
