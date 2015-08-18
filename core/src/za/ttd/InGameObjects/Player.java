package za.ttd.InGameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Player extends Actor {

    protected static final String THOM_FULL = "core/assets/textures/in/characters/thomFull.jpg";
    protected Texture thomas;
    protected Direction direction = Direction.NONE;

    public Player(Position position, TryMoveListener listener, int speed) {
        super(position, listener);
        setMovementSpeed(speed);
        thomas = new Texture(Gdx.files.internal(THOM_FULL));
    }

    @Override
    public void update() {
        processKeys();
        Position next = getNextPosition();
        if(tryMoveListener.tryMove(position, next)) {
            position.setX(next.getX());
            position.setY(next.getY());
        }
        direction = Direction.NONE;
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

    private void processKeys() {
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            direction = Direction.UP;
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            direction = Direction.DOWN;
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            direction = Direction.LEFT;
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            direction = Direction.RIGHT;
    }

    private Position getNextPosition() {
        Position next = position.clone();
        switch (direction) {
            case UP:
                next.increaseX(movementSpeed);
                break;
            case DOWN:
                next.increaseX(-movementSpeed);
                break;
            case LEFT:
                next.increaseY(movementSpeed);
                break;
            case RIGHT:
                next.increaseY(-movementSpeed);
                break;
            default:
                break;
        }
        return next;
    }
}
