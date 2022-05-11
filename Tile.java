
/**
 * Class to create a tile
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Tile {
    private int topLeftX, topLeftY;
    private int width, height;
    private Color currentColor = null;
    private Graphics g;
    private Line2D[] lines;
    private JLabel label;

    private final Color selectedColor = Color.BLUE;
    private final Color defaultColor = Color.BLACK;

    public Tile(String lbl, int tX, int tY, int width, int height, Graphics g) {
        topLeftX = tX;
        topLeftY = tY;
        this.label = new JLabel(lbl);
        this.width = width;
        this.height = height;
        this.g = g;
        lines = new Line2D[4];

        // draw the tile and label
    }

    private void drawTile(Color color) {
        Graphics2D g2 = (Graphics2D) g;
        lines[0] = new Line2D.Float(topLeftX, topLeftY, topLeftX + width, topLeftY);
        lines[1] = new Line2D.Float(topLeftX, topLeftY + height, topLeftX + width, topLeftY + height);
        lines[2] = new Line2D.Float(topLeftX, topLeftY, topLeftX, topLeftY + height);
        lines[3] = new Line2D.Float(topLeftX + width, topLeftY, topLeftX + width, topLeftY + height);

        g.setColor(color);
        currentColor = color;

        for (int i = 0; i < lines.length; i++) {
            g2.draw(lines[i]);
        }
    }

    public void drawLabel(Color color) {
        // set the bounds for the JLabel and add it in the middle of this tile
    }

    public void setLabel(String newLabel) {
        // set the new label here
    }

    public void toggleSelected() {
        lines = new Line2D[4]; // reset everything
        if (currentColor.equals(selectedColor)) {
            drawTile(defaultColor);
            currentColor = defaultColor;
        }

        else {
            drawTile(selectedColor);
            currentColor = selectedColor;
        }
    }

}