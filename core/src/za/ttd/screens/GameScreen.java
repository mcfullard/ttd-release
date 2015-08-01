package za.ttd.screens;

import com.badlogic.gdx.Game;
import za.ttd.MapDraw.MazeRenderer;

/**
 * @author minnaar
 * @since 2015/07/20.
 */
public class GameScreen extends AbstractScreen {

    private MazeRenderer renderer;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {

        int[][] maze = new int[12][4];
        renderer = new MazeRenderer(maze);

    }

    @Override
    public void render(float delta) {
        renderer.render();
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
