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
    private int POS_GUESSES;
    private final int INIT_OPEN_SPACES;
    private Set<Point> available = new HashSet<>();
    private Set<Block> blocks = new HashSet<>();
    private int rows, cols;
    private Random random;
    private RandomEnum<Shape> shapeChooser;

    public Grid(int rows, int cols, long seed) {
        if(rows < 5)
            rows = 5;
        if(cols < 3)
            cols = 3;
        this.rows = rows;
        this.cols = cols;
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

    public void populateGrid(double maxOpenPercentage) {
        while (available.size()/(double)INIT_OPEN_SPACES >= maxOpenPercentage) {
            Block block = new Block(0,0, shapeChooser.random());
            setRandomOrigin(block);
            tryPlace(block);
        }
        useAvailable();
    }

    private void useAvailable() {
        for(Iterator<Point> iter = available.iterator(); iter.hasNext(); ) {
            Point point = iter.next();
            blocks.add(new Block(point.r, point.c, SpecialShape.SMALL_BOX));
            iter.remove();
        }
    }

    public Set<Point> getAvailable() {return this.available;}

    public Set<Block> getBlocks() {return this.blocks;}

    public class Map {
        private int[][] map;
        private int SCALE_FACTOR;
        public static final int WALL = 0;
        public static final int PATH = 1;

        public Map(int scaleFactor) {
            this.SCALE_FACTOR = scaleFactor;
            map = new int[rows * scaleFactor][cols * scaleFactor];
            initMap();
        }

        private void initMap() {
            for(int r = 0; r < map.length; r++)
                for(int c = 0; c < map[0].length; c++)
                    map[r][c] = WALL;
        }

        public void drawRightEdge(Point point) {
            fillRectangle(
                    lowerBound(point.r),
                    upperBound(point.r),
                    upperBound(point.c),
                    upperBound(point.c)
            );
        }

        public void drawBottomEdge(Point point) {
            fillRectangle(
                    upperBound(point.r),
                    upperBound(point.r),
                    lowerBound(point.c),
                    upperBound(point.c)
            );
        }

        public void drawLeftEdge(Point point) {
            fillRectangle(
                    lowerBound(point.r),
                    upperBound(point.r),
                    lowerBound(point.c),
                    lowerBound(point.c)
            );
        }

        public void drawRightBottomCell(Point point) {
            fillRectangle(
                    upperBound(point.r),
                    upperBound(point.r),
                    upperBound(point.c),
                    upperBound(point.c)
            );
        }

        private int lowerBound(int x) {
            return SCALE_FACTOR * x;
        }

        private int upperBound(int x) {
            return SCALE_FACTOR * (x + 1) - 1;
        }

        private void fillRectangle(int rowStart, int rowEnd, int colStart, int colEnd) {
            for (int r = rowStart; r <= rowEnd; r++) {
                for (int c = colStart; c <= colEnd; c++) {
                    map[r][c] = PATH;
                }
            }
        }
    }
}
