package za.ttd.tests;

import org.junit.Test;
import za.ttd.database.ConnectDB;
import za.ttd.game.Player;

import static org.junit.Assert.*;

public class ConnectTest {

    @Test
    public void TestRunSQL() throws Exception
    {
        assertTrue(ConnectDB.TestConnectivity());
    }

    @Test
    public void TestAddPlayer() throws Exception {
        Player player = new Player("testName", 0, 1, 2);
        ConnectDB.addPlayer(player);
        Player returnedPlayer = ConnectDB.getPlayer("testName");
    }
}