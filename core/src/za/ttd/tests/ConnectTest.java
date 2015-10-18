package za.ttd.tests;

import javafx.util.Pair;
import org.junit.Test;
import za.ttd.database.ConnectDB;
import za.ttd.game.Player;
import za.ttd.game.Security;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class ConnectTest {

    @Test
    public void testRunSQL() throws Exception
    {
        assertTrue(ConnectDB.TestConnectivity());
    }

    @Test
    public void testGetHighScores() throws Exception {
        List<Pair<String, Integer>> highScores = ConnectDB.getHighestScoringPlayers();
        assertEquals(2, highScores.size());
    }
}