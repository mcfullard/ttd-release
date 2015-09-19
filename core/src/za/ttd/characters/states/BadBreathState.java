package za.ttd.characters.states;

import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.BadBreath;
import com.badlogic.gdx.ai.fsm.State;

/**
 * @author minnaar
 * @since 2015/09/18.
 */
public enum BadBreathState implements State<BadBreath> {
    ;

    @Override
    public void enter(BadBreath entity) {

    }

    @Override
    public void update(BadBreath entity) {

    }

    @Override
    public void exit(BadBreath entity) {

    }

    @Override
    public boolean onMessage(BadBreath entity, Telegram telegram) {
        return false;
    }
}
