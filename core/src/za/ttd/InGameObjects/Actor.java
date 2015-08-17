package za.ttd.InGameObjects;

/**
 * Created by s213391244 on 7/24/2015.
 */
public abstract class Actor extends InGameObject {
    public Actor(Position position) {
        super(position);
    }

    public void Move(float x, float y) {
        if (x == 0) {
            moveY(y);
        } else{
            moveX(x);
        }
    }

    public void Kill() {

    }

    public void Die() {
    }

}
