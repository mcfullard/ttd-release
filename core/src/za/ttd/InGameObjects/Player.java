package za.ttd.InGameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Player extends Actor {

    private static final String THOM_FULL = "core/assets/textures/in/characters/thomFull.jpg";
    private Texture thomas;

    public Player(Position position, TryUpdateListener listener, int speed) {
        super(position, listener);
        setMovementSpeed(speed);
        thomas = new Texture(Gdx.files.internal(THOM_FULL));
    }

    @Override
    public void update() {
    }

    @Override
    public Texture getTexture() {
        return thomas;
    }

    @Override
    public Animation getAnimation() {
        return null;
    }

    @Override
    public float getX() {
        return position.getX();
    }

    @Override
    public float getY() {
        return position.getY();
    }


}
