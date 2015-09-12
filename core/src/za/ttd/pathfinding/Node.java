package za.ttd.pathfinding;

import java.util.*;

import za.ttd.characters.objects.Position;

/**
 * Data structure similar to vertex of graph, but with the added property of a distance vector.
 * For more on the distance vector algorithm, see Goodrich & Tamassia p.532
 *
 * @author minnaar
 * @since 2015/09/09.
 */
public class Node {
    private Position origin;
    private List<Edge> edges = new LinkedList<>();
    private Map<Position, EdgeContainer> distanceVector = new HashMap<>();

    public Position getOrigin() {
        return origin;
    }

    public Map<Position, EdgeContainer> getDistanceVector() {
        return distanceVector;
    }

    public Node(Position origin) {
        this.origin = origin;
    }

    public void addEdges(Edge... edges) {
        for(Edge edge : edges)
            this.edges.add(edge);
    }

    public void initDistanceVector(Collection<Node> allNodes) {
        for(Edge edge : edges) {
            distanceVector.put(
                    edge.getAdjacentPosition(this),
                    new EdgeContainer(edge.clone(), edge.getWeight())
            );
        }
        for(Node node : allNodes) {
            if(distanceVector.get(node.getOrigin()) == null) {
                distanceVector.put(
                    node.getOrigin(),
                    new EdgeContainer(null, Integer.MAX_VALUE)
                );
            }
        }
    }

    public void updateDistanceVector(Collection<Node> allNodes) {
        for(Edge edge : edges) {
            Node adjacent = edge.getAdjacent(this);
            for(Node other : allNodes) {
                EdgeContainer container = distanceVector.get(other.getOrigin());
                Integer directDistance = container.distance;
                Integer distanceViaAdjacent = adjacent
                        .getDistanceVector()
                        .get(other.getOrigin())
                        .distance;
                if(distanceViaAdjacent + edge.getWeight() < directDistance) {
                    container.distance = edge.getWeight() + distanceViaAdjacent;
                    if(container.edge != null)
                        container.edge.setAdjacent(this, adjacent);
                    else
                        container.edge = new Edge(this, adjacent);
                }
            }
        }
    }

    public Position shortestPathTo(Position destination) {
        return distanceVector.get(destination).edge.getAdjacent(this).getOrigin();
    }
}
