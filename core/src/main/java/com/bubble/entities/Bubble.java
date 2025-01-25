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
    private boolean growing; // wont need if we just call from player class


    public Bubble(World world, int id, float x, float y, MyTimer timer, MyResourceManager myResourceManager) {
        super(id, myResourceManager);
        this.timer = timer;
        this.world = world;
        this.growing = true;
        int timeToLive = 5;

        setAnimation(TextureRegion.split(resourceManager.getTexture("bubble"), 964, 980)[0], 1/5f, true, 0.02f);

        // Defining a body
        BodyDef bdef = new BodyDef();
        bdef = new BodyDef();
        bdef.position.set(x / Constants.PPM, y / Constants.PPM);
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        // Defining the shape of the fixture
        CircleShape circle = new CircleShape();
        circle.setRadius(this.width / Constants.PPM / 2);
        circle.setPosition(new Vector2(0, 0));

        // Defining a fixture
        FixtureDef fdef = new FixtureDef();
        fdef.shape = circle;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Constants.BIT_HAZARD;
        fdef.filter.maskBits = Constants.BIT_PLAYER | Constants.BIT_GROUND | Constants.BIT_HAZARD;
        b2body.createFixture(fdef).setUserData(this);

    }

    //should update size of the bubble either by going through a list or by drawing a bigger bubble
    public int gettingFat(int radius) {
        radius += 8;
        return radius;
    }

    public void iWantToBreakFree(Vector2 direction, int x, int y) {
        //
//        switch (movementState) {
//            case RIGHT:
//                b2body.applyLinearImpulse(new Vector2(0.5f, 0), b2body.getWorldCenter(), true);
//                break;
//            case LEFT:
//                b2body.applyLinearImpulse(new Vector2(-0.5f, 0), b2body.getWorldCenter(), true);
//                break;
//            case UP:
//                b2body.applyLinearImpulse(new Vector2(0.0f, 0.5f), b2body.getWorldCenter(), true);
//                break;
//            case DOWN:
//                b2body.applyLinearImpulse(new Vector2(0, -0.5f), b2body.getWorldCenter(), true);
//                break;
//            case UPRIGHT:
//                b2body.applyLinearImpulse(new Vector2(0.5f, 0.5f), b2body.getWorldCenter(), true);
//                break;
//            case UPLEFT:
//                b2body.applyLinearImpulse(new Vector2(-0.5f, 0.5f), b2body.getWorldCenter(), true);
//                break;
//            case DOWNRIGHT:
//                b2body.applyLinearImpulse(new Vector2(0.5f, -0.5f), b2body.getWorldCenter(), true);
//                break;
//            case DOWNLEFT:
//                b2body.applyLinearImpulse(new Vector2(-0.5f, -0.5f), b2body.getWorldCenter(), true);
//                break;
    }

    public void update(float delta) {
        animation.update(delta);
        super.update(delta);
        if (growing) {
            this.resize += Constants.BUBBLE_GR;
            if (this.resize >= 0.05f) {
                growing = false;
            }
        }
        this.width = animation.getFrame().getRegionWidth() * resize;
        this.height = animation.getFrame().getRegionHeight() * resize;;
    }

    public void notify(String flag) {
        switch (flag) {
            case "pop":
                break;
            case "merge":
                break;
        }
    }
}
