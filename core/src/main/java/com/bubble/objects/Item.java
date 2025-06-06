package com.bubble.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.bubble.graphics.ShaderHandler;
import com.bubble.helpers.Constants;
import com.bubble.sprites.B2Sprite;
import com.bubble.tools.MyResourceManager;

public class Item extends B2Sprite {

    private final String name;
    private final ShaderHandler shaderHandler;

    public Item(float x, float y, World world, String name, ShaderHandler shaderHandler, MyResourceManager resourceManager) {
        this.name = name;
        this.shaderHandler = shaderHandler;

        if (name.equals("papaya")) setAnimation(TextureRegion.split(resourceManager.getTexture("papaya"), 13, 10)[0], 1/4f, true, 0.7f);
        else if (name.equals("bug")) setAnimation(TextureRegion.split(resourceManager.getTexture("bug"), 11, 13)[0], 1/4f, false, 0.7f);

        // Creating body and fixture (sensor)
        BodyDef bdef = new BodyDef();
        bdef.position.set(x / Constants.PPM, y / Constants.PPM);
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        // If this is a bug item, don't create a sensor
        if (name.equals("bug")) return;

        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        polygonShape.setAsBox(4 / Constants.PPM, 4 / Constants.PPM);
        fdef.shape = polygonShape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Constants.BIT_GROUND;
        fdef.filter.maskBits = Constants.BIT_PLAYER;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void render(SpriteBatch batch) {
        if (name.equals("papaya")) batch.setShader(shaderHandler.getShaderProgram("blink"));    // Applying blink shader to item
        super.render(batch);
        batch.setShader(null);
    }

    public void dispose() {
        for (Fixture fixture : b2body.getFixtureList()) {
            b2body.destroyFixture(fixture);
        }
    }
}
