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
    public static ArrayList<ClientHandler> clienHandlers = new ArrayList<>();
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
        String messageFromClient;
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readline(); // Read a message from the client's input stream
                broadcastMessage(messageFromClient);// Broadcast the message to all other clients 
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;// If an exception occurs (e.g., the client disconnects), close the client's resources and break the loop

            }
        }
    }
    
    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clienHandlers) {
            try {// Broadcast the message to all clients except the sender
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(messageToSend);// Write the message to the client's output stream
                    clientHandler.bufferedWriter.newLine();// Add a new line after the message
                    clientHandler.bufferedWriter.flush();// Flush the output stream to ensure the message is sent
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);// If an exception occurs while broadcasting, close the client's resources
            }
        }
    }

    public void removeClientHandler() {
        clienHandlers.remove(this);// Remove the client from the list of connected clients
        broadcastMessage("Server: " + clientUsername + " has left the chat!");// Broadcast a message to all clients that the client has left
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();// Remove the client from the list of connected clients
        try {
            if (bufferedReader != null) {
                bufferedReader.close();// Close the client's input stream
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();// Close the client's output stream
            }
            if (socket != null) {
                socket.close();// Close the client's socket
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}