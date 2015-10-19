package za.ttd.characters.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import za.ttd.characters.Enemy;
import za.ttd.characters.Plaque;

/**
 * @author minnaar
 * @since 2015/09/20.
 */
public enum EnemySpeedState implements State<Enemy> {

    NORMAL() {
        @Override
        public void enter(Enemy enemy) {
                enemy.normalSpeed();
        }

        @Override
        public void update(Enemy enemy) {
            if (enemy.getGameItems().get(enemy.getPosition()) instanceof Plaque)
                enemy.getSpeedStateMachine().changeState(FAST);
        }
    },
    SLOW(){
        @Override
        public void enter(Enemy enemy) {
            enemy.slow();
        }

        @Override
        public void update(Enemy enemy) {
            if (enemy.getGameItems().get(enemy.getPosition()) instanceof Plaque)
                enemy.normalSpeed();
        }

        @Override
        public boolean onMessage(Enemy enemy, Telegram telegram) {
            boolean status = super.onMessage(enemy, telegram);

            if (!status && telegram.message == MessageType.MOUTHWASH_EXPIRED) {
                enemy.getSpeedStateMachine().changeState(NORMAL);
                return true;
            }
            else
                return status;
        }
    },
    FAST(){
        @Override
        public void enter(Enemy enemy) {
            enemy.speedUp();
        }

        @Override
        public void update(Enemy enemy) {
            if (!(enemy.getGameItems().get(enemy.getPosition()) instanceof Plaque))
                enemy.getSpeedStateMachine().changeState(NORMAL);
        }
    }
    ;
    @Override
    public void exit(Enemy enemy) {

    }

    @Override
    public boolean onMessage(Enemy enemy, Telegram telegram) {

        switch(telegram.message){
            case MessageType.MOUTHWASH_COLLECTED:
                enemy.getSpeedStateMachine().changeState(SLOW);
                return true;
            case MessageType.LEVEL_RESET:
                enemy.getSpeedStateMachine().changeState(NORMAL);
                return true;
            default:
                return false;
        }
    }
}
