package res;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements a simple client for our custom presence protocol.
 * When the client connects to a server, a thread is started to listen for
 * notifications sent by the server.
 *
 * @author Olivier Liechti
 */
public class Client {

    final static Logger LOG = Logger.getLogger(Client.class.getName());

    Socket clientSocket;
    BufferedReader in;
    PrintWriter out;
    boolean connected = false;
    String userName;

    /**
     * This inner class implements the Runnable interface, so that the run()
     * method can execute on its own thread. This method reads data sent from the
     * server, line by line, until the connection is closed or lost.
     */
    class NotificationListener implements Runnable {

        @Override
        public void run() {
            String notification;

            try {
                while ((connected && (notification = in.readLine()) != null)) {
                    LOG.log(Level.INFO, "{0}", notification);
                }
            } catch (IOException e) {
                LOG.log(Level.SEVERE, "Connection problem : {0}", e.getMessage());
                connected = false;
            } finally {
                cleanup();
            }
        }
    }

    /**
     * This method is used to connect to the server and to inform the server that
     * the user "behind" the client has a name (in other words, the HELLO command
     * is issued after successful connection).
     *
     * @param serverAddress the IP address used by the Presence Server
     * @param serverPort the port used by the Presence Server
     */
    public void connect(String serverAddress, int serverPort) {
        try {
            clientSocket = new Socket(serverAddress, serverPort);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
            connected = true;
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Unable to connect to server: {0}", e.getMessage());
            cleanup();
            return;
        }

        Scanner scanner = new Scanner(System.in);

        String notification;

        // Let us start a thread, so that we can listen for server notifications
        new Thread(new NotificationListener()).start();

        while(true) {
            String calcul = scanner.nextLine();

            if(calcul == "bye") {
                disconnect();
                break;
            }

            out.println(calcul);
            out.flush();
        }
    }

    public void disconnect() {
        LOG.log(Level.INFO, "{0} has requested to be disconnected.", userName);
        connected = false;
        out.println("bye");
        cleanup();
    }

    private void cleanup() {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }

        if (out != null) {
            out.close();
        }

        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}