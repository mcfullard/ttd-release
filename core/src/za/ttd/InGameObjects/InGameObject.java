package za.ttd.InGameObjects;

/**
 * Created by s213391244 on 7/30/2015.
 */
public class InGameObject {
    private float posX, posY;
    private int movementSpeed;

    static final int ALIVE = 1;
    static final int DEAD = 0;

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void moveX(float x) {
        posX = +x;
    }
    public void moveY(float y){
        posY = +y;
    }
}
