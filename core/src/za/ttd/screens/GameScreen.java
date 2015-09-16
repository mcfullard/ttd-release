package za.ttd.screens;

import za.ttd.level.Level;
import za.ttd.ttd;

/**
 * @author minnaar
 * @since 2015/07/20.
 */
public class GameScreen extends AbstractScreen {

    private Level level;
    
    public GameScreen(ttd game) {
        super(game);

        this.level = game.getLevel();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        level.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {

    }
}
