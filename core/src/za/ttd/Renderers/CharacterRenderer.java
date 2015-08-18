package za.ttd.Renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class CharacterRenderer{

    private Texture thomFull;
    private int[][] maze;
    private int path = 0, imgScale;
    private Batch batch;
    private List<Renderable> renderables;
    private float elapsedTime;

    public CharacterRenderer(int[][] maze, int imgScale) {
        this.maze = maze;
        this.imgScale = imgScale;
        this.batch = new SpriteBatch();
        this.elapsedTime = 0;
    }

    public void render(List<Renderable> renderables) {
        this.renderables = renderables;

        //Tell SpriteBatch to render in the co-ordinate system specified by screen
        batch.getProjectionMatrix().setToOrtho2D(-200,-100,1850,2400);

        //Begin a new batch and draw the maze
        batch.begin();
        drawCharacters();
        batch.end();
    }

    //Draw characters to screen
    private void drawCharacters() {

        elapsedTime += Gdx.graphics.getDeltaTime();

        for (Renderable curRenderable : renderables) {

            int x = curRenderable.getX();
            int y = curRenderable.getY();

            Texture characterStill = curRenderable.getTexture();
            Animation characterAnimation = curRenderable.getAnimation();


            if (maze[x][y] == path)
                if (characterStill != null)
                    batch.draw(characterStill, y * imgScale, (maze.length - x )* imgScale);
                else {
                    batch.draw(characterAnimation.getKeyFrame(elapsedTime, true), y * imgScale, (maze.length - x) * imgScale);
                }
        }
    }
}
