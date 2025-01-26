package com.bubble.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.LinkedList;

public class ParticleHandler {

    // Using particle pools to avoid garbage collection
    private ParticleEffectPool bubbleEffectPool;
    private LinkedList<PooledEffect> effects;

    public ParticleHandler() {

        effects = new LinkedList<>();

        ParticleEffect bubbles = new ParticleEffect();
        bubbles.load(Gdx.files.internal("particles/bubble.p"), Gdx.files.internal("particles"));

        bubbleEffectPool = new ParticleEffectPool(bubbles, 1, 2);
        
    }

    // Adding particle effect based on the provided key
    public void addParticleEffect(String key, float x, float y, float scale) {
        PooledEffect effect = null;
        switch (key) {
            case "bubble":
                effect = bubbleEffectPool.obtain();
                break;
            default:
                break;
        }

        // Making sure the effect is not null
        assert effect != null;
        effect.setPosition(x, y);
        effect.scaleEffect(scale);
        effects.add(effect);
    }

    public void render(SpriteBatch batch, float delta) {
        // Linked list to keep track of particles to remove
        LinkedList<ParticleEffect> toRemove = new LinkedList<>();

        //Rendering all particles
        batch.begin();
        for (int i = effects.size() - 1; i >= 0; i--) {
            PooledEffect effect = effects.get(i);
            effect.draw(batch, delta);
            if (effect.isComplete()) {
                effect.free();
                toRemove.add(effect);
            }
        }
        batch.end();
        effects.removeAll(toRemove);
    }
}
