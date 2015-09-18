package za.ttd.characters.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.ToothDecay;

/**
 * @author minnaar
 * @since 2015/09/18.
 */
public enum ToothDecayState implements State<ToothDecay> {
    ;

    @Override
    public void enter(ToothDecay entity) {

    }

    @Override
    public void update(ToothDecay entity) {

    }

    @Override
    public void exit(ToothDecay entity) {

    }

    @Override
    public boolean onMessage(ToothDecay entity, Telegram telegram) {
        return false;
    }
}
