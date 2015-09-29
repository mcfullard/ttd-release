package za.ttd.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import za.ttd.game.Assets;

public class MazeRenderer {

    private int wall = 1, path = 0;
    private Assets assets;
    private Color color;
    int imgScale;

    //private OrthographicCamera camera;
    private SpriteBatch batch;

    private int[][] maze;

    public MazeRenderer(int[][] maze, int imgScale, Color color) {
        this.maze = maze;
        this.imgScale = imgScale;
        this.color = color;
        assets = Assets.getInstance();
        create();
    }

    public void create() {
        //Sprite Batch for drawing images
        batch = new SpriteBatch();
        //Tell SpriteBatch to render in the co-ordinate system specified by screen
        batch.getProjectionMatrix().setToOrtho2D(-200,-100,925,1200);
    }

    public void render() {
        //Clear screen with black colour
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Begin a new batch and draw the maze
        batch.setColor(color);
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
                    if (top == wall && right == wall && bottom != wall && left == wall)
                        batch.draw(assets.getTexture("tocWall"), j * imgScale, (row - i) * imgScale);
                    else if (top == wall && right == wall && bottom == wall && left != wall)
                        batch.draw(assets.getTexture("rocWall"), j * imgScale, (row - i) * imgScale);
                    else if (top != wall && right == wall && bottom == wall && left == wall)
                        batch.draw(assets.getTexture("bocWall"), j * imgScale, (row - i) * imgScale);
                    else if (top == wall && right != wall && bottom == wall && left == wall)
                        batch.draw(assets.getTexture("locWall"), j * imgScale, (row - i) * imgScale);
                    else if (top != wall && right == wall && bottom != wall && left == wall)
                        batch.draw(assets.getTexture("hocWall"), j * imgScale, (row - i) * imgScale);
                    else if (top == wall && right != wall && bottom == wall && left != wall)
                        batch.draw(assets.getTexture("vocWall"), j * imgScale, (row - i) * imgScale);
                    else if (top == wall && right != wall && bottom != wall && left != wall)
                        batch.draw(assets.getTexture("bveWall"), j * imgScale, (row - i) * imgScale);
                    else if (top != wall && right == wall && bottom != wall && left != wall)
                        batch.draw(assets.getTexture("lheWall"), j * imgScale, (row - i) * imgScale);
                    else if (top != wall && right != wall && bottom == wall && left != wall)
                        batch.draw(assets.getTexture("tveWall"), j * imgScale, (row - i) * imgScale);
                    else if (top != wall && right != wall && bottom != wall && left == wall)
                        batch.draw(assets.getTexture("rheWall"), j * imgScale, (row - i) * imgScale);
                    else if (top != wall && right == wall && bottom == wall && left != wall)
                        batch.draw(assets.getTexture("tlcWall"), j * imgScale, (row - i) * imgScale);
                    else if (top == wall && right == wall && bottom != wall && left != wall)
                        batch.draw(assets.getTexture("blcWall"), j * imgScale, (row - i) * imgScale);
                    else if (top != wall && right != wall && bottom == wall && left == wall)
                        batch.draw(assets.getTexture("trcWall"), j * imgScale, (row - i) * imgScale);
                    else if (top == wall && right != wall && bottom != wall && left == wall)
                        batch.draw(assets.getTexture("brcWall"), j * imgScale, (row - i) * imgScale);
                    else if(top != wall && right != wall && bottom != wall && left != wall)
                        batch.draw(assets.getTexture("blkWall"), j * imgScale, (row - i) * imgScale);
                    else if(top == wall && right == wall && bottom == wall && left == wall)
                        batch.draw(assets.getTexture("empWall"), j * imgScale, (row - i) * imgScale);
                }
            }
        }
    }
}
