package com.bubble.entities;

import java.util.EnumSet;

import javax.swing.text.Utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bubble.helpers.Constants;
import com.bubble.helpers.Subscriber;
import com.bubble.tools.ColourGenerator;
import com.bubble.tools.MyResourceManager;
import com.bubble.tools.MyTimer;
import com.bubble.tools.UtilityStation;
import com.bubble.world.EntityHandler;

import box2dLight.PointLight;

public class Bubble extends Entity implements Subscriber {
    private final MyTimer timer;
    private final World world;
    private boolean growing; // wont need if we just call from player class
    private CircleShape circle;
    private FixtureDef fdef;
    private EnumSet<Constants.BSTATE> states;       // Set of player states
    private UtilityStation utilityStation;
    public Player creator;
    private Vector2 shootigDirection;
    private int bounceCounter;
    private PointLight light;
    private boolean merged;

    public Bubble(World world, int id, MyTimer timer, MyResourceManager myResourceManager, UtilityStation utilityStation, Player creator, ColourGenerator colourGenerator) {
        super(id, myResourceManager);
        this.timer = timer;
        this.world = world;
        this.growing = true;
        states = EnumSet.noneOf(Constants.BSTATE.class);
        this.utilityStation = utilityStation;
        this.creator = creator;
        this.shootigDirection = new Vector2(0,0);
        //time to live
        timer.start(10, "pop", this);

        bounceCounter = 0;

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

        light = utilityStation.getLightManager().addPointLight(b2body, 25, Constants.BIT_GROUND, new Color(colourGenerator.getCurrentColour().x / 255 ,colourGenerator.getCurrentColour().y / 255, colourGenerator.getCurrentColour().z / 255, 0.5f));
    }


    public void handlePosition() {
        if (creator == null) {return;}
        switch (creator.getCurrAState()) {
            case RUN_RIGHT:
            case IDLE_RIGHT:
                if (creator.facingRight) {
                    setPosition(creator.getPosition().x + this.width / (2 * Constants.PPM), creator.getPosition().y);
                    this.shootigDirection.x = Constants.BUBBLE_SPEED;
                    this.shootigDirection.y = 0;
                } else {
                    setPosition(creator.getPosition().x - this.width / (2 * Constants.PPM), creator.getPosition().y);
                    this.shootigDirection.x = -Constants.BUBBLE_SPEED;
                    this.shootigDirection.y = 0;
                }
                break;
            case RUN_DOWN:
            case IDLE_DOWN:
                setPosition(creator.getPosition().x, creator.getPosition().y - this.width / (2 * Constants.PPM));
                this.shootigDirection.x = 0;
                this.shootigDirection.y = -Constants.BUBBLE_SPEED;
                break;
            case RUN_UP:
            case IDLE_UP:
                setPosition(creator.getPosition().x, creator.getPosition().y + this.width / (2 * Constants.PPM));
                this.shootigDirection.x = 0;
                this.shootigDirection.y = Constants.BUBBLE_SPEED;
                break;
            case RUN_UP_RIGHT:
            case IDLE_UP_RIGHT:
                if (creator.facingRight) {
                    setPosition(creator.getPosition().x + this.width / (2 * Constants.PPM), creator.getPosition().y + this.width / (2 * Constants.PPM));
                    this.shootigDirection.x = Constants.BUBBLE_SPEED;
                    this.shootigDirection.y = Constants.BUBBLE_SPEED;
                } else {
                    setPosition(creator.getPosition().x - this.width / (2 * Constants.PPM), creator.getPosition().y + this.width / (2 * Constants.PPM));
                    this.shootigDirection.x = -Constants.BUBBLE_SPEED;
                    this.shootigDirection.y = Constants.BUBBLE_SPEED;
                }
                break;
            case RUN_DOWN_RIGHT:
            case IDLE_DOWN_RIGHT:
                if (creator.facingRight) {
                    setPosition(creator.getPosition().x + this.width / (2 * Constants.PPM), creator.getPosition().y - this.width / (2 * Constants.PPM));
                    this.shootigDirection.x = Constants.BUBBLE_SPEED;
                    this.shootigDirection.y = -Constants.BUBBLE_SPEED;
                } else {
                    setPosition(creator.getPosition().x - this.width / (2 * Constants.PPM), creator.getPosition().y - this.width / (2 * Constants.PPM));
                    this.shootigDirection.x = -Constants.BUBBLE_SPEED;
                    this.shootigDirection.y = -Constants.BUBBLE_SPEED;
                }
                break;
            default:
                break;
        }
    }

    public void release() {
        addState(Constants.BSTATE.FREE);
        FixtureDef sensor = new FixtureDef();

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width /2.5f / Constants.PPM, width/10/ Constants.PPM, new Vector2(0, width/2/ Constants.PPM), 0);
        fdef.shape = polygonShape;
        fdef.filter.categoryBits = Constants.BIT_BSENSOR;
        fdef.filter.maskBits = Constants.BIT_GROUND;
        b2body.createFixture(fdef).setUserData("vert");

