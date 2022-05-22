import java.net.*;
import java.io.*;
import java.util.*;

/**
 * I don't think we actually ever use this class.
 */
public class Server {
    private static final int PORT = 5000;
    private Socket socket;

    /**
     * we can put all the socket and reading stuff in Game.java
     * when user on welcome screen selects the multiplayer option, they can start
     * the game in Game.java where it is multiplayer
     * 
     * From that point, we're basically executing a method to constantly check
     * whether the bufferedreader is getting some new functions,
     * and then updating the questions based on that
     * 
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(PORT);
        socket = listener.accept(); // listens for the connection

        // PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        // System.out.println((new Date()).toString());
        // out.println((new Date()).toString());

        BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String str = bf.readLine();
        System.out.println(str);

        socket.close(); // close all connections
        listener.close(); // close all connections

    }
}
