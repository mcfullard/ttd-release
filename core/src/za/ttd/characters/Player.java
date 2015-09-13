package za.ttd.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import za.ttd.characters.objects.Direction;
import za.ttd.characters.objects.Position;
import za.ttd.characters.objects.Movement;
import za.ttd.mapgen.Map;

public class Player extends Actor {

    private final String FILENAME = "core/assets/textures/in/characters/ThomL1.png";
    private Texture thomas;
    private  Movement movement;
    protected Direction curDirection, nextDirection;

    public Player(Position position, float speed) {
        super(position);
        setMovementSpeed(speed);
        thomas = new Texture(Gdx.files.internal(FILENAME));
        this.movement = new Movement(null, position, speed);
        curDirection = Direction.NONE;
        nextDirection = Direction.NONE;
    }

    public void setMovementMap(Map map) {
        movement.setMap(map);
    }

    @Override
    public void update() {
        processKeys();
        Move();
    }

    @Override
    public Texture getTexture() {
        return thomas;
    }

    @Override
    public Animation getAnimation() {
        return null;
    }

    /*
    * Get input from user*/
    private void processKeys() {
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            nextDirection = Direction.UP;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            nextDirection = Direction.DOWN;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            nextDirection = Direction.LEFT;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            nextDirection = Direction.RIGHT;

        if (curDirection == Direction.NONE)
            curDirection = nextDirection;
    }

    /*
    * Control movement of character, can remember next key presses*/
    private void Move() {
        switch (nextDirection) {
            case UP:
                if(movement.tryMoveUp(curDirection))
                    curDirection = nextDirection;
                break;
            case DOWN:
                if(movement.tryMoveDown(curDirection))
                    curDirection = nextDirection;
                break;
            case LEFT:
                if(movement.tryMoveLeft(curDirection))
                    curDirection = nextDirection;
                break;
            case RIGHT:
                if(movement.tryMoveRight(curDirection))
                    curDirection = nextDirection;
                break;
            default:
                break;
        }

        switch (curDirection) {
            case UP:
                movement.moveUp();
                break;
            case DOWN:
                movement.moveDown();
                break;
            case LEFT:
                movement.moveLeft();
                break;
            case RIGHT:
                movement.moveRight();
                break;
            default:
                break;
        }
    }
}
