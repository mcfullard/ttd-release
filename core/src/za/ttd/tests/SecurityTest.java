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
        Player player = new Player("testName", 0, 1, 2);
        Security.generateHash(player, "t3stP@ssw0RD");
        assertTrue(Security.hashMatch(player, "t3stP@ssw0RD"));
        assertFalse(Security.hashMatch(player, "testP@ssw0RD"));
    }
}