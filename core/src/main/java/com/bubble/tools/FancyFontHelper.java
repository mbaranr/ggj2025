package com.bubble.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * A singleton class to have a slightly more fancy font. Other classes can call the instance of this class via FancyFontHelper.getInstance()
 * Author: Fabio Papaccini
 */
public class FancyFontHelper {
    private static FancyFontHelper INSTANCE = null;
    private FreeTypeFontGenerator fontGenerator;

    private FancyFontHelper() {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kongtext.regular.ttf"));
    }

    public static FancyFontHelper getInstance() {
        if (INSTANCE == null)
            INSTANCE = new FancyFontHelper();
        return INSTANCE;
    }

    public BitmapFont getFont(Color aColor, int size) {
        FreeTypeFontParameter fontParams = new FreeTypeFontParameter();
        fontParams.borderColor = Color.BLACK;
        fontParams.borderWidth = 3;
        fontParams.color = aColor;
        fontParams.size = size;

        return this.fontGenerator.generateFont(fontParams);
    }

    public void dispose() {
        this.fontGenerator.dispose();
    }
}