package com.mygdx.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bubble.objects.*;
import com.bubble.world.*;
import com.bubble.scenes.HUD;
import com.bubble.tools.MyTimer;
import com.bubble.helpers.Constants;
import com.bubble.tools.MyResourceManager;
import com.bubble.tools.TextureDrawer;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class B2WorldHandler {

    private final World world;
    private final MyResourceManager resourceManager;
    private final UtilityStation util;

    public B2WorldHandler(World world, TiledMap map, MyResourceManager resourceManager, MyTimer timer, AtomicInteger eidAllocator, UtilityStation util, int level, HUD hud, TextureDrawer textureDrawer) {

        this.world = world;
        this.resourceManager = resourceManager;
        this.util = util;

        createPlayer(eidAllocator, timer, hud, level);

        BodyDef bdef  = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        if (level == 1) hud.pushCutscene("intro");

        // Create ground
        for (RectangleMapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Constants.PPM, (rect.getHeight() / 2) / Constants.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Constants.BIT_GROUND;
            body.createFixture(fdef).setUserData("ground");
        }

        // Create ending sensors
        for (RectangleMapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            util.getLightManager().addConeLight(rect.getX() / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM, 60, new Color(96 / 255f, 130 / 255f, 182 / 255f, 1), 180, 90l);
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Constants.PPM, (rect.getHeight() / 2) / Constants.PPM);
            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.BIT_GROUND;
            fdef.filter.maskBits = Constants.BIT_MAGE;
            body.createFixture(fdef).setUserData("end");
        }

        fdef = new FixtureDef();

        // Create spikes
        for (RectangleMapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Constants.PPM, (rect.getHeight() / 2) / Constants.PPM);
            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.BIT_HAZARD;
            body.createFixture(fdef).setUserData("spike");
        }

        // Create see through fixtures
        for (RectangleMapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Constants.PPM, (rect.getHeight() / 2) / Constants.PPM);
            fdef.shape = shape;
            fdef.isSensor = false;
            fdef.filter.categoryBits = Constants.BIT_GROUND;
            body.createFixture(fdef).setUserData("seethrough");
        }

        // Create messages
        for (RectangleMapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            textureDrawer.addTexture(resourceManager.getTexture("x"), rect.getX() / Constants.PPM, rect.getY() / Constants.PPM, 0.6f);
        }
        for (RectangleMapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            textureDrawer.addTexture(resourceManager.getTexture("shift"), rect.getX() / Constants.PPM, rect.getY() / Constants.PPM, 0.6f);
        }
        for (RectangleMapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            textureDrawer.addTexture(resourceManager.getTexture("space"), rect.getX() / Constants.PPM, rect.getY() / Constants.PPM, 0.6f);
        }

        util.getVisionMap().initialize(util.getEntityHandler(), util.getCharacterCycle());

    }

}
