package za.ttd.mapgen;

import static java.lang.System.arraycopy;

/**
 * @author minnaar
 * @since 2015/07/30.
 */
public class Map {
    private int[][] map;
    private int SCALE_FACTOR;
    public static final int WALL = 1;
    public static final int PATH = 0;

    public Map(int rows, int cols, int scaleFactor) {
        this.SCALE_FACTOR = scaleFactor;
        map = new int[rows * scaleFactor][cols * scaleFactor];
        initMap();
    }

    public int[][] getMap() { return map; }

    public boolean isWall(int x, int y) {

        return map[y][x] == WALL;
    }

    public void verticalMirror() {
        int[][] mirrored = new int[map.length][map[0].length * 2];
        for(int r = 0; r < map.length; r++) {
            for(int c = 0; c < map[0].length; c++) {
                mirrored[r][c + map[r].length] = map[r][c];
                mirrored[r][map[r].length - 1 - c] = map[r][c];
            }
        }
        map = mirrored;
    }

    private void initMap() {
        for(int r = 0; r < map.length; r++)
            map[r] = fillIntArray(map[0].length, WALL);
    }

    public void drawRightEdge(Point point) {
        fillRectangle(
                lowerBound(point.r),
                upperBound(point.r),
                upperBound(point.c),
                upperBound(point.c),
                PATH
        );
    }

    public void drawBottomEdge(Point point) {
        fillRectangle(
                upperBound(point.r),
                upperBound(point.r),
                lowerBound(point.c),
                upperBound(point.c),
                PATH
        );
    }

    public void drawLeftEdge(Point point) {
        fillRectangle(
                lowerBound(point.r),
                upperBound(point.r),
                lowerBound(point.c),
                lowerBound(point.c),
                PATH
        );
    }

    public void drawRightBottomCell(Point point) {
        fillRectangle(
                upperBound(point.r),
                upperBound(point.r),
                upperBound(point.c),
                upperBound(point.c),
                PATH
        );
    }

    private int lowerBound(int x) {
        return SCALE_FACTOR * x;
    }

    private int upperBound(int x) {
        return SCALE_FACTOR * (x + 1) - 1;
    }

    private void fillRectangle(int rowStart, int rowEnd, int colStart, int colEnd, int type) {
        for (int r = rowStart; r <= rowEnd; r++) {
            for (int c = colStart; c <= colEnd; c++) {
                map[r][c] = type;
            }
        }
    }

    public static int[] fillIntArray(int length, int fill) {
        int[] result = new int[length];
        for(int i = 0; i < length; i++)
            result[i] = fill;
        return result;
    }

    public void displayMap() {
        for(int r = 0; r < map.length; r++) {
            for(int c = 0; c < map[0].length; c++){
                System.out.print(map[r][c]);
            }
            System.out.print("\n");
        }
    }

    public void insertRow(int[] row, int pos) throws IndexOutOfBoundsException {
        int[][] newMap = new int[map.length + 1][map[0].length];
        arraycopy(map, 0, newMap, 0, pos);
        newMap[pos] = row;
        arraycopy(map, pos, newMap, pos + 1, map.length - pos);
        map = newMap;
    }

    public void insertCol(int[] col, int pos) throws IndexOutOfBoundsException {
        int[][] newMap = new int[map.length][map[0].length + 1];
        for(int r = 0; r < map.length; r++) {
            arraycopy(map[r], 0, newMap[r], 0, pos);
            newMap[r][pos] = col[r];
            arraycopy(map[r], pos, newMap[r], pos + 1, map[r].length - pos);
        }
        map = newMap;
    }
}
