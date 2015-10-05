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
    private Player player;

    public Controls() {
        direction = Direction.NONE;
        registerSelfAsProvider();
        player = Game.getInstance().getPlayer();
    }


    //Get input from user
    public void processKeys() {
        if(Gdx.input.isKeyPressed(player.controls.getUp()))
            direction = Direction.UP;
        if(Gdx.input.isKeyPressed(player.controls.getDown()))
            direction = Direction.DOWN;
        if(Gdx.input.isKeyPressed(player.controls.getLeft()))
            direction = Direction.LEFT;
        if(Gdx.input.isKeyPressed(player.controls.getRight()))
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
