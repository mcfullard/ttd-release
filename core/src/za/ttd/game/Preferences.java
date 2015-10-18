package za.ttd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author minnaar
 * @since 2015/10/17.
 */
public class Preferences {
    public String connectionString;

    public static Preferences getInstance() {
        Preferences instance = null;
        try {
            Json json = new Json();
            FileHandle fin = Gdx.files.internal("core/assets/data/preferences");
            String data = fin.readString();
            instance = json.fromJson(Preferences.class, data);
        } catch (GdxRuntimeException e) {
            Gdx.app.error("CONFIG", e.getMessage());
        }
        return instance;
    }

    public void storeInstance() {
        try {
            FileWriter writer = new FileWriter("core/assets/data/preferences");
            Json json = new Json();
            String data = json.prettyPrint(this);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
}
