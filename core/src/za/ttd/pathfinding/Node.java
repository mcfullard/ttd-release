package za.ttd.pathfinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import za.ttd.characters.objects.Position;

/**
 * Data structure similar to vertex of graph, but with the added property of a distance vector
 *
 * @author minnaar
 * @since 2015/09/09.
 */
public class Node {
    private Position origin;
    private List<Edge> edges;
    private Map<Position, EdgeContainer> distanceVector = new HashMap<>();

    public Node(Position origin, Edge... edges) {
        this.origin = origin;
        addEdges(edges);
    }

    public void addEdges(Edge... edges) {
        for(Edge edge : edges)
            this.edges.add(edge);
    }
}
