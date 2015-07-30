package za.ttd.mapgen;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Iterator;
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
                origin.addChild(new Coordinate(0, 1, origin));
                break;
            case L:
                origin.addChild(new Coordinate(0, 1, origin));
                origin.addChild(new Coordinate(0, 2, origin));
                origin.addChild(new Coordinate(1, 2, origin));
                break;
            case CORNER:
                origin.addChild(new Coordinate(0, 1, origin));
                origin.addChild(new Coordinate(1, 1, origin));
                break;
            case BOX:
                origin.addChild(new Coordinate(0, 1, origin));
                origin.addChild(new Coordinate(1, 1, origin));
                origin.addChild(new Coordinate(1, 0, origin));
                break;
            case T:
                origin.addChild(new Coordinate(0, 1, origin));
                origin.addChild(new Coordinate(1, 1, origin));
                origin.addChild(new Coordinate(0, 2, origin));
                break;
        }
    }

    public void rotate() {
        if(origin.getChildren() != null) {
            for(Coordinate descendant : origin.getChildren()) {
                descendant.switchRowCol();
                descendant.negateCol();
            }
        }
    }

    public Set<Point> getPositions() {
        Set<Point> positions = new HashSet<>();
        positions.add(origin.getAbsoluteValue());;
        for(Coordinate coord : origin.getChildren())
            positions.add(coord.getAbsoluteValue());
        return positions;
    }

    public Set<Point> getHasRight() {
        Set<Point> positions = getPositions();
        Set<Point> hasRight = new HashSet<>();
        return null;
    }

    @Override
    public String toString() {
        return origin.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Block rhs = (Block)obj;
        return new EqualsBuilder()
                .append(this.origin, rhs.getOrigin())
                .append(this.origin.getChildren().hashCode(), rhs.getOrigin().getChildren().hashCode())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(7, 23)
                .append(this.origin)
                .append(this.origin.getChildren())
                .toHashCode();
    }
}
