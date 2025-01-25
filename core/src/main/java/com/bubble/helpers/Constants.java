package com.bubble.helpers;

public class Constants {
    public static final int TILE_SIZE = 32; //Used to maintain constant dimension regardless of machine size

    public static final float PPM = 100;    //Pixels per meter
    public static final float MAX_SPEED = 0.8f;       //Max speed allowed in x-direction
    public static final float TEXT_SPEED = 20;          //Speed at which text prints in cutscene
    public static final float KNOCKBACK_SCALE = 1f;     //Move character backwards when attacked
    public static final float G = 11;                   //Normal gravity for world
    public static final float G_ENHANCED = 13;          //Enhanced gravity for falling in the world
    public static final float BUTTON_WIDTH = 250;       //Button width
    public static final float BUTTON_HEIGHT = 175.5f;   //Button height

    // Movement state Flag
    public enum MSTATE {
        LEFT, RIGHT, UP, DOWN, UPRIGHT, UPLEFT, DOWNRIGHT, DOWNLEFT,    // 8 different directions
        PREV,           // Keep previous direction
        STILL;         // Full still
    }

    // Player state flag
    public enum PSTATE {
        STUNNED, ON_GROUND, LANDING, ATTACKING, ATTACK_STUN, HIT, DYING
    }
    // Animation state flag
    public enum ASTATE {
        RUN, JUMP, IDLE, FALL, LAND, OPEN, CLOSED, ATTACK, DEATH
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
    public static final short BIT_PlAYER = 8;
    public static final short BIT_LIGHT = 16;
}
