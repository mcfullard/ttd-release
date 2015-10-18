package za.ttd.exceptions;

/**
 * @author minnaar
 * @since 2015/09/12.
 */
public class NodeNotIncidentOnEdgeException extends IllegalArgumentException {
    public NodeNotIncidentOnEdgeException() {}
    public NodeNotIncidentOnEdgeException(String message) {
        super(message);
    }
}
