package za.ttd.characters.states;

/**
 * @author minnaar
 * @since 2015/09/20.
 */
public class MessageType {
    //Sending items
    public static final int SEND_ITEMS = 0;
    public static final int SEND_THOMAS = 1;
    public static final int SEND_PATHFINDER = 2;
    public static final int SEND_TOOTHDECAY = 3;

    //Collection
    public static final int PLAQUE_COLLECTED = 4;
    public static final int TOOTHBRUSH_COLLECTED = 5;
    public static final int MOUTHWASH_COLLECTED = 6;
    public static final int MOUTHWASH_EXPIRED = 7;

    //Deaths
    public static final int TOOTHDECAY_DEAD = 8;
    public static final int BADBREATH_DEAD = 9;
    public static final int THOMAS_LOSES_LIFE = 10;

    //Level related
    public static final int LOAD_LEVEL = 11;
    public static final int LEVEL_STARTED = 12;
    public static final int LEVEL_PAUSED = 13;
    public static final int LEVEL_RESET = 14;
    public static final int NEXT_LEVEL = 15;
    public static final int GAME_OVER = 16;


}
