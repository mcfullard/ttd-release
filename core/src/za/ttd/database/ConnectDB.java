package za.ttd.database;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import za.ttd.characters.states.MessageType;
import za.ttd.game.Game;
import za.ttd.game.Player;

import java.net.URISyntaxException;
import java.sql.*;

public class ConnectDB implements Telegraph {
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
            String highscoreInsertSql = String.format(
                    "if exists (select min(highscore) from highscore where min(highscore) < %d) then " +
                    "begin insert into highscore values (%d, %d); " +
                    "delete from highscore where highscore = min(highscore);",
                    player.getPlayerID(),
                    player.getTotScore(),
                    player.getTotScore()
            );
            stmt.execute(highscoreInsertSql);
            String highscoreUpdateSql = String.format(
                    "update highscore set " +
                    "highscore = %d " +
                    "where highscore < %d and " +
                    "playerid = %d;",
                    player.getTotScore(),
                    player.getTotScore(),
                    player.getPlayerID()
            );
            stmt.execute(highscoreUpdateSql);
            stmt.close();
            connection.close();
        } catch (SQLException | URISyntaxException | ClassNotFoundException e) {
            Gdx.app.error("CONNECTDB_ADDPLAYER", e.getMessage());
        }
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MessageType.LEVEL_STARTED:
            case MessageType.LEVEL_PAUSED:
            case MessageType.NEXT_LEVEL:
            case MessageType.LEVEL_RESET:
            case MessageType.GAME_OVER:
                Player player = Game.getInstance().getPlayer();
                if(player != null) {
                    updatePlayer(player);
                }
                break;
        }
        return false;
    }
}
