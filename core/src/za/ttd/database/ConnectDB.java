package za.ttd.database;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import javafx.util.Pair;
import za.ttd.characters.states.MessageType;
import za.ttd.game.Achievement;
import za.ttd.game.Player;
import za.ttd.game.Preferences;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;

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
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(Preferences.getInstance().getConnectionString());
        } catch (ClassNotFoundException e) {
            Gdx.app.error("CONNECTDB_GETCONNECTION", e.getMessage());
        }
        return connection;
    }

    /**
     * Test if user can connect to heroku database
     * @return true if connection to database is possible , false if not.
     * @throws Exception
     */
    public static boolean TestConnectivity() throws Exception
    {
        Class.forName("org.sqlite.JDBC");
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

    public static boolean checkPlayerExists(String name) {
        boolean check = false;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = getConnection();
            String sql = String.format(
                    "select p.name " +
                    "from player p " +
                    "where p.name = '%s';",
                    name
            );
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            check = result.next();
            pstmt.close();
            con.close();
        } catch (ClassNotFoundException | SQLException | URISyntaxException e) {
            Gdx.app.log("CONNECTDB_CHECK", e.getMessage());
        }
        return check;
    }

    public static void populatePlayer(String name) {
        Player player = Player.getInstance();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = getConnection();
            String sql = String.format(
                    "select h.highscore, l.level, p.lives, p.playerid, p.salt, p.hash, " +
                    "c.left_key, c.right_key, c.up_key, c.down_key, " +
                    "s.levellives, s.collectible, s.badbreath, s.powersused, l.score " +
                    "from player p, levels l, statistics s , controls c,  highscore h " +
                    "where p.name = '%s' and " +
                    "p.playerid = l.playerid and " +
                    "l.playerid = s.playerid and " +
                    "s.playerid = c.playerid and " +
                    "c.playerid = h.playerid;",
                    name
            );
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while(result.next()) {
                player.setName(name);
                player.setHighestScore(result.getInt(1));
                player.setHighestLevel(result.getInt(2));
                player.setLives(result.getInt(3));
                player.setPlayerID(result.getInt(4));
                player.setSalt(result.getBytes(5));
                player.setHash(result.getBytes(6));
                player.controls.setLeft(result.getInt(7));
                player.controls.setRight(result.getInt(8));
                player.controls.setUp(result.getInt(9));
                player.controls.setDown(result.getInt(10));
                player.scoring.setTotLivesUsed(result.getInt(11));
                player.scoring.setTotCollectiblesFound(result.getInt(12));
                player.scoring.setTotBadBreathKilled(result.getInt(13));
                player.scoring.setTotPowersUsed(result.getInt(14));
                player.scoring.setTotScore(result.getInt(15));
            }
            player.setAchievements(getAchievements());
            stmt.close();
            con.close();
        } catch (SQLException | URISyntaxException | ClassNotFoundException e) {
            Gdx.app.error("CONNECTDB_GETPLAYER", e.getMessage());
        }
    }

    private static List<Achievement> getAchievementsObtained(Statement stmt, Player player) {
        List<Achievement> achievements = new ArrayList<>();
        try {
            String sql = String.format(
                    "select t.achievementid, t.bound, m.value, c.value, t.description " +
                            "from achievement t, metric m, condition c, achieved d " +
                            "where d.playerid = %d and " +
                            "d.achievementid = t.achievementid and " +
                            "t.metricid = m.metricid and " +
                            "t.conditionid = c.conditionid;",
                    player.getPlayerID()
            );
            ResultSet result = stmt.executeQuery(sql);
            while(result.next()) {
                Achievement achievement = new Achievement(
                    result.getInt(1),
                    result.getInt(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)
                );
                achievements.add(achievement);
            }
        } catch (SQLException e) {
            Gdx.app.error("CONNECTDB_GETPLAYERACHIEVEMENTS", e.getMessage());
        }
        return achievements;
    }

    public static void addPlayer() {
        try {
            Player player = Player.getInstance();
            Class.forName("org.sqlite.JDBC");
            Connection connection = getConnection();
            int playerId = -1;
            String playerSql = String.format(
                    "insert into player(name, salt, hash, lives) values (?, ?, ?, ?);"
            );
            PreparedStatement pstmt = connection.prepareStatement(playerSql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, player.getName());
            pstmt.setBytes(2, player.getSalt());
            pstmt.setBytes(3, player.getHash());
            pstmt.setInt(4, player.getLives());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                playerId = rs.getInt(1);
                player.setPlayerID(playerId);
            }
            pstmt.close();
            Statement stmt = connection.createStatement();
            String controlsSql = String.format(
                    "insert into controls values (%d, %d, %d, %d, %d)",
                    playerId,
                    player.controls.getLeft(),
                    player.controls.getRight(),
                    player.controls.getUp(),
                    player.controls.getDown()
            );
            stmt.execute(controlsSql);
            String levelsSql = String.format(
                    "insert into levels values (%d, %d, %d)",
                    playerId,
                    player.getHighestLevel(),
                    player.getHighestScore()
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
            String highscoreSql = String.format(
                    "insert into highscore values (%d, %d)",
                    playerId,
                    player.getHighestScore()
            );
            stmt.execute(highscoreSql);
            insertAchievementsObtained(stmt, player);
            stmt.close();
            connection.close();
        } catch (SQLException | URISyntaxException | ClassNotFoundException e) {
            Gdx.app.error("CONNECTDB_ADDPLAYER", e.getMessage());
        }
    }

    private static void insertAchievementsObtained(Statement stmt, Player player) {
        try {
            for (Achievement achievement : player.getAchievementsObtained()) {
                String achievementSql = String.format(
                        "insert into achieved (achievementid, playerid) " +
                                "select %d, %d " +
                                "where not exists ( " +
                                "select achievementid " +
                                "from achieved " +
                                "where achievementid = %d " +
                                ");",
                        achievement.getId(),
                        player.getPlayerID(),
                        achievement.getId()
                );
                stmt.addBatch(achievementSql);
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            Gdx.app.error("CONNECTDB_INSERTACHIEVEMENT", e.getMessage());
        }
    }

    private void updatePlayer() {
        try {
            Player player = Player.getInstance();
            Class.forName("org.sqlite.JDBC");
            Connection connection = getConnection();
            Statement stmt = connection.createStatement();
            stmt.addBatch(String.format(
                    "update player set " +
                    "lives = %d " +
                    "where playerid=%d; ",
                    player.getLives(),
                    player.getPlayerID()
            ));
            stmt.addBatch(String.format(
                    "update controls set " +
                    "left_key=%d, " +
                    "right_key=%d, " +
                    "up_key=%d, " +
                    "down_key=%d " +
                    "where playerid=%d; ",
                    player.controls.getLeft(),
                    player.controls.getRight(),
                    player.controls.getUp(),
                    player.controls.getDown(),
                    player.getPlayerID()
            ));
            stmt.addBatch(String.format(
                    "update levels set " +
                    "level = %d, " +
                    "score = %d " +
                    "where playerid = %d; ",
                    player.getHighestLevel(),
                    player.scoring.getTotScore(),
                    player.getPlayerID()
            ));
            stmt.addBatch(String.format(
                    "update statistics set " +
                    "levellives = %d, " +
                    "collectible = %d, " +
                    "badbreath = %d, " +
                    "powersused = %d " +
                    "where playerid = %d; ",
                    player.scoring.getTotLivesUsed(),
                    player.scoring.getTotCollectiblesFound(),
                    player.scoring.getTotBadBreathKilled(),
                    player.scoring.getTotPowersUsed(),
                    player.getPlayerID()
            ));
            stmt.addBatch(String.format(
                    "update highscore " +
                    "set highscore = %d " +
                    "where playerid = %d;",
                    player.getHighestScore(),
                    player.getPlayerID()
            ));
            stmt.executeBatch();
            stmt.clearBatch();
            insertAchievementsObtained(stmt, player);
            stmt.close();
            connection.close();
        } catch (SQLException | URISyntaxException | ClassNotFoundException e) {
            Gdx.app.error("CONNECTDB_ADDPLAYER", e.getMessage());
        }
    }

    public static List<Pair<String, Integer>> getHighestScoringPlayers() {
        List<Pair<String, Integer>> highScores = null;
        try {
            highScores = new ArrayList<>();
            Class.forName("org.sqlite.JDBC");
            Connection connection = getConnection();
            Statement stmt = connection.createStatement();
            String sql = "select p.name, h.highscore " +
                    "from highscore h, player p " +
                    "where h.playerid = p.playerid " +
                    "order by highscore DESC " +
                    "limit 10;";
            ResultSet result = stmt.executeQuery(sql);
            while(result.next()) {
                highScores.add(new Pair<>(result.getString(1), result.getInt(2)));
            }
            stmt.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException | URISyntaxException e) {
            Gdx.app.error("CONNECTDB_GETHISCORES", e.getMessage());
        }
        return highScores;
    }

    public static List<Achievement> getAchievements() {
        List<Achievement> achievements = new ArrayList<>();
        try {
            Connection connection = getConnection();
            String sql = "select t.achievementid, t.bound, m.value, c.value, t.description " +
                    "from achievement t, metric m, condition c " +
                    "where t.metricid = m.metricid and " +
                    "t.conditionid = c.conditionid;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            while(result.next()) {
                Achievement achievement = new Achievement(
                        result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5)
                );
                achievements.add(achievement);
            }
            pstmt.close();
            connection.close();
        } catch (SQLException | URISyntaxException e) {
            Gdx.app.error("CONNECTDB_GETACHIEVEMENTS", e.getMessage());
        }
        return achievements;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.UPDATE_DB:
                if (checkPlayerExists(Player.getInstance().getName())) { // if the player's in the DB
                    updatePlayer();
                } else {
                    addPlayer();
                }
                return true;
        }
        return false;
    }
}
