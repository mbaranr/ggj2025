package com.bubble.world;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.bubble.entities.Player;
import com.bubble.scenes.HUD;
import com.bubble.screens.ScreenManager;
import com.bubble.tools.MyTimer;
import com.bubble.helpers.Constants;
import com.bubble.tools.MyResourceManager;
import com.bubble.tools.UtilityStation;
import com.bubble.tools.TextureDrawer;
import java.util.concurrent.atomic.AtomicInteger;

public class B2WorldHandler {

    private final World world;
    private final MyResourceManager resourceManager;
    private final UtilityStation util;
    private final ScreenManager screenManager;

    public B2WorldHandler(World world, TiledMap map, MyResourceManager resourceManager, MyTimer timer, AtomicInteger eidAllocator, UtilityStation util, HUD hud, TextureDrawer textureDrawer, ScreenManager screenManager) {

        this.world = world;
        this.resourceManager = resourceManager;
        this.util = util;
        this.screenManager = screenManager;

        createPlayer(eidAllocator, timer, hud);

        BodyDef bdef  = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Create ground
        for (RectangleMapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Constants.PPM, (rect.getHeight() / 2) / Constants.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Constants.BIT_GROUND;
            body.createFixture(fdef).setUserData("ground");
        }

        for (RectangleMapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Constants.PPM, (rect.getHeight() / 2) / Constants.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Constants.BIT_GROUND;
            body.createFixture(fdef).setUserData("ground");
        }

    }

    public void createPlayer(AtomicInteger eidAllocator, MyTimer timer, HUD hud) {

        Player p1 = null;
        Player p2 = null;

        p1 = new Player(600,300,world, 1, timer, resourceManager, util, screenManager);
        p2 = new Player(1300,300,world, 2, timer, resourceManager, util, screenManager);

        hud.setPlayers(p1,p2);
        util.getEntityHandler().addPlayer1(p1);
        util.getEntityHandler().addPlayer2(p2);
    }

}
