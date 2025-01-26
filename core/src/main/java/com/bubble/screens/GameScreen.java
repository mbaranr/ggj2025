package com.bubble.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bubble.Main;
import com.bubble.audio.MusicManager;
import com.bubble.entities.Player;
import com.bubble.graphics.LightManager;
import com.bubble.graphics.ParticleHandler;
import com.bubble.graphics.ShaderHandler;
import com.bubble.tools.*;
import com.bubble.world.*;
import com.bubble.entities.Bubble;
import com.bubble.listeners.MyContactListener;
import com.bubble.listeners.GameInputProcessor;
import com.bubble.world.B2WorldHandler;
import com.bubble.helpers.Constants;
import java.util.concurrent.atomic.AtomicInteger;

public class GameScreen extends ManagedScreen {
    private final MyTimer timer;                    // Timer distributed to any other classes that require it
    private final Main game;
    private final OrthographicCamera gameCam;       // Camera that will follow the player
    private final Viewport gamePort;
    private final OrthogonalTiledMapRenderer renderer;
    private final World world;    // World holding all the physical objects
    private final Box2DDebugRenderer b2dr;
    private final UtilityStation util;
    private final TextureDrawer textureDrawer;
    private final GameInputProcessor inputProcessor;
    private final ScreenManager screenManager;

    public GameScreen(Main game, MyResourceManager resourceManager, ScreenManager screenManager, MusicManager musicManager) {

        this.game = game;
        this.screenManager = screenManager;

        // Creating tile map
        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("tilemaps/Map2.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.PPM);
        world = new World(new Vector2(0, 0), true);
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Constants.TILE_SIZE * 60 / Constants.PPM, Constants.TILE_SIZE * 39 / Constants.PPM, gameCam);

        ColourGenerator colourGenerator = new ColourGenerator();
        AtomicInteger eidAllocator = new AtomicInteger();

        timer = new MyTimer();

        // Tools and handlers
        ShaderHandler shaderHandler = new ShaderHandler(colourGenerator);
        textureDrawer = new TextureDrawer(shaderHandler);
        LightManager lightManager = new LightManager(world);
        ObjectHandler objectHandler = new ObjectHandler(resourceManager);
        EntityHandler entityHandler = new EntityHandler(shaderHandler);
        ParticleHandler particleHandler = new ParticleHandler();

        // Creating station
        util = new UtilityStation(entityHandler, objectHandler, particleHandler, shaderHandler, lightManager, musicManager);

        world.setContactListener(new MyContactListener(util, game.hud, screenManager, resourceManager));
        b2dr = new Box2DDebugRenderer();
        new B2WorldHandler(world, map, resourceManager, timer, eidAllocator, util, game.hud, textureDrawer, screenManager);     //Creating world

        inputProcessor = new GameInputProcessor(game, screenManager, resourceManager, util, colourGenerator, world, timer);
        Gdx.input.setInputProcessor(inputProcessor);

        lightManager.setDim(0.8f);  // Making the environment 40% less bright
    }

    @Override
    public void show() {  }

    public void update(float delta) {
        util.update(delta, gameCam);
        timer.update(delta);
        world.step(1/60f, 6, 2);
    }

    @Override
    public void render(float delta) {

        //Uncomment this to check fps
        //System.out.println(fpsCounter.getFramesPerSecond());

        update(delta);

        game.batch.setProjectionMatrix(gameCam.combined);

        // Clearing the screen
        Gdx.gl.glClearColor(24 / 255f, 20 / 255f, 37 / 255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(gameCam);
        renderer.render();

        util.render(game.batch, delta);

        textureDrawer.render(game.batch);

        game.hud.render(game.batch);


        screenManager.render(delta);

        // Uncomment this to render fixture outlines
        // b2dr.render(world, gameCam.combined);

        gameCam.position.set(Constants.TILE_SIZE * 60 / Constants.PPM / 2, Constants.TILE_SIZE * 39 / Constants.PPM / 2, 0);
        gameCam.update();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
    }

    @Override
    public Matrix4 getProjectionMatrix() {
        return gameCam.combined;
    }
}
