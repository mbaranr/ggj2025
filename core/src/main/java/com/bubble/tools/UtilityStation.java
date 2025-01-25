package com.bubble.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubble.audio.MusicManager;
import com.bubble.graphics.LightManager;
import com.bubble.graphics.ParticleHandler;
import com.bubble.graphics.ShaderHandler;
import com.bubble.listeners.GameInputProcessor;
import com.bubble.world.EntityHandler;
import com.bubble.world.ObjectHandler;

//Centralized point of access for handler interaction
public class UtilityStation {

    private final EntityHandler entityHandler;
    private final ObjectHandler objectHandler;
    private final ParticleHandler particleHandler;
    private final ShaderHandler shaderHandler;
    private final LightManager lightManager;
    private final MusicManager musicManager;

    public UtilityStation(EntityHandler entityHandler, ObjectHandler objectHandler, ParticleHandler particleHandler, ShaderHandler shaderHandler, LightManager lightManager, MusicManager musicManager) {
        this.entityHandler = entityHandler;
        this.objectHandler = objectHandler;
        this.particleHandler = particleHandler;
        this.shaderHandler = shaderHandler;
        this.lightManager = lightManager;
        this.musicManager = musicManager;
    }

    public void update(float delta, OrthographicCamera gameCam) {
        shaderHandler.update(delta);
        entityHandler.update(delta);
        objectHandler.update(delta);
        lightManager.update(gameCam);
    }

    public void render(SpriteBatch batch, float delta) {
        particleHandler.render(batch, delta);
        objectHandler.render(batch);
        lightManager.render();
        entityHandler.render(batch);
    }

    public ObjectHandler getObjectHandler() {
        return objectHandler;
    }

    public EntityHandler getEntityHandler() {
        return entityHandler;
    }

    public ParticleHandler getParticleHandler() {
        return particleHandler;
    }

    public ShaderHandler getShaderHandler() {
        return shaderHandler;
    }

    public LightManager getLightManager() {
        return lightManager;
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }
}
