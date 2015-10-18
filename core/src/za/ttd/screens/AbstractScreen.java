package za.ttd.screens;

import com.badlogic.gdx.Screen;
import za.ttd.game.Game;

/**
 * @author minnaar
 * @since 20 July 2015
 */
public abstract class AbstractScreen implements Screen {
    public AbstractScreen() {
        Game.getInstance();
    }

    @Override
    public void resize (int width, int height) {
    }

    @Override
    public void show () {
    }

    @Override
    public void hide () {
    }

    @Override
    public void pause () {

    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
    }
}
