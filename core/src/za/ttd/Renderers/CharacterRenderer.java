package za.ttd.Renderers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CharacterRenderer{

    private Texture thomFull;
    private int[][] maze;
    private int path = 0, imgScale;
    private Batch batch;

    public CharacterRenderer(int[][] maze, int imgScale) {
        this.maze = maze;
        this.imgScale = imgScale;
        batch = new SpriteBatch();
    }

    private void drawCharacters(Texture character, int x, int y) {

        if (maze[x][y] == path)
            batch.draw(character, y * imgScale, (maze.length - x )* imgScale);
    }
}
