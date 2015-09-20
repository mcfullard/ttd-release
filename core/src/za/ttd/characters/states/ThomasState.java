package za.ttd.characters.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.Thomas;

/**
 * @author minnaar
 * @since 2015/09/20.
 */
public enum ThomasState implements State<Thomas> {
    ;
    @Override
    public void enter(Thomas thomas) {

    }

    @Override
    public void update(Thomas thomas) {

    }

    @Override
    public void exit(Thomas thomas) {

    }

    @Override
    public boolean onMessage(Thomas thomas, Telegram telegram) {
        return false;
    }
}
