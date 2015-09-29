package za.ttd.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import za.ttd.characters.InGameObject;
import za.ttd.game.Game;

import java.util.List;

public class CharacterRenderer{

    private int[][] maze;
    private int imgScale;
    private Batch batch;
    private List<Renderable> renderables;
    private float elapsedTime;

    public CharacterRenderer(int[][] maze, int imgScale) {
        this.maze = maze;
        this.imgScale = imgScale;
        this.batch = new SpriteBatch();
        this.elapsedTime = 0;

        //Tell SpriteBatch to render in the co-ordinate system specified by screen
        batch.getProjectionMatrix().setToOrtho2D(-85,-10, (int)(Game.WIDTH*1.25),(int)(Game.HEIGHT*1.25));
    }

    public void render(List<Renderable> renderables) {
        this.renderables = renderables;

        //Begin a new batch and draw the maze
        batch.begin();
        drawCharacters();
        batch.end();
    }

    //Draw characters to screen
    private void drawCharacters() {

        elapsedTime += Gdx.graphics.getDeltaTime();

        for (Renderable curRenderable : renderables) {
            if (((InGameObject)curRenderable).isAlive()) {
                float x = curRenderable.getX();
                float y = curRenderable.getY();

                TextureRegion characterStill = curRenderable.getTexture();
                Animation characterAnimation = curRenderable.getAnimation();

                if (characterAnimation == null)
                    batch.draw(characterStill, x * imgScale, (maze.length - y) * imgScale);
                else {
                    batch.draw(characterAnimation.getKeyFrame(elapsedTime, true), x * imgScale, (maze.length - y) * imgScale);
                }
            }
        }
    }
}
