package za.ttd.mapgen;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * @author minnaar
 * @since 2015/07/21.
 *
 * This is the base class for randomized map generation. The Grid must be at least 5x3 in RxC dimensions.
 * The implementation of the grid uses a container that stores values for all the indexes that are available.
 *
 */
public class Grid {
    private static final int ROTATE_NUMBER = 3;
    private static final double ABSOLUTE_DIFFERENCE = 0.001;
    private int POS_GUESSES;
    private final int INIT_OPEN_SPACES;
    private Set<Point> available = new HashSet<>();
    private Set<Block> blocks = new HashSet<>();
    private int rows, cols;
    private Random random;
    private RandomEnum<Shape> shapeChooser;
    private Map map;

    public Grid(int rows, int cols, long seed) {
        if(rows < 5)
            rows = 5;
        if(cols < 3)
            cols = 3;
        this.rows = rows;
        this.cols = cols;
        this.map = new Map(rows, cols, 2);
        INIT_OPEN_SPACES = rows * cols - 4;
        POS_GUESSES = rows * cols;
        this.random = new Random(seed);
        this.shapeChooser = new RandomEnum<>(Shape.class, random);
        initAvailable();
        initPlaceHolders();
    }

    private void initAvailable() {
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                available.add(new Point(i, j));
    }

    private void initPlaceHolders() {
        int top = rows/2;
        Block box = new Block(top, 0, SpecialShape.BOX);
        tryPlace(box);
    }

    private void tryPlace(Block block) {
        int pos = 0;
        do {
            int rotations = 0;
            do {
                if(!isCollision(block)) {
                    place(block);
                    return;
                }
                block.rotate();
                rotations++;
            } while(rotations < ROTATE_NUMBER);
            setRandomOrigin(block);
            pos++;
        } while(pos < POS_GUESSES);
    }

    private void place(Block block) {
        available.removeAll(block.getPositions());
        blocks.add(block);
    }

    public void setRandomOrigin(Block block) {
        block.getOrigin().r = random.nextInt(rows);
        block.getOrigin().c = random.nextInt(cols);
    }

    public boolean isCollision(Block block) {
        for (Point point : block.getPositions())
            if (!available.contains(point))
                return true;
        return false;
    }

    // keep trying to place blocks until the open space percentage stops differing by a predefined amount
    public void populateGrid() {
        double openPercentage;
        do {
            openPercentage = available.size()/(double)INIT_OPEN_SPACES;
            Block block = new Block(0,0, shapeChooser.random());
            setRandomOrigin(block);
            tryPlace(block);
        } while (absoluteDifference(available.size()/(double)INIT_OPEN_SPACES, openPercentage) >= ABSOLUTE_DIFFERENCE);
        useAvailable();
    }

    private double absoluteDifference(double first, double second) {
        return Math.abs(first - second);
    }

    private void useAvailable() {
        for(Iterator<Point> iter = available.iterator(); iter.hasNext(); ) {
            Point point = iter.next();
            blocks.add(new Block(point.r, point.c, SpecialShape.SMALL_BOX));
            iter.remove();
        }
    }

    public void drawEdges() {
        for(Block block : blocks) {
            drawBlockEdges(block);
        }
        map.insertRow(Map.fillIntArray((map.getMap())[0].length, Map.PATH), 0);
        map.insertRow(Map.fillIntArray((map.getMap())[0].length, Map.WALL), 0);
        map.insertRow(Map.fillIntArray((map.getMap())[0].length, Map.WALL), (map.getMap()).length);
        map.insertCol(Map.fillIntArray(map.getMap().length, Map.WALL), map.getMap()[0].length);
    }

    private void drawBlockEdges(Block block) {
        Set<Point> hasRight = new HashSet<>(),
                hasDown = new HashSet<>(),
                hasBoth = new HashSet<>(),
                hasNeither = new HashSet<>(),
                hasDiagAndBoth = new HashSet<>();
        block.populateDirectionPoints(hasRight, hasDown, hasBoth, hasNeither, hasDiagAndBoth);
        for(Point point : hasDown) {
            map.drawRightEdge(point);
        }
        for(Point point : hasRight) {
            map.drawBottomEdge(point);
        }
        for(Point point : hasNeither) {
            map.drawRightEdge(point);
            map.drawBottomEdge(point);
        }
        for(Point point : hasBoth) {
            map.drawRightBottomCell(point);
        }
        for(Point point: hasDiagAndBoth) {
            map.drawLeftEdge(point);
            map.drawBottomEdge(point);
        }
    }

    public void drawRegions() {
        map.drawDenDoor();
        map.drawDen();
        map.drawAboveDen();
        map.drawBelowDen();
        map.drawTopRight();
        map.drawBottomRight();
    }

    public Set<Point> getAvailable() {return this.available;}

    public Set<Block> getBlocks() {return this.blocks;}

    public Map getMap() { return map; }

    public static Map generateMap(int rows, int cols, long seed) {
        Grid grid = new Grid(rows, cols, seed);
        grid.populateGrid();
        grid.drawEdges();
        grid.drawRegions();
        grid.getMap().verticalMirror();
        return grid.getMap();
    }
}
