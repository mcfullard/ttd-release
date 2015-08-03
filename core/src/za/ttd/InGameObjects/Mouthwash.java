package za.ttd.InGameObjects;

/**
 * Created by s213391244 on 7/24/2015.
 */
public class Mouthwash extends Collectible {
    static final int score = 50;

    public Mouthwash(float x, float y, int speed){
        setPosX(x);
        setPosY(y);
        setMovementSpeed(speed);
    }
}
