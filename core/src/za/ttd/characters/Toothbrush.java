package za.ttd.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import za.ttd.characters.objects.Position;

/**
 * Created by s213391244 on 7/24/2015.
 */
public class Toothbrush extends Collectible {
    public Toothbrush(Position position) {
        super(position);
    }

    @Override
    public Texture getTexture() {
        return null;
    }

    @Override
    public Animation getAnimation() {
        return null;
    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }
}
