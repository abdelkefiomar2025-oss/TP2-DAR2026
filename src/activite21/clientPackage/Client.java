package activite21.clientPackage;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    public static void main(String[] args) throws IOException {
        // 1. Se connecter au serveur : adresse + port
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Je suis connecté");

        // 2. Préparer les flux
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner clavier = new Scanner(System.in);

        // 3. Modèle de syntaxe : nombre, opérateur, nombre
        Pattern modele = Pattern.compile("^(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(?:\\.\\d+)?)$");

        // 4. Saisir, vérifier, envoyer, lire la réponse
        while (true) {
            System.out.print("Entrez une opération (ex: 55 * 25) ou quit : ");
            String saisie = clavier.nextLine().trim();

            if (saisie.equals("quit")) {
                out.println("quit");
                break;
            }

            Matcher m = modele.matcher(saisie);
            if (!m.matches()) {
                System.out.println("Syntaxe incorrecte, réessayez.");
                continue;
            }

            out.println(m.group(1));
            out.println(m.group(2));
            out.println(m.group(3));

            System.out.println("Résultat : " + in.readLine());
        }

        // 5. Fermer la socket
        socket.close();
    }
}