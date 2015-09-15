package za.ttd.mapgen;


import static java.lang.System.arraycopy;

/**
 * @author minnaar
 * @since 2015/07/30.
 */
public class Map {
    private int[][] map;
    private int r;
    private int c;
    private int SCALE_FACTOR;
    private int FIRST_ADDED_ROWS = 2;
    public static final int PATH = 0;
    public static final int WALL = 1;
    public static final int MOUTHWASH = 2;
    public static final int BRUSH = 3;
    public static final int THOMAS = 4;
    public static final int BAD_BREATH = 5;
    public static final int TOOTH_DECAY = 6;
    public static final int DOOR = 7;

    public Map(int rows, int cols, int scaleFactor) {
        this.r = rows;
        this.c = cols;
        this.SCALE_FACTOR = scaleFactor;
        map = new int[rows * scaleFactor][cols * scaleFactor];
        initMap();
    }

    public int[][] getMap() { return map; }

    /**
     * A generic method to test different types of map regions. This will replace isWall, isPath
     *
     * @param c Column of positive X
     * @param r Row or positive Y
     * @param type Any of the integer types defined in Map class
     * @return True if the value at respective coordinate is of that type
     */
    public boolean isType(int c, int r, int type) {
        return map[r][c] == type;
    }

    public boolean isMouthwash(int c, int r) {
        return map[r][c] == MOUTHWASH;
    }

    public boolean isBrush(int c, int r) {
        return map[r][c] == BRUSH;
    }

    public boolean isBadBreath(int c, int r) {
        return map[r][c] == BAD_BREATH;
    }

    public boolean isThomas(int c, int r) {
        return map[r][c] == THOMAS;
    }

    public boolean isDoor(int c, int r) {
        return map[r][c] == DOOR;
    }

    public boolean isToothDecay(int c, int r) {
        return map[r][c] == TOOTH_DECAY;
    }

    /**
     * @param c Column or positive X
     * @param r Row or positive Y
     * @return True if path to the left of coordinates
     */
    public boolean pathLeft(int c, int r) {
        if(withinBounds(c - 1, r))
            if(!isType(c - 1, r, Map.WALL))
                return true;
        return false;
    }

    /**
     * @param c Column or positive X
     * @param r Row or positive Y
     * @return True if path to the right of coordinates
     */
    public boolean pathRight(int c, int r) {
        if(withinBounds(c + 1, r))
            if(!isType(c + 1, r, Map.WALL))
                return true;
        return false;
    }

    /**
     * @param c Column or positive X
     * @param r Row or positive Y
     * @return True if path above coordinates
     */
    public boolean pathUp(int c, int r) {
        if(withinBounds(c, r - 1))
            if(!isType(c, r - 1, Map.WALL))
                return true;
        return false;
    }

    /**
     * @param c Column or positive X
     * @param r Row or positive Y
     * @return True if path below coordinates
     */
    public boolean pathDown(int c, int r) {
        if(withinBounds(c, r + 1))
            if(!isType(c, r + 1, Map.WALL))
                return true;
        return false;
    }

    /**
     * @param c Column or positive X
     * @param r Row or positive Y
     * @return True if coordinate is on the map
     */
    public boolean withinBounds(int c, int r) {
        return r < map.length &&
                r >= 0 &&
                c < map[0].length &&
                c >= 0;
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

    public int getDenDoorRow() { return lowerBound((r-1)/2) + FIRST_ADDED_ROWS; }

    public void drawDenDoor() {
        fillRectangle(getDenDoorRow(), getDenDoorRow(), 0, 0, DOOR);
    }

    public void drawDen() {
        fillRectangle(getDenDoorRow() + 1, getDenDoorRow() + 1, 0, 1, BAD_BREATH);
    }

    public void drawAboveDen() {
        fillRectangle(getDenDoorRow() - 1, getDenDoorRow() - 1, 0, 0, BRUSH);
    }

    public void drawBelowDen() {
        fillRectangle(getDenDoorRow() + 3, getDenDoorRow() + 3, 0, 0, THOMAS);
    }

    public void drawTopRight() {
        fillRectangle(1, 1, upperBound(c - 1), upperBound(c - 1), MOUTHWASH);
    }

    public void drawBottomRight() {
        int row = upperBound(r - 1) + FIRST_ADDED_ROWS;
        fillRectangle(row, row, upperBound(c - 1), upperBound(c - 1), MOUTHWASH);
    }
}
