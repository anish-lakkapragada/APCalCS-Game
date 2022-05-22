import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Client class should be ran (in isolation) to give the other user functions to
 * make their
 * lives hell.
 */
public class Client extends JFrame implements ActionListener {
    private static final String SERVER_IP = "localhost";

    // https://www.youtube.com/watch?v=h2zi2lVNhtk
    private JButton sendButton; // sends data to the Server
    private JButton restartButton;
    private JTextField ipTF, functionTF; // enters in the stuff over here
    private Socket socket; // client socket
    private PrintWriter pw;

    public Client() throws IOException {

        setSize(1200, 600);
        setTitle("Derivatiles");
        setResizable(false);
        setFocusable(true);
        getContentPane().setLayout(null);

        JLabel ipLabel = new JLabel("Enter IP Address of Computer to Send to Below!", SwingConstants.CENTER);
        ipLabel.setFont(new Font("Calibri", Font.BOLD, 10));
        ipLabel.setBounds(500, 75, 300, 50);
        add(ipLabel);

        ipTF = new JTextField("");
        ipTF.setBounds(500, 130, 300, 50);
        add(ipTF);

        JLabel label = new JLabel("Enter function below!", SwingConstants.CENTER);
        label.setFont(new Font("Calibri", Font.BOLD, 15));
        label.setBounds(500, 200, 300, 50);
        add(label);

        functionTF = new JTextField("f(x) = ");
        functionTF.setBounds(500, 275, 300, 50);
        add(functionTF);

        sendButton = new JButton("Send to other!");
        sendButton.setBounds(500, 335, 300, 50);
        sendButton.addActionListener(this);
        add(sendButton);

        restartButton = new JButton("Restart connection!");
        restartButton.setBounds(500, 395, 300, 50);
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

                System.out.println("still here");
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
        if (e.getSource() == sendButton) {
            try {
                if (socket == null) {
                    socket = new Socket(ipTF.getText(), 5000);
                    pw = new PrintWriter(socket.getOutputStream(), true);
                }
            }

            catch (Exception ex) {
                System.out.println("didn't work");
                System.out.println(ex);
                return;
            }

            pw.println(functionTF.getText()); // yeet memers
            pw.flush(); // flush the data, don't close the socket though
            System.out.println("flusing it out");

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
        Client client = new Client(); // 192.168.4.108
    }
}
