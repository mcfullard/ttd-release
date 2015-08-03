package za.ttd.MapDraw;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MazeRenderer implements ApplicationListener{

    private Texture tocWall; //toc: Top Open Connector
    private Texture rocWall; //roc: Right Open Connector
    private Texture bocWall; //boc: Bottom Open Connector
    private Texture locWall; //loc: Left Open Connector
    private Texture hocWall; //hoc: Horizontal Open Connector
    private Texture vocWall; //voc: Vertical Open Connector
    private Texture tveWall; //tve: Top Vertical Edge
    private Texture rheWall; //rhe: Right Horizontal Edge
    private Texture bveWall; //bve: Bottom Vertical Edge
    private Texture lheWall; //lhe: Left Horizontal Edge
    private Texture tlcWall; //tle: Top Left Corner
    private Texture blcWall; //ble: Bottom Left Corner
    private Texture trcWall; //tre: Top Right Corner
    private Texture brcWall; //bre: Bottom Right Corner

    int imgScale = 64;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private int[][] maze;

    public MazeRenderer(int[][] maze) {
        this.maze = maze;
        create();
    }

    public void create() {
        /*Here we load the images for each level before the level shows up
          Images are 32X32 pixels*/

        tocWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/walls/tocWall.png"));
        rocWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/walls/rocWall.png"));
        bocWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/walls/bocWall.png"));
        locWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/walls/locWall.png"));
        hocWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/walls/hocWall.png"));
        vocWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/walls/vocWall.png"));
        tveWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/walls/tveWall.png"));
        rheWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/walls/rheWall.png"));
        bveWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/walls/bveWall.png"));
        lheWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/walls/lheWall.png"));
        tlcWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/walls/tlcWall.png"));
        blcWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/walls/blcWall.png"));
        trcWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/walls/trcWall.png"));
        brcWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/walls/brcWall.png"));

        //Create new camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 2400, 1800);

        //Sprite Batch for drawing images
        batch = new SpriteBatch();
    }

    @Override
    public void resize(int width, int height) {

    }

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
        drawMaze();
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    private void drawMaze() {

        int row = maze.length;
        int column = maze[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; ++j) {
                int curCell = maze[i][j];

                /* 1 = wall
                *  0 = path*/
                if (curCell == 1) {
                    int top = maze[i-1][j];
                    int right = maze[i][j+1];
                    int bottom = maze[i+1][j];
                    int left = maze[i][j-1];

                    //Wall type checker
                    if (top == 1 && right == 1 && bottom == 0 && left == 1)
                        batch.draw(tocWall, j * imgScale, (row - i) * imgScale);
                    else if (top == 1 && right == 1 && bottom == 1 && left == 0)
                        batch.draw(rocWall, j * imgScale, (row - i) * imgScale);
                    else if (top == 0 && right == 1 && bottom == 1 && left == 1)
                        batch.draw(bocWall, j * imgScale, (row - i) * imgScale);
                    else if (top == 1 && right == 0 && bottom == 1 && left == 1)
                        batch.draw(locWall, j * imgScale, (row - i) * imgScale);
                    else if (top == 0 && right == 1 && bottom == 0 && left == 1)
                        batch.draw(hocWall, j * imgScale, (row - i) * imgScale);
                    else if (top == 1 && right == 0 && bottom == 1 && left == 0)
                        batch.draw(vocWall, j * imgScale, (row - i) * imgScale);
                    else if (top == 1 && right == 0 && bottom == 0 && left == 0)
                        batch.draw(bveWall, j * imgScale, (row - i) * imgScale);
                    else if (top == 0 && right == 1 && bottom == 0 && left == 0)
                        batch.draw(lheWall, j * imgScale, (row - i) * imgScale);
                    else if (top == 0 && right == 0 && bottom == 1 && left == 0)
                        batch.draw(tveWall, j * imgScale, (row - i) * imgScale);
                    else if (top == 0 && right == 0 && bottom == 0 && left == 1)
                        batch.draw(rheWall, j * imgScale, (row - i) * imgScale);
                    else if (top == 0 && right == 1 && bottom == 1 && left == 0)
                        batch.draw(tlcWall, j * imgScale, (row - i) * imgScale);
                    else if (top == 1 && right == 1 && bottom == 0 && left == 0)
                        batch.draw(blcWall, j * imgScale, (row - i) * imgScale);
                    else if (top == 0 && right == 0 && bottom == 1 && left == 1)
                        batch.draw(trcWall, j * imgScale, (row - i) * imgScale);
                    else if (top == 1 && right == 0 && bottom == 0 && left == 1)
                        batch.draw(brcWall, j * imgScale, (row - i) * imgScale);
                }
            }
        }
    }

    public void dispose() {
        tocWall.dispose();
        rocWall.dispose();
        bocWall.dispose();
        locWall.dispose();
        hocWall.dispose();
        vocWall.dispose();
        tveWall.dispose();
        rheWall.dispose();
        bveWall.dispose();
        lheWall.dispose();
        tlcWall.dispose();
        blcWall.dispose();
        trcWall.dispose();
        brcWall.dispose();

        batch.dispose();
    }


}
