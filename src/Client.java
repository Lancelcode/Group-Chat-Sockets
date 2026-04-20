import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            System.out.println("Client " + clientUsername + " has connected!");// Print a message to the console that a new client has connected
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    
    public void sendMessage(String messageToSend) {// Send a message to the server
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