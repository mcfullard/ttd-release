package za.ttd.characters;

import za.ttd.characters.objects.Position;

/**
 * Created by s213391244 on 7/30/2015.
 */
public class InGameObject {
    protected Position position;
    protected boolean alive;

    public InGameObject(Position position) {
        this.position = position;
        alive = true;
    }

    public Position getPosition() {return position;}

    public boolean isAlive() {
        return alive;
    }
}
