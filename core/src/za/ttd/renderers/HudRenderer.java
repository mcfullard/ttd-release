package za.ttd.renderers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import za.ttd.game.Game;
import za.ttd.game.Player;

public class HudRenderer {
    private BitmapFont lblLvlScore, lblLives, lblLvlNum;
    private Batch batch;
    private int scale;

    public HudRenderer() {
        batch = new SpriteBatch();
        lblLvlScore = new BitmapFont();
        lblLives = new BitmapFont();
        lblLvlNum = new BitmapFont();
        //Tell SpriteBatch to render in the co-ordinate system specified by screen
        batch.getProjectionMatrix().setToOrtho2D(0,0, Game.WIDTH, Game.HEIGHT);
        scale = 1;
    }

    public void render() {
        int width = Game.WIDTH;
        int height = Game.HEIGHT;
        batch.begin();
        lblLvlScore.draw(batch, "LEVEL SCORE : " + Player.getInstance().scoring.getCurrentLvlScore(), width*.05f, height*.95f);
        lblLvlNum.draw(batch, "LEVEL : " + Player.getInstance().getHighestLevel(), width*.45f, height*.95f);
        lblLives.draw(batch, "LIVES : " + Player.getInstance().getLives(), width*.85f, height*.95f);
        batch.end();
    }

}
