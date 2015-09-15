package za.ttd.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import za.ttd.characters.objects.Position;

public class Mouthwash extends Collectible {
    private final String FILENAME = "core/assets/textures/in/items/MintyMouthwash.png";
    private TextureRegion texture;

    public Mouthwash(Position position){
        super(position, "MintyMouthwash");
        texture = null;
    }


}
