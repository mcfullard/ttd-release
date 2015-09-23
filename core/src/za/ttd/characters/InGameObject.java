package za.ttd.characters;

import za.ttd.characters.objects.Position;

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
