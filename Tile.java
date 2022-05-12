
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
    private JFrame frame;

    private final Color selectedColor = Color.BLUE;
    private final Color defaultColor = Color.BLACK;

    public Tile(String lbl, int tX, int tY, int width, int height, JFrame frame) {
        topLeftX = tX;
        topLeftY = tY;
        this.label = new JLabel(lbl);
        this.width = width;
        this.height = height;
        this.frame = frame;
        lines = new Line2D[4];

        currentColor = defaultColor;
    }

    @Override
    public void paintComponent(Graphics g) {
        // System.out.println("tile paint component is called baby");

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

    public void addLabel(Color color) {
        label.setBounds((int) (topLeftX + 0.1 * width), (int) (topLeftY + 0.1 * height), (int) (width * 0.9), height);
        frame.add(label);
        System.out.println(label.getText());
    }

    public void setLabel(String newLabel) {
        label.setText(newLabel);
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