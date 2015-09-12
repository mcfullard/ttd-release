package za.ttd.characters;

import za.ttd.characters.objects.Position;
import za.ttd.renderers.Renderable;

/**
 * Created by s213391244 on 7/24/2015.
 */
public abstract class Actor extends InGameObject implements Renderable {

    private final float x, y;

    public Actor(Position position) {
        super(position);
        x = position.getX();
        y = position.getY();
    }

    public void update() {

    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }

    public int getIntX() {
        return position.getIntX();
    }

    public int getIntY() {
        return position.getIntY();
    }

    public void resetPositions() {
        position.setX(x);
        position.setY(y);
    }
}
