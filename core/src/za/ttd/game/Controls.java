package za.ttd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.TelegramProvider;
import com.badlogic.gdx.ai.msg.Telegraph;
import za.ttd.characters.objects.Direction;
import za.ttd.characters.states.MessageType;

public class Controls implements Telegraph, TelegramProvider{

    private Direction direction;

    public Controls() {
        direction = Direction.NONE;
        registerSelfAsProvider();
    }


    //Get input from user
    public void processKeys() {
        if(Gdx.input.isKeyPressed(Player.Controls.UP))
            direction = Direction.UP;
        if(Gdx.input.isKeyPressed(Player.Controls.DOWN))
            direction = Direction.DOWN;
        if(Gdx.input.isKeyPressed(Player.Controls.LEFT))
            direction = Direction.LEFT;
        if(Gdx.input.isKeyPressed(Player.Controls.RIGHT))
            direction = Direction.RIGHT;
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            MessageManager.getInstance().dispatchMessage(this, MessageType.LEVEL_PAUSED);

    }

    private void registerSelfAsProvider() {
        MessageManager.getInstance().addProviders(this,
                MessageType.LEVEL_PAUSED);
    }

    public boolean keyPressed() {
            return direction != Direction.NONE;
    }

    public Direction getDirection() {
        return direction;
    }

    public void reset() {
        direction = Direction.NONE;
    }

    @Override
    public Object provideMessageInfo(int msg, Telegraph receiver) {
        return null;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        return false;
    }
}
