package com.bubble.entities;

import java.util.EnumSet;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bubble.helpers.Constants;
import com.bubble.helpers.Subscriber;
import com.bubble.tools.MyResourceManager;
import com.bubble.tools.MyTimer;
import com.bubble.world.EntityHandler;

public class Bubble extends Entity implements Subscriber {
    private final MyTimer timer;
    private final World world;
    private boolean growing; // wont need if we just call from player class
    private CircleShape circle;
    private FixtureDef fdef;
    private EnumSet<Constants.BSTATE> states;       // Set of player states
    private EntityHandler entityHandler;
    private Player creator;

    public Bubble(World world, int id, MyTimer timer, MyResourceManager myResourceManager, EntityHandler entityHandler, Player creator) {
        super(id, myResourceManager);
        this.timer = timer;
        this.world = world;
        this.growing = true;
        states = EnumSet.noneOf(Constants.BSTATE.class);
        this.entityHandler = entityHandler;
        this.creator = creator;

        setAnimation(TextureRegion.split(resourceManager.getTexture("bubble"), 964, 980)[0], 1/5f, true, 0f);

        // Defining a body
        BodyDef bdef = new BodyDef();
        bdef = new BodyDef();
        bdef.position.set(creator.getPosition().x, creator.getPosition().y);
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        // Defining the shape of the fixture
        circle = new CircleShape();
        circle.setRadius(this.width / Constants.PPM / 2);
        circle.setPosition(new Vector2(0, 0));

        // Defining a fixture
        fdef = new FixtureDef();
        fdef.shape = circle;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Constants.BIT_HAZARD;
        fdef.filter.maskBits = Constants.BIT_PLAYER | Constants.BIT_GROUND | Constants.BIT_HAZARD;
        b2body.createFixture(fdef).setUserData(this);

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

    public void handlePosition() {
        switch (creator.getCurrAState()) {
            case RUN_RIGHT:
            case IDLE_RIGHT:
                if (creator.facingRight) {
                    setPosition(creator.getPosition().x + 32 / Constants.PPM, creator.getPosition().y);
                } else {
                    setPosition(creator.getPosition().x - 32 / Constants.PPM, creator.getPosition().y);
                }
                break;
            case RUN_DOWN:
            case IDLE_DOWN:
                setPosition(creator.getPosition().x, creator.getPosition().y - 32 / Constants.PPM);
                break;
            case RUN_UP:
            case IDLE_UP:
                setPosition(creator.getPosition().x, creator.getPosition().y + 32 / Constants.PPM);
                break;
            case RUN_UP_RIGHT:
            case IDLE_UP_RIGHT:
                if (creator.facingRight) {
                    setPosition(creator.getPosition().x + 32 / Constants.PPM, creator.getPosition().y + 32 / Constants.PPM);
                } else {
                    setPosition(creator.getPosition().x - 32 / Constants.PPM, creator.getPosition().y + 32 / Constants.PPM);
                }
                break;
            case RUN_DOWN_RIGHT:
            case IDLE_DOWN_RIGHT:
                if (creator.facingRight) {
                    setPosition(creator.getPosition().x + 32 / Constants.PPM, creator.getPosition().y - 32 / Constants.PPM);
                } else {
                    setPosition(creator.getPosition().x - 32 / Constants.PPM, creator.getPosition().y - 32 / Constants.PPM);
                }
                break;
            default:
                break;
        }
    }

    public void pop() {
        world.destroyBody(b2body);
    }

    public boolean isStateActive(Constants.BSTATE state) { return states.contains(state); }

    @Override
    public void update(float delta) {
        animation.update(delta);
        super.update(delta);

        if (!states.contains(Constants.BSTATE.FREE)) {
            handlePosition();
        }

        if (growing) {
            this.resize += Constants.BUBBLE_GR;

            this.width = animation.getFrame().getRegionWidth() * resize;
            this.height = animation.getFrame().getRegionHeight() * resize;

            circle.setRadius(this.width / Constants.PPM / 2);
            fdef.shape = circle;

            b2body.destroyFixture(b2body.getFixtureList().get(0));
            b2body.createFixture(fdef).setUserData(this);

            if (this.resize >= 0.05f) {
                growing = false;
                states.add(Constants.BSTATE.FULL);
                timer.start(Constants.TTP, "pop", this);
            }
        }
    }

    public void notify(String flag) {
        switch (flag) {
            case "pop":
                states.add(Constants.BSTATE.POPPING);
                this.pop();
                entityHandler.addEntityOperation(this, "pop");
                break;
            case "merge":
                break;
        }
    }
}
