package com.bubble.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.bubble.graphics.ShaderHandler;
import com.bubble.helpers.Constants;
import com.bubble.entities.Bubble;
import com.bubble.entities.Entity;
import com.bubble.entities.Player;
import java.util.HashMap;
import java.util.LinkedList;

// Class to handle risky operations outside world step and map entities to their id
public class EntityHandler {
    private final HashMap<Integer, Entity> entities;        // Map of entities with id
    private final LinkedList<EntityOp> entityOps;           // Entity operations to handle
    private final ShaderHandler shaderHandler;
    private Player p1;
    private Player p2;

    public EntityHandler(ShaderHandler shaderHandler) {
        entityOps = new LinkedList<>();
        entities = new HashMap<>();
        this.shaderHandler = shaderHandler;
    }

    public void addPlayer1(Player p1) {this.p1 = p1;}
    public void addPlayer2(Player p2) {this.p2 = p2;}

    public Player getPlayer1() {
        return this.p1;

    }

    public Player getPlayer2() {
        return this.p2;
    }



    public void addEntity(Entity entity) {
        entities.put(entity.getID(), entity);
    }

    // Returning entity based on id
    public Entity getEntity(int id) {
        return entities.get(id);
    }

    // Returning entity based on box2d body
    public Entity getEntity(Body b2body) {
        for (Entity entity : entities.values()) {
            if (entity.getB2body().equals(b2body)) {
                return entity;
            }
        }
        return null;
    }

    public void addEntityOperation(Entity entity, String operation) {
        entityOps.add(new EntityOp(entity, operation));
    }

    // Handling all entity operations
    public void handleEntities() {
        for (EntityOp entityOp : entityOps) {
            entityOp.resolve();
        }
        entityOps.clear();
    }

    public void update(float delta) {
        p1.update(delta);
        p2.update(delta);

        // Updating all entities
        for (Entity entity : entities.values()) {
            entity.update(delta);
        }
        handleEntities();
    }

    public void render(SpriteBatch batch) {
        p1.render(batch);
        p2.render(batch);

        // Rendering entities
        for (Entity entity : entities.values()) {
            if (entity instanceof Bubble) {
                if (((Bubble) entity).isStateActive(Constants.BSTATE.FULL)) {
                    batch.setShader(shaderHandler.getShaderProgram("blink"));
                } else {
                    batch.setShader(shaderHandler.getShaderProgram("rand_col"));
                }
            }
            entity.render(batch);
            batch.setShader(null);
        }
    }

    public LinkedList<Entity> getEntities() {
        return new LinkedList<>(entities.values());
    }



    // Helper entity operation class mapping an entity with an operation
    private class EntityOp {
        public Entity entity;
        public String operation;

        public EntityOp(Entity entity, String op) {
            this.entity = entity;
            this.operation = op;
        }

        public void resolve() {
            if (operation.equals("die") || operation.equals("pop")) {
                entities.remove(entity.getID());
                entity.die();
            }
        }
    }
}

