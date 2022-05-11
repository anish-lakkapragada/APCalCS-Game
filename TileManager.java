
/**
 * Tile Manager class for managing the tiles.
 */

import java.awt.*;

public class TileManager {
    private Tile[][] grid;
    private int numRows, numCols;
    private int curRow, curCol;
    private int tileWidth, tileHeight;
    private int topX, topY; // starting X and Y coordinates of the entire grid
    private Graphics g;

    /**
     * Constructor for the TileManager class to manage the tiles.
     * 
     * @param numRows    the number of rows in this grid
     * @param numCols    the number of columns in this grid
     * @param tileWidth  the width of each column in this grid
     * @param tileHeight the height of each column in this grid
     * @param topX       the top-left x-coordinate for the first tile
     * @param topY       the top-left y-coordinate for the first tile
     * @param g          the graphics object
     */
    public TileManager(int numRows, int numCols, int tileWidth, int tileHeight, int topX, int topY, Graphics g) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.topX = topX;
        this.topY = topY;
        this.g = g;
    }

    /**
     * Draws the tiles (with the specified list of labels) onto the grid.
     * 
     * @param gridLabels labels (same dimensions as the grid itself)
     */
    public void drawGrid(String[][] gridLabels) {
        grid = new Tile[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                grid[i][j] = new Tile(gridLabels[i][j], topX + i * tileWidth, topY + i * tileHeight, tileWidth,
                        tileHeight, g); // draws the tile on the grid (each of which has the label)
            }
        }
    }

    private boolean validLoc(int r, int c) {
        return r >= 0 && r < numRows && c >= 0 && c < numCols;
    }

    /**
     * Sets the current location on the grid (in the JFrame) to the specified
     * row and column.
     * 
     * Returns 1 if successful and 0 if not (out of bounds value).
     * 
     * @param newRow
     * @param new    Col
     */
    public int setLoc(int newRow, int newCol) {
        if (!validLoc(newRow, newCol)) {
            return 0;
        }

        grid[curRow][curCol].toggleSelected();
        grid[newRow][newCol].toggleSelected();
        curRow = newRow;
        curCol = newCol;

        return 1;
    }

    public void setLabels(String[][] gridLabels) {
        // go through each of the tiles and set their label.
    }

    /**
     * Get the current row selected in the tile grid.
     * 
     * @return current row selected in the tile grid.
     */
    public int curRow() {
        return curRow;
    }

    /**
     * Get the current column selected in the tile grid.
     * 
     * @return current column selected in the tile grid.
     */
    public int curCol() {
        return curCol;
    }
}
