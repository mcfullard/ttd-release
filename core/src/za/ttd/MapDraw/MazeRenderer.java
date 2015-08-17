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
    private Texture blkWall; //bre: Block

    private int wall = 1, path = 0;

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

        //TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("core/assets/textures/out/texture.atlas"));

        /*tocWall = textureAtlas.findRegion("map/tocWall").getTexture();
        rocWall = textureAtlas.findRegion("map/rocWall").getTexture();
        bocWall = textureAtlas.findRegion("map/bocWall").getTexture();
        locWall = textureAtlas.findRegion("map/locWall").getTexture();
        hocWall = textureAtlas.findRegion("map/hocWall").getTexture();
        vocWall = textureAtlas.findRegion("map/vocWall").getTexture();
        tveWall = textureAtlas.findRegion("map/tveWall").getTexture();
        rheWall = textureAtlas.findRegion("map/rheWall").getTexture();
        bveWall = textureAtlas.findRegion("map/bveWall").getTexture();
        lheWall = textureAtlas.findRegion("map/lheWall").getTexture();
        tlcWall = textureAtlas.findRegion("map/tlcWall").getTexture();
        blcWall = textureAtlas.findRegion("map/blcWall").getTexture();
        trcWall = textureAtlas.findRegion("map/trcWall").getTexture();
        brcWall = textureAtlas.findRegion("map/brcWall").getTexture();*/

        tocWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/tocWall.png"));
        rocWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/rocWall.png"));
        bocWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/bocWall.png"));
        locWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/locWall.png"));
        hocWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/hocWall.png"));
        vocWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/vocWall.png"));
        tveWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/tveWall.png"));
        rheWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/rheWall.png"));
        bveWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/bveWall.png"));
        lheWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/lheWall.png"));
        tlcWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/tlcWall.png"));
        blcWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/blcWall.png"));
        trcWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/trcWall.png"));
        brcWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/brcWall.png"));
        blkWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/blkWall.png"));

        //Create new camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 2400, 2400);

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

                    int top;
                    int right;
                    int bottom;
                    int left;

                    try {
                       top = maze[i-1][j];
                    }
                    catch(IndexOutOfBoundsException e){
                        top = path;
                    }
                    try {
                        right = maze[i][j+1];
                    }
                    catch(IndexOutOfBoundsException e){
                        right = path;
                    }
                    try {
                        bottom = maze[i+1][j];
                    }
                    catch(IndexOutOfBoundsException e){
                        bottom = path;
                    }
                    try {
                        left = maze[i][j-1];
                    }
                    catch(IndexOutOfBoundsException e){
                        left = path;
                    }

                    //Wall type checker
                    if (top == wall && right == wall && bottom == path && left == wall)
                        batch.draw(tocWall, j * imgScale, (row - i) * imgScale);
                    else if (top == wall && right == wall && bottom == wall && left == path)
                        batch.draw(rocWall, j * imgScale, (row - i) * imgScale);
                    else if (top == path && right == wall && bottom == wall && left == wall)
                        batch.draw(bocWall, j * imgScale, (row - i) * imgScale);
                    else if (top == wall && right == path && bottom == wall && left == wall)
                        batch.draw(locWall, j * imgScale, (row - i) * imgScale);
                    else if (top == path && right == wall && bottom == path && left == wall)
                        batch.draw(hocWall, j * imgScale, (row - i) * imgScale);
                    else if (top == wall && right == path && bottom == wall && left == path)
                        batch.draw(vocWall, j * imgScale, (row - i) * imgScale);
                    else if (top == wall && right == path && bottom == path && left == path)
                        batch.draw(bveWall, j * imgScale, (row - i) * imgScale);
                    else if (top == path && right == wall && bottom == path && left == path)
                        batch.draw(lheWall, j * imgScale, (row - i) * imgScale);
                    else if (top == path && right == path && bottom == wall && left == path)
                        batch.draw(tveWall, j * imgScale, (row - i) * imgScale);
                    else if (top == path && right == path && bottom == path && left == wall)
                        batch.draw(rheWall, j * imgScale, (row - i) * imgScale);
                    else if (top == path && right == wall && bottom == wall && left == path)
                        batch.draw(tlcWall, j * imgScale, (row - i) * imgScale);
                    else if (top == wall && right == wall && bottom == path && left == path)
                        batch.draw(blcWall, j * imgScale, (row - i) * imgScale);
                    else if (top == path && right == path && bottom == wall && left == wall)
                        batch.draw(trcWall, j * imgScale, (row - i) * imgScale);
                    else if (top == wall && right == path && bottom == path && left == wall)
                        batch.draw(brcWall, j * imgScale, (row - i) * imgScale);
                    else if(top == path && right == path && bottom == path && left == path)
                        batch.draw(blkWall, j * imgScale, (row - i) * imgScale);
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
