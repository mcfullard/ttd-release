package za.ttd.screens;

import com.badlogic.gdx.Game;
import za.ttd.level.Level;

/**
 * @author minnaar
 * @since 2015/07/20.
 */
public class GameScreen extends AbstractScreen {

    private Level level;

    public GameScreen(Game game) {
        super(game);
        level = new Level();
    }

    @Override
    public void show() {
        level.renderMap();
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
