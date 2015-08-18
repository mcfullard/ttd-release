package za.ttd.InGameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Player extends Actor {

    private  Texture thomas;

    public Player(Position position, int speed) {
        super(position);
        setMovementSpeed(speed);

        thomas = new Texture(Gdx.files.internal("core/assets/textures/in/characters/thomFull.jpg"));
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
    public int getX() {
        return position.getIntX();
    }

    @Override
    public int getY() {
        return position.getIntY();
    }


}
