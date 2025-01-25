package com.bubble.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.bubble.entities.PlayableCharacter;
import com.bubble.tools.MyResourceManager;
import com.bubble.tools.MyTimer;
import com.bubble.tools.UtilityStation;

public class Player extends PlayableCharacter{

    public Player(World world, int id, MyTimer timer, MyResourceManager myResourceManager, UtilityStation util) {
        super(world, id, timer, myResourceManager, util);
        //TODO Auto-generated constructor stub
    }
    
}
