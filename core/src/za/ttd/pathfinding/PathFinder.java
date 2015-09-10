package za.ttd.pathfinding;

import java.lang.reflect.Array;
import java.util.*;

import za.ttd.characters.objects.Position;

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
        populateNodes();
        attachEdges();
    }

    private void populateNodes() {
        nodes = new HashMap<>();
        for(Node node : getNodesFromMap())
            nodes.put(node.getOrigin(), node);
    }

    private Set<Node> getNodesFromMap() {
        Set<Node> nodes = new HashSet<>();
        for(int r = 0; r < map.getMap().length; r++) {
            for(int c = 0; c < map.getMap()[0].length; c++) {
                if(map.getMap()[r][c] == za.ttd.mapgen.Map.PATH) {
                    nodes.add(new Node(new Position(c, r)));
                }
            }
        }
        return nodes;
    }

    private void attachEdges() {
        Set<Node> checked = new HashSet<>();
        for(Node node : nodes.values()) {
            for(Node adjacent : getAdjacentNodes(node)) {
                if (!checked.contains(node)) {
                    Edge edge = new Edge(node, adjacent);
                    node.addEdges(edge);
                    adjacent.addEdges(edge);
                }
            }
            checked.add(node);
        }
    }

    private Set<Node> getAdjacentNodes(Node node) {
        Set<Node> adjacent = new HashSet<>();
        Position origin = node.getOrigin();
        Position up = getUpPosition(origin);
        Position down = getDownPosition(origin);
        Position left = getLeftPosition(origin);
        Position right = getRightPosition(origin);
        if(up != null)
            adjacent.add(new Node(up));
        if(down != null)
            adjacent.add(new Node(down));
        if(left != null)
            adjacent.add(new Node(left));
        if(right != null)
            adjacent.add(new Node(right));
        return adjacent;
    }

    private Position getLeftPosition(Position pos) {
        if(map.pathLeft(pos.getIntX(), pos.getIntY()))
            return new Position(pos.getIntX() - 1, pos.getIntY());
        return null;
    }

    private Position getRightPosition(Position pos) {
        if(map.pathRight(pos.getIntX(), pos.getIntY()))
            return new Position(pos.getIntX() + 1, pos.getIntY());
        return null;
    }

    private Position getUpPosition(Position pos) {
        if(map.pathUp(pos.getIntX(), pos.getIntY()))
            return new Position(pos.getIntX(), pos.getIntY() - 1);
        return null;
    }

    private Position getDownPosition(Position pos) {
        if(map.pathDown(pos.getIntX(), pos.getIntY()))
            return new Position(pos.getIntX(), pos.getIntY() + 1);
        return null;
    }

}
