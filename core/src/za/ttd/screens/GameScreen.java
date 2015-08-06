package za.ttd.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import za.ttd.MapDraw.MazeRenderer;
import za.ttd.mapgen.Map;

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


        /*Map map = new Map(20, 50, 2);

        int[] column = new int[20];
        int[] row = new int[52];

        for (int i = 0; i < 20; i++) {
            column[i]  = 0;
        }

        for (int i = 0; i < 52; i++) {
            row[i] = 0;
        }

        map.insertCol(column, 0);
        map.insertCol(column, map.getMap()[0].length-1);

        map.insertRow(row, 0);
        map.insertRow(row, map.getMap().length-1);

        int[][] maze = map.getMap();*/


        int[][] maze = new int[][]{
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                //{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                {0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                /*{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
                {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
                {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
                {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},*/
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        };
        renderer = new MazeRenderer(maze);

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
