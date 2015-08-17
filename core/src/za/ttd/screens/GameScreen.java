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

    }

    @Override
    public void render(float delta) {

        /*//Clear screen with black colour
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);*/

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
