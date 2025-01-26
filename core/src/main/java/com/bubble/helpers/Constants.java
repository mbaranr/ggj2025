package com.bubble.helpers;

public class Constants {
    public static final int TILE_SIZE = 32; //Used to maintain constant dimension regardless of machine size

    public static final float PPM = 100;    //Pixels per meter
    public static final float MAX_SPEED = 0.8f;       //Max speed allowed in x-direction
    public static final float TEXT_SPEED = 20;          //Speed at which text prints in cutscene
    public static final float KNOCKBACK_SCALE = 1f;     //Move character backwards when attacked
    public static final float BUTTON_WIDTH = 250;       //Button width
    public static final float BUTTON_HEIGHT = 175.5f;   //Button height
    public static final float BUBBLE_GR = 0.0005f;      //Bubble growth rate
    public static final float TTP = 4;                  //Time to pop
    public static final float BUBBLE_SPEED = 0.15f;

    // Movement state Flag
    public enum MSTATE {
        LEFT, RIGHT, UP, DOWN
    }

    // Player state flag
    public enum PSTATE {
        STUNNED, ON_GROUND, LANDING, ATTACKING, ATTACK_STUN, HIT, DYING
    }

    // Bubbbles state flag
    public enum BSTATE {
        POPPING, FULL, FREE, POPPED
    }
    // Animation state flag
    public enum ASTATE {
        RUN_RIGHT, RUN_UP, RUN_DOWN, RUN_UP_RIGHT, RUN_DOWN_RIGHT, IDLE_UP, IDLE_DOWN, IDLE_RIGHT, IDLE_UP_RIGHT, IDLE_DOWN_RIGHT
    }

    public enum SCREEN_OP {
        START, RESUME, GAME, EXIT, RESTART
    }

    //Slide transition directions
    public enum SLIDE_DIR {
        SLIDE_UP, SLIDE_DOWN, SLIDE_RIGHT, SLIDE_LEFT
    }

    public static final short BIT_GROUND = 2;   // Includes ground and walls (because sometimes the ground can be a wall)
    public static final short BIT_HAZARD = 4;
    public static final short BIT_PLAYER = 8;
    public static final short BIT_LIGHT = 16;
    public static final short BIT_BSENSOR = 32;
}
