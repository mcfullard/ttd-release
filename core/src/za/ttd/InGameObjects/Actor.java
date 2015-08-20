package za.ttd.InGameObjects;

import za.ttd.Renderers.Renderable;

/**
 * Created by s213391244 on 7/24/2015.
 */
public abstract class Actor extends InGameObject implements Renderable {

    public Actor(Position position) {
        super(position);
    }

    public void update() {

    }

    public void kill() {

    }

    public void die() {
    }

}
