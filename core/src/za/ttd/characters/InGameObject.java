package za.ttd.characters;

import za.ttd.characters.objects.Position;

/**
 * Created by s213391244 on 7/30/2015.
 */
public class InGameObject {
    protected Position position;

    public InGameObject(Position position) {
        this.position = position;
    }

    public Position getPosition() {return position;}

    public boolean compareBase(InGameObject other) {
        return this.position.compareBase(other.getPosition());
    }
}
