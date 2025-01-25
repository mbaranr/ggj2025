package com.bubble.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.bubble.entities.Player;
import com.bubble.screens.ScreenManager;
import com.bubble.tools.MyResourceManager;
import com.bubble.Main;
import com.bubble.entities.PlayableCharacter;
import com.bubble.screens.GameScreen;
import com.bubble.helpers.Constants;
import com.bubble.helpers.Constants.*;

// Game screen input processor
public class GameInputProcessor implements InputProcessor {

    private final Main game;
    private final ScreenManager screenManager;
    private final MyResourceManager resourceManager;

    public GameInputProcessor(Main game, ScreenManager screenManager, MyResourceManager resourceManager) {
        this.game = game;
        this.screenManager = screenManager;
        this.resourceManager = resourceManager;
    }

    // When a key is pressed
    @Override
    public boolean keyDown (int keycode) {

        // Input multiplexing
        if (!(game.getScreen() instanceof GameScreen)) return false;

        // switch (keycode) {
        //     case Input.Keys.RIGHT:
        //     case Input.Keys.D:
        //         // Moving right
        //         character.setMovementState(Constants.MSTATE.RIGHT);
        //         break;
        //     case Input.Keys.LEFT:
        //     case Input.Keys.A:
        //         // Moving left
        //         character.setMovementState(Constants.MSTATE.LEFT);
        //         break;
        //     case Input.Keys.SHIFT_LEFT:
        //         // Cycling characters
        //         if (characterCycle.cycleNext()) resourceManager.getSound("cycle").play(0.5f);
        //         // Making the previous character loose control
        //         character.looseControl();
        //         if (Gdx.input.isKeyPressed(Input.Keys.A)) characterCycle.getCurrentCharacter().setMovementState(Constants.MSTATE.LEFT);
        //         else if (Gdx.input.isKeyPressed(Input.Keys.D)) characterCycle.getCurrentCharacter().setMovementState(Constants.MSTATE.RIGHT);
        //         break;
        //     case Input.Keys.J:
        //         // Attacking
        //         if (character instanceof ArmourGoblin) ((ArmourGoblin) character).attack();
        //         break;
        //     case Input.Keys.X:
        //         // Interacting
        //         if (character instanceof Mage && ((Mage) character).getMerchantInRange() != null) {
        //             ((Mage) character).getMerchantInRange().interact();
        //         } else {
        //             character.interact();
        //         }
        //         break;
        //     case Input.Keys.I:
        //         // Inventory
        //         character.setMovementState(MSTATE.STILL);
        //         game.hud.pushInventory();
        //         break;
        //     case Input.Keys.ESCAPE:
        //         // Menu
        //         screenManager.pushScreen(Constants.SCREEN_OP.MENU, "none");
        //         break;
        //     default:
        //         break;
        // }
        return true;
    }

    // When a key is released
    @Override
    public boolean keyUp (int keycode) {

        if (!(game.getScreen() instanceof GameScreen)) return false;

        // switch (keycode) {
        //     case Input.Keys.SPACE:
        //         // Increasing gravity, makes movement smoother
        //         if (character.isStateActive(PSTATE.ON_GROUND) || character.isStateActive(PSTATE.STUNNED)) break;
        //         character.fall();
        //         break;
        //     case Input.Keys.RIGHT:
        //     case Input.Keys.D:
        //         // Stopping and immediately checking for input
        //         if (Gdx.input.isKeyPressed(Input.Keys.A)) character.setMovementState(Constants.MSTATE.LEFT);
        //         else character.setMovementState(Constants.MSTATE.STILL);
        //         break;
        //     case Input.Keys.LEFT:
        //     case Input.Keys.A:
        //         // Stopping and immediately checking for input
        //         if (Gdx.input.isKeyPressed(Input.Keys.D)) character.setMovementState(Constants.MSTATE.RIGHT);
        //         else character.setMovementState(Constants.MSTATE.STILL);
        //         break;
        //     default:
        //         break;
        // }
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


