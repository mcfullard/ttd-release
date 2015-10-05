package za.ttd.database;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import za.ttd.characters.states.MessageType;
import za.ttd.game.Game;
import za.ttd.game.Player;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ConnectDB
        implements Telegraph
{
    public static ConnectDB instance = null;

    private ConnectDB() {
        registerSelfAsListener();
    }

    public static ConnectDB getInstance() {
        if(instance == null)
            instance = new ConnectDB();
        return instance;
    }

    private void registerSelfAsListener() {
        MessageManager.getInstance().addListeners(this,
                MessageType.UPDATE_DB
        );
    }

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

    public static Player getPlayer(String name) {
        Player player = null;
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = getConnection();
            String sql = String.format(
                    "select l.score, l.level, s.levellives, p.playerid, p.salt, p.hash, " +
                    "c.left_key, c.right_key, c.up_key, c.down_key, " +
                    "s.levellives, s.collectible, s.badbreath, s.powersused " +
                    "from player p, levels l, statistics s , controls c " +
                    "where p.name = '%s' and " +
                    "p.playerid = l.playerid and " +
                    "l.playerid = s.playerid and " +
                    "s.playerid = c.playerid;",
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
                Player.Controls.LEFT = result.getInt(7);
                Player.Controls.RIGHT = result.getInt(8);
                Player.Controls.UP = result.getInt(9);
                Player.Controls.DOWN = result.getInt(10);
                player.scoring.setTotLivesUsed(result.getInt(11));
                player.scoring.setTotCollectiblesFound(result.getInt(12));
                player.scoring.setTotBadBreathKilled(result.getInt(13));
                player.scoring.setTotPowersUsed(result.getInt(14));
            }
            stmt.close();
            con.close();
        } catch (SQLException | URISyntaxException | ClassNotFoundException e) {
            Gdx.app.error("CONNECTDB_GETPLAYER", e.getMessage());
        }
        return player;
    }

    public static void addPlayer(Player player) {
        try {
            Class.forName("org.postgresql.Driver");
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
            pstmt.close();
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
            stmt.close();
            connection.close();
        } catch (SQLException | URISyntaxException | ClassNotFoundException e) {
            Gdx.app.error("CONNECTDB_ADDPLAYER", e.getMessage());
        }
    }

    private void updatePlayer(Player player) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = getConnection();
            String prepSql = String.format(
                    "begin; " +
                    "update controls set " +
                    "left_key=%d, " +
                    "right_key=%d, " +
                    "up_key=%d, " +
                    "down_key=%d " +
                    "where playerid=%d; " +

                    "update levels set " +
                    "level = %d, " +
                    "score = %d " +
                    "where playerid = %d; " +

                    "update statistics set " +
                    "levellives = %d, " +
                    "collectible = %d, " +
                    "badbreath = %d, " +
                    "powersused = %d " +
                    "where playerid = %d; " +

                    "delete from highscore " +
                    "where playerid = ( " +
                    "    select h.playerid " +
                    "    from highscore h " +
                    "    where h.highscore = ( " +
                    "        select min(highscore) " +
                    "        from highscore " +
                    "    ) " +
                    ") " +
                    "and 10 = ( " +
                    "    select count(highscore) " +
                    "    from highscore " +
                    "); " +

                    "insert into highscore (playerid, highscore) " +
                    "select %d, %d " +
                    "where (select count(highscore) from highscore) >= 0 " +
                    "and (select count(highscore) from highscore) <= 10 " +
                    "and %d > (select min(highscore) from highscore); " +

                    "commit;",
                    Player.Controls.LEFT,
                    Player.Controls.RIGHT,
                    Player.Controls.UP,
                    Player.Controls.DOWN,
                    player.getPlayerID(),
                    player.getHighestLevel(),
                    player.getTotScore(),
                    player.getPlayerID(),
                    player.scoring.getTotLivesUsed(),
                    player.scoring.getTotCollectiblesFound(),
                    player.scoring.getTotBadBreathKilled(),
                    player.scoring.getTotPowersUsed(),
                    player.getPlayerID(),
                    player.getPlayerID(),
                    player.getTotScore(),
                    player.getTotScore()
            );
            PreparedStatement pstmt = connection.prepareStatement(prepSql);
            pstmt.execute();

            /*
            Statement stmt = connection.createStatement();
            String controlsSql = String.format(
                    "update controls set " +
                    "left_key=%d, " +
                    "right_key=%d, " +
                    "up_key=%d, " +
                    "down_key=%d " +
                    "where playerid=%d;",
                    Player.Controls.LEFT,
                    Player.Controls.RIGHT,
                    Player.Controls.UP,
                    Player.Controls.DOWN,
                    player.getPlayerID()
            );
            stmt.execute(controlsSql);
            String levelsSql = String.format(
                    "update levels set " +
                    "level = %d, " +
                    "score = %d " +
                    "where playerid = %d;",
                    player.getHighestLevel(),
                    player.getTotScore(),
                    player.getPlayerID()
            );
            stmt.execute(levelsSql);
            String statsSql = String.format(
                    "update statistics set " +
                    "levellives = %d, " +
                    "collectible = %d, " +
                    "badbreath = %d, " +
                    "powersused = %d " +
                    "where playerid = %d;",
                    player.scoring.getTotLivesUsed(),
                    player.scoring.getTotCollectiblesFound(),
                    player.scoring.getTotBadBreathKilled(),
                    player.scoring.getTotPowersUsed(),
                    player.getPlayerID()
            );
            stmt.execute(statsSql);
            String highscoreDeleteSql =
                    "delete from highscore " +
                    "where playerid = ( " +
                    "    select h.playerid " +
                    "    from highscore h " +
                    "    where h.highscore = ( " +
                    "        select min(highscore) " +
                    "        from highscore " +
                    "    ) " +
                    ") " +
                    "and 10 = ( " +
                    "    select count(highscore) " +
                    "    from highscore " +
                    ");";
            stmt.execute(highscoreDeleteSql);
            String highscoreInsertSql = String.format(
                    "insert into highscore (playerid, highscore) " +
                    "select %d, %d " +
                    "where (select count(highscore) from highscore) >= 0 " +
                    "and (select count(highscore) from highscore) <= 10 " +
                    "and %d > (select min(highscore) from highscore);",
                    player.getPlayerID(),
                    player.getTotScore(),
                    player.getTotScore()
            );
            stmt.execute(highscoreInsertSql);
            stmt.close();
            */
            pstmt.close();
            connection.close();
        } catch (SQLException | URISyntaxException | ClassNotFoundException e) {
            Gdx.app.error("CONNECTDB_ADDPLAYER", e.getMessage());
        }
    }

    public static Map<String, Integer> getHighestScoringPlayers() {
        Map<String, Integer> highScores = null;
        try {
            highScores = new HashMap<>();
            Class.forName("org.postgresql.Driver");
            Connection connection = getConnection();
            Statement stmt = connection.createStatement();
            String sql = "select p.name, h.highscore " +
                    "from highscore h, player p " +
                    "where h.playerid = p.playerid;";
            ResultSet result = stmt.executeQuery(sql);
            while(result.next()) {
                highScores.put(result.getString(1), result.getInt(2));
            }
            stmt.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException | URISyntaxException e) {
            Gdx.app.error("CONNECTDB_GETHISCORES", e.getMessage());
        }
        return highScores;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.UPDATE_DB:
                Player player = Game.getInstance().getPlayer();
                if (player != null) { // if the game actually has a current player
                    if(getPlayer(player.getName()) != null) { // if the player's in the DB
                        updatePlayer(player);
                    } else {
                        addPlayer(player);
                    }
                }
                return true;
        }
        return false;
    }
}
