package za.ttd.renderers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import za.ttd.game.Player;

public class HudRenderer {
    private BitmapFont lblLvlScore, lblLives, lblLvlNum;
    private Batch batch;

    public HudRenderer() {
        batch = new SpriteBatch();
        lblLvlScore = new BitmapFont();
        lblLives = new BitmapFont();
        lblLvlNum = new BitmapFont();
        //Tell SpriteBatch to render in the co-ordinate system specified by screen
        batch.getProjectionMatrix().setToOrtho2D(0,0,600,800);
    }

    public void render(Player player) {
        batch.begin();
        lblLvlScore.draw(batch, "LEVEL SCORE: " + player.scoring.getLvlScore(), 10, 750);
        lblLvlScore.draw(batch, "LEVEL : " + player.getHighestLevel(), 250, 750);
        lblLvlScore.draw(batch, "Lives : " + player.getLives(), 450, 750);
        batch.end();
    }

}
