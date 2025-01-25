package com.bubble.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bubble.helpers.Constants;
import com.bubble.helpers.Subscriber;
import com.bubble.tools.MyResourceManager;
import com.bubble.tools.MyTimer;

public class Bubble extends Entity implements Subscriber {
    protected final MyTimer timer;
    protected final World world;
    protected int bubbleSize;
    private boolean gettingFat; // wont need if we just call from player class



    public Bubble(World world, int id, float x, float y, MyTimer timer, MyResourceManager myResourceManager) {
        super(id, myResourceManager);
        this.timer = timer;
        this.world = world;
        this.bubbleSize = 1;
        this.gettingFat = true;
//        int timeToLive = 5;

        // Defining a body
        BodyDef bdef = new BodyDef();
        bdef = new BodyDef();
        bdef.position.set(x / Constants.PPM, y / Constants.PPM);
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        // Defining the shape of the fixture
        CircleShape circle = new CircleShape();
        circle.setRadius(16 / Constants.PPM);
        circle.setPosition(new Vector2(16 / Constants.PPM, 16 / Constants.PPM));

        // Defining a fixture
        FixtureDef fdef = new FixtureDef();
        fdef.shape = circle;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Constants.BIT_GROUND;
        fdef.filter.maskBits = Constants.BIT_PlAYER;
        b2body.createFixture(fdef).setUserData(this);

    }

    //should update size of the bubble either by going through a list or by drawing a bigger bubble
    public int gettingFat(int radius){ radius += 8; return radius;}

    public void iWantToBreakFree(Vector2 direction, int x, int y){

    }


    public void notify(String flag){

    }
}
