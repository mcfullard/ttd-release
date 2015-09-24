package za.ttd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Assets {

    private final String atlasFilePath = "core/assets/textures/out/texture.atlas";
    private TextureAtlas textureAtlas;
    private static Map<String, Map<String, Animation>> animations;
    private static Map<String, Animation> animationType;
    private static Map<String, TextureRegion> texturedObjects;
    private static ArrayList<String> gameCharacters;
    private boolean loaded;

    public Assets() {
        loaded = false;
        textureAtlas = new TextureAtlas(Gdx.files.internal(atlasFilePath));
        animations = new HashMap<>();
        animationType = new HashMap<>();
        texturedObjects = new HashMap<>();
    }

    public void Load() {
        //Create and store animations for characters
        for (String character:gameCharacters) {
            animationType.put("Up", new Animation(1 / 8f,
                    textureAtlas.findRegion(String.format("characters/%s0", character))));
            animationType.put("Up", new Animation(1 / 8f,
                    textureAtlas.findRegion(String.format("characters/%sU1", character)),
                    textureAtlas.findRegion(String.format("characters/%sU2", character)),
                    textureAtlas.findRegion(String.format("characters/%sU3", character))));
            animationType.put("Down", new Animation(1/8f,
                    textureAtlas.findRegion(String.format("characters/%sU1", character)),
                    textureAtlas.findRegion(String.format("characters/%sU2", character)),
                    textureAtlas.findRegion(String.format("characters/%sU3", character))));
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

        texturedObjects.put("tocWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("rocWall", textureAtlas.findRegion("map/rocWall"));
        texturedObjects.put("bocWall", textureAtlas.findRegion("map/bocWall"));
        texturedObjects.put("locWall", textureAtlas.findRegion("map/locWall"));
        texturedObjects.put("hocWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("vocWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("tveWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("rheWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("bveWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("lheWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("tlcWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("blcWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("trcWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("brcWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("blkWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("empWall", textureAtlas.findRegion("map/tocWall"));
        texturedObjects.put("tocWall", textureAtlas.findRegion("map/tocWall"));

        loaded = true;
    }

    public Animation getAnimation(String character, String animation) {
        if (animations.get(character) != null)
            return animations.get(character).get(animation);
        else
            return null;
    }

    public Boolean isLoaded() {
        return loaded;
    }

    public void dispose() {
        textureAtlas.dispose();

    }
}
