import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Użycie: java ChatClient <adres_IP_serwera> <numer_portu>");
            System.exit(1);
        }

        String serverAddress = args[0];
        int port = Integer.parseInt(args[1]);

        try {
            Socket socket = new Socket(serverAddress, port);
            System.out.println("Połączono z serwerem.");

            Scanner serverIn = new Scanner(socket.getInputStream());
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);

            Scanner userInput = new Scanner(System.in);

            System.out.print("Podaj swoje imię: ");
            String clientName = userInput.nextLine();
            serverOut.println(clientName);

            Thread readThread = new Thread(() -> {
                while (true) {
                    if (serverIn.hasNext()) {
                        String message = serverIn.nextLine();
                        System.out.println(message);
                    }
                }
            });
            readThread.start();

            while (true) {
                String userMessage = userInput.nextLine();
                serverOut.println(userMessage);
                if ("exit".equalsIgnoreCase(userMessage)) {
                    break;
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
