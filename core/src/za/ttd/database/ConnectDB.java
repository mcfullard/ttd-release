package za.ttd.database;

import com.badlogic.gdx.Gdx;
import za.ttd.game.Player;

import java.net.URISyntaxException;
import java.sql.*;

public class ConnectDB {
    public final class Statistics {
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
        stmt.executeUpdate("UPDATE levels SET LEVEL='"+Level+"',score='"+Score+"' WHERE PlayerID='"+ID+"' ");
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
       ResultSet rs=stmt.executeQuery("SELECT Level,score FROM levels WHERE PlayerID='" + PlayerID + "'");
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

    public static Player getPlayer(String name) throws
            ClassNotFoundException {
        Player player = null;
        Class.forName("org.postgresql.Driver");
        try {
            Connection con = getConnection();
            String sql = String.format(
                    "select l.score, l.level, s.levellives, p.playerid, p.salt, p.hash " +
                            "from player p, levels l, statistics s " +
                            "where p.name = '%s' and " +
                            "p.playerid = l.playerid and " +
                            "l.playerid = s.playerid;",
                    name
            );
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while(result.next()) {
                player = new Player(name,
                        result.getInt(1),
                        result.getInt(2),
                        result.getInt(3),
                        result.getInt(4),
                        result.getBytes(5),
                        result.getBytes(6));
            }
            stmt.close();
            con.close();
        } catch (SQLException | URISyntaxException e) {
            Gdx.app.error("CONNECTDB_GETPLAYER", e.getMessage());
        }
        return player;
    }

    public static void addPlayer(Player player) throws ClassNotFoundException
    {
        Class.forName("org.postgresql.Driver");
        try {
            Connection connection = getConnection();
            int playerId = -1;
            String playerSql = String.format(
                    "insert into player(name, salt, hash) values (?, ?, ?) " +
                    "returning playerid"
            );
            PreparedStatement pstmt = connection.prepareStatement(playerSql);
            pstmt.setString(1, player.getName());
            pstmt.setBytes(2, player.getSalt());
            pstmt.setBytes(3, player.getHash());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                playerId = rs.getInt("playerid");
                player.setPlayerID(playerId);
            }
            Statement stmt = connection.createStatement();
            String controlsSql = String.format(
                    "insert into controls values (%d, %d, %d, %d, %d)",
                    playerId,
                    Player.Controls.LEFT,
                    Player.Controls.RIGHT,
                    Player.Controls.UP,
                    Player.Controls.DOWN
            );
            stmt.execute(controlsSql);
            String levelsSql = String.format(
                    "insert into levels values (%d, %d, %d)",
                    playerId,
                    player.getHighestLevel(),
                    player.getTotScore()
            );
            stmt.execute(levelsSql);
            String statsSql = String.format(
                    "insert into statistics values (%d, %d, %d, %d, %d)",
                    playerId,
                    player.scoring.getTotLivesUsed(),
                    player.scoring.getTotCollectiblesFound(),
                    player.scoring.getTotBadBreathKilled(),
                    player.scoring.getTotPowersUsed()
            );
            stmt.execute(statsSql);
            pstmt.close();
            stmt.close();
            connection.close();
        } catch (SQLException | URISyntaxException e) {
            Gdx.app.error("CONNECTDB_ADDPLAYER", e.getMessage());
        }
    }

}
