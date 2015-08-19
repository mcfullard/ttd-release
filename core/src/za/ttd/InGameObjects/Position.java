package za.ttd.InGameObjects;

import javafx.geometry.Pos;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author minnaar
 * @since 2015/08/17.
 */
public class Position {
    private float x;
    private float y;
    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public int getIntX() {return Math.round(x);}
    public int getIntY() {return Math.round(y);}
    public float getX() {return x;}
    public float getY() {return y;}
    public void setX(float x) {this.x = x;}
    public void setY(float y) {this.y = y;}
    public void increaseX(float x) {this.x += x;}
    public void increaseY(float y) {this.y += y;}
    public Position clone() {
        return new Position(this.x, this.y);
    }
/**
    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Position rhs = (Position) obj;
        return new EqualsBuilder()
                .append(x, rhs.getX())
                .append(y, rhs.getY())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17,31).
                append(x).
                append(y).
                toHashCode();
    }*/
}
