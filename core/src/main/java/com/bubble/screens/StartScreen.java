package com.bubble.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bubble.Main;
import com.bubble.graphics.ShaderHandler;
import com.bubble.helpers.Constants;
import com.bubble.tools.ColourGenerator;
import com.bubble.tools.MyResourceManager;

public class StartScreen extends ManagedScreen {
    private final Main game;
    private final ScreenManager screenManager;
    private final MyResourceManager resourceManager;
    private final Stage stage;                          //use as input processor for start screen
    private final float buttonWidth, buttonHeight;
    private Array<ImageButton> buttons;
    private final TextureRegion bg;  //textures for start screen art
    private final ShaderHandler shaderHandler;          //for game title effect

    public StartScreen(Main game, MyResourceManager resourceManager, ScreenManager screenManager) {

        this.game = game;
        this.screenManager = screenManager;
        this.resourceManager = resourceManager;
        this.shaderHandler = new ShaderHandler(new ColourGenerator());
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.buttonWidth = Constants.BUTTON_WIDTH;
        this.buttonHeight = Constants.BUTTON_HEIGHT;

        //load start screen art
        bg = new TextureRegion(resourceManager.getTexture("start_bg"));

        initStartScreen();  //initialize start screen
    }

    //method to set up buttons using skins, buttonstyle, image buttons
    public void initButton(final String unclickedImagePath, final String hoverImagePath, final String clickedImagePath, int offset, final float width, final float height, final Constants.SCREEN_OP screenType)  {
        Skin skin = new Skin(); //use skin to store different button image
        skin.add(unclickedImagePath, resourceManager.getTexture(unclickedImagePath));
        skin.add(hoverImagePath, resourceManager.getTexture(hoverImagePath));
        skin.add(clickedImagePath, resourceManager.getTexture(clickedImagePath));

        //button style to display according to button state
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.imageUp = skin.getDrawable(unclickedImagePath);
        buttonStyle.imageOver = skin.getDrawable(hoverImagePath);
        buttonStyle.imageDown = skin.getDrawable(clickedImagePath);

        //set position, size and click outcome of button
        final ImageButton button = new ImageButton(buttonStyle);
        button.setPosition((Gdx.graphics.getWidth() - button.getWidth())/2 - 30 , (Gdx.graphics.getHeight() - button.getHeight())/2 - offset);
        button.getImageCell().size(width, height);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                button.setChecked(false);
                screenManager.pushScreen(screenType,"slide_left");  //change screen using transition
            }
        });
        buttons.add(button);        //add buttons to buttons array
        stage.addActor(button);     //add actor to stage
    }

    private void initStartScreen() {    //method that calls initButton for every start screen button

        buttons = new Array<>();

        initButton("UnclickedPlayButton", "HoverPlayButton", "ClickedPlayButton", 100, buttonWidth, buttonHeight, Constants.SCREEN_OP.GAME);

        initButton("UnclickedExitButton", "HoverExitButton", "ClickedExitButton", 300, buttonWidth, buttonHeight, Constants.SCREEN_OP.EXIT);
    }

    public void update(float delta) {
        shaderHandler.update(delta);
    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);;

        game.batch.begin();

        //draw background
        game.batch.draw(bg, -20, 100, bg.getRegionWidth() * 6f, bg.getRegionHeight() * 6f);
        //draw game title mind
        // game.batch.draw(mind, stage.getViewport().getWorldWidth() / 2 - 500, stage.getViewport().getWorldHeight() - 300, mind.getRegionWidth() * 1.4f, mind.getRegionHeight() * 1.4f);
        //use water effect
        game.batch.setShader(shaderHandler.getShaderProgram("bubble"));
        //draw game title weaver
        // game.batch.draw(weaver, stage.getViewport().getWorldWidth() / 2 - 150, stage.getViewport().getWorldHeight() - 300, weaver.getRegionWidth() * 2f, weaver.getRegionHeight() * 2f);

        game.batch.setShader(null);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        stage.draw();   //draw buttons

        game.batch.end();
    }
    @Override
    public void dispose() {
        stage.dispose();        //dispose stage
        for (ImageButton button : buttons) {        //dispose start screen buttons' skins
            Skin skin = button.getSkin();
            if (skin != null) {
                skin.dispose();
            }
        }
    }
    @Override
    public Matrix4 getProjectionMatrix() { return stage.getBatch().getProjectionMatrix();  }  //method for screen transition
    @Override
    public void show() { }
    @Override
    public void resize(int i, int j) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
}
