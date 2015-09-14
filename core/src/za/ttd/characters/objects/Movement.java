package za.ttd.characters.objects;

import za.ttd.mapgen.Map;

/**
 * Created by Bas on 19/08/2015.
 */
public class Movement {

    protected Position position;
    private float speed;
    private Map map;
    private boolean moving;

    public Movement(Map map) {
        this.map = map;
        moving = false;
    }

    public boolean Move(Position position, float speed, Direction curDirection, Direction nextDirection) {
        this.position = position;
        this.speed = speed;

        moving = true;
        switch (nextDirection) {
            case UP:
                if(tryMoveUp(curDirection))
                    curDirection = nextDirection;
                break;
            case DOWN:
                if(tryMoveDown(curDirection))
                    curDirection = nextDirection;
                break;
            case LEFT:
                if(tryMoveLeft(curDirection))
                    curDirection = nextDirection;
                break;
            case RIGHT:
                if(tryMoveRight(curDirection))
                    curDirection = nextDirection;
                break;
            default:
                moving = false;
                break;
        }

        switch (curDirection) {
            case UP:
                if (!moveUp())
                    moving = false;
                break;
            case DOWN:
                if (!moveDown())
                    moving = false;
                break;
            case LEFT:
                if (!moveLeft())
                    moving = false;
                break;
            case RIGHT:
                if (!moveRight())
                    moving = false;
                break;
            default:
                moving = false;
                break;
        }

        return moving;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean moveUp() {
        int y = position.getChangedFloorY(-speed);
        int x = position.getIntX();

        if (tryMove(x, y)) {
            position.increaseY(-speed);
            position.setX(position.getIntX());
            return true;
        }
        else
            position.increaseY(position.getIntY() - position.getY());

        return false;
    }

    public boolean moveDown() {
        int y = position.getChangedCeilY(speed);
        int x = position.getIntX();

        if (tryMove(x, y)) {
            position.increaseY(speed);
            position.setX(position.getIntX());
            return true;
        }
        else
            position.increaseY(position.getIntY()- position.getY());

        return false;
    }

    public boolean moveLeft() {
        int y = position.getIntY();
        int x = position.getChangedFloorX(-speed);

        if (tryMove(x, y)) {
            position.increaseX(-speed);
            position.setY(position.getIntY());
            return true;
        }
        else
            position.increaseX(position.getIntX() - position.getX());

        return false;
    }

    public boolean moveRight() {
        int y = position.getIntY();
        int x = position.getChangedCeilX(speed);

        if (tryMove(x, y)) {
            position.increaseX(speed);
            position.setY(position.getIntY());
            return true;
        }
        else
            position.increaseX(position.getIntX()-position.getX());

        return false;
    }


    public boolean tryMoveUp(Direction dir) {
        int y = position.getChangedFloorY(-speed);
        int x; // = position.getIntX();

        if (dir == Direction.LEFT)
            x = position.getCeilX();
        else
            x = position.getFloorX();

        return tryMove(x,y);
    }

    public boolean tryMoveDown(Direction dir) {
        int y = position.getChangedCeilY(speed);
        int x; // = position.getIntX();

        if (dir == Direction.LEFT)
            x = position.getCeilX();
        else
            x = position.getFloorX();

        return tryMove(x,y);
    }

    public boolean tryMoveLeft(Direction dir) {
        int y; // = position.getIntY();
        int x = position.getChangedFloorX(-speed);


        if (dir == Direction.UP)
            y = position.getCeilY();
        else
            y = position.getFloorY();

        return tryMove(x,y);
    }

    public boolean tryMoveRight(Direction dir) {
        int y; // = position.getIntY();
        int x = position.getChangedCeilX(speed);

        if (dir == Direction.UP)
            y = position.getCeilY();
        else
            y = position.getFloorY();

        return tryMove(x,y);
    }

    private boolean tryMove(int x, int y) {
        return !map.isWall(x,y);
    }


}
