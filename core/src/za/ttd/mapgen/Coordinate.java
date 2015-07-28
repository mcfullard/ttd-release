package za.ttd.mapgen;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author minnaar
 * @since 2015/07/24.
 */
public class Coordinate extends Point {
    private Coordinate parent = null;
    protected Set<Coordinate> children = new HashSet<>();

    public Coordinate(int row, int col) {
        super(row, col);
    }

    public Coordinate(int row, int col, Coordinate parent) {
        super(row, col);
        this.parent = parent;
    }

    public HashSet<Coordinate> getChildren() {
        return (HashSet<Coordinate>)children;
    }

    public void addChild(Coordinate... children) {
        for(Coordinate coordinate : children)
            this.children.add(coordinate);
    }

    public void negateCol() { this.c = -this.c; }

    public void switchRowCol() {
        int temp = this.r;
        this.r = this.c;
        this.c = temp;
    }

    public Point getAbsoluteValue() {
        if(parent != null)
            return new Point(this.r + parent.r, this.c + parent.c);
        return new Point(this.r, this.c);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(getAbsoluteValue().toString());
        for(Coordinate c : children)
            stringBuilder.append(String.format(", %s", c.getAbsoluteValue().toString()));
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Coordinate rhs = (Coordinate) obj;
        return new EqualsBuilder()
                .append(getAbsoluteValue(), rhs.getAbsoluteValue())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13,43)
            .append(getAbsoluteValue())
            .toHashCode();
    }
}
