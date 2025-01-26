package com.bubble.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.bubble.Main;
import com.bubble.audio.MusicManager;
import com.bubble.graphics.SlidingTransition;
import com.bubble.graphics.Transition;
import com.bubble.helpers.Constants;
import com.bubble.tools.MyResourceManager;
import java.util.LinkedList;
import java.util.Queue;

public final class ScreenManager {
    private final Main game;
    private final MyResourceManager resourceManager;
    private final Queue<Transition> transitionQueue;
    private ManagedScreen prevScreen;
    private ManagedScreen currScreen;
    private int levelProgression;
    private final FrameBuffer fb;
    private final MusicManager musicManager;

    public ScreenManager(Main game, MyResourceManager resourceManager, MusicManager musicManager) {
        this.game = game;
        this.resourceManager = resourceManager;
        this.currScreen = null;
        transitionQueue = new LinkedList<>();
        fb = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        levelProgression = 1;           //set levels completed to level 1
        this.musicManager = musicManager;

        musicManager.play("music/start_theme.mp3");  //play background music
    }

    public void pushScreen(Constants.SCREEN_OP screenType, String flag) {
        //method to change screen with flag (if the transition has been performed or not)
        handleTransition(flag);

        // Dispose resources of the previous screen if it exists
        if(prevScreen != null) {
            prevScreen.dispose();
        }

        // Set previous screen to current
        if (currScreen != null) {
            prevScreen = currScreen;
        }

        // Set the new screen
        switch (screenType) {
            case START: //load start screen
                currScreen = new StartScreen(game, resourceManager, this);
                break;
            case GAME:   //load level 1
                musicManager.stop();
                musicManager.play("music/main_theme.mp3");

                currScreen = new GameScreen(game, resourceManager, this, musicManager);
                break;
            case EXIT:  //dispose all resources and exit the application
                resourceManager.disposeAll();   //state will dispose all resources
                Gdx.app.exit();
                break;
            default:
                break;
        }

        game.setScreen(currScreen); //set screen to new screen
    }

    public void handleTransition(String flag) {
        //method to apply transitions
        switch (flag){
            case "slide_up":
                transitionQueue.add(new SlidingTransition(new TextureRegion(currScreen.screenToTexture(fb)), 3, 80, Constants.SLIDE_DIR.SLIDE_UP, currScreen.getProjectionMatrix()));
                break;
            case "slide_down":
                transitionQueue.add(new SlidingTransition(new TextureRegion(currScreen.screenToTexture(fb)), 3, 80, Constants.SLIDE_DIR.SLIDE_DOWN, currScreen.getProjectionMatrix()));
                break;
            case "slide_left":
                transitionQueue.add(new SlidingTransition(new TextureRegion(currScreen.screenToTexture(fb)), 3, 80, Constants.SLIDE_DIR.SLIDE_LEFT, currScreen.getProjectionMatrix()));
                break;
            case "slide_right":
                transitionQueue.add(new SlidingTransition(new TextureRegion(currScreen.screenToTexture(fb)), 3, 80, Constants.SLIDE_DIR.SLIDE_RIGHT, currScreen.getProjectionMatrix()));
                break;
            default:
                break;
        }
    }

    public void render(float delta) {
        //method to keep track which transitions have been performed and have to be removed
        LinkedList<Transition> toRemove = new LinkedList<>();

        for (Transition transition : transitionQueue) {
            if (transition.isDone()) toRemove.add(transition);
            transition.render(game.batch, delta);
        }

        transitionQueue.remove(toRemove);
    }

    public void setLevelProgression(int progression) {
        //if the level has been completed and is greater than highest completed level
        if (progression > levelProgression) {
            //highest completed level contains new level
            levelProgression = progression;
        }
    }

}


