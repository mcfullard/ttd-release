package za.ttd.InGameObjects;

/**
 * Created by s213391244 on 7/24/2015.
 */
public class Player extends Actor {

    static final int AWESOME = 6;
    static final int NOT_AWESOME = 7;

    public Player(Position position, int speed) {
        super(position);
        setMovementSpeed(speed);
    }


}
