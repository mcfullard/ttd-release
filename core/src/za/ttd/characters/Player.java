package za.ttd.characters;

import za.ttd.characters.objects.Position;

public class Player extends Actor {

    public Player(Position position, float speed) {
        super(position, speed, "Thomas");
    }

    @Override
    public void update() {
        super.update();
    }
}
