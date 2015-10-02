package za.ttd.tests;

import org.junit.Test;
import za.ttd.database.ConnectDB;
import za.ttd.game.Player;
import za.ttd.game.Security;

import java.util.Random;

import static org.junit.Assert.*;

public class ConnectTest {

    @Test
    public void TestRunSQL() throws Exception
    {
        assertTrue(ConnectDB.TestConnectivity());
    }

    @Test
    public void TestAddPlayer() throws Exception {
        Random random = new Random();
        String randomString = Long.toString(random.nextLong());
        Player player = new Player(randomString, 0, 1, 2);
        Security.generateHash(player, "password");
        ConnectDB.addPlayer(player);
        Player player1 = ConnectDB.getPlayer(randomString);
        assertTrue(Security.hashMatch(player1, "password"));
        Player player2 = ConnectDB.getPlayer("noasdtapla1245yer");
        assertNull(player2);
    }
}