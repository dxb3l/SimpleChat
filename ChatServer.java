import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {

    private static final int PORT = 8888;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Serwer czatu uruchomiony na porcie " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nowy klient dołączył: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                executorService.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final PrintWriter out;
    private final Scanner in;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new Scanner(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            welcomeMessage();

            String clientName = getClientName();
            broadcast(clientName + " dołączył do czatu.");

            while (true) {
                String message = in.nextLine();
                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }
                broadcast(clientName + ": " + message);
            }

            broadcast(clientName + " opuścił czat.");
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void welcomeMessage() {
        out.println("Witaj! Podaj swoje imię:");
    }

    private String getClientName() {
        return in.nextLine();
    }

    private void broadcast(String message) {
        synchronized (ChatServer.class) {
            for (ClientHandler client : ClientHandlers.getClients()) {
                client.out.println(message);
            }
        }
    }
}

class ClientHandlers {

    private static final java.util.List<ClientHandler> clients = new java.util.concurrent.CopyOnWriteArrayList<>();

    private ClientHandlers() {
    }

    public static void addClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public static void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public static java.util.List<ClientHandler> getClients() {
        return clients;
    }
}
