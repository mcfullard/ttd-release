package za.ttd.tests;

import org.junit.Test;
import za.ttd.game.Player;
import za.ttd.game.Security;

import static org.junit.Assert.*;

/**
 * @author minnaar
 * @since 2015/10/02.
 */
public class SecurityTest {
    @Test
    public void testSecurity() throws Exception {
        Player player = Player.getInstance();
        player.setName("testName");
        player.setHighestLevel(1);
        player.setHighestScore(0);
        player.setLives(3);
        Security.generateHash(player, "t3stP@ssw0RD");
        assertTrue(Security.hashMatch(player, "t3stP@ssw0RD"));
        assertFalse(Security.hashMatch(player, "testP@ssw0RD"));
    }
}