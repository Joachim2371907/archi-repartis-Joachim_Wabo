# Architecture Logicielle et Objets Répartis

Projet réalisé dans le cadre du cours **Architecture Logicielle et Objets Répartis**.

Ce projet regroupe les différents travaux pratiques réalisés sur les
architectures de communication et les objets distribués en Java.

## Structure du projet

```text
archi-repartis-Joachim_Wabo/
│
├── socket/
│   ├── EchoServer.java
│   ├── EchoClient.java
│   ├── CalcServer.java
│   ├── CalcClient.java
│   ├── README.md
│   └── NOTES.md
│
├── rmi/
│   ├── pom.xml
│   └── src/
│       └── main/
│           └── java/
│               ├── client/
│               │   └── Client.java
│               └── server/
│                   ├── CalculatorService.java
│                   ├── CalculatorServiceImpl.java
│                   ├── HistoryService.java
│                   ├── HistoryServiceImpl.java
│                   └── Server.java
│
├── README.md
└── .gitignore

Environnement
Java 17
Maven
Git
GitHub
Visual Studio Code
Session 1 — TCP Sockets
Objectif

Mettre en œuvre une communication client/serveur avec les sockets TCP en
Java.

Réalisations
Serveur et client Echo
Serveur et client de calcul
Addition
Soustraction
Multiplication
Division
Gestion de la division par zéro
Communication entre plusieurs clients
Fichiers principaux
socket/
├── EchoServer.java
├── EchoClient.java
├── CalcServer.java
├── CalcClient.java
├── README.md
└── NOTES.md

La documentation détaillée de cette session se trouve dans :

socket/README.md
socket/NOTES.md
Session 2 — Java RMI
Objectif

Mettre en œuvre la communication entre objets distribués avec
Java RMI (Remote Method Invocation).

L'implémentation utilise les fonctionnalités RMI fournies par le JDK,
sans bibliothèque externe.

Technologies
Java 17
Maven
Java RMI
Architecture
                    CLIENT
                      |
                      | lookup()
                      v
                REGISTRE RMI
                 localhost:1099
                      |
              +-------+-------+
              |               |
              v               v
     CalculatorService   HistoryService
              |               |
              +--- record() --+
CalculatorService

CalculatorService est une interface distante qui étend Remote.

Les opérations disponibles sont :

add(a, b)
sub(a, b)
mul(a, b)
div(a, b)

Chaque méthode distante peut lever une RemoteException.

CalculatorServiceImpl

CalculatorServiceImpl implémente CalculatorService et étend
UnicastRemoteObject.

La division par zéro est gérée avec une exception :

Division par zéro
HistoryService

HistoryService est un deuxième service distant permettant de conserver
l'historique des opérations.

Les méthodes principales sont :

record(String entry)
getAll()

Après chaque opération réussie, l'opération est enregistrée dans
l'historique.

Exemple :

ADD(4.0, 7.0) = 11.0
SUB(10.0, 3.0) = 7.0
MUL(5.0, 6.0) = 30.0
DIV(20.0, 4.0) = 5.0

Une opération échouée, comme une division par zéro, n'est pas enregistrée.

Registre RMI

Le serveur crée un registre RMI sur le port :

1099

Il enregistre les deux services :

CalculatorService
HistoryService

CalculatorService récupère la référence distante de HistoryService
avec lookup.

Compilation

Depuis le dossier rmi :

mvn clean package
Démarrage du serveur
java -cp target\classes server.Server

Le serveur utilise :

localhost:1099
Démarrage du client

Dans une deuxième fenêtre PowerShell :

java -cp target\classes client.Client

Exemple de résultat :

4 + 7 = 11.0
10 - 3 = 7.0
5 * 6 = 30.0
20 / 4 = 5.0
Erreur : Division par zéro

===== HISTORIQUE =====
ADD(4.0, 7.0) = 11.0
SUB(10.0, 3.0) = 7.0
MUL(5.0, 6.0) = 30.0
DIV(20.0, 4.0) = 5.0

L'historique est conservé côté serveur. Ainsi, lorsque le client est
exécuté plusieurs fois sans arrêter le serveur, les opérations précédentes
restent disponibles.

Test avec serveur arrêté

Après l'arrêt du serveur RMI, le lancement du client produit une erreur de
connexion.

Exception principale :

java.rmi.ConnectException: Connection refused to host: localhost

Cause :

java.net.ConnectException: Connection refused: connect

Cette erreur indique que le client ne peut plus contacter le registre RMI
sur le port 1099.
Gestion des fichiers compilés

Les fichiers compilés Java et les fichiers générés par Maven ne sont pas
versionnés dans Git.

Le fichier .gitignore contient notamment :

*.class
**/target/

Les fichiers .class restent présents sur la machine locale mais ne sont
pas ajoutés au dépôt Git.

Historique des sessions
Session 1

TCP Sockets

Communication client/serveur avec les sockets TCP en Java.

Session 2

Java RMI

Communication entre objets distants avec Java RMI, registre RMI,
services de calcul et service d'historique.

Auteur

Joachim Wabo

Master 2 Génie Informatique.