package za.ttd.characters;

import com.badlogic.gdx.ai.msg.MessageManager;
import za.ttd.characters.objects.Position;
import za.ttd.characters.states.MessageType;

public class Thomas extends Actor {

    public Thomas(Position position, float speed) {
        super(position, speed, "Thomas");
    }

    @Override
    public void update() {
        super.update();
        checkCollisions();
    }

    /*
    * Check Thomas' collisions with the rest of the game objects
    * Depending on who or what he collides with, do the related methods*/
    private void checkCollisions() {
        Position thomPos = this.getPosition();

        if (gameItems.get(thomPos) instanceof Plaque) {
            gameItems.remove(thomPos);
            MessageManager.getInstance().dispatchMessage(this, MessageType.PLAQUE_COLLECTED);
        }

        if (gameItems.get(thomPos) instanceof Mouthwash) {
            gameItems.remove(thomPos);
            MessageManager.getInstance().dispatchMessage(this, MessageType.MOUTHWASH_COLLECTED);
            MessageManager.getInstance().dispatchMessage(10, this, MessageType.MOUTHWASH_EXPIRED);
        }

        if (gameItems.get(thomPos) instanceof Toothbrush){
            gameItems.remove(thomPos);
            MessageManager.getInstance().dispatchMessage(this, MessageType.TOOTHBRUSH_COLLECTED);
        }
    }
}
