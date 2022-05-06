
/**
 * Class to create a tile
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Tile {
    private int topLeftX, topLeftY;
    private String label;
    private int width, height;
    private final Color selectedColor = Color.BLUE;
    private final Color defaultColor = Color.BLACK;
    private Color currentColor = null;

    public Tile(String lbl, int tX, int tY, int width, int height, Graphics g) {
        topLeftX = tX;
        topLeftY = tY;
        label = lbl;
        this.width = width;
        this.height = height;

        drawTile(g, defaultColor);
        drawLabel(g, defaultColor);
    }

    /**
     * drawing a single time
     * 
     * @param g graphics object
     */
    private void drawTile(Graphics g, Color color) {
        Graphics2D g2 = (Graphics2D) g;
        Line2D lineTop = new Line2D.Float(topLeftX, topLeftY, topLeftX + width, topLeftY);
        Line2D lineBottom = new Line2D.Float(topLeftX, topLeftY + height, topLeftX + width, topLeftY + height);
        Line2D lineLeft = new Line2D.Float(topLeftX, topLeftY, topLeftX, topLeftY + height);
        Line2D lineRight = new Line2D.Float(topLeftX + width, topLeftY, topLeftX + width, topLeftY + height);

        g.setColor(color);
        currentColor = color;

        g2.draw(lineTop);
        g2.draw(lineBottom);
        g2.draw(lineLeft);
        g2.draw(lineRight);
    }

    private void drawLabel(Graphics g, Color color) {
        g.setColor(color);
        currentColor = color;
        g.drawString(label, topLeftX + 5, topLeftY + 20);
    }

    public String getLabel() {
        return label; // return the label
    }

    /**
     * toggle the selected feature on the exterior of the
     */
    public void toggleSelected(Graphics g) {
        if (currentColor == selectedColor) {
            drawTile(g, defaultColor);
            drawLabel(g, defaultColor);
        }

        else {
            drawTile(g, selectedColor);
            drawLabel(g, selectedColor);
        }
    }
}