package za.ttd.tests;

import org.junit.Test;
import za.ttd.connecttodatabase.ConnectDB;


public class ConnectTest {

    @Test
    public void TestRunSQL()throws Exception
    {
        ConnectDB p= new ConnectDB();
        p.RunSQL("INSERT INTO HIGHSCORE VALUES (5,'In','Ass',5)");


    }
}