package za.ttd.InGameObjects;

import za.ttd.Renderers.Renderable;

/**
 * Created by s213391244 on 7/24/2015.
 */
public abstract class Actor extends InGameObject implements Renderable {

    protected TryMoveListener tryMoveListener;

    public interface TryMoveListener {
        public boolean tryMove(Position before, Position after);
    }

    public Actor(Position position, TryMoveListener listener) {
        super(position);
        tryMoveListener = listener;
    }

    public void update() {

    }

    public void kill() {

    }

    public void die() {
    }

}
