package telran.net;

import telran.view.*;

public class Main {
    private static ReverseClient reverseClient;

    public static void main(String[] args) {
        Item[] items = {
            Item.of("Start session", Main::startSession),
            Item.of("Exit", Main::exit, true)
        };
        Menu menu = new Menu("Reverse-Length Application", items);
        menu.perform(new StandardInputOutput());
    }

    static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname");
        int port = io.readNumberRange("Enter port", "Invalid port", 3000, 50000).intValue();

        if (reverseClient != null) {
            reverseClient.close();
        }
        reverseClient = new ReverseClient(host, port);

        Menu sessionMenu = new Menu("Session Menu",
            Item.of("Send reverse request", (io1) -> sendRequest(io1, "reverse")),
            Item.of("Send length request", (io1) -> sendRequest(io1, "length")),
            Item.ofExit()
        );
        sessionMenu.perform(io);
    }

    static void sendRequest(InputOutput io, String type) {
        String data = io.readString("Enter a string to process");
        String response = reverseClient.sendAndReceive(type, data); 
        io.writeLine("Response: " + response);
    }

    static void exit(InputOutput io) {
        if (reverseClient != null) {
            reverseClient.close();
        }
    }
}
