package res;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");

        Client client = new Client();
        client.sendWrongHttpRequest();

    }
}