        polygonShape.setAsBox(width /2.5f/ Constants.PPM, width/10/ Constants.PPM, new Vector2(0, -width/2/ Constants.PPM), 0);
        fdef.shape = polygonShape;
        fdef.filter.categoryBits = Constants.BIT_BSENSOR;
        fdef.filter.maskBits = Constants.BIT_GROUND;
        b2body.createFixture(fdef).setUserData("vert");

        polygonShape.setAsBox(width/10/ Constants.PPM, width /2.5f/ Constants.PPM, new Vector2(width/2/ Constants.PPM, 0), 0);
        fdef.shape = polygonShape;
        fdef.filter.categoryBits = Constants.BIT_BSENSOR;
        fdef.filter.maskBits = Constants.BIT_GROUND;
        b2body.createFixture(fdef).setUserData("hor");

        polygonShape.setAsBox(width/10/ Constants.PPM, width /2.5f/ Constants.PPM, new Vector2(-width/2/ Constants.PPM, 0), 0);
        fdef.shape = polygonShape;
        fdef.filter.categoryBits = Constants.BIT_BSENSOR;
        fdef.filter.maskBits = Constants.BIT_GROUND;
        b2body.createFixture(fdef).setUserData("hor");
    }


    public float damage() {
        float sizeFactor = Math.min(this.resize / 0.05f, 1.0f);
        float dmg = 0.5f + (sizeFactor * 9.5f);

        return Math.max(0.5f, Math.min(dmg, 10.0f));
    }

    public void pop() {
        if (!isStateActive(Constants.BSTATE.POPPED)) {
            states.add(Constants.BSTATE.POPPED);
            utilityStation.getEntityHandler().addEntityOperation(this, "pop");
        }
    }

    public void addState(Constants.BSTATE state) { states.add(state); }

    public boolean isStateActive(Constants.BSTATE state) { return states.contains(state); }

    @Override
    public void update(float delta) {
        animation.update(delta);
        super.update(delta);

        if (!states.contains(Constants.BSTATE.FREE)) {
            handlePosition();
            if (!states.contains(Constants.BSTATE.FULL)) {
                this.resize += Constants.BUBBLE_GR;

                this.width = animation.getFrame().getRegionWidth() * resize;
                this.height = animation.getFrame().getRegionHeight() * resize;

                circle.setRadius(this.width / Constants.PPM / 2);
                fdef.shape = circle;

                if (b2body.getFixtureList().size != 0) {
                    b2body.destroyFixture(b2body.getFixtureList().get(0));
                }
                b2body.createFixture(fdef).setUserData(this);

                if (this.resize >= 0.05f) {
                    states.add(Constants.BSTATE.FULL);
                    timer.start(Constants.TTP, "full", this);
                }
            }
        }
        else {
            if(merged){
                b2body.setLinearVelocity(this.shootigDirection.x, this.shootigDirection.y);
            }
            else{
                b2body.applyLinearImpulse(this.shootigDirection, b2body.getWorldCenter(), true);
            }
        }
    }

    public void die() {
        utilityStation.getParticleHandler().addParticleEffect("bubble", b2body.getPosition().x, b2body.getPosition().y, resize / 18);
        world.destroyBody(b2body);
    }

    public void bubbleMerge(Bubble incomingBubble) {
        if (Math.abs(incomingBubble.width - this.width) <= 2) { // checks within a range of 2 in size
            incomingBubble.pop();
            pop();
        }
        else{
            this.merged = true;
            Vector2 temVector = new Vector2();
            temVector = (incomingBubble.width > this.width) ?  incomingBubble.b2body.getLinearVelocity() : this.b2body.getLinearVelocity();
            System.out.println(shootigDirection + " " + temVector);
            // this.shootigDirection.scl(10);
            this.shootigDirection.add(temVector.scl(0.8f));
            System.out.println(shootigDirection);
            // this.shootigDirection.scl(-0.005f);
            // this.shootigDirection = this.b2body.getLinearVelocity();

            this.width += incomingBubble.width;
            this.height += incomingBubble.height;

            circle.setRadius(this.width / Constants.PPM / 2);
            fdef.shape = circle;

            incomingBubble.pop();
        }
    }

    public void bounce(boolean bbl){
        
        b2body.setLinearVelocity(0, 0);

        bounceCounter++;
        // this.merged = true;

        if (bounceCounter == 5) {
            pop();
            return;
        }
        if(bbl){// vertical
            shootigDirection.y = shootigDirection.y * (-1);
        }
        else { // horizontal
            shootigDirection.x = shootigDirection.x * (-1);
        }
        timer.start(0.15f, "bounce", this);
    }

    public void notify(String flag) {
        switch (flag) {
            case "pop":
                states.add(Constants.BSTATE.POPPING);
                this.pop();
                break;
            case "full":
                states.add(Constants.BSTATE.POPPING);
                this.pop();
                creator = null;
                break;
            case "bounce":
                states.remove(Constants.BSTATE.BOUNCING);
                break;
        }
    }
}
