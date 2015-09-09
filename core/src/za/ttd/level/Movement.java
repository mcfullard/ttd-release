package za.ttd.level;

import za.ttd.InGameObjects.Direction;
import za.ttd.InGameObjects.Position;
import za.ttd.mapgen.Map;

/**
 * Created by Bas on 19/08/2015.
 */
public class Movement {

    protected Position position;
    private float speed;
    private Map map;

    public Movement(Map map, Position position, float speed) {
        this.position = position;
        this.speed = speed;
        this.map = map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void moveUp() {
        int y = (int)Math.floor((double)position.getY() - speed);
        int x = position.getIntX();

        if (tryMove(x, y)) {
            position.increaseY(-speed);
            position.setX(position.getIntX());
        }
        else
            position.increaseY(position.getIntY() - position.getY());
    }

    public void moveDown() {
        int y = (int)Math.ceil((double) position.getY() + speed);
        int x = position.getIntX();

        if (tryMove(x, y)) {
            position.increaseY(speed);
            position.setX(position.getIntX());
        }
        else
            position.increaseY(position.getIntY()- position.getY());
    }

    public void moveLeft() {
        int y = position.getIntY();
        int x = (int)Math.floor((double) position.getX() - speed);

        if (tryMove(x, y)) {
            position.increaseX(-speed);
            position.setY(position.getIntY());
        }
        else
            position.increaseX(position.getIntX() - position.getX());
    }

    public void moveRight() {
        int y = position.getIntY();
        int x = (int)Math.ceil((double) position.getX() + speed);

        if (tryMove(x, y)) {
            position.increaseX(speed);
            position.setY(position.getIntY());
        }
        else
            position.increaseX(position.getIntX()-position.getX());
    }


    public boolean tryMoveUp(Direction dir) {
        int y = (int)Math.floor((double)position.getY() - speed);
        int x; // = position.getIntX();

        if (dir == Direction.LEFT)
            x = (int)Math.ceil((double)position.getX());
        else
            x = (int)Math.floor((double) position.getX());

        return tryMove(x,y);
    }

    public boolean tryMoveDown(Direction dir) {
        int y = (int)Math.ceil((double) position.getY() + speed);
        int x; // = position.getIntX();

        if (dir == Direction.LEFT)
            x = (int)Math.ceil((double)position.getX());
        else
            x = (int)Math.floor((double)position.getX());

        return tryMove(x,y);
    }

    public boolean tryMoveLeft(Direction dir) {
        int y; // = position.getIntY();
        int x = (int)Math.floor((double) position.getX() - speed);


        if (dir == Direction.UP)
            y = (int)Math.ceil((double) position.getY());
        else
            y = (int)Math.floor((double) position.getY());

        return tryMove(x,y);
    }

    public boolean tryMoveRight(Direction dir) {
        int y; // = position.getIntY();
        int x = (int)Math.ceil((double) position.getX() + speed);

        if (dir == Direction.UP)
            y = (int)Math.ceil((double)position.getY());
        else
            y = (int)Math.floor((double)position.getY());

        return tryMove(x,y);
    }

    private boolean tryMove(int x, int y) {
        return !map.isWall(x,y);
    }


}
