package za.ttd.MapGeneration;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Data structure to store sequentially located coordinates in a block.
 * The data structure should behave like a linked list, but potentially
 * have more than one "next" pointer, which is similar to a tree.
 *
 * A Coordinate's value is an integer array of length 2, where the first
 * entry is the row and the second entry the column.
 *
 * @author minnaar
 * @since 2015/07/24.
 */
public class Coordinate {
    private int[] value;
    protected ArrayList<Coordinate> descendants = null;

    public Coordinate(int[] value) {
        this.value = value;
    }

    public ArrayList<Coordinate> getDescendants() {return descendants;}

    public void add(Coordinate... descendants) {
        Collections.addAll(this.descendants, descendants);
    }

    public void negateRow() { this.value[0] = -this.value[0]; }

    public void negateCol() { this.value[1] = -this.value[1]; }

    public void switchRowCol() {
        int temp = this.value[0];
        this.value[0] = this.value[1];
        this.value[1] = temp;
    }
}
