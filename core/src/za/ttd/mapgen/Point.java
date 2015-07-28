package za.ttd.mapgen;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public String toString() {
        return String.format("(%d, %d)", r, c);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Point rhs = (Point) obj;
        return new EqualsBuilder()
                .append(r, rhs.r)
                .append(c, rhs.c)
                .isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17,31).
            append(r).
            append(c).
            toHashCode();
    }

}
