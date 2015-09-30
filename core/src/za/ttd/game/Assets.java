package za.ttd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Assets {

    private static Assets instance = null;
    private final String atlasFilePath = "core/assets/textures/out/texture.atlas";
    private TextureAtlas textureAtlas;
    private Map<String, Map<String, Animation>> animations;
    private Map<String, Animation> animationType;
    private Map<String, TextureRegion> texturedObjects;
    private ArrayList<String> gameCharacters;
    private boolean loading;

    private Assets() {
        loading = false;
        textureAtlas = new TextureAtlas(Gdx.files.internal(atlasFilePath));

        animations = new HashMap<>();
        texturedObjects = new HashMap<>();
        gameCharacters = new ArrayList<>();

        //Preset game characters for loading
        gameCharacters.add("Thomas");
        gameCharacters.add("BadBreath");
        gameCharacters.add("ToothDecay");
    }

    public void Load() {
        //Create and store animations for characters
        for (String character:gameCharacters) {
            animationType = new HashMap<>();
            animationType.put("Idle", new Animation(1 / 1f,
                    textureAtlas.findRegion(String.format("characters/%s0", character))));
            animationType.put("Up", new Animation(1 / 8f,
                    textureAtlas.findRegion(String.format("characters/%sU1", character)),
                    textureAtlas.findRegion(String.format("characters/%sU2", character)),
                    textureAtlas.findRegion(String.format("characters/%sU3", character))));
            animationType.put("Down", new Animation(1/8f,
                    textureAtlas.findRegion(String.format("characters/%sD1", character)),
                    textureAtlas.findRegion(String.format("characters/%sD2", character)),
                    textureAtlas.findRegion(String.format("characters/%sD3", character))));
            animationType.put("Left", new Animation(1/8f,
                    textureAtlas.findRegion(String.format("characters/%sL1", character)),
                    textureAtlas.findRegion(String.format("characters/%sL2", character)),
                    textureAtlas.findRegion(String.format("characters/%sL3", character))));
            animationType.put("Right", new Animation(1/8f,
                    textureAtlas.findRegion(String.format("characters/%sR1", character)),
                    textureAtlas.findRegion(String.format("characters/%sR2", character)),
                    textureAtlas.findRegion(String.format("characters/%sR3", character))));
            animations.put(character, animationType);
        }

        //Load collectible items
        texturedObjects.put("Plaque", textureAtlas.findRegion("items/Plaque"));
        texturedObjects.put("MintyMouthwash", textureAtlas.findRegion("items/MintyMouthwash"));
        texturedObjects.put("Benny", textureAtlas.findRegion("items/Benny"));

        //Load walls and door
        texturedObjects.put("tocWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("rocWall", textureAtlas.findRegion("map/rocWall"));
        texturedObjects.put("bocWall", textureAtlas.findRegion("map/bocWall"));
        texturedObjects.put("locWall", textureAtlas.findRegion("map/locWall"));
        texturedObjects.put("hocWall", textureAtlas.findRegion("map/hocWall"));
        texturedObjects.put("vocWall", textureAtlas.findRegion("map/vocWall"));
        texturedObjects.put("tveWall", textureAtlas.findRegion("map/tveWall"));
        texturedObjects.put("rheWall", textureAtlas.findRegion("map/rheWall"));
        texturedObjects.put("bveWall", textureAtlas.findRegion("map/bveWall"));
        texturedObjects.put("lheWall", textureAtlas.findRegion("map/lheWall"));
        texturedObjects.put("tlcWall", textureAtlas.findRegion("map/tlcWall"));
        texturedObjects.put("blcWall", textureAtlas.findRegion("map/blcWall"));
        texturedObjects.put("trcWall", textureAtlas.findRegion("map/trcWall"));
        texturedObjects.put("brcWall", textureAtlas.findRegion("map/brcWall"));
        texturedObjects.put("blkWall", textureAtlas.findRegion("map/blkWall"));
        texturedObjects.put("empWall", textureAtlas.findRegion("map/empWall"));
        texturedObjects.put("tocWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("door", textureAtlas.findRegion("map/door"));

        //Loading is complete, tell the rest of the world
        loading = false;
    }

    public Animation getAnimation(String character, String animation) {
        if (animations.get(character) != null)
            return animations.get(character).get(animation);
        else
            return null;
    }

    public TextureRegion getTexture(String gameObject) {
        return texturedObjects.get(gameObject);
    }

    public Boolean loading() {
        return loading;
    }

    public void dispose() {
        textureAtlas.dispose();
    }

    public static Assets getInstance() {
        if (instance == null)
            instance = new Assets();

        return instance;
    }
}
