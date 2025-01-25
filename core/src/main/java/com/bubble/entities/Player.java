package com.bubble.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bubble.objects.Interactable;
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
    protected int floorContacts; // Number of contacts with the floor to avoid anomalies
    protected int airIterations;
    protected UtilityStation util;
    private LinkedList<Interactable> interactablesInRange;

    public Player(float x, float y,World world, int id, MyTimer timer, MyResourceManager myResourceManager, UtilityStation util) {

        super(id, myResourceManager);
        this.timer = timer;
        this.world = world;
        this.util = util;

        if (id == 1){
            setAnimation(TextureRegion.split(resourceManager.getTexture("p1"), 20, 20)[0], 1/5f, false, 1);
        } else if (id == 2){setAnimation(TextureRegion.split(resourceManager.getTexture("p2"), 20, 20)[0], 1/5f, false, 1);}


        lives = 3;
        interactablesInRange = new LinkedList<>();

        // Initializing states
        playerStates = EnumSet.noneOf(Constants.PSTATE.class);
        addPlayerState(Constants.PSTATE.ON_GROUND);
        currAState = Constants.ASTATE.IDLE;
        prevAState = Constants.ASTATE.IDLE;
        movementStates = new LinkedList<>();

        wallState = 0;
        floorContacts = 0;
        airIterations = 0;

        BodyDef bdef = new BodyDef();
        bdef.position.set(x / Constants.PPM, y / Constants.PPM);
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
    }

    public void update(float delta) {

        // Capping y velocity

        if (isStateActive(Constants.PSTATE.DYING)) movementStates.clear();
        else if (isStateActive(Constants.PSTATE.STUNNED)) movementStates.clear();
        handleMovement();

        // Animation priority

//        if (isStateActive(Constants.PSTATE.DYING)) {
//          currAState = Constants.ASTATE.DEATH;
//        } else if (isStateActive(Constants.PSTATE.ATTACKING)) {
//            currAState = Constants.ASTATE.ATTACK;
//        } else if (airIterations >= 5) {
//            if (isFalling()) {
//                currAState = Constants.ASTATE.FALL;
//                b2body.setLinearDamping(0);
//            } else {
//                currAState = Constants.ASTATE.JUMP;
//            }
//        }

        if (currAState != prevAState) {
            handleAnimation();
            prevAState = currAState;
        }

        // Update the animation
        animation.update(delta);
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
                currAState = Constants.ASTATE.RUN;
                facingRight = false;
                if (prevState == null || prevState == Constants.MSTATE.RIGHT) { moveLeft(); }
                if (prevState == Constants.MSTATE.UP) { moveUpLeft(); }
                if (prevState == Constants.MSTATE.DOWN) { moveDownLeft(); }
                break;

            case RIGHT:
                currAState = Constants.ASTATE.RUN;
                facingRight = false;
                if (prevState == null || prevState == Constants.MSTATE.LEFT) { moveRight(); }
                if (prevState == Constants.MSTATE.UP) { moveUpRight(); }
                if (prevState == Constants.MSTATE.DOWN) { moveDownRight(); }
                break;

            case UP:
                currAState = Constants.ASTATE.RUN;
                facingRight = false;
                if (prevState == null || prevState == Constants.MSTATE.DOWN) { moveUp(); }
                if (prevState == Constants.MSTATE.RIGHT) { moveUpRight(); }
                if (prevState == Constants.MSTATE.LEFT) { moveUpLeft(); }
                break;

            case DOWN:
                currAState = Constants.ASTATE.RUN;
                facingRight = false;
                if (prevState == null || prevState == Constants.MSTATE.UP) { moveDown(); }
                if (prevState == Constants.MSTATE.RIGHT) { moveDownRight(); }
                if (prevState == Constants.MSTATE.LEFT) { moveDownLeft(); }
                break;
        }
    }

    public void moveRight() {
        //Initial acceleration
        if (b2body.getLinearVelocity().x == 0) b2body.applyLinearImpulse(new Vector2(0.5f, 0), b2body.getWorldCenter(), true);
        else b2body.setLinearVelocity(Constants.MAX_SPEED, b2body.getLinearVelocity().y);
    }

    public void moveLeft() {
        //Initial acceleration
        if (b2body.getLinearVelocity().x == 0) b2body.applyLinearImpulse(new Vector2(-0.5f, 0), b2body.getWorldCenter(), true);
        else b2body.setLinearVelocity(-Constants.MAX_SPEED, b2body.getLinearVelocity().y);
    }

    public void moveUp() {
        //Initial acceleration
        if (b2body.getLinearVelocity().y == 0) b2body.applyLinearImpulse(new Vector2(0, 0.5f), b2body.getWorldCenter(), true);
        else b2body.setLinearVelocity(b2body.getLinearVelocity().x, Constants.MAX_SPEED);
    }

    public void moveDown() {
        //Initial acceleration
        if (b2body.getLinearVelocity().y == 0) b2body.applyLinearImpulse(new Vector2(0, -0.5f), b2body.getWorldCenter(), true);
        else b2body.setLinearVelocity(b2body.getLinearVelocity().x, -Constants.MAX_SPEED);
    }

    public void moveUpRight() {
        //Initial acceleration
        if (b2body.getLinearVelocity().x == 0 && b2body.getLinearVelocity().y == 0) b2body.applyLinearImpulse(new Vector2(0.5f, 0.5f), b2body.getWorldCenter(), true);
        else b2body.setLinearVelocity((float)(Constants.MAX_SPEED / Math.sqrt(2)), (float)(Constants.MAX_SPEED / Math.sqrt(2)));
    }

    public void moveUpLeft() {
        //Initial acceleration
        if (b2body.getLinearVelocity().x == 0 && b2body.getLinearVelocity().y == 0) b2body.applyLinearImpulse(new Vector2(-0.5f, 0.5f), b2body.getWorldCenter(), true);
        else b2body.setLinearVelocity((float)(-Constants.MAX_SPEED / Math.sqrt(2)), (float)(Constants.MAX_SPEED / Math.sqrt(2)));
    }

    public void moveDownRight() {
        //Initial acceleration
        if (b2body.getLinearVelocity().x == 0 && b2body.getLinearVelocity().y == 0) b2body.applyLinearImpulse(new Vector2(0.5f, -0.5f), b2body.getWorldCenter(), true);
        else b2body.setLinearVelocity((float)(Constants.MAX_SPEED / Math.sqrt(2)), (float)(-Constants.MAX_SPEED / Math.sqrt(2)));
    }

    public void moveDownLeft() {
        //Initial acceleration
        if (b2body.getLinearVelocity().x == 0 && b2body.getLinearVelocity().y == 0) b2body.applyLinearImpulse(new Vector2(-0.5f, -0.5f), b2body.getWorldCenter(), true);
        else b2body.setLinearVelocity((float)(-Constants.MAX_SPEED / Math.sqrt(2)), (float)(-Constants.MAX_SPEED / Math.sqrt(2)));
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
            case "hit":
                removePlayerState(Constants.PSTATE.HIT);
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
        lives--;
        timer.start(0.05f, "hit", this);
        addPlayerState(Constants.PSTATE.HIT);
        if (lives == 0 && !isStateActive(Constants.PSTATE.DYING)) {
            timer.start(2f, "death_and_disposal", this);
            addPlayerState(Constants.PSTATE.DYING);
        }
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

    public void dispose() {
        for (Fixture fixture : b2body.getFixtureList()) {
            b2body.destroyFixture(fixture);
        }
        world.destroyBody(b2body);
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
