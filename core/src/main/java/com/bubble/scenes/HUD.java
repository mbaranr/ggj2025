package com.bubble.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bubble.entities.Player;
import com.bubble.graphics.ShaderHandler;
import com.bubble.tools.ColourGenerator;
import com.bubble.tools.MyResourceManager;
import com.bubble.world.EntityHandler;

public class HUD {

    public Stage stage;
    private final Viewport viewport;
    private final MyResourceManager resourceManager;
    private final ShapeRenderer shapeRenderer; // Class-level variable
    private final BitmapFont font;

    private Player p1;
    private Player p2;
    // private final LifeActor lifeActor;

    public HUD(SpriteBatch batch, MyResourceManager resourceManager) {
        this.resourceManager = resourceManager;

        viewport = new FitViewport(3840, 2160, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(5.0f);

        // lifeActor = new LifeActor(resourceManager.getTexture("life"));
        // stage.addActor(lifeActor);


    }


    // public void setPlayers(Player player1, Player player2) { lifeActor.setPlayers(player1, player2); }

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
        drawHealthBar(175, 2025, 600, 50, p1.getHealth(), Color.GREEN, Color.RED);
        drawHealthBar(3050, 2025, 600, 50, p2.getHealth(), Color.GREEN, Color.RED);
        batch.begin();
        font.draw(batch, "P1", 50, 2080);
        font.draw(batch, "P2", 3695, 2080);
        batch.end();
        //drawHealthBar(600, 600, 300, 30, 10, Color.GREEN, Color.RED);
//        if (p1 != null) {
//            drawHealthBar(600, 600, 300, 30, p1.getHealth(), Color.GREEN, Color.RED);
//        }
//        if (p2 != null) {
//            drawHealthBar(viewport.getWorldWidth() - 350, viewport.getWorldHeight() - 100, 300, 30, p2.getHealth(), Color.GREEN, Color.RED);
//        }
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

    // Actor used to draw lives
//    public class LifeActor extends Actor {
//        private final TextureRegion region;
//        private Player p1;
//        private Player p2;
//
//        public LifeActor(Texture texture) {
//            region = new TextureRegion(texture);
//        }
//
//        public void setPlayers(Player p1, Player p2) {
//            this.p1 = p1;
//            this.p2 = p2;
//        }
//
//        @Override
//        public void draw(Batch batch, float parentAlpha) {
//            if (p1 == null || p2 == null) return;
//
//            Color color = getColor();
//            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
//            float x = 20;
//            float y = viewport.getWorldHeight() - 240;
//
//            for (int i = 0; i < p1.getLives(); i++) {
//                batch.draw(region, x, y, 230, 180);
//                x += 250;
//            }
//        }
//    }

}
