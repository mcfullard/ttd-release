package za.ttd.level;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import za.ttd.InGameObjects.InGameObject;
import za.ttd.InGameObjects.Position;
import za.ttd.MapDraw.MazeRenderer;
import za.ttd.mapgen.Grid;
import za.ttd.mapgen.Map;

/**
 *
 * The Level class contains references to all the characters in a level.
 *
 * @author minnaar
 * @since 2015/08/17.
 */
public class Level implements ApplicationListener {
    private Map map;
    private long seed;
    private java.util.Map<Position, InGameObject> gameObjects;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    int imgScale;
    private MazeRenderer mazeRenderer;

    public Level() {

        mazeRenderer = new MazeRenderer(Grid.generateMap(9, 9, 3));

    }


    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 2400, 2400);

        batch = new SpriteBatch();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        //Clear screen with black colour
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update camera matrices
        camera.update();

        //Tell SpriteBatch to render in the co-ordinate system specified by the camera
        batch.setProjectionMatrix(camera.combined);

        //Begin a new batch and draw the maze
        batch.begin();
        mazeRenderer.getBatch();
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
