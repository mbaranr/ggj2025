package com.bubble.listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.World;
import com.bubble.entities.Bubble;
import com.bubble.entities.Player;
import com.bubble.screens.ScreenManager;
import com.bubble.tools.ColourGenerator;
import com.bubble.tools.MyResourceManager;
import com.bubble.tools.MyTimer;
import com.bubble.tools.UtilityStation;
import com.bubble.Main;
import com.bubble.screens.GameScreen;
import com.bubble.helpers.Constants;
import com.bubble.world.EntityHandler;
import java.util.concurrent.atomic.AtomicInteger;

// Game screen input processor
public class GameInputProcessor implements InputProcessor {

    private static final AtomicInteger idCounter = new AtomicInteger(1); // Static ID counter for all bubbles
    private final Main game;
    private final ScreenManager screenManager;
    private final MyResourceManager resourceManager;
    private final Player p1;
    private final Player p2;
    private final UtilityStation utilityStation;
    private final World world;
    private final MyTimer timer;
    private Bubble p1b;
    private final ColourGenerator colourGenerator;
    private Bubble p2b;


    public GameInputProcessor(Main game, ScreenManager screenManager, MyResourceManager resourceManager, UtilityStation utilityStation, ColourGenerator colourGenerator, World world, MyTimer timer) {
        this.game = game;
        this.screenManager = screenManager;
        this.resourceManager = resourceManager;
        this.utilityStation = utilityStation;
        this.p1 = utilityStation.getEntityHandler().getPlayer1();
        this.p2 = utilityStation.getEntityHandler().getPlayer2();
        this.world = world;
        this.timer = timer;
        this.p1b = null;
        this.p2b = null;
        this.colourGenerator = colourGenerator;
    }


    // When a key is pressed
    @Override
    public boolean keyDown (int keycode) {

        // Input multiplexing
        if (!(game.getScreen() instanceof GameScreen)) return false;
        switch (keycode) {
            // Player 1 Movement
            case Input.Keys.D:
                p1.addMovementState(Constants.MSTATE.RIGHT);
                break;
            case Input.Keys.A: // P1 move left
                p1.addMovementState(Constants.MSTATE.LEFT);
                break;
            case Input.Keys.W: // P1 move up
                p1.addMovementState(Constants.MSTATE.UP);
                break;
            case Input.Keys.S: // P1 move down
                p1.addMovementState(Constants.MSTATE.DOWN);
                break;
            case Input.Keys.SPACE:
                colourGenerator.getNextColor();
                p1b = new Bubble(world, idCounter.incrementAndGet(), timer, resourceManager, utilityStation, p1, colourGenerator);
                utilityStation.getEntityHandler().addEntity(p1b);
                break;

            // Player 2 Movement
            case Input.Keys.RIGHT: // P2 move right
                p2.addMovementState(Constants.MSTATE.RIGHT);
                break;
            case Input.Keys.LEFT: // P2 move left
                p2.addMovementState(Constants.MSTATE.LEFT);
                break;
            case Input.Keys.UP: // P2 move up
                p2.addMovementState(Constants.MSTATE.UP);
                break;
            case Input.Keys.DOWN: // P2 move down
                p2.addMovementState(Constants.MSTATE.DOWN);
                break;
            case Input.Keys.ENTER:
                colourGenerator.getNextColor();
                p2b = new Bubble(world, idCounter.incrementAndGet(), timer, resourceManager, utilityStation, p2, colourGenerator);
                utilityStation.getEntityHandler().addEntity(p2b);
                break;
            default:
                break;
        }
        return true;
    }

    // When a key is released
    @Override
    public boolean keyUp (int keycode) {

        if (!(game.getScreen() instanceof GameScreen)) return false;
        switch (keycode) {
            // Player 1 Movement Stop
            case Input.Keys.D:
                p1.removeMovementState(Constants.MSTATE.RIGHT);
                break;
            case Input.Keys.A:
                p1.removeMovementState(Constants.MSTATE.LEFT);
                break;
            case Input.Keys.W:
                p1.removeMovementState(Constants.MSTATE.UP);
                    break;
            case Input.Keys.S:
                p1.removeMovementState(Constants.MSTATE.DOWN);
                break;
            case Input.Keys.SPACE:
                if (p1b != null) {
                    p1b.release();
                }
                break;

            // Player 2 Movement Stop
            case Input.Keys.RIGHT:
                p2.removeMovementState(Constants.MSTATE.RIGHT);
                    break;
            case Input.Keys.LEFT:
                p2.removeMovementState(Constants.MSTATE.LEFT);
                break;
            case Input.Keys.UP:
                p2.removeMovementState(Constants.MSTATE.UP);
                break;
            case Input.Keys.DOWN:
                p2.removeMovementState(Constants.MSTATE.DOWN);
                break;
            case Input.Keys.ENTER:
                if (p2b != null) {
                    p2b.release();
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped (char character) {
        return false;
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }
    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved (int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled (float amountX, float amountY) {
        return false;
    }

}


