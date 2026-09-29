package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import server.CalculatorService;
import server.HistoryService;

public class Client {

    public static void main(String[] args) throws Exception {

        Registry registry =
                LocateRegistry.getRegistry("localhost", 1099);

        CalculatorService calc =
                (CalculatorService) registry.lookup("CalculatorService");

        System.out.println("4 + 7 = " + calc.add(4, 7));

        System.out.println("10 - 3 = " + calc.sub(10, 3));

        System.out.println("5 * 6 = " + calc.mul(5, 6));

        System.out.println("20 / 4 = " + calc.div(20, 4));

        try {
            System.out.println("10 / 0 = " + calc.div(10, 0));
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        HistoryService history =
                (HistoryService) registry.lookup("HistoryService");

        List<String> operations = history.getAll();

        System.out.println();
        System.out.println("===== HISTORIQUE =====");

        for (String operation : operations) {
            System.out.println(operation);
        }
    }
}