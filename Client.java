import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * Client class should be ran (in isolation) to give the other user functions to
 * make their
 * lives hell.
 */
public class Client extends JFrame implements ActionListener {
    private static final String SERVER_IP = "localhost";

    // https://www.youtube.com/watch?v=h2zi2lVNhtk
    private JButton sendButton; // sends data to the Server
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
        sendButton.setBounds(500, 375, 300, 50);
        sendButton.addActionListener(this);
        add(sendButton);

        setVisible(true);

        // basically setup the buttons and the text fields over here.
        // then setup the Socket over here to connect the server

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            try {
                socket = new Socket(ipTF.getText(), 5000);
                pw = new PrintWriter(socket.getOutputStream(), true);
            }

            catch (Exception ex) {
                System.out.println("didn't work");
                System.out.println(ex);
                return;
            }

            pw.println(functionTF.getText()); // yeet memers
            pw.flush(); // flush the data, don't close the socket though
            System.out.println("flusing it out");
        }

    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }
}
