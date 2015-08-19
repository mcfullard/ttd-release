package za.ttd.level;

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

        if (tryMove(x, y))
            position.increaseY(-speed);
        else
            position.setY((float) Math.floor(position.getY()));
    }

    public void moveDown() {
        int y = (int)Math.ceil((double) position.getY() + speed);
        int x = position.getIntX();

        if (tryMove(x, y))
            position.increaseY(speed);
        else
            position.setY((float) Math.ceil(position.getY()));
    }

    public void moveLeft() {
        int y = position.getIntY();
        int x = (int)Math.floor((double) position.getX() - speed);

        if (tryMove(x, y))
            position.increaseX(-speed);
        else
            position.setX((float)Math.floor(position.getX()));
    }

    public void moveRight() {
        int y = position.getIntY();
        int x = (int)Math.ceil((double) position.getX() + speed);

        if (tryMove(x, y))
            position.increaseX(speed);
        else
            position.setX((float) Math.ceil(position.getX()));
    }

    private boolean tryMove(int x, int y) {
        if(map.isWall(x, y))
            return false;
        else
            return true;
    }


}
