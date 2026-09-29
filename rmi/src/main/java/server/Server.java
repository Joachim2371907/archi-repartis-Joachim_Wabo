package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws Exception {

        Registry registry = LocateRegistry.createRegistry(1099);

        // Création et enregistrement du service d'historique
        HistoryService historyImpl = new HistoryServiceImpl();
        registry.rebind("HistoryService", historyImpl);

        // Récupération de la référence distante via lookup
        HistoryService history =
                (HistoryService) registry.lookup("HistoryService");

        // Création et enregistrement du service calculatrice
        CalculatorService calc =
                new CalculatorServiceImpl(history);
        registry.rebind("CalculatorService", calc);

        System.out.println("Service enregistré : HistoryService");
        System.out.println("Service enregistré : CalculatorService");
        System.out.println("Serveur RMI en écoute sur le port 1099");
    }
}