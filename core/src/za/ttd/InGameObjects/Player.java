package za.ttd.InGameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import za.ttd.level.Movement;
import za.ttd.mapgen.Map;

public class Player extends Actor {

    protected static final String FILENAME = "core/assets/textures/in/characters/thomFull.jpg";
    protected Texture thomas;
    private  Movement movement;

    public Player(Position position, float speed) {
        super(position);
        setMovementSpeed(speed);
        thomas = new Texture(Gdx.files.internal(FILENAME));
        this.movement = new Movement(null, position, speed);
    }

    public void setMovementMap(Map map) {
        movement.setMap(map);
    }

    @Override
    public void update() {
        processKeys();
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
            movement.moveUp();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            movement.moveDown();
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            movement.moveLeft();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            movement.moveRight();
    }
}
