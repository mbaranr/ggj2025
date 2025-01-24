package com.bubble.objects;

import com.badlogic.gdx.physics.box2d.World;
import com.bubble.helpers.Constants;
import com.bubble.sprites.B2Sprite;
import com.bubble.tools.MyResourceManager;

// Class from where door and platform will inherit from
public abstract class Reactable extends B2Sprite {

    protected World world;
    protected MyResourceManager resourceManager;
    protected Constants.ASTATE currAState;     // Current animation state
    protected Constants.ASTATE prevAState;     // Previous animation state

    public Reactable(World world, MyResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.world = world;
    }


    public void react() { }

}
