package activite21.serverPackage;

import java.io.*;
import java.net.*;

public class Serveur {
    public static void main(String[] args) throws IOException {
        // 1. Créer la socket d'écoute sur le port 1234
        ServerSocket socketServeur = new ServerSocket(1234);
        System.out.println("Le serveur attend un client...");

        // 2. Attendre puis accepter la connexion d'un client
        Socket socket = socketServeur.accept();
        System.out.println("Un client est connecté");

        // 3. Préparer les flux de lecture et d'écriture
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // 4. Lire les opérations envoyées par le client jusqu'à "quit"
        String ligne = in.readLine();
        while (ligne != null && !ligne.equals("quit")) {
            double a = Double.parseDouble(ligne);
            String operateur = in.readLine();
            double b = Double.parseDouble(in.readLine());
            System.out.println("Reçu : " + a + " " + operateur + " " + b);

            // 5. Traiter, puis envoyer la réponse
            String resultat;
            if (operateur.equals("+")) {
                resultat = String.valueOf(a + b);
            } else if (operateur.equals("-")) {
                resultat = String.valueOf(a - b);
            } else if (operateur.equals("*")) {
                resultat = String.valueOf(a * b);
            } else if (b == 0) {
                resultat = "Erreur : division par zéro";
            } else {
                resultat = String.valueOf(a / b);
            }
            out.println(resultat);

            ligne = in.readLine();
        }

        // 6. Fermer les sockets
        socket.close();
        socketServeur.close();
        System.out.println("Connexion fermée");
    }
}