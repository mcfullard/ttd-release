package za.ttd.InGameObjects;

import za.ttd.Renderers.Renderable;

/**
 * Created by s213391244 on 7/24/2015.
 */
public abstract class Collectible extends InGameObject implements Renderable {

    public Collectible(Position position) {
        super(position);
    }

    public void Pickup() {

    }

}
