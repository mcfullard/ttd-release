package za.ttd.MapGeneration;

/**
 * @author minnaar
 * @since 2015/07/27.
 */
public class Point {
    public int r;
    public int c;

    public Point(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public boolean equals(Point other) {
        return this.r == other.r && this.c == other.c;
    }
}
