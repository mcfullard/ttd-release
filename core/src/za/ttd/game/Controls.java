package za.ttd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import za.ttd.characters.objects.Direction;

public class Controls {

    Direction curDirection, nextDirection;

    public Controls() {
        curDirection = Direction.NONE;
        nextDirection = Direction.NONE;
    }

    public void update() {
        processKeys();
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

    public Direction getCurDirection() {
        return curDirection;
    }

    public Direction getNextDirection() {
        return nextDirection;
    }
}
