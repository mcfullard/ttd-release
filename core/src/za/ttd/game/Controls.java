package za.ttd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import za.ttd.characters.objects.Direction;

public class Controls{

    private Direction direction;

    public Controls() {
        direction = Direction.NONE;
        //startLevel = listener;
    }


    //Get input from user
    public void processKeys() {
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            direction = Direction.UP;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            direction = Direction.DOWN;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            direction = Direction.LEFT;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            direction = Direction.RIGHT;
    }

    public boolean keyPressed() {
        if (direction == Direction.LEFT || direction == Direction.RIGHT)
            return true;
        else
            return false;
    }

    public Direction getDirection() {
        return direction;
    }

    public void reset() {
        direction = Direction.NONE;
    }
}
