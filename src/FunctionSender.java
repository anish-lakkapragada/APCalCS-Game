package src;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * FunctionSender class should be ran (in isolation) to give the other user
 * functions to
 * make their
 * lives hell.
 */
public class FunctionSender extends JFrame implements ActionListener {
    // https://www.youtube.com/watch?v=h2zi2lVNhtk
    private JLabel gLabel;
    private JButton sendButton; // sends data to the Server
    private JButton restartButton;
    private JTextField ipTF, functionTF; // enters in the stuff over here
    private Socket socket; // client socket
    private PrintWriter pw;
    private JLabel ipLabel;
    private JLabel functionLabel;

    public FunctionSender() throws IOException {

        setSize(1200, 600);
        setTitle("Derivatiles");
        setResizable(false);
        setFocusable(true);
        getContentPane().setLayout(null);

        gLabel = new JLabel("Derivatiles", SwingConstants.CENTER);
        gLabel.setBounds(450, 5, 300, 100);
        gLabel.setFont(new Font("Calibri", Font.BOLD, 40));
        add(gLabel);

        JLabel labelTwo = new JLabel("(Online Mode)");
        labelTwo.setBounds(540, 45, 300, 100);
        labelTwo.setFont(new Font("Calibri", Font.BOLD, 15));
        add(labelTwo);

        ipLabel = new JLabel("Enter IP Address of Computer to Send to Below!", SwingConstants.CENTER);
        ipLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        ipLabel.setBounds(300, 165, 600, 50);
        add(ipLabel);

        ipTF = new JTextField("");
        ipTF.setBounds(450, 215, 300, 50);
        add(ipTF);

        functionLabel = new JLabel("Enter function below!", SwingConstants.CENTER);
        functionLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        functionLabel.setBounds(450, 310, 300, 50);
        add(functionLabel);

        functionTF = new JTextField("f(x) = ");
        functionTF.setBounds(450, 360, 300, 50);
        add(functionTF);

        sendButton = new JButton("Send to other!");
        sendButton.setFont(new Font("Calibri", Font.ITALIC, 15));
        sendButton.setBounds(450, 420, 300, 50);
        sendButton.addActionListener(this);
        add(sendButton);

        restartButton = new JButton("Restart connection!");
        restartButton.setFont(new Font("Calibri", Font.ITALIC, 15));
        restartButton.setBounds(450, 480, 300, 50);
        restartButton.addActionListener(this);
        add(restartButton);

        setVisible(true);

        (new Thread(() -> {
            BufferedReader bf = null;
            while (bf == null) {
                try {
                    bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    break;
                } catch (Exception e) {
                }

                try {
                    Thread.sleep(1500);
                } catch (Exception e) {
                }

                // System.out.println("still here");
            }

            // buffered reader is created.
            try {
                String message = bf.readLine();
                if (message.equals("done")) {
                    // the other socket is exited now, time to shut down the socket
                    socket.close();
                    socket = null;
                }
            } catch (Exception e) {
            }

        })).start();

        // Timer timer = new Timer();
        // timer.scheduleAtFixedRate(new TimerTask() {
        // @Override
        // public void run() {
        // try {
        // socket = new Socket(ipTF.getText(), 5000);
        // pw = new PrintWriter(socket.getOutputStream(), true);
        // } catch (Exception e) {}
        // }
        // }, 2000, 3000);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // String curIp = ipTF.getText();
        // String curFunction = functionTF.getText().substring(7);

        // if (curIp.isEmpty() && curFunction.isEmpty()) {
        // ipLabel.setForeground(Color.RED);
        // functionLabel.setForeground(Color.RED);
        // return;
        // }

        if (e.getSource() == sendButton) {
            try {
                if (socket == null) {
                    socket = new Socket(ipTF.getText(), 5000);
                    pw = new PrintWriter(socket.getOutputStream(), true);
                }
            }

            catch (Exception ex) {
                // System.out.println("didn't work");
                // System.out.println(ex);
                return;
            }

            pw.println(functionTF.getText()); // yeet memers
            pw.flush(); // flush the data, don't close the socket though
            // System.out.println("flusing it out");

            // 192.168.4.27
        }

        else if (e.getSource() == restartButton) {
            try {
                socket = new Socket(ipTF.getText(), 5000);
                pw = new PrintWriter(socket.getOutputStream(), true);
            } catch (Exception ex) {
            }
        }

    }

    public static void main(String[] args) throws IOException {
        FunctionSender client = new FunctionSender(); // 192.168.4.108
    }
}