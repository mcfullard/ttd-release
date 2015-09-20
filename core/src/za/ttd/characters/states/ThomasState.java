package za.ttd.characters.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.Player;

/**
 * @author minnaar
 * @since 2015/09/20.
 */
public enum ThomasState implements State<Player> {
    ;
    @Override
    public void enter(Player thomas) {

    }

    @Override
    public void update(Player thomas) {

    }

    @Override
    public void exit(Player thomas) {

    }

    @Override
    public boolean onMessage(Player thomas, Telegram telegram) {
        return false;
    }
}
