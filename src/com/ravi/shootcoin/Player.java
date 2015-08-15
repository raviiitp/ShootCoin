package com.ravi.shootcoin;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public abstract  class Player extends AnimatedSprite{

	private Body body;
	private boolean canFall = false;
	
    public Player(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld)
    {
        super(pX, pY, ResourcesManager.getInstance().ball_region, vbo);
        createPhysics(camera, physicsWorld);
        //camera.setChaseEntity(this);
    }

    public abstract void onDie();
    
    private void createPhysics(final Camera camera, PhysicsWorld physicsWorld)
    {        
        body = PhysicsFactory.createCircleBody(physicsWorld, this, BodyType.StaticBody, PhysicsFactory.createFixtureDef(5f, 1f, 0.5f));
        body.setUserData("ball");
        body.setFixedRotation(false);
        body.setAngularVelocity(ROTATION_DEFAULT);
        MassData data =  body.getMassData();
        data.mass = 10f;
        body.setMassData(data);
        
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, true)
        {
            @Override
            public void onUpdate(float pSecondsElapsed)
            {
                super.onUpdate(pSecondsElapsed);
                camera.onUpdate(0.1f);
                
                if (getY() <= 0)
                {                    
                    onDie();
                }
                
                if (canFall)
                {    
                    //body.setLinearVelocity(new Vector2(5, body.getLinearVelocity().y));
                	
                }
            }
        });
    }
    
    public void setFalling()
    {
        canFall = true;
        body.setType(BodyType.DynamicBody);
        //final long[] PLAYER_ANIMATE = new long[] { 100, 100, 100 };
            
       // animate(PLAYER_ANIMATE, 0, 2, true);
    }
    
    public void jump()
    {
        if (footContacts < 1) 
        {
            return; 
        }
        //body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, 12)); 
    }

    private int footContacts = 0;
    
    public void increaseFootContacts()
    {
        footContacts++;
    }

    public void decreaseFootContacts()
    {
        footContacts--;
    }
 
}

