
/**
 * Class to create a tile
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Tile
        extends JComponent {
    private int topLeftX, topLeftY;
    private int width, height;
    private Color currentColor = null;
    private Line2D[] lines;
    private JLabel label;

    private final Color selectedColor = Color.BLUE;
    private final Color defaultColor = Color.BLACK;

    public Tile(String lbl, int tX, int tY, int width, int height) {
        topLeftX = tX;
        topLeftY = tY;
        this.label = new JLabel(lbl);
        this.width = width;
        this.height = height;
        lines = new Line2D[4];

        currentColor = defaultColor;
    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("tile paint component is called baby");

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g.setColor(currentColor);
        g2.setColor(currentColor);

        g2.drawLine(topLeftX, topLeftY, topLeftX + width, topLeftY);
        g2.drawLine(topLeftX, topLeftY + height, topLeftX + width, topLeftY + height);
        g2.drawLine(topLeftX, topLeftY, topLeftX, topLeftY + height);
        g2.drawLine(topLeftX + width, topLeftY, topLeftX + width, topLeftY + height);
    }

    public void drawLabel(Color color) {
        // set the bounds for the JLabel and add it in the middle of this tile
    }

    public void setLabel(String newLabel) {
        // set the new label here
    }

    public void toggleSelected() {
        if (currentColor.equals(selectedColor)) {
            currentColor = defaultColor;
        }

        else {
            System.out.println("ahhahah");
            currentColor = selectedColor;
        }
    }

}