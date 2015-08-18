package za.ttd.Renderers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public interface Renderable {

    public Texture getTexture();

    public Animation getAnimation();

    public float getX();

    public float getY();
}
