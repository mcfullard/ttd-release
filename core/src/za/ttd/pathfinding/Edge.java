package za.ttd.pathfinding;

import za.ttd.characters.objects.Position;
import za.ttd.exceptions.NodeNotIncidentOnEdgeException;

/**
 * @author minnaar
 * @since 2015/09/09.
 */
public class Edge {
    private Integer weight = 1;
    private Node source;
    private Node destination;
    public Edge(Node source, Node destination) {
        this.source = source;
        this.destination = destination;
    }

    public Node getAdjacent(Node node) {
        if(node.getOrigin().equals(source.getOrigin()))
            return destination;
        if(node.getOrigin().equals(destination.getOrigin()))
            return source;
        throw new NodeNotIncidentOnEdgeException(String.format(
                "%s is not incident on the edge %s",
                node.toString(),
                this.toString()
        ));
    }

    public void setAdjacent(Node node, Node newAdjacent) {
        if(node.getOrigin().equals(source.getOrigin())) {
            destination = newAdjacent;
        } else if(node.getOrigin().equals(destination.getOrigin())) {
            source = newAdjacent;
        } else {
            throw new NodeNotIncidentOnEdgeException(String.format(
                    "%s is not incident on the edge %s",
                    node.toString(),
                    this.toString()
            ));
        }
    }

    public Position getAdjacentPosition(Node node) {
        Node adjacent = getAdjacent(node);
        if(adjacent != null)
            return adjacent.getOrigin();
        return null;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Edge clone() {
        Edge clone = new Edge(source, destination);
        clone.setWeight(weight);
        return clone;
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }
}
