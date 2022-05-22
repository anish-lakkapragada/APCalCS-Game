import java.awt.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

/**
 * Tile Manager class for managing the tiles.
 * 
 * @version 5/18/22 - 11:50 pm
 */
public class TileManager extends JComponent {
    private Tile[][] grid;
    private int numRows, numCols;
    private int curRow, curCol;
    private int tileWidth, tileHeight;
    private int topX, topY; // starting X and Y coordinates of the entire grid
    private String[][] labels;
    private JFrame frame;

    /**
     * Constructor for the TileManager class to manage the tiles.
     * 
     * @param numRows    the number of rows in this grid
     * @param numCols    the number of columns in this grid
     * @param tileWidth  the width of each column in this grid
     * @param tileHeight the height of each column in this grid
     * @param topX       the top-left x-coordinate for the first tile
     * @param topY       the top-left y-coordinate for the first tile
     */
    public TileManager(int numRows, int numCols, int tileWidth, int tileHeight, int topX, int topY,
            String[][] gridLabels, JFrame frame) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.topX = topX;
        this.topY = topY;
        labels = gridLabels;
        this.frame = frame;

        curRow = -1;
        curCol = -1;
        grid = new Tile[numRows][numCols]; // create the grid

    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("tile manager paint called");
        super.paintComponent(g);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (grid[i][j] == null) {
                    grid[i][j] = new Tile(labels[i][j], topX + j * tileWidth, topY + i * tileHeight, tileWidth - 1,
                            tileHeight - 1, frame); // draws the tile on the grid (each of which has the label)
                }

                // frame.add(grid[i][j]);
                grid[i][j].paintComponent(g);

            }
        }
    }

    /**
     * Returns the function at a specified
     * point on the grid.
     * @param r row of function to return
     * @param c column of function to return
     * @return The function at [r][c].
     */
    public String getFunction(int r, int c) {
        return grid[r][c].getText();
    }

    /**
     * Reset the labels with <code> gridLabels </code>
     * and redraw the grid in the same place.
     * 
     * @param gridLabels
     */
    public void setLabels(String[][] gridLabels) {
        labels = gridLabels;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (grid[i][j] != null) {
                    grid[i][j].setLabel(gridLabels[i][j]);
                }
            }
        }

    }

    /**
     * Determines if a location as specified by the
     * parameters r and c is valid or not.
     * 
     * @param r row to look at
     * @param c column to look at
     * @return True if [r][c] is a valid location on the grid,
     * false otherwise.
     */
    public boolean validLoc(int r, int c) {
        return r >= 0 && r < numRows && c >= 0 && c < numCols;
    }

    /**
     * Sets the current location on the grid (in the JFrame) to the specified
     * row and column.
     * 
     * @param newRow new row for current location
     * @param newCol new column for current location
     * @return 1 if move is successful, 0 otherwise (out of bounds value).
     */
    public int setLoc(int newRow, int newCol, Graphics g) {
        if (!validLoc(newRow, newCol)) {
            System.out.println("nah");
            return 0;
        }

        if (curRow >= 0 && curCol >= 0) {
            grid[curRow][curCol].toggleSelected();
            // grid[curRow][curCol].repaint();
        }

        grid[newRow][newCol].toggleSelected();
        // grid[newRow][newCol].repaint();

        repaint();

        curRow = newRow;
        curCol = newCol;

        return 1;
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

    /**
     * Returns the current state of the grid as a 2-D array.
     * @return grid (in its current state)
     */
    public Tile[][] getGrid() {
        return grid;
    }
}
