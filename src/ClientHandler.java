import java.util.ArrayList;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ClientHandler implements Runnable {
    // You can add fields for the client's socket, input/output streams, etc. 
    // Basically anything you need to share with other users in the same server 
    // For example, you can have a list of all connected clients to broadcast messages to them.
    Private Socket socket;
    Private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private string clientUsername;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientUsername = bufferedReader.readLine(); // Assuming the client sends their username as the first message
            System.out.println("Client " + clientUsername + " has connected.");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        // Handle the client's requests here. This is where you would read from the client's input stream,
        // process the request, and write back to the client's output stream.
    }
}