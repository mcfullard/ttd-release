package za.ttd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    private ArrayList<String> items;
    public HighScoresScreen(Game game) {
        super(game);
        this.game = game;
        items = new ArrayList<>();
    }

    private Stage stage = new Stage();
    private Skin skin = new Skin(Gdx.files.internal("core/assets/skins/menuSKin.json"));
    private List list = new List(skin);
    private ScrollPane scrollPane = new ScrollPane(list);
    private Table table = new Table(skin);


    private Label highScoresLabel = new Label("High Scores", skin);
    private TextButton back = new TextButton("Back", skin);


    private void getPlayers() {
        //this.players = connectDB.getPlayers();
        getItems();
        list.setItems(items);
    }
    private void getItems(){
        for(Player player: players){
            items.add(player.getName()+"\t"+player.getTotScore());
        }
    }

    @Override
    public void show() {
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //display gameOverScreen
            }
        });


        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
