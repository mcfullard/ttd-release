package za.ttd.Renderers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Renderable {

    public Texture getTexture();

    public SpriteBatch getAnimation();

    public int getX();

    public int getY();
}
