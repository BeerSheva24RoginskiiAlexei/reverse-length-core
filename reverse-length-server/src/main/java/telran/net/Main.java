package telran.net;

import java.net.*;
import java.io.*;

public class Main {
    private static final int PORT = 4000;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server is listening on port " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            runSession(socket);
        }
    }

    private static void runSession(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintStream writer = new PrintStream(socket.getOutputStream())) {
             
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length < 2) {
                    writer.println("Error: Invalid request format");
                    continue;
                }
                String requestType = parts[0];
                String data = parts[1];

                String response;
                switch (requestType) {
                    case "reverse":
                        response = new StringBuilder(data).reverse().toString();
                        break;
                    case "length":
                        response = String.valueOf(data.length());
                        break;
                    default:
                        response = "Error: Unsupported request type";
                }
                writer.println(response);
            }
        } catch (Exception e) {
            System.out.println("Client closed connection abnormally");
        }
    }
}
