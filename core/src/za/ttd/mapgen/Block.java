package za.ttd.mapgen;

import java.util.Set;
import java.util.HashSet;

/**
 * A Block is a data structure that describes a Tetris-like block. Blocks
 * can be rotated relative to the origin coordinate.
 *
 * @author minnaar
 * @since 2015/07/21.
 *
 */
public class Block {
    protected Coordinate origin;

    public Block(int row, int col, Shape shape) {
        this.origin = new Coordinate(row, col);
        initShape(shape);
    }

    public Coordinate getOrigin() { return this.origin; }

    private void initShape(Shape shape) {
        switch (shape) {
            case I:
                origin.add(new Coordinate(0, 1, origin));
            case L:
                origin.add(new Coordinate(0, 2, origin));
                origin.add(new Coordinate(1, 2, origin));
                break;
            case CORNER:
                origin.add(new Coordinate(1, 1, origin));
            case BOX:
                origin.add(new Coordinate(1, 0, origin));
            case T:
                origin.add(new Coordinate(0, 2, origin));
                break;
        }
    }

    public void rotate() {
        while(origin.getChildren() != null) {
            for(Coordinate descendant : origin.getChildren()) {
                descendant.switchRowCol();
                descendant.negateCol();
            }
        }
    }

    public boolean equals(Block other) {
        if(other.getOrigin().equals(origin))
            return true;
        for(Coordinate otherCoord : other.getOrigin().getChildren()) {
            for(Coordinate coord : origin.getChildren())
                if(coord.equals(otherCoord))
                    return true;
        }
        return false;
    }

    public Set<Point> getPositions() {
        Set<Point> positions = new HashSet<>();
        positions.add(origin.getAbsoluteValue());;
        for(Coordinate coord : origin.getChildren())
            positions.add(coord.getAbsoluteValue());
        return positions;
    }
}
