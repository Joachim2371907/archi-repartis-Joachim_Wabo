# Module Socket TCP

## Description

Ce module constitue la première implémentation du projet fil rouge
d'architecture logicielle et objets répartis.

L'objectif est de mettre en œuvre une communication client-serveur
directement avec les socke# NOTES — Séance 1

## B.1 — Service d'écho

### Fonctionnement

Le serveur utilise un `ServerSocket` sur le port 5000.

Lorsqu'un client se connecte, `accept()` retourne un `Socket
représentant la connexion avec ce client.

Le serveur lit une ligne avec `BufferedReader` puis renvoie cette
ligne avec `PrintWriter`.

Les ressources sont fermées automatiquement grâce au
try-with-resources.

### Observation avec deux clients

Le serveur initial est mono-thread.

Il traite donc une connexion à la fois. Lorsqu'un client est en cours
de traitement, le traitement d'une autre connexion doit attendre que
le serveur soit disponible pour effectuer un nouvel `accept()`.

---

## B.2 — Mini-calculatrice distante

### Protocole

Format :

    OPERATION;A;B

Opérations supportées :

- ADD
- SUB
- MUL
- DIV

### Tests réalisés

| Requête | Résultat attendu | Résultat observé |
|---|---|---|
| ADD;4;7 | 11.0 | 11.0 |
| SUB;10;3 | 7.0 | 7.0 |
| MUL;5;6 | 30.0 | 30.0 |
| DIV;20;4 | 5.0 | 5.0 |
| ADD;4.5;2.5 | 7.0 | 7.0 |
| SUB;-10;3 | -13.0 | -13.0 |
| DIV;10;0 | Erreur division par zéro | Erreur observée |
| XYZ;4;7 | Erreur opération inconnue | Erreur observée |
| ADD;bonjour;7 | Erreur nombre invalide | Erreur observée |
| ADD;4 | Erreur format invalide | Erreur observée |

---

## Question de réflexion 1

### Client connecté sans envoyer de données

Le serveur reste bloqué sur :

    reader.readLine();

`readLine()` est une opération bloquante : le serveur attend
l'arrivée d'une ligne de données.

### Observation exacte

À compléter avec le résultat exact obtenu pendant le test.

---

## Question de réflexion 2

### Déconnexion brutale du client

Scénario :

1. lancer le serveur ;
2. lancer un client ;
3. laisser le client attendre la saisie ;
4. interrompre brutalement le client avec Ctrl+C ;
5. observer le terminal du serveur.

### Observation exacte

À compléter avec le message exact affiché par le serveur.

---

## B.3 — Multi-clients

La version multi-client utilise un thread par connexion.

Le serveur principal accepte une connexion puis crée un nouveau
thread chargé de traiter le client :

    Thread clientThread =
        new Thread(() -> handleClient(client));

    clientThread.start();

Le thread principal peut alors revenir à `accept()` pendant que le
thread précédent traite son client.

### Test

Trois clients ont été utilisés simultanément afin de vérifier que
plusieurs requêtes peuvent être traitées en parallèle.

### Conclusion

Le modèle utilisé est :

    une connexion → un threadts TCP, sans utiliser de framework.

## Contenu

- `EchoServer.java` : serveur TCP d'écho
- `EchoClient.java` : client du service d'écho
- `CalcServer.java` : serveur de calcul distant
- `CalcClient.java` : client de la calculatrice distante
- `NOTES.md` : observations réalisées pendant les tests

## Prérequis

- JDK 17 ou supérieur

## Compilation

Depuis le dossier `socket` :

```bash
javac EchoServer.java EchoClient.java
javac CalcServer.java CalcClient.java