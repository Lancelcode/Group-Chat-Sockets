import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    private serverSocket serverSocket;

    public ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
    publicvoid start() {
        try{
            while(!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }

    } cath(IOexceptions e) {

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
}

