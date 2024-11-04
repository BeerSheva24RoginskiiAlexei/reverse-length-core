package telran.net;

import java.io.*;
import java.net.*;

public class ReverseClient {
    private Socket socket;
    private PrintStream writer;
    private BufferedReader reader;

    public ReverseClient(String host, int port) {
        try {
            socket = new Socket(host, port);
            writer = new PrintStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String sendAndReceive(String requestType, String data) {
        try {
            writer.println(requestType + ":" + data); // Отправляем тип запроса и строку
            return reader.readLine(); // Ожидаем ответ от сервера
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
