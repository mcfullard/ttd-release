package za.ttd.characters.objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import za.ttd.mapgen.RandomEnum;

import java.util.Random;

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
    public int getFloorX() {return (int)Math.floor(x);}
    public int getChangedFloorX(float change) {return (int)Math.floor(x + change);}
    public int getFloorY() {return (int)Math.floor(y);}
    public int getChangedFloorY(float change) {return (int)Math.floor(y + change);}
    public int getCeilX() {return (int)Math.ceil(x);}
    public int getChangedCeilX(float change) {return (int)Math.ceil(x + change);}
    public int getCeilY() {return (int)Math.ceil(y);}
    public int getChangedCeilY(float change) {return (int)Math.ceil(y + change);}
    public float getX() {return x;}
    public float getY() {return y;}
    public void setX(float x) {this.x = x;}
    public void setY(float y) {this.y = y;}
    public void increaseX(float x) {this.x += x;}
    public void increaseY(float y) {this.y += y;}
    public Position clone() {
        return new Position(this.x, this.y);
    }
    public boolean compareBase(Position other) {
        return getIntX() == other.getIntX() && getIntY() == other.getIntY();
    }

    @Override
    public String toString() {
        return String.format(
                "(%f, %f)",
                x,
                y
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Position rhs = (Position) obj;
        return new EqualsBuilder()
                .append(this.getIntX(), rhs.getIntX())
                .append(this.getIntY(), rhs.getIntY())
                .isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder(13,47)
                .append(this.getIntX())
                .append(this.getIntY())
                .toHashCode();
    }

    public Direction getDirectionTo(Position destination) {
        // remember that x is positive to the right and y is positive down
        float changeX = destination.x - this.x;
        float changeY = destination.y - this.y;
        // if one or no axes have change
        if(changeX == 0f) {
            if(changeY == 0f) {
                return Direction.NONE;
            } else if(changeY > 0f) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        } else if(changeX > 0f) {
            if(changeY == 0f) {
                return Direction.RIGHT;
            }
        } else {
            if(changeY == 0f) {
                return Direction.LEFT;
            }
        }
        // if both axes have change
        RandomEnum<Direction> randomDirection = new RandomEnum<Direction>(Direction.class, new Random());
        return randomDirection.random();
    }
}
