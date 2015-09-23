package za.ttd.database;

import java.net.URISyntaxException;
import java.sql.*;

public class ConnectDB {
    final class Statistics {
        private final int Benny;
        private final int LevelLives;
        private final int Minty;
        private final int Plaque;
        private final int BadBreath;
        private final int ToothDecay;


        Statistics(int benny, int levelLives, int minty, int plaque, int badBreath, int toothDecay) {
            Benny = benny;
            LevelLives = levelLives;
            Minty = minty;
            Plaque = plaque;
            BadBreath = badBreath;
            ToothDecay = toothDecay;
        }

        public int getBenny() {
            return Benny;
        }

        public int getLevelLives() {
            return LevelLives;
        }

        public int getMinty() {
            return Minty;
        }

        public int getPlaque() {
            return Plaque;
        }

        public int getBadBreath() {
            return BadBreath;
        }

        public int getToothDecay() {
            return ToothDecay;
        }
    }

    final class Loaded {
        private final int Level;
        private final int Score;

        public Loaded(int first, int second) {
            this.Level = first;
            this.Score = second;
        }

        public int getLevel() {
            return Level;
        }

        public int getScore() {
            return Score;
        }
    }


    public static void RunSQL(String sql) throws URISyntaxException, SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
    }

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

    /**
     * Save Thomas Current level and Current score
     * @param ID
     * @param Level
     * @param Score
     * @throws Exception
     */
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

    public static void setStatistics(int PlayerID,int LevelLives,int Benny,int Minty,int Plaque,int BadBreath,int ToothDecay) throws Exception
    {
        Class.forName("org.postgresql.Driver");
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE STATISTICS SET(LevelLives,Benny,Minty,Plaque,BadBreath,ToothDecay)=('"+LevelLives+"','"+Benny+"','"+Minty+"','"+Plaque+"','"+BadBreath+"','"+ToothDecay+"') WHERE PlayerID='"+PlayerID+"'");
    }

    /**
     * @param PlayerID
     * @return Loaded Object use getters to obtain level and score (if any are -1 then there was an error//SHOULD NEVER HAPPEN THOUGH)
     * @throws Exception
     */
    public  Loaded LoadLevel(int PlayerID)throws Exception
    {
        Class.forName("org.postgresql.Driver");
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
       ResultSet rs=stmt.executeQuery("SELECT Level,levelscore FROM COMPLETEDLEVELS WHERE PlayerID='" + PlayerID + "'");
        int level=-1;
        int score=-1;
        while(rs.next())
        {
            level=   rs.getInt("LEVEL");
            score=rs.getInt("LEVELSCORE");
        }
        return new Loaded(level,score);

    }

    /**
     * @param PlayerID
     * @return Statistic Object use getters to obtain each statistic (if any are -1 then there was an error//SHOULD NEVER HAPPEN THOUGH)
     * @throws Exception
     */
    public Statistics getStatistics(int PlayerID)throws Exception
    {
        Class.forName("org.postgresql.Driver");
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs=stmt.executeQuery("SELECT * FROM STATISTICS WHERE PlayerID='" + PlayerID + "'");
        int LevelLives=-1;
        int Benny=-1;
        int Minty=-1;
        int Plaque=-1;
        int BadBreath=-1;
        int ToothDecay=-1;
        while (rs.next())
        {
            LevelLives=rs.getInt("LEVELLIVES");
            Benny=rs.getInt("BENY");
            Minty=rs.getInt("MINTY");
            Plaque=rs.getInt("PLAQUE");
            BadBreath=rs.getInt("BADBREATH");
            ToothDecay=rs.getInt("TOOTHDECAY");
        }
        return new Statistics(LevelLives,Benny,Minty,Plaque,BadBreath,ToothDecay);

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
