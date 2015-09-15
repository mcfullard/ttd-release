package za.ttd.pathfinding;

import java.util.*;
import java.util.Map;

import za.ttd.characters.objects.Position;
import za.ttd.mapgen.*;

/**
 * This class encapsulates all path generation and position finding based thereon.
 *
 * @author minnaar
 * @since 2015/09/09.
 */
public class PathFinder {
    private za.ttd.mapgen.Map map;
    private Map<Position, Node> nodes;

    public PathFinder(za.ttd.mapgen.Map map) {
        this.map = map;
    }

    public void initialize() {
        populateNodes();
        attachEdges();
        initDistanceVectors();
        updateDistanceVectors();
    }

    public void populateNodes() {
        nodes = new HashMap<>();
        for(Node node : getNodesFromMap())
            nodes.put(node.getOrigin(), node);
    }

    public Set<Node> getNodesFromMap() {
        Set<Node> nodes = new HashSet<>();
        for(int r = 0; r < map.getMap().length; r++) {
            for(int c = 0; c < map.getMap()[0].length; c++) {
                if(!map.isType(c, r, za.ttd.mapgen.Map.WALL)) {
                    nodes.add(new Node(new Position(c, r)));
                }
            }
        }
        return nodes;
    }

    public void attachEdges() {
        Set<Node> checked = new HashSet<>();
        for(Node node : nodes.values()) {
            for(Node adjacent : getAdjacentNodes(node)) {
                if (!checked.contains(node)) {
                    Edge edge = new Edge(node, adjacent);
                    node.addEdges(edge);
                }
            }
            checked.add(node);
        }
    }

    public Set<Node> getAdjacentNodes(Node node) {
        Set<Node> adjacent = new HashSet<>();
        Position origin = node.getOrigin();
        Position up = getUpPosition(origin);
        Position down = getDownPosition(origin);
        Position left = getLeftPosition(origin);
        Position right = getRightPosition(origin);
        if(up != null)
            adjacent.add(nodes.get(up));
        if(down != null)
            adjacent.add(nodes.get(down));
        if(left != null)
            adjacent.add(nodes.get(left));
        if(right != null)
            adjacent.add(nodes.get(right));
        return adjacent;
    }

    public Position getLeftPosition(Position pos) {
        if(map.pathLeft(pos.getIntX(), pos.getIntY()))
            return new Position(pos.getIntX() - 1, pos.getIntY());
        return null;
    }

    public Position getRightPosition(Position pos) {
        if(map.pathRight(pos.getIntX(), pos.getIntY()))
            return new Position(pos.getIntX() + 1, pos.getIntY());
        return null;
    }

    public Position getUpPosition(Position pos) {
        if(map.pathUp(pos.getIntX(), pos.getIntY()))
            return new Position(pos.getIntX(), pos.getIntY() - 1);
        return null;
    }

    public Position getDownPosition(Position pos) {
        if(map.pathDown(pos.getIntX(), pos.getIntY()))
            return new Position(pos.getIntX(), pos.getIntY() + 1);
        return null;
    }

    private void initDistanceVectors() {
        for(Node node: nodes.values()) {
            node.initDistanceVector(nodes.values());
        }
    }

    private void updateDistanceVectors() {
        for(int i = 0; i < nodes.size() - 1; i++) {
            for (Node node : nodes.values()) {
                node.updateDistanceVector(nodes.values());
            }
        }
    }

    public Map<Position, Node> getNodes() {
        return nodes;
    }

}
