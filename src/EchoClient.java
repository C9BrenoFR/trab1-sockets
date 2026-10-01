import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uso: java EchoClient <nome> <host>");
            return;
        }

        String screenName = args[0];
        String host = args[1];
        int port = 4444;

        Socket socket = new Socket(host, port);

        Scanner stdin = new Scanner(System.in);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        System.err.println("Connected to " + host + " on port " + port);

        out.println(screenName);

        System.out.print("> ");
        while (stdin.hasNextLine()) {
            String s = stdin.nextLine();
            out.println(s);

            String resposta = in.readLine();

            if (resposta == null || resposta.equals("Bye...")) {
                if (resposta != null) System.out.println(resposta);
                break;
            }

            System.out.println(resposta);
            System.out.print("> ");
        }

        System.err.println("Closing connection to " + host);
        out.close();
        in.close();
        stdin.close();
        socket.close();
    }
}