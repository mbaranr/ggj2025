package com.bubble.listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.bubble.entities.Player;
import com.bubble.screens.ScreenManager;
import com.bubble.tools.MyResourceManager;
import com.bubble.Main;
import com.bubble.screens.GameScreen;
import com.bubble.helpers.Constants;
import com.bubble.world.EntityHandler;

// Game screen input processor
public class GameInputProcessor implements InputProcessor {

    private final Main game;
    private final ScreenManager screenManager;
    private final MyResourceManager resourceManager;
    private final Player p1;
    private final Player p2;



    public GameInputProcessor(Main game, ScreenManager screenManager, MyResourceManager resourceManager, EntityHandler entityHandler) {
        this.game = game;
        this.screenManager = screenManager;
        this.resourceManager = resourceManager;
        this.p1 = entityHandler.getPlayer1();
        this.p2 = entityHandler.getPlayer2();
    }


    // When a key is pressed
    @Override
    public boolean keyDown (int keycode) {

        // Input multiplexing
        if (!(game.getScreen() instanceof GameScreen)) return false;
        switch (keycode) {
            // Player 1 Movement
            case Input.Keys.D:
                p1.setMovementState(Constants.MSTATE.RIGHT);
                if (p1.getMovementState() == Constants.MSTATE.UP){p1.setMovementState(Constants.MSTATE.UPRIGHT);}// P1 move right
                break;
            case Input.Keys.A: // P1 move left
                p1.setMovementState(Constants.MSTATE.LEFT);
                break;
            case Input.Keys.W: // P1 move up
                p1.setMovementState(Constants.MSTATE.UP);
                break;
            case Input.Keys.S: // P1 move down
                p1.setMovementState(Constants.MSTATE.DOWN);
                break;

            // Player 2 Movement
            case Input.Keys.RIGHT: // P2 move right
                p2.setMovementState(Constants.MSTATE.RIGHT);
                break;
            case Input.Keys.LEFT: // P2 move left
                p2.setMovementState(Constants.MSTATE.LEFT);
                break;
            case Input.Keys.UP: // P2 move up
                p2.setMovementState(Constants.MSTATE.UP);
                break;
            case Input.Keys.DOWN: // P2 move down
                p2.setMovementState(Constants.MSTATE.DOWN);
                break;

            default:
                break;
        }



//         switch (keycode) {
//             case Input.Keys.RIGHT:
//             case Input.Keys.D:
//                 // Moving right
//                 character.setMovementState(Constants.MSTATE.RIGHT);
//                 break;
//             case Input.Keys.LEFT:
//             case Input.Keys.A:
//                 // Moving left
//                 character.setMovementState(Constants.MSTATE.LEFT);
//                 break;
//             case Input.Keys.SHIFT_LEFT:
//                 // Cycling characters
//                 if (characterCycle.cycleNext()) resourceManager.getSound("cycle").play(0.5f);
//                 // Making the previous character loose control
//                 character.looseControl();
//                 if (Gdx.input.isKeyPressed(Input.Keys.A)) characterCycle.getCurrentCharacter().setMovementState(Constants.MSTATE.LEFT);
//                 else if (Gdx.input.isKeyPressed(Input.Keys.D)) characterCycle.getCurrentCharacter().setMovementState(Constants.MSTATE.RIGHT);
//                 break;
//             case Input.Keys.J:
//                 // Attacking
//                 if (character instanceof ArmourGoblin) ((ArmourGoblin) character).attack();
//                 break;
//             case Input.Keys.X:
//                 // Interacting
//                 if (character instanceof Mage && ((Mage) character).getMerchantInRange() != null) {
//                     ((Mage) character).getMerchantInRange().interact();
//                 } else {
//                     character.interact();
//                 }
//                 break;
//             case Input.Keys.I:
//                 // Inventory
//                 character.setMovementState(MSTATE.STILL);
//                 game.hud.pushInventory();
//                 break;
//             case Input.Keys.ESCAPE:
//                 // Menu
//                 screenManager.pushScreen(Constants.SCREEN_OP.MENU, "none");
//                 break;
//             default:
//                 break;
//         }
        return true;
    }

    // When a key is released
    @Override
    public boolean keyUp (int keycode) {

        if (!(game.getScreen() instanceof GameScreen)) return false;
        switch (keycode) {
            // Player 1 Movement Stop
            case Input.Keys.D:
            case Input.Keys.A:
            case Input.Keys.W:
            case Input.Keys.S:
                p1.setMovementState(Constants.MSTATE.STILL);
                break;

            // Player 2 Movement Stop
            case Input.Keys.RIGHT:
            case Input.Keys.LEFT:
            case Input.Keys.UP:
            case Input.Keys.DOWN:
                p2.setMovementState(Constants.MSTATE.STILL);
                break;

            default:
                break;
        }

//         switch (keycode) {
//             case Input.Keys.SPACE:
//                 // Increasing gravity, makes movement smoother
//                 if (character.isStateActive(PSTATE.ON_GROUND) || character.isStateActive(PSTATE.STUNNED)) break;
//                 character.fall();
//                 break;
//             case Input.Keys.RIGHT:
//             case Input.Keys.D:
//                 // Stopping and immediately checking for input
//                 if (Gdx.input.isKeyPressed(Input.Keys.A)) character.setMovementState(Constants.MSTATE.LEFT);
//                 else character.setMovementState(Constants.MSTATE.STILL);
//                 break;
//             case Input.Keys.LEFT:
//             case Input.Keys.A:
//                 // Stopping and immediately checking for input
//                 if (Gdx.input.isKeyPressed(Input.Keys.D)) character.setMovementState(Constants.MSTATE.RIGHT);
//                 else character.setMovementState(Constants.MSTATE.STILL);
//                 break;
//             default:
//                 break;
//         }
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


