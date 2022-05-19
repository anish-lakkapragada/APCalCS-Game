import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Creates the grid tiles upon which the game is played.
 * 
 * @version 5/18/22 - 11:50 pm
 */
public class Tile
        extends JComponent {
    private int topLeftX, topLeftY;
    private int width, height;
    private Color currentColor = null;
    private Line2D[] lines;
    private JLabel label;
    private JFrame frame;
    private Graphics g;

    private final Color selectedColor = Color.BLUE;
    private final Color defaultColor = Color.BLACK;

    /**
     * Constructor method.
     * @param lbl the JLabel that goes on this specific tile
     * @param tX x-coordinate of tile's top left corner.
     * @param tY y-coordinate of tile's top left corner.
     * @param width width of the tile.
     * @param height height of the tile.
     * @param frame JFrame of the tile.
     */
    public Tile(String lbl, int tX, int tY, int width, int height, JFrame frame) {
        topLeftX = tX;
        topLeftY = tY;
        label = new JLabel(lbl);

        // adding the label right here
        frame.add(label);

        this.width = width;
        this.height = height;
        this.frame = frame;
        lines = new Line2D[4];

        currentColor = defaultColor;
    }

    @Override
    public void paintComponent(Graphics g) {
        this.g = g;

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g.setColor(currentColor);
        g2.setColor(currentColor);

        g2.drawLine(topLeftX, topLeftY, topLeftX + width, topLeftY);
        g2.drawLine(topLeftX, topLeftY + height, topLeftX + width, topLeftY + height);
        g2.drawLine(topLeftX, topLeftY, topLeftX, topLeftY + height);
        g2.drawLine(topLeftX + width, topLeftY, topLeftX + width, topLeftY + height);

        addLabel(currentColor);

    }

    /**
     * Adds a label of a specified color.
     * @param color the new label will be of this color
     */
    public void addLabel(Color color) {
        label.setForeground(color);
        label.setBounds((int) (topLeftX + 0.1 * width), (int) (topLeftY + 0.1 * height), (int) (width * 0.8), height);

    }

    /**
     * Sets the text inside this tile's JLabel.
     * @param newLabel text to set
     */
    public void setLabel(String newLabel) {
        label.setText(Differentiate.formatSubscript(newLabel, true));
    }

    /**
     * Returns the label's text.
     * @return label's text
     */
    public String getText() {
        return label.getText();
    }

    /**
     * Toggles the color of the selected tile.
     */
    public void toggleSelected() {
        System.out.println(currentColor);
        if (currentColor.equals(selectedColor)) {
            currentColor = defaultColor;
        }

        else {
            System.out.println("ahhahah");
            currentColor = selectedColor;
        }

        repaint();
        label.setForeground(currentColor);
    }

}
