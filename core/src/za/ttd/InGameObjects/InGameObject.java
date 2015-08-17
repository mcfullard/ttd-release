package za.ttd.InGameObjects;

/**
 * Created by s213391244 on 7/30/2015.
 */
public class InGameObject {
    private Position position;
    private int movementSpeed;

    static final int ALIVE = 1;
    static final int DEAD = 0;

    public InGameObject(Position position) {
        this.position = position;
        this.movementSpeed = 0;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }
    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void moveX(float x) {
        position.increaseX(x);
    }
    public void moveY(float y){
        position.increaseY(y);
    }
}
