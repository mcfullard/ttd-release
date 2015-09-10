package za.ttd.pathfinding;

/**
 * @author minnaar
 * @since 2015/09/09.
 */
public class EdgeContainer {
    private Edge edge;
    private int distance;
    public EdgeContainer(Edge edge, int distance) {
        this.edge = edge;
        this.distance = distance;
    }
}
