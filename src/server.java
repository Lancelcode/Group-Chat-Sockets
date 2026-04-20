import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    private ServerSocket serverSocket;

    public ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
    public void startServer() {
        try{
            while(!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }

    } catch (IOException e) {

    }

    public void closeServerSocket(){
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Public static void main(String[] args) throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            Server server = new server(serverSocket);
            System.out.println("Server is listening on port 1234...");
            server.startServer();
        } catch (IOException e) {
            e.printStackTrace();
}

