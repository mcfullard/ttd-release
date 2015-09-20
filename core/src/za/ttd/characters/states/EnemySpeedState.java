package za.ttd.characters.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.Enemy;

/**
 * @author minnaar
 * @since 2015/09/20.
 */
public enum EnemySpeedState implements State<Enemy> {
    SLOW(){

    },
    FAST(){

    }
    ;

    @Override
    public void enter(Enemy entity) {

    }

    @Override
    public void update(Enemy entity) {

    }

    @Override
    public void exit(Enemy entity) {

    }

    @Override
    public boolean onMessage(Enemy entity, Telegram telegram) {
        return false;
    }
}
