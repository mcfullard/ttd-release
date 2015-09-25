package za.ttd.screens;

import za.ttd.database.ConnectDB;
import za.ttd.game.Game;

/**
 * Created by s213391244 on 9/18/2015.
 */
public class PlayerStatisticsScreen extends AbstractScreen {
    ConnectDB connectDB;
    ConnectDB.Statistics statistics;
    public PlayerStatisticsScreen(Game game) {
        super(game);
        try {
            statistics = connectDB.getStatistics(game.getPlayerID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {

    }
}
