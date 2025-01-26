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

		resourceManager.loadTexture("player/run_right2.png", "run_right2");
        resourceManager.loadTexture("player/run_down2.png", "run_down2");
		resourceManager.loadTexture("player/run_up2.png", "run_up2");
		resourceManager.loadTexture("player/run_down_right2.png", "run_down_right2");
        resourceManager.loadTexture("player/run_up_right2.png", "run_up_right2");
        resourceManager.loadTexture("player/idle2.png", "idle2");


		//Start Screen buttons
		resourceManager.loadTexture("buttons/UnclickedPlayButton.png", "UnclickedPlayButton");
		resourceManager.loadTexture("buttons/HoverPlayButton.png", "HoverPlayButton");
		resourceManager.loadTexture("buttons/ClickedPlayButton.png", "ClickedPlayButton");
		resourceManager.loadTexture("buttons/UnclickedExitButton.png", "UnclickedExitButton");
		resourceManager.loadTexture("buttons/HoverExitButton.png", "HoverExitButton");
		resourceManager.loadTexture("buttons/ClickedExitButton.png", "ClickedExitButton");
		
		resourceManager.loadTexture("objects/bubble.png", "bubble");

		// //Menu Screen buttons
		// resourceManager.loadTexture("Buttons/UnclickedResumeButton.png", "UnclickedResumeButton");
		// resourceManager.loadTexture("Buttons/HoverResumeButton.png", "HoverResumeButton");
		// resourceManager.loadTexture("Buttons/ClickedResumeButton.png", "ClickedResumeButton");

		// resourceManager.loadTexture("Buttons/UnclickedRestartButton.png", "UnclickedRestartButton");
		// resourceManager.loadTexture("Buttons/HoverRestartButton.png", "HoverRestartButton");
		// resourceManager.loadTexture("Buttons/ClickedRestartButton.png", "ClickedRestartButton");

		// resourceManager.loadTexture("Buttons/UnclickedLevelsButton.png", "UnclickedLevelsButton");
		// resourceManager.loadTexture("Buttons/HoverLevelsButton.png", "HoverLevelsButton");
		// resourceManager.loadTexture("Buttons/ClickedLevelsButton.png", "ClickedLevelsButton");

		// resourceManager.loadTexture("Buttons/UnclickedControlsButton.png", "UnclickedControlsButton");
		// resourceManager.loadTexture("Buttons/HoverControlsButton.png", "HoverControlsButton");
		// resourceManager.loadTexture("Buttons/ClickedControlsButton.png", "ClickedControlsButton");

		// // Mage
		// resourceManager.loadTexture("Mage/mage_run.png", "mage_run");
		// resourceManager.loadTexture("Mage/mage_idle.png", "mage_idle");
		// resourceManager.loadTexture("Mage/mage_jump.png", "mage_jump");
		// resourceManager.loadTexture("Mage/mage_land.png", "mage_land");
		// resourceManager.loadTexture("Mage/mage_fall.png", "mage_fall");
		// resourceManager.loadTexture("Mage/mage_death.png", "mage_death");

		// // Base goblin
		// resourceManager.loadTexture("Goblins/basegoblin_run.png", "goblin_run");
		// resourceManager.loadTexture("Goblins/basegoblin_idle.png", "goblin_idle");
		// resourceManager.loadTexture("Goblins/basegoblin_jump.png", "goblin_jump");
		// resourceManager.loadTexture("Goblins/basegoblin_land.png", "goblin_land");
		// resourceManager.loadTexture("Goblins/basegoblin_fall.png", "goblin_fall");
		// resourceManager.loadTexture("Goblins/basegoblin_death.png", "goblin_death");

		// // Armoured goblin
		// resourceManager.loadTexture("Goblins/armourgoblin_idle.png", "armour_idle");
		// resourceManager.loadTexture("Goblins/armourgoblin_run.png", "armour_run");
		// resourceManager.loadTexture("Goblins/armourgoblin_land.png", "armour_land");
		// resourceManager.loadTexture("Goblins/armourgoblin_fall.png", "armour_fall");
		// resourceManager.loadTexture("Goblins/armourgoblin_jump.png", "armour_jump");
		// resourceManager.loadTexture("Goblins/armourgoblin_attack.png", "armour_attack");
		// resourceManager.loadTexture("Goblins/armourgoblin_death.png", "armour_death");

		// // Objects
		// resourceManager.loadTexture("Objects/pressureplate_up.png", "pressureplate_up");
		// resourceManager.loadTexture("Objects/pressureplate_up2.png", "pressureplate_up2");
		// resourceManager.loadTexture("Objects/pressureplate_down.png", "pressureplate_down");
		// resourceManager.loadTexture("Objects/pressureplate_down2.png", "pressureplate_down2");
		// resourceManager.loadTexture("Objects/door_closed.png", "door_closed");
		// resourceManager.loadTexture("Objects/door_closed2.png", "door_closed2");
		// resourceManager.loadTexture("Objects/door_open.png", "door_open");
		// resourceManager.loadTexture("Objects/door_open2.png", "door_open2");
		// resourceManager.loadTexture("Objects/lever_up.png", "lever_up");
		// resourceManager.loadTexture("Objects/lever_down.png", "lever_down");
		// resourceManager.loadTexture("Objects/platform.png", "platform");

		// //Items
		// resourceManager.loadTexture("Items/bug.png", "bug");
		// resourceManager.loadTexture("Items/papaya.png", "papaya");

		// //Pet
		// resourceManager.loadTexture("Items/pet.png", "pet");

		// //Merchant
		// resourceManager.loadTexture("Merchant/merchant_idle.png", "merchant_idle");

		// //HUD
		// resourceManager.loadTexture("HUD/life.png", "life");
		// resourceManager.loadTexture("HUD/pause.png", "pause");
		// resourceManager.loadTexture("HUD/inventory.png", "inventory");

		// //Shapes
		// resourceManager.loadTexture("Shapes/purple_pixel.png", "purple_pixel");
		// resourceManager.loadTexture("Shapes/translucent_pixel.png", "translucent_pixel");
		// resourceManager.loadTexture("Shapes/gray_pixel.png", "gray_pixel");

		// //Cutscenes
		// resourceManager.loadTexture("Cutscenes/cutscene_bg.png", "cutscene_bg");
		// resourceManager.loadTexture("Cutscenes/mage_neutral.png", "mage_neutral");
		// resourceManager.loadTexture("Cutscenes/mage_chained.png", "mage_chained");
		// resourceManager.loadTexture("Cutscenes/mage_freed.png", "mage_freed");
		// resourceManager.loadTexture("Cutscenes/mage_happy.png", "mage_happy");
		// resourceManager.loadTexture("Cutscenes/mage_pokerface.png", "mage_pokerface");
		// resourceManager.loadTexture("Cutscenes/mage_surprised.png", "mage_surprised");
		// resourceManager.loadTexture("Cutscenes/merchant_neutral.png", "merchant_neutral");
		// resourceManager.loadTexture("Cutscenes/merchant_bling.png", "merchant_bling");
		// resourceManager.loadTexture("Cutscenes/butterfly.png", "butterfly");
		// resourceManager.loadTexture("Cutscenes/merchant_papaya.png", "merchant_papaya");
		// resourceManager.loadTexture("Cutscenes/merchant_papaya3.png", "merchant_papaya3");
		// resourceManager.loadTexture("Cutscenes/merchant_smell.png", "merchant_smell");
		// resourceManager.loadTexture("Cutscenes/merchant_bug.png", "merchant_bug");
		// resourceManager.loadTexture("Cutscenes/mage_eating.png", "mage_eating");
		// resourceManager.loadTexture("Cutscenes/mage_ate.png", "mage_ate");
		// resourceManager.loadTexture("Cutscenes/bug_teacher.png", "bug_teacher");

		// //Messages
		// resourceManager.loadTexture("Messages/press_x.png", "x");
		// resourceManager.loadTexture("Messages/press_shift.png", "shift");
		// resourceManager.loadTexture("Messages/press_space.png", "space");

		// //SFX
		// resourceManager.loadSound("SoundEffects/jump.mp3", "jump");
		// resourceManager.loadSound("SoundEffects/land.mp3", "land");
		// resourceManager.loadSound("SoundEffects/laugh.wav", "laugh");
		// resourceManager.loadSound("SoundEffects/papaya_picked.wav", "item");
		// resourceManager.loadSound("SoundEffects/attack.mp3", "attack");
		// resourceManager.loadSound("SoundEffects/lever.mp3", "lever");
		// resourceManager.loadSound("SoundEffects/door.mp3", "door");
		// resourceManager.loadSound("SoundEffects/cycle.mp3", "cycle");
		// resourceManager.loadSound("SoundEffects/level_complete.mp3", "level_complete");

		// //Art
		resourceManager.loadTexture("art/Mind.png", "mind");
		resourceManager.loadTexture("art/Weaver.png", "weaver");
		// resourceManager.loadTexture("Art/LevelsScreen1.png", "LevelsScreen1");
		// resourceManager.loadTexture("Art/LevelsScreen2.png", "LevelsScreen2");
		// resourceManager.loadTexture("Art/LevelsScreen3.png", "LevelsScreen3");
		// resourceManager.loadTexture("Art/LevelsScreen4.png", "LevelsScreen4");
		// resourceManager.loadTexture("Art/LevelsScreen5.png", "LevelsScreen5");
		// resourceManager.loadTexture("Art/levels_background.png", "levels_bg");
		resourceManager.loadTexture("art/start_background.png", "start_bg");
		// resourceManager.loadTexture("Art/translucent_background.png", "translucent_bg");
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
