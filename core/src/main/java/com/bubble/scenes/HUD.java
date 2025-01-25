package com.bubble.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bubble.graphics.ShaderHandler;
import com.bubble.tools.FancyFontHelper;
import com.bubble.tools.ColourGenerator;
import com.bubble.entities.Player;
import com.bubble.tools.MyResourceManager;

public class HUD {

    public Stage stage;
    private final Viewport viewport;
    private final MyResourceManager resourceManager;
    // private final LifeActor lifeActor;

    public HUD(SpriteBatch batch, MyResourceManager resourceManager) {
        this.resourceManager = resourceManager;


        viewport = new FitViewport(3840, 2160, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        // lifeActor = new LifeActor(resourceManager.getTexture("life"));
        // stage.addActor(lifeActor);
    }

    // public void setPlayers(Player player1, Player player2) { lifeActor.setPlayers(player1, player2); }

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
    }

    // Actor used to draw lives
    private class LifeActor extends Actor {
        private final TextureRegion region;
        private Player p1;
        private Player p2;

        public LifeActor(Texture texture) {
            region = new TextureRegion(texture);
        }

        public void setPlayers(Player p1, Player p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            if (p1 == null || p2 == null) return;

            Color color = getColor();
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
            float x = 20;
            float y = viewport.getWorldHeight() - 240;

            for (int i = 0; i < p1.getLives(); i++) {
                batch.draw(region, x, y, 230, 180);
                x += 250;
            }
        }
    }
    
}
