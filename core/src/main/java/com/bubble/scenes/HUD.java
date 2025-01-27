package com.bubble.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bubble.entities.Player;
import com.bubble.tools.MyResourceManager;

public class HUD {

    public Stage stage;
    private final Viewport viewport;
    private final ShapeRenderer shapeRenderer; // Class-level variable
    private final BitmapFont font;

    private Player p1;
    private Player p2;
    // private final LifeActor lifeActor;

    public HUD(SpriteBatch batch, MyResourceManager resourceManager) {
        viewport = new FitViewport(3840, 2160, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(5.0f);
    }

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
        drawHealthBar(175, 2025, 600, 50, p1.getHealth(), Color.GREEN, Color.RED);
        drawHealthBar(3050, 2025, 600, 50, p2.getHealth(), Color.GREEN, Color.RED);
        batch.begin();
        font.draw(batch, "P1", 50, 2080);
        font.draw(batch, "P2", 3695, 2080);
        if (p1.getHealth() <= 0) {
            font.draw(batch, "Player 2 wins!", 1760, 2080);
        } else if (p2.getHealth() <= 0) {
            font.draw(batch, "Player 1 wins!", 1760, 2080);
        }
        batch.end();
    }

    public void setPlayers(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    private void drawHealthBar(float x, float y, float width, float height, float health, Color fillColor, Color emptyColor) {
        float filledWidth = (health / 100) * width; // Calculate the width based on health percentage

        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw empty part of the health bar
        shapeRenderer.setColor(emptyColor);
        shapeRenderer.rect(x, y, width, height);

        // Draw filled part of the health bar
        shapeRenderer.setColor(fillColor);
        shapeRenderer.rect(x, y, filledWidth, height);

        shapeRenderer.end();
    }
}