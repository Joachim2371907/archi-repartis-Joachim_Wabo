B.1 — Observation avec deux clients

Le serveur Echo est mono-thread. Il traite une connexion à la fois.
Lorsqu'un client est en cours de traitement, un deuxième client peut
établir une connexion TCP mais son traitement par l'application attend
que le serveur soit de nouveau disponible sur accept().

Observation réalisée le : 28/09/2026

## Test avec le serveur arrêté

Après avoir arrêté le serveur RMI avec `Ctrl+C`, le client a été
relancé avec :

    java -cp target\classes client.Client

Le client n'a pas réussi à contacter le registre RMI.

Exception principale :

    java.rmi.ConnectException: Connection refused to host: localhost

Cause :

    java.net.ConnectException: Connection refused: connect

La trace montre que l'exception se produit lors de l'appel :

    registry.lookup("CalculatorService")

Le client tente donc de contacter le registre RMI sur localhost:1099,
mais aucun registre n'est disponible puisque le serveur a été arrêté.