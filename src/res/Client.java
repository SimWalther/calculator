package res;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    static final Logger LOG = Logger.getLogger(Client.class.getName());

    final static int BUFFER_SIZE = 1024;
    final static String ip = "10.192.106.182";
    final static int port = 2323;

    /**
     * This method does the whole processing
     */
    public void sendWrongHttpRequest() {
        Socket clientSocket = null;
        OutputStream os = null;
        InputStream is = null;

        try {
            clientSocket = new Socket(ip, port);
            os = clientSocket.getOutputStream();
            is = clientSocket.getInputStream();

            String initialMessage = "Hello\r\n\r\n";

            os.write(initialMessage.getBytes());

            ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int newBytes;

            while ((newBytes = is.read(buffer)) != -1) {
                responseBuffer.write(buffer, 0, newBytes);
            }

            LOG.log(Level.INFO, "Response sent by the server: ");
            LOG.log(Level.INFO, responseBuffer.toString());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}