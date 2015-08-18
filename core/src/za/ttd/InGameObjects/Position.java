package za.ttd.InGameObjects;

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
    public int getIntX() {return (int)x;}
    public int getIntY() {return (int)y;}
    public float getX() {return x;}
    public float getY() {return y;}
    public void setX(float x) {this.x = x;}
    public void setY(float y) {this.y = y;}
    public void increaseX(float x) {this.x += x;}
    public void increaseY(float y) {this.y += y;}
    public Position clone() {
        return new Position(this.x, this.y);
    }
}
