package za.ttd.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MazeRenderer {

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
    private Texture empWall; //bre: empty

    private int wall = 1, path = 0;

    int imgScale;

    //private OrthographicCamera camera;
    private SpriteBatch batch;

    private int[][] maze;

    public MazeRenderer(int[][] maze, int imgScale) {
        this.maze = maze;
        this.imgScale = imgScale;
        create();
    }

    public void create() {
        /*Here we load the images for each level before the level shows up
          Images are 64X64 pixels*/

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
        empWall = new Texture(Gdx.files.internal("core/assets/textures/in/map/empWall.png"));

        //Sprite Batch for drawing images
        batch = new SpriteBatch();
    }

    public void render() {
        //Clear screen with black colour
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Tell SpriteBatch to render in the co-ordinate system specified by screen
        batch.getProjectionMatrix().setToOrtho2D(-200,-100,1850,2400);

        //Begin a new batch and draw the maze
        batch.begin();
        drawMaze();
        batch.end();
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
                    else if(top == wall && right == wall && bottom == wall && left == wall)
                        batch.draw(empWall, j * imgScale, (row - i) * imgScale);
                }
            }
        }
    }
}
