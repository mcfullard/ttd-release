package za.ttd.MapDraw;

/**
 * Created by Bas on 24/07/2015.
 */
public class Maze {

    private int[][] cells;

    public Maze(int[][] cells) {
        this.cells = cells;
    }


    private void drawMap() {

        int row = cells.length;
        int column = cells[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; ++j) {
                int curCell = cells[row][column];
                int top = cells[row-1][column];
                int right = cells[row][column+1];
                int bottom = cells[row+1][column];
                int left = cells[row][column-1];

                /* 1 = wall
                *  0 = path*/
                if (curCell == 1)
                    if (top == 1 && right == 1 && bottom == 0 && left == 1) {
                        //draw up open connector
                    }
                    else if  (top == 1 && right == 1 && bottom == 1 && left == 0) {
                        //draw right open connector
                    }
                    else if  (top == 0 && right == 1 && bottom == 1 && left == 1) {
                        //draw bottom open connector
                    }
                    else if  (top == 1 && right == 0 && bottom == 1 && left == 1) {
                        //draw left open connector
                    }
                    else if  (top == 0 && right == 1 && bottom == 0 && left == 1) {
                        //draw horizontal connector
                    }
                    else if  (top == 1 && right == 0 && bottom == 1 && left == 0) {
                        //draw vertical connector
                    }
                    else if  (top == 1 && right == 0 && bottom == 0 && left == 0) {
                        //draw bottom edge
                    }
                    else if  (top == 0 && right == 1 && bottom == 0 && left == 0) {
                        //draw left edge
                    }
                    else if  (top == 0 && right == 0 && bottom == 1 && left == 0) {
                        //draw top edge
                    }
                    else if  (top == 0 && right == 0 && bottom == 0 && left == 1) {
                        //draw right edge
                    }
                    else if  (top == 0 && right == 1 && bottom == 1 && left == 0) {
                        //draw top left edge
                    }
                    else if  (top == 1 && right == 1 && bottom == 0 && left == 0) {
                        //draw bottom left edge
                    }
                    else if  (top == 0 && right == 0 && bottom == 1 && left == 1) {
                        //draw top right edge
                    }
                    else if  (top == 1 && right == 0 && bottom == 0 && left == 1) {
                        //draw bottom right edge
                    }

            }


        }
    }
}
