package za.ttd.InGameObjects;

/**
 * Created by s213391244 on 7/24/2015.
 */
public class ToothDecay extends Enemy {
    static final int score = 1000;

    public ToothDecay(Position position, int speed){
        super(position);
        this.setMovementSpeed(speed);
    }
}
