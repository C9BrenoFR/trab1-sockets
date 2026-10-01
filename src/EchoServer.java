import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        int port = 4444;
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("[SERVER] Started server on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientThread thread = new ClientThread(clientSocket);
            thread.start();
        }
    }
}