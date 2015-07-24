package za.ttd.MapGeneration;

/**
 * A Block is a data structure that describes a Tetris-like block. Blocks
 * can be rotated relative to the origin coordinate. The defualt rotation
 * of a block is ZERO degrees.
 *
 * @author minnaar
 * @since 2015/07/21.
 *
 */
public class Block {
    protected Coordinate origin;

    public Block(int[] origin, Shape shape) {
        this.origin = new Coordinate(origin);
        initShape(shape);
    }

    private void initShape(Shape shape) {
        switch (shape) {
            case I:
                origin.add(new Coordinate(new int[]{0, 1}));
            case L:
                origin.add(new Coordinate(new int[]{0, 2}));
                origin.add(new Coordinate(new int[]{1, 2}));
                break;
            case CORNER:
                origin.add(new Coordinate(new int[]{1, 1}));
            case T:
                origin.add(new Coordinate(new int[]{0, 2}));
                break;
        }
    }

    public void rotate(Rotation rotation) {
        switch (rotation) {
            case NINETY:
                recursiveAction(
                    origin,
                    coordinate -> {
                        coordinate.switchRowCol();
                        coordinate.negateRow();
                    }
                );
                break;
            case ONE_EIGHTY:
                recursiveAction(
                    origin,
                    coordinate -> {
                        coordinate.negateRow();
                        coordinate.negateCol();
                    }
                );
                break;
            case TWO_SEVENTY:
                recursiveAction(
                    origin,
                    coordinate -> {
                        coordinate.switchRowCol();
                        coordinate.negateCol();
                    }
                );
                break;
        }
    }

    private void recursiveAction(Coordinate coordinate, CoordinateAction action) {
        while(coordinate.getDescendants() != null) {
            for(Coordinate descendant : coordinate.getDescendants()) {
                action.doAction(descendant);
                recursiveAction(descendant, action);
            }
        }
    }
}
