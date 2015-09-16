package za.ttd.database;

import java.net.URISyntaxException;
import java.sql.*;

public class ConnectDB {


    public static void RunSQL(String sql) throws URISyntaxException, SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
    }
    /* stmt.executeUpdate("DROP TABLE IF EXISTS HIGHSCORE");
        stmt.executeUpdate("CREATE TABLE HIGHSCORE(PlayerID INT PRIMARY KEY NOT NULL,NAME TEXT NOT NULL,SCORE INT)");
        stmt.executeUpdate("CREATE TABLE CONTROLS(PlayerID INT PRIMARY KEY NOT NULL,LEFTt char(1) NOT NULL,RIGHTt char(1) NOT NULL,UPp char(1) NOT NULL,DOWNn char(1) NOT NULL)");
        stmt.executeUpdate("CREATE TABLE PLAYER(PlayerID SERIAL PRIMARY KEY,NAME TEXT NOT NULL)");
        stmt.executeUpdate("CREATE TABLE COMPLETEDLEVELS(PlayerID INT PRIMARY KEY NOT NULL,LEVEL INT NOT NULL,LEVELSCORE INT)");
        stmt.executeUpdate("CREATE TABLE STATISTICS(PlayerID INT PRIMARY KEY NOT NULL,LEVELLIVES INT NOT NULL,BENNY TEXT NOT NULL,MINTY TEXT NOT NULL,PLAQUE TEXT NOT NULL,BADBREATH TEXT NOT NULL,TOOTHDECAY TEXT NOT NULL)");
        stmt.executeUpdate("CREATE TABLE ACHIEVEMENTS(AchievedID INT PRIMARY KEY NOT NULL,DESCROBTION TEXT NOT NULL)");
        stmt.executeUpdate("CREATE TABLE ACHIEVED(PlayerID INT PRIMARY KEY NOT NULL,AchievedID INT SECONDARY KEY NOT NULL)");*/
    // stmt.executeUpdate("CREATE TABLE RANDOM(ID SERIAL PRIMARY KEY NOT NULL,Number INT NOT NULL,Stringg TEXT NOT NULL)");
    private static Connection getConnection() throws URISyntaxException, SQLException {

        String url ="jdbc:postgresql://ec2-54-83-10-210.compute-1.amazonaws.com:5432/d7vip2bviocqst?user=gmbkotoiwmiilq&password=IsEhBuPJ9kviVI3h8ov0sjSeq7&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        //UserName=gmbkotoiwmiilq
        //Password=IsEhBuPJ9kviVI3h8ov0sjSeq7
        return DriverManager.getConnection(url);
    }
    public static void AddPlayer(String playerName) throws Exception
    {
        Class.forName("org.postgresql.Driver");
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        int ID=0;
        //stmt.executeUpdate("INSERT INTO PLAYER(NAME)VALUES ('"+playerName+"')");
        ResultSet rs = stmt.executeQuery("INSERT INTO PLAYER(PLAYERID,NAME)VALUES (DEFAULT ,'"+playerName+"') RETURNING PLAYERID");
        while(rs.next()){
             ID= rs.getInt("PLAYERID");
        }
        stmt.executeUpdate("INSERT INTO CONTROLS VALUES ('"+ID+"','left','right','up','down')");
        stmt.executeUpdate("INSERT INTO COMPLETEDLEVELS VALUES ('"+ID+"',0,0)");
        stmt.executeUpdate("INSERT INTO STATISTICS VALUES ('"+ID+"',0,0,0,0,0,0)");
    }
    public static void SaveGame(int ID,int Level,int Score)throws Exception
    {
        Class.forName("org.postgresql.Driver");
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE COMPLETEDLEVELS SET LEVEL='"+Level+"',LEVELSCORE='"+Score+"' WHERE PlayerID='"+ID+"' ");
    }
    public static void Achieved(int pID,int aID)throws Exception
    {
        Class.forName("org.postgresql.Driver");
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO ACHIEVED VALUES ('"+pID+"','"+aID+"')");
    }
    public static void SetStatistics(int PlayerID,int LevelLives,int Benny,int Minty,int Plaque,int BadBreath,int ToothDecay) throws Exception
    {
        Class.forName("org.postgresql.Driver");
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE STATISTICS SET(LevelLives,Benny,Minty,Plaque,BadBreath,ToothDecay)=('"+LevelLives+"','"+Benny+"','"+Minty+"','"+Plaque+"','"+BadBreath+"','"+ToothDecay+"') WHERE PlayerID='"+PlayerID+"'");
    }


}
