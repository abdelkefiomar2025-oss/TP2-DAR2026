package activite22.serverPackage;

import activite22.commonPackage.Operation;

import java.io.*;
import java.net.*;

public class Serveur {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 1. Créer la socket d'écoute sur le port 1234
        ServerSocket socketServeur = new ServerSocket(1234);
        System.out.println("Le serveur attend un client...");

        // 2. Attendre puis accepter la connexion d'un client
        Socket socket = socketServeur.accept();
        System.out.println("Un client est connecté");

        // 3. Flux d'objets : d'abord la sortie, ensuite l'entrée
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        // 4. Lire l'objet reçu, récupérer ses données
        Operation op = (Operation) in.readObject();
        while (op.getOperateur() != 'q') {
            double a = op.getOperande1();
            double b = op.getOperande2();
            char operateur = op.getOperateur();
            System.out.println("Reçu : " + a + " " + operateur + " " + b);

            // 5. Traiter, puis envoyer la réponse
            String resultat;
            if (operateur == '+') {
                resultat = String.valueOf(a + b);
            } else if (operateur == '-') {
                resultat = String.valueOf(a - b);
            } else if (operateur == '*') {
                resultat = String.valueOf(a * b);
            } else if (operateur == '/' && b != 0) {
                resultat = String.valueOf(a / b);
            } else if (operateur == '/') {
                resultat = "Erreur : division par zéro";
            } else {
                resultat = "Opérateur invalide";
            }
            out.writeObject(resultat);
            out.flush();

            op = (Operation) in.readObject();
        }

        // 6. Fermer les sockets
        socket.close();
        socketServeur.close();
        System.out.println("Connexion fermée");
    }
}