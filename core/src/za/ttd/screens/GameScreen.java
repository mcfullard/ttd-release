package za.ttd.screens;

import za.ttd.game.Game;
import za.ttd.game.Level;

/**
 * @author minnaar
 * @since 2015/07/20.
 */
public class GameScreen extends AbstractScreen {

    private Level level;
    
    public GameScreen(Game game) {
        super(game);

        this.level = game.getLevel();
    }

    @Override
    public void render(float delta) {
        level.render();
    }
}
