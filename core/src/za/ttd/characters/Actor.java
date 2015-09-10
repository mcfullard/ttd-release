package za.ttd.characters;

import za.ttd.characters.objects.Position;
import za.ttd.renderers.Renderable;

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
