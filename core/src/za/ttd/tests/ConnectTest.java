package za.ttd.tests;

import org.junit.Test;
import za.ttd.database.ConnectDB;
import static org.junit.Assert.*;

public class ConnectTest {

    @Test
    public void TestRunSQL()throws Exception
    {
        assertTrue(ConnectDB.TestConnectivity());
    }
}