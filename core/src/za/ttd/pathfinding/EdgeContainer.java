package za.ttd.pathfinding;

/**
 * @author minnaar
 * @since 2015/09/09.
 */
public class EdgeContainer {
    public static final int LARGE = 10000;
    public Edge edge;
    public Integer distance;
    public EdgeContainer(Edge edge, Integer distance) {
        this.edge = edge;
        this.distance = distance;
    }
}
