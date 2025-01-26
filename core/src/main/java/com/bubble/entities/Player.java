package com.bubble.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bubble.objects.Interactable;
import com.bubble.screens.ScreenManager;
import com.bubble.tools.MyResourceManager;
import com.bubble.tools.MyTimer;
import com.bubble.helpers.Subscriber;
import com.bubble.helpers.Constants;
import java.util.EnumSet;
import com.bubble.tools.UtilityStation;
import java.util.LinkedList;

public class Player extends Entity implements Subscriber {
    protected final MyTimer timer;
    protected final World world;
    protected int wallState;  // -1 for left, 1 for right, 0 for none
    protected final LinkedList<Constants.MSTATE> movementStates;
    protected Constants.ASTATE currAState;     // Current animation state
    protected Constants.ASTATE prevAState;     // Previous animation state
    protected final EnumSet<Constants.PSTATE> playerStates;       // Set of player states
    protected UtilityStation util;
    private LinkedList<Interactable> interactablesInRange;
    public boolean dead;
    private ScreenManager screenManager;
    private float currentHealth = 100; // Current health

    public Player(float x, float y,World world, int id, MyTimer timer, MyResourceManager myResourceManager, UtilityStation util, ScreenManager screenManager) {

        super(id, myResourceManager);
        this.timer = timer;
        this.world = world;
        this.util = util;
        this.screenManager = screenManager;
        dead = false;

        setAnimation(TextureRegion.split(resourceManager.getTexture((id == 2) ? "idle2" : "idle"), 32, 32)[0], 1/5f, false, 1);

        lives = 3;
        interactablesInRange = new LinkedList<>();

        // Initializing states
        playerStates = EnumSet.noneOf(Constants.PSTATE.class);
        addPlayerState(Constants.PSTATE.ON_GROUND);
        currAState = Constants.ASTATE.IDLE_DOWN;
        prevAState = Constants.ASTATE.IDLE_DOWN;
        movementStates = new LinkedList<>();

        BodyDef bdef = new BodyDef();
        bdef.position.set(x / Constants.PPM, y / Constants.PPM);
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(5f / Constants.PPM, 8 / Constants.PPM, new Vector2(0, 0), 0);
        fdef.shape = polygonShape;
        fdef.isSensor = false;
        fdef.filter.categoryBits = Constants.BIT_PLAYER;
        fdef.filter.maskBits = Constants.BIT_PLAYER | Constants.BIT_GROUND | Constants.BIT_HAZARD;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float delta) {

        // Update the animation
        animation.update(delta);

        if (currentHealth <= 0) {
            setAnimation(TextureRegion.split(resourceManager.getTexture((ID == 2) ? "die2" : "die"), 32, 32)[0], 1/5f, true, 1);
            b2body.setLinearVelocity(0, 0);
            return;
        }

        // Capping y velocity
        if (isStateActive(Constants.PSTATE.DYING)) movementStates.clear();
        else if (isStateActive(Constants.PSTATE.STUNNED)) movementStates.clear();
        handleMovement();

        if (movementStates.size() == 0) {
            switch (currAState) {
                case RUN_DOWN:
                    currAState = Constants.ASTATE.IDLE_DOWN;
                    break;
                case RUN_UP:
                    currAState = Constants.ASTATE.IDLE_UP;
                    break;
                case RUN_UP_RIGHT:
                    currAState = Constants.ASTATE.IDLE_UP_RIGHT;
                    break;
                case RUN_DOWN_RIGHT:
                    currAState = Constants.ASTATE.IDLE_DOWN_RIGHT;
                    break;
                case RUN_RIGHT:
                    currAState = Constants.ASTATE.IDLE_RIGHT;
                    break;
                default:
                    break;
            }
        }
        if (currAState != prevAState) {
            handleAnimation();
            prevAState = currAState;
        }
    }

    public void handleMovement() {

        if (movementStates.size() == 0) { b2body.setLinearVelocity(0, 0); return; }

        int size = movementStates.size();
        Constants.MSTATE state = movementStates.get(size - 1);
        Constants.MSTATE prevState;
        if (size == 1) {
            prevState = null;
        } else {
            prevState = movementStates.get(size - 2);
        }

        switch (state) {
            case LEFT:
                facingRight = false;
                if (prevState == null || prevState == Constants.MSTATE.RIGHT) { moveLeft(); }
                if (prevState == Constants.MSTATE.UP) { moveUpLeft(); }
                if (prevState == Constants.MSTATE.DOWN) { moveDownLeft(); }
                break;
            case RIGHT:
                facingRight = true;
                if (prevState == null || prevState == Constants.MSTATE.LEFT) { moveRight(); }
                if (prevState == Constants.MSTATE.UP) { moveUpRight(); }
                if (prevState == Constants.MSTATE.DOWN) { moveDownRight(); }
                break;
            case UP:
                if (prevState == null || prevState == Constants.MSTATE.DOWN) { moveUp(); }
                if (prevState == Constants.MSTATE.RIGHT) { moveUpRight(); }
                if (prevState == Constants.MSTATE.LEFT) { moveUpLeft(); }
                break;
            case DOWN:
                if (prevState == null || prevState == Constants.MSTATE.UP) { moveDown(); }
                if (prevState == Constants.MSTATE.RIGHT) { moveDownRight(); }
                if (prevState == Constants.MSTATE.LEFT) { moveDownLeft(); }
                break;
        }
    }

