package com.bubble;

import com.bubble.audio.MusicManager;
import com.bubble.helpers.Constants;
import com.bubble.scenes.HUD;
import com.bubble.tools.MyResourceManager;
import com.bubble.screens.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {

	/*
	 * HUD holding inventory will remain constant across all screens
	 * This allows transferring of item information through every level
	 */
	public HUD hud;
	public SpriteBatch batch;	//instantiate sprite batch
	private MyResourceManager resourceManager; //instantiate resource manager

	@Override
	public void create () {
		batch = new SpriteBatch();					//instantiate and initialise batch
		resourceManager = new MyResourceManager();	//initialise resource manager
		//instantiate and initialise screen manager
		ScreenManager screenManager = new ScreenManager(this, resourceManager, new MusicManager());
		loadSprites();

		hud = new HUD(batch, resourceManager);	//heads up display for lives and papayas

		screenManager.pushScreen(Constants.SCREEN_OP.START, "none");	//set screen to start screen
		// screenManager.pushScreen(Constants.SCREEN_OP.GAME, "none");	//set screen to start screen
	}

	// Loading sprites to the resource manager for later use
	public void loadSprites() {

        resourceManager.loadTexture("player/run_right.png", "run_right");
        resourceManager.loadTexture("player/run_down.png", "run_down");
		resourceManager.loadTexture("player/run_up.png", "run_up");
		resourceManager.loadTexture("player/run_down_right.png", "run_down_right");
        resourceManager.loadTexture("player/run_up_right.png", "run_up_right");
        resourceManager.loadTexture("player/idle.png", "idle");
        resourceManager.loadTexture("player/die.png", "die");


		resourceManager.loadTexture("player/run_right2.png", "run_right2");
        resourceManager.loadTexture("player/run_down2.png", "run_down2");
		resourceManager.loadTexture("player/run_up2.png", "run_up2");
		resourceManager.loadTexture("player/run_down_right2.png", "run_down_right2");
        resourceManager.loadTexture("player/run_up_right2.png", "run_up_right2");
        resourceManager.loadTexture("player/idle2.png", "idle2");
        resourceManager.loadTexture("player/die2.png", "die2");



		//Start Screen buttons
		resourceManager.loadTexture("buttons/UnclickedPlayButton.png", "UnclickedPlayButton");
		resourceManager.loadTexture("buttons/HoverPlayButton.png", "HoverPlayButton");
		resourceManager.loadTexture("buttons/ClickedPlayButton.png", "ClickedPlayButton");
		resourceManager.loadTexture("buttons/UnclickedExitButton.png", "UnclickedExitButton");
		resourceManager.loadTexture("buttons/HoverExitButton.png", "HoverExitButton");
		resourceManager.loadTexture("buttons/ClickedExitButton.png", "ClickedExitButton");
		
		resourceManager.loadTexture("objects/bubble.png", "bubble");
		resourceManager.loadTexture("art/start_background.png", "start_bg");
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
