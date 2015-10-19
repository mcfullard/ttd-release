package za.ttd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author minnaar
 * @since 2015/10/17.
 */
public class Preferences {
    public String connectionString;

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean debugMode;

    public static Preferences getInstance() {
        Preferences instance = null;
        Json json = new Json();
        FileHandle fin = Gdx.files.local("data/preferences");
        String data = fin.readString();
        instance = json.fromJson(Preferences.class, data);
        return instance;
    }

    public void storeInstance() {
        try {
            FileWriter writer = new FileWriter("data/preferences");
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
