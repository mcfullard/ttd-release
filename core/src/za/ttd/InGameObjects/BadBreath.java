package za.ttd.InGameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by s213391244 on 7/24/2015.
 */
public class BadBreath extends Enemy {
    static final int score = 100;

    public BadBreath(Position position, int speed) {
        super(position);
        this.setMovementSpeed(speed);
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
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }
}
