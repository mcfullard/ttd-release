package za.ttd.InGameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class BadBreath extends Enemy {
    static final int score = 100;

    public BadBreath(Position position, TryMoveListener listener, int speed) {
        super(position, listener);
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
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }
}
