package za.ttd.characters;

import za.ttd.characters.objects.Position;

public class BadBreath extends Enemy {


    public BadBreath(Position position, float speed, String actorName) {
        super(position, speed, actorName);
    }

    public boolean getVulnerability() {
        return super.vulnerable;
    }

}
