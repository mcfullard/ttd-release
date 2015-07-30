package za.ttd.mapgen;

/**
 * @author minnaar
 * @since 2015/07/30.
 */
public class Map {
    private int[][] map;
    private int SCALE_FACTOR;
    public static final int WALL = 0;
    public static final int PATH = 1;

    public Map(int rows, int cols, int scaleFactor) {
        this.SCALE_FACTOR = scaleFactor;
        map = new int[rows * scaleFactor][cols * scaleFactor];
        initMap();
    }

    public int[][] getMap() { return map; }

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

    public void displayMap() {
        for(int r = 0; r < map.length; r++) {
            for(int c = 0; c < map[0].length; c++){
                System.out.print(map[r][c]);
            }
            System.out.print("\n");
        }
    }
}
