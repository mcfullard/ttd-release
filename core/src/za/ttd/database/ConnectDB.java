package za.ttd.connecttodatabase;

import java.net.URISyntaxException;
import java.sql.*;

public class ConnectDB {

    public static void RunSQL(String sql) throws URISyntaxException, SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
    }






    private static Connection getConnection() throws URISyntaxException, SQLException {

        String url ="jdbc:postgresql://ec2-54-83-10-210.compute-1.amazonaws.com:5432/d7vip2bviocqst?user=gmbkotoiwmiilq&password=IsEhBuPJ9kviVI3h8ov0sjSeq7&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        return DriverManager.getConnection(url);
    }
}