    public void handleAnimation() {
        switch (currAState) {
            case RUN_DOWN:
                setAnimation(TextureRegion.split(resourceManager.getTexture((ID == 2) ? "run_down2" : "run_down"), 32, 32)[0], 1/5f, false, 1);
                break;
            case RUN_UP:
                setAnimation(TextureRegion.split(resourceManager.getTexture((ID == 2) ? "run_up2" : "run_up"), 32, 32)[0], 1/5f, false, 1);
                break;
            case RUN_RIGHT:
                setAnimation(TextureRegion.split(resourceManager.getTexture((ID == 2) ? "run_right2" : "run_right"), 32, 32)[0], 1/5f, false, 1);
                break;
            case RUN_UP_RIGHT:
                setAnimation(TextureRegion.split(resourceManager.getTexture((ID == 2) ? "run_up_right2" : "run_up_right"), 32, 32)[0], 1/5f, false, 1);
                break;
            case RUN_DOWN_RIGHT:
                setAnimation(TextureRegion.split(resourceManager.getTexture((ID == 2) ? "run_down_right2" : "run_down_right"), 32, 32)[0], 1/5f, false, 1);
                break;
            case IDLE_DOWN:
            case IDLE_RIGHT:
            case IDLE_UP:
            case IDLE_DOWN_RIGHT:
            case IDLE_UP_RIGHT:
                setAnimation(TextureRegion.split(resourceManager.getTexture((ID == 2) ? "idle2" : "idle"), 32, 32)[0], 1/5f, false, 1);
            default:
                break;
        }
    }

    public void moveRight() {
        //Initial acceleration
        currAState = Constants.ASTATE.RUN_RIGHT;
        b2body.setLinearVelocity(Constants.MAX_SPEED, 0);
    }

    public void moveLeft() {
        //Initial acceleration
        currAState = Constants.ASTATE.RUN_RIGHT;
        b2body.setLinearVelocity(-Constants.MAX_SPEED, 0);
    }

    public void moveUp() {
        //Initial acceleration
        currAState = Constants.ASTATE.RUN_UP;
        b2body.setLinearVelocity(0, Constants.MAX_SPEED);
    }

    public void moveDown() {
        //Initial acceleration
        currAState = Constants.ASTATE.RUN_DOWN;
        b2body.setLinearVelocity(0, -Constants.MAX_SPEED);
    }

    public void moveUpRight() {
        //Initial acceleration
        currAState = Constants.ASTATE.RUN_UP_RIGHT;
        b2body.setLinearVelocity((float)(Constants.MAX_SPEED / Math.sqrt(2)), (float)(Constants.MAX_SPEED / Math.sqrt(2)));
    }

    public void moveUpLeft() {
        //Initial acceleration
        currAState = Constants.ASTATE.RUN_UP_RIGHT;
        b2body.setLinearVelocity((float)(-Constants.MAX_SPEED / Math.sqrt(2)), (float)(Constants.MAX_SPEED / Math.sqrt(2)));
    }

    public void moveDownRight() {
        //Initial acceleration
        currAState = Constants.ASTATE.RUN_DOWN_RIGHT;
        b2body.setLinearVelocity((float)(Constants.MAX_SPEED / Math.sqrt(2)), (float)(-Constants.MAX_SPEED / Math.sqrt(2)));
    }

    public void moveDownLeft() {
        //Initial acceleration
        currAState = Constants.ASTATE.RUN_DOWN_RIGHT;
        b2body.setLinearVelocity((float)(-Constants.MAX_SPEED / Math.sqrt(2)), (float)(-Constants.MAX_SPEED / Math.sqrt(2)));
    }

    @Override
    public void notify(String flag) {
        switch (flag) {
            case "stun":
                break;
            case "land":
                removePlayerState(Constants.PSTATE.LANDING);
                break;
            case "stop":
                // movementState = Constants.MSTATE.STILL;
                break;
            case "reset":
                screenManager.pushScreen(Constants.SCREEN_OP.GAME, "");
                break;
            case "death_and_disposal":
                dispose();
                util.getEntityHandler().addEntityOperation(this, "die");
                break;
            case "death":
                util.getEntityHandler().addEntityOperation(this, "die");
                break;
        }
    }


    public void stun(float seconds) {
        addPlayerState(Constants.PSTATE.STUNNED);
        timer.start(seconds, "stun", this);
    }

    @Override
    public void die() {
        
        timer.start(5, "reset", this);
    }

    public void addInteractable(Interactable interactable) {
        interactablesInRange.add(interactable);
    }

    public void removeInteractable(Interactable interactable) {
        interactablesInRange.remove(interactable);
    }


    public void interact() {
        for (Interactable interactable : interactablesInRange) {
            interactable.interact();
        }
    }

    public boolean isFalling() { return b2body.getLinearVelocity().y < 0; } // REMOVE THIS

    public void setWallState(int wallState) { this.wallState = wallState; } // REMOVE THIS

    public int getWallState() { return wallState; }

    public void addMovementState(Constants.MSTATE direction) { this.movementStates.add(direction); }

    public void removeMovementState(Constants.MSTATE direction) { this.movementStates.remove(direction); }

    public void addPlayerState(Constants.PSTATE state) { playerStates.add(state); }

    public void removePlayerState(Constants.PSTATE state) { playerStates.remove(state); }

    public boolean isStateActive(Constants.PSTATE state) { return playerStates.contains(state); }

    public Constants.ASTATE getCurrAState() { return currAState; }

    public void dispose() {
        for (Fixture fixture : b2body.getFixtureList()) {
            b2body.destroyFixture(fixture);
        }
        world.destroyBody(b2body);
    }

    public void takeDamage(float damage) {
        currentHealth = Math.max(currentHealth - damage, 0);
    }

    public float getHealth() {
        return currentHealth;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

}
