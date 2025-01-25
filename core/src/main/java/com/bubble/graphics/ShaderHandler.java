package com.bubble.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.bubble.tools.ColourGenerator;

// Centralised point to manage shader programs
public class ShaderHandler {

    private float time;         // Current program time
    private final ColourGenerator colourGenerator;        // Random color generator
    private final ShaderProgram waveShader;             // Wave signal
    private final ShaderProgram blinkShader;            // Blinking objects (original colours - white)
    private final ShaderProgram alphaShader;            // Alpha blinking objects (original colours - alpha 0)
    private final ShaderProgram redMaskShader;          // Tinting objects red
    private final ShaderProgram outlineShader;          // Making the outlines purple
    private final ShaderProgram randColShader;          // Random color tinting
    private final ShaderProgram waterShader;            // Water like movement
    private final ShaderProgram bubbleShader;            // Bubbles


    public ShaderHandler(ColourGenerator colourGenerator) {
        this.colourGenerator = colourGenerator;
        time = 0;

        // Initializing shader programs

        waveShader = new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl").readString(), Gdx.files.internal("shaders/wave.glsl").readString());
        bubbleShader = new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl").readString(), Gdx.files.internal("shaders/bubble.glsl").readString());
        blinkShader = new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl").readString(), Gdx.files.internal("shaders/blink.glsl").readString());
        redMaskShader = new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl").readString(), Gdx.files.internal("shaders/red_mask.glsl").readString());
        outlineShader = new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl").readString(), Gdx.files.internal("shaders/outline.glsl").readString());
        randColShader = new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl").readString(), Gdx.files.internal("shaders/random_color_mask.glsl").readString());
        alphaShader = new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl").readString(), Gdx.files.internal("shaders/alpha.glsl").readString());
        waterShader = new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl").readString(), Gdx.files.internal("shaders/water.glsl").readString());

        ShaderProgram.pedantic = false;  // Makes updating uniform variables more flexible

        // Printing error messages, if any

        if (!waveShader.isCompiled()) {
            System.out.println(waveShader.getLog());
        }
        if (!blinkShader.isCompiled()) {
            System.out.println(blinkShader.getLog());
        }
        if (!redMaskShader.isCompiled()) {
            System.out.println(redMaskShader.getLog());
        }
        if (!bubbleShader.isCompiled()) {
            System.out.println(bubbleShader.getLog());
        }
        if (!outlineShader.isCompiled()) {
            System.out.println(outlineShader.getLog());
        }
        if (!randColShader.isCompiled()) {
            System.out.println(randColShader.getLog());
        }
        if (!alphaShader.isCompiled()) {
            System.out.println(randColShader.getLog());
        }
        if (!waterShader.isCompiled()) {
            System.out.println(randColShader.getLog());
        }
    }

    public void update(float delta) {
        time += delta;

        // Updating shader uniforms

        waveShader.bind();
        waveShader.setUniformf("u_time", time);
        waveShader.setUniformf("u_resolution", new Vector2(100, 100));

        blinkShader.bind();
        blinkShader.setUniformf("u_time", time);

        alphaShader.bind();
        alphaShader.setUniformf("u_time", time);

        randColShader.bind();
        randColShader.setUniformf("r", colourGenerator.getCurrentColour().x);
        randColShader.setUniformf("g", colourGenerator.getCurrentColour().y);
        randColShader.setUniformf("b", colourGenerator.getCurrentColour().z);

        waterShader.bind();
        waterShader.setUniformf("u_amount", 2.5f);
        waterShader.setUniformf("u_speed", 1f);
        waterShader.setUniformf("u_time", time);

        bubbleShader.bind();
        bubbleShader.setUniformf("u_time", time);

    }

    // Returning shader program depending on key
    public ShaderProgram getShaderProgram(String key) {
        if (key.equals("wave")) return waveShader;
        if (key.equals("blink")) return blinkShader;
        if (key.equals("redMask")) return redMaskShader;
        if (key.equals("outline")) return outlineShader;
        if (key.equals("rand_col")) return randColShader;
        if (key.equals("alpha")) return alphaShader;
        if (key.equals("water")) return waterShader;
        if (key.equals("bubble")) return bubbleShader;
        return null;
    }
}
