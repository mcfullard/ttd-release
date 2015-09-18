package za.ttd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import za.ttd.characters.objects.Direction;

public class Controls {

    public interface startLevelListener {
        void startLevel(boolean status);
    }

    Direction direction;
    public startLevelListener startLevel;

    public Controls(startLevelListener listener) {
        direction = Direction.NONE;
        startLevel = listener;
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

        if (direction == Direction.NONE)
            startLevel.startLevel(false);
        else
            startLevel.startLevel(true);
    }

    public boolean keyPressed() {
        return direction != Direction.NONE;
    }

    public Direction getDirection() {
        return direction;
    }

    public void reset() {
        direction = Direction.NONE;
    }
}
