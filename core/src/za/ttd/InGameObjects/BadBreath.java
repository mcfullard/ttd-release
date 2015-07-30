package za.ttd.InGameObjects;

/**
 * Created by s213391244 on 7/24/2015.
 */
public class BadBreath extends Enemy {
    static final int score = 100;

    public BadBreath(float x, float y, int speed) {
        this.setPosX(x);
        this.setPosY(y);
        this.setMovementSpeed(speed);
    }
}
