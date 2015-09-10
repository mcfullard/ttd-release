package za.ttd.pathfinding;
import za.ttd.characters.objects.Position;

/**
 * @author minnaar
 * @since 2015/09/09.
 */
public class Edge {
    private int weight = 1;
    private Position source;
    private Position destination;
    public Edge(Position source, Position destination) {
        this.source = source;
        this.destination = destination;
    }
}
