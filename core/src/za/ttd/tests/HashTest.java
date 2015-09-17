package za.ttd.tests;

import org.junit.Test;
import za.ttd.characters.InGameObject;
import za.ttd.characters.Plaque;
import za.ttd.characters.Player;
import za.ttd.characters.objects.Position;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bas on 17/09/2015.
 */
public class HashTest {

    @Test
    public void hashMapTest() throws Exception{

        Map<Position, Float> hashMap = new HashMap<>();

        Position pos1 = new Position(1, 1);
        Position pos2 = new Position(2, 2);

        Float num = 10f;

        hashMap.put(pos1, num);

        assertEquals(num, hashMap.get(pos1));
        //assertEquals(player2, hashMap.get(pos2));

        pos1.setX(22.3f);
        pos1.setY(22.3f);

        assertEquals(num, hashMap.get(pos1));

    }
}
