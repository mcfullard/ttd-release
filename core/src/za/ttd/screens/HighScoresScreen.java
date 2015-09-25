package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import za.ttd.database.ConnectDB;
import za.ttd.game.Game;
import za.ttd.game.Player;

import java.util.ArrayList;

/**
 * Created by s213391244 on 9/18/2015.
 */
public class HighScoresScreen extends AbstractScreen {
    private Game game;
    private ConnectDB connectDB = new ConnectDB();
    private ArrayList<Player> players;
    public HighScoresScreen(Game game) {
        super(game);
        this.game = game;
    }

    private Stage stage = new Stage();
    private Skin skin = new Skin(Gdx.files.internal("core/assets/skins/menuSKin.json"));
    private List table = new List(skin);

    private Label highScoresLabel = new Label("High Scores", skin);
    private TextButton back = new TextButton("Back", skin);



    private void getPlayers(){
        //this.players = connectDB.getPlayers();
    }


    @Override
    public void render(float delta) {

    }
}
