package za.ttd.pathfinding;

import java.util.*;

import za.ttd.characters.objects.Position;
import za.ttd.exceptions.AllPathsNotExploredException;

/**
 * Data structure similar to vertex of graph, but with the added property of a distance vector.
 * For more on the distance vector algorithm, see Goodrich & Tamassia p.532
 *
 * @author minnaar
 * @since 2015/09/09.
 */
public class Node {
    private Position origin;
    private Set<Edge> edges = new HashSet<>();
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
                    /**
                     * you want containers to have cloned edges because the container's
                     * edges may change during the distance vector update
                     */
            );
        }
        for(Node node : allNodes) {
            if(distanceVector.get(node.getOrigin()) == null) {
                distanceVector.put(
                    node.getOrigin(),
                    new EdgeContainer(null, EdgeContainer.LARGE)
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
                // if it's shorter via the adjacent edge to the other node
                if(distanceViaAdjacent + edge.getWeight() < directDistance) {
                    // update the new distance and change the edge in the container
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
        Edge edge = distanceVector.get(destination).edge;
        if(edge != null)
            return edge.getAdjacent(this).getOrigin();
        throw new AllPathsNotExploredException(String.format(
                "The path from %s to %s has not been catalogued by the PathFinder algorithm. Please check implementation.",
                origin.toString(),
                destination.toString()
        ));
    }

    public Set<Edge> getEdges() {
        return edges;
    }
}
