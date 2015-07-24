package za.ttd.MapGeneration;

import java.util.ArrayList;

/**
 * @author minnaar
 * @since 2015/07/21.
 *
 * This class forms a basic static structure that describes Tetris-like shapes
 */
public class Block {
    protected Rotation rotation = Rotation.ZERO;
    protected Shape shape;
    protected int[] origin;
    protected ArrayList<int[]> relativeCoordinates;

    public Block(int[] origin, Shape shape) {
        this.origin = origin;
        initShape(shape);
    }

    private void initShape(Shape shape) {
        relativeCoordinates = new ArrayList<>();
        switch (shape) {
            case I:
                relativeCoordinates.add(new int[] {0,1});
            case L:
                relativeCoordinates.add(new int[] {0,2});
                relativeCoordinates.add(new int[] {1,2});
                break;
            case CORNER:
                relativeCoordinates.add(new int[] {1,1});
            case T:
                relativeCoordinates.add(new int[] {0,2});
                break;
        }
    }

    public void rotate(Rotation rotation) {
        
    }

}
