import java.util.ArrayList;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
            // Initialize the client's socket and input/output streams
            this.socket = socket;
            // Initialize the input and output streams for the client
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Read the client's username (or any initial message) and store it
            this.clientUsername = bufferedReader.readLine();
            // Add the client to a list of connected clients (if you have one)
            clienHandlers.add(this);
            // For example: clients.add(this)
            System.out.println("Client " + clientUsername + " has connected!");
            // Print a message to the console that a new client has connected
            broadcastMessage("Server: " + clientUsername + " has joined the chat!");
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