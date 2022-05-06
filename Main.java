import javax.swing.*;
import java.awt.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.awt.geom.*;

public class Main extends JFrame implements KeyListener {
    private Tile[][] grid;

    public Main() {
        super("tiles");
        setSize(500, 500);
        this.addKeyListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void paint(Graphics g) {
        super.paint(g);
        grid = new Tile[3][5];
        int width = 30;
        int height = 30;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = new Tile("lbl", 50 + j * width, 50 + i * height, width, height, g);
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());
        if (e.getKeyChar() == 'c') {
            grid[0][1].toggleSelected(getGraphics());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(e.getKeyChar());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e.getKeyChar());
    }
}
