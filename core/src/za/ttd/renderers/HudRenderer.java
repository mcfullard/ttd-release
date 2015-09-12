package za.ttd.renderers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HudRenderer {
    private BitmapFont lblLvlScore, lblPlayTime;
    private Batch batch;

    public HudRenderer() {
        batch = new SpriteBatch();
        lblLvlScore = new BitmapFont();
        lblPlayTime = new BitmapFont();
        //Tell SpriteBatch to render in the co-ordinate system specified by screen
        batch.getProjectionMatrix().setToOrtho2D(0,0,600,800);
    }

    public void render(int lvlScore, long time) {

        batch.begin();
        lblLvlScore.draw(batch, "LEVEL SCORE: " + lvlScore, 10, 750);
        lblPlayTime.draw(batch, String.format("TIME %s:%s", time/60, time%60), 300, 750);
        batch.end();
    }

}
