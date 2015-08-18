package za.ttd.InGameObjects;

import za.ttd.Renderers.Renderable;

/**
 * Created by s213391244 on 7/24/2015.
 */
public abstract class Actor extends InGameObject implements Renderable {

    protected TryUpdateListener tryUpdateListener;

    public interface TryUpdateListener {
        public boolean tryUpdate(Position position);
    }

    public Actor(Position position, TryUpdateListener listener) {
        super(position);
        tryUpdateListener = listener;
    }

    public void update() {

    }

    public void kill() {

    }

    public void die() {
    }

}
