import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
    private final Socket clientSocket;
    private String clientName;

    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            this.setClientName(in.readLine());
            System.err.println("[SERVER] Accepted connection from " + clientName);

            String s;

            while ((s = in.readLine()) != null) {
                String[] parts = s.split(" ", 2);

                if(parts[0].toLowerCase().equals("quit")){
                    out.println("Bye...");
                    break;
                }else if(parts[0].toLowerCase().equals("echo")){
                    out.println(parts.length > 1 ? "["+ clientName + "] " + parts[1] : "");
                }else{
                    out.println("[ERROR] Unknown command \"" + parts[0] + "\"");
                }
            }

            System.err.println("Closing connection with client");
            out.close();
            in.close();
            clientSocket.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName.replaceAll("[^a-zA-Z0-9_]", "");
    }
}
