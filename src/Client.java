import java.io.IOException;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket , String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            System.out.println("Client " + username + " has connected!");// Print a message to the console that a new client has connected
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    
    public void sendMessage() {// Send a message to the server
        try {
            bufferedWriter.write(username);// Write the client's username to the output stream
            bufferedWriter.newLine();// Add a new line after the username
            bufferedWriter.flush();// Flush the output stream to ensure the username is sent

            Scanner scanner = new Scanner(System.in);// Read messages from the console and send them to the server
            while (socket.isConnected()) {// While the socket is connected, read messages from the console and send them to the server
                String messageToSend = scanner.nextLine();// Read a message from the console
                bufferedWriter.write(username + ": " + messageToSend);// Write the message to the output stream, prefixed with the client's username
                bufferedWriter.newLine();// Add a new line after the message
                bufferedWriter.flush();// Flush the output stream to ensure the message is sent
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {// Listen for messages from the server and print them to the console
        new Thread(new Runnable() {// Create a new thread to listen for messages from the server
            @Override// Override the run method of the Runnable interface to listen for messages from the server
            public void run() {
                String msgFromGroupChat;// Variable to store messages received from the server

                while (socket.isConnected()) {// While the socket is connected, listen for messages from the server
                    try {
                        msgFromGroupChat = bufferedReader.readLine();// Read a message from the server
                        System.out.println(msgFromGroupChat);// Print the message to the console
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 1234);// Connect to the server at localhost on port 1234
        Client client = new Client(socket, username);// Create a new client instance with the socket and username
        client.listenForMessage();// Start listening for messages from the server
        client.sendMessage();// Start sending messages to the server
    }
    
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}