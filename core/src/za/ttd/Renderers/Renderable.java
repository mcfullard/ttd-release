package za.ttd.Renderers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public interface Renderable {

    public Texture getTexture();

    public Batch getAnimation();

    public int getX();

    public int getY();
}
