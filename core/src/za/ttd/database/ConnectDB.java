package za.ttd.database;

import java.net.URISyntaxException;
import java.sql.*;

public class ConnectDB {

    private static Connection getConnection() throws URISyntaxException, SQLException {

        String url ="jdbc:postgresql://ec2-54-83-10-210.compute-1.amazonaws.com:5432/d7vip2bviocqst?user=gmbkotoiwmiilq&password=IsEhBuPJ9kviVI3h8ov0sjSeq7&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        //UserName=gmbkotoiwmiilq
        //Password=IsEhBuPJ9kviVI3h8ov0sjSeq7
        return DriverManager.getConnection(url);
    }

    /**
     * Test if user can connect to heroku database
     * @return true if connection to database is possible , false if not.
     * @throws Exception
     */
    public static boolean TestConnectivity() throws Exception
    {
        Class.forName("org.postgresql.Driver");
        try {
            Connection connection = getConnection();
            return true;
            //reachable= connection.isValid(10);
        }
        catch (SQLException e)
        {
            return false;
        }
    }
}
