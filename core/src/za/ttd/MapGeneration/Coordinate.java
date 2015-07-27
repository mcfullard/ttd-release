package za.ttd.MapGeneration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author minnaar
 * @since 2015/07/24.
 */
public class Coordinate extends Point {
    private Coordinate parent = null;
    protected Set<Coordinate> children = null;

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

    public void add(Coordinate... children) { Collections.addAll(this.children, children); }

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

    public boolean equals(Coordinate other) {
        return this.getAbsoluteValue().equals(other.getAbsoluteValue());
    }
}
