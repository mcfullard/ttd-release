package za.ttd.renderers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import za.ttd.game.Gamer;

public class HudRenderer {
    private BitmapFont lblLvlScore, lblPlayTime, lblLvlNum;
    private Batch batch;

    public HudRenderer() {
        batch = new SpriteBatch();
        lblLvlScore = new BitmapFont();
        lblPlayTime = new BitmapFont();
        lblPlayTime = new BitmapFont();
        //Tell SpriteBatch to render in the co-ordinate system specified by screen
        batch.getProjectionMatrix().setToOrtho2D(0,0,600,800);
    }

    public void render(Gamer gamer) {
        long time = gamer.scoring.getElapsedTime();
        batch.begin();
        lblLvlScore.draw(batch, "LEVEL SCORE: " + gamer.scoring.getLvlScore(), 10, 750);
        lblLvlScore.draw(batch, "LEVEL : " + gamer.getHighestLevel(), 250, 750);
        lblPlayTime.draw(batch,
                String.format(
                        "TIME %s:%s",
                        time / 60,
                        time % 60),
                500,
                750);
        batch.end();
    }

}
