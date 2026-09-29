import java.io.*;
import java.net.*;

public class CalcServer {

    public static void main(String[] args) throws IOException {

        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Serveur calculatrice en ecoute sur le port " + port);

            while (true) {

                Socket client = serverSocket.accept();

                System.out.println(
                    "Nouveau client connecte : " + client.getInetAddress()
                );

                Thread clientThread = new Thread(() -> handleClient(client));

                clientThread.start();
            }
        }
    }

    public static void handleClient(Socket client) {

        try (
            Socket socket = client;

            BufferedReader reader =
                new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
                );

            PrintWriter writer =
                new PrintWriter(
                    socket.getOutputStream(), true
                )
        ) {

            String request = reader.readLine();

            if (request != null) {

                String response = calculate(request);

                writer.println(response);

                System.out.println(
                    "Requete : " + request + " ->" + response
                );
            }

        } catch (IOException e) {

            System.out.println(
                "Erreur avec le client : " + e.getMessage()
            );
        }
    }

    public static String calculate(String request) {

        String[] parts = request.split(";");

        if (parts.length != 3) {
            return "ERREUR: format invalide. Utilisez OPERATION;A;B";
        }

        String operation = parts[0];

        try {

            double a = Double.parseDouble(parts[1]);
            double b = Double.parseDouble(parts[2]);

            switch (operation) {

                case "ADD":
                    return String.valueOf(a + b);

                case "SUB":
                    return String.valueOf(a - b);

                case "MUL":
                    return String.valueOf(a * b);

                case "DIV":

                    if (b == 0) {
                        return "ERREUR: division par zero";
                    }

                    return String.valueOf(a / b);

                default:
                    return "ERREUR: operation inconnue";
            }

        } catch (NumberFormatException e) {

            return "ERREUR: A et B doivent etre des nombres";
        }
    }
}