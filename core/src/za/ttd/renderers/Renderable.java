package za.ttd.renderers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Renderable {

    public TextureRegion getTexture();

    public Animation getAnimation();

    public float getX();

    public float getY();
}
