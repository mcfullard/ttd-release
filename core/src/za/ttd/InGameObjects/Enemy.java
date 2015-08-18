package za.ttd.InGameObjects;

/**
 * Created by s213391244 on 7/24/2015.
 */
public abstract class Enemy extends Actor {
    //"Ghost" states- fairly self-explanatory
    static final int AGGRESSIVE = 2;
    static final int PASSIVE = 3;
    static final int RETREAT = 4;
    static final int VULNERABLE = 5;

    public Enemy(Position position, TryUpdateListener listener) {
        super(position, listener);
    }
}
