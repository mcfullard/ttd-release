package za.ttd.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import za.ttd.MapDraw.MazeRenderer;
import za.ttd.mapgen.Grid;

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



        /*int[][] maze = new int[][]{
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                {0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
                {0,1,0,0,0,1,1,1,0,0,1,1,0,0,0,1,0,0,1,0},
                {0,1,0,0,0,0,1,0,0,0,1,1,0,1,1,1,0,0,1,0},
                {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
                {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        };*/


        renderer = new MazeRenderer(Grid.generateMap(9,9,3));

    }

    @Override
    public void render(float delta) {

        //Clear screen with black colour
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
