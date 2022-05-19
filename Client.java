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
    private JTextField functionTF; // enters in the stuff over here
    private Socket socket; // client socket
    private PrintWriter pw;

    public Client() throws IOException {

        setSize(1200, 600);
        setTitle("Derivatiles");
        setResizable(false);
        setFocusable(true);
        getContentPane().setLayout(null);

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

        socket = new Socket(SERVER_IP, 5000);
        pw = new PrintWriter(socket.getOutputStream(), true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            pw.println(functionTF.getText()); // yeet memers
            pw.flush(); // flush the data, don't close the socket though
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }
}
