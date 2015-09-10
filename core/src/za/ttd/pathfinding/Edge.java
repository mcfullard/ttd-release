package za.ttd.pathfinding;
import za.ttd.characters.objects.Position;

import java.util.LinkedList;
import java.util.List;

/**
 * @author minnaar
 * @since 2015/09/09.
 */
public class Edge {
    private int weight = 1;
    private Node source;
    private Node destination;
    public Edge(Node source, Node destination) {
        this.source = source;
        this.destination = destination;
    }

    public List<Node> getNodes() {
        List<Node> nodes = new LinkedList<>();
        nodes.add(source);
        nodes.add(destination);
        return nodes;
    }
}
