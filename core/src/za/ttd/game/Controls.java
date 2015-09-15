package za.ttd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import za.ttd.characters.objects.Direction;

public class Controls {

    Direction direction;

    public Controls() {
        direction = Direction.NONE;
    }

    public void update() {
        processKeys();
    }

    /*
    * Get input from user*/
    private void processKeys() {
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            direction = Direction.UP;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            direction = Direction.DOWN;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            direction = Direction.LEFT;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            direction = Direction.RIGHT;
    }

    public Direction getDirection() {
        return direction;
    }
}
