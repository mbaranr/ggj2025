package com.bubble.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubble.objects.Interactable;
import com.bubble.objects.Item;
import com.bubble.objects.Reactable;
import com.bubble.tools.MyResourceManager;

import java.util.LinkedList;

// Class to render and update objects: Interactables, Reactables and Items
public class ObjectHandler {

    private LinkedList<Interactable> interactables;
    private LinkedList<Reactable> reactables;
    private LinkedList<Item> items;
    private MyResourceManager resourceManager;

    public ObjectHandler(MyResourceManager resourceManager) {
        interactables = new LinkedList<>();
        reactables = new LinkedList<>();
        items = new LinkedList<>();
        this.resourceManager = resourceManager;
    }

    public void addObject(Interactable interactable) {
        interactables.add(interactable);
    }

    public void addObject(Reactable reactable) {
        reactables.add(reactable);
    }

    public void addObject(Item item) {
        items.add(item);
    }

    public void removeObject(Item item) {
        if (items.contains(item)) resourceManager.getSound("item").play(0.6f);
        items.remove(item);
    }

    public void update(float delta) {
        for (Interactable interactable : interactables) {
            interactable.update(delta);
        }
        for (Reactable reactable : reactables) {
            reactable.update(delta);
        }
        for (Item item : items) {
            item.update(delta);
        }
    }

    public void render(SpriteBatch batch) {
        for (Interactable interactable : interactables) {
            interactable.render(batch);
        }
        for (Reactable reactable : reactables) {
            reactable.render(batch);
        }
        for (Item item : items) {
            item.render(batch);
        }
    }
}
