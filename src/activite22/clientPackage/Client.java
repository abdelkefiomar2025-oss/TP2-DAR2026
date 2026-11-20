package activite22.clientPackage;

import activite22.commonPackage.Operation;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 1. Se connecter au serveur : adresse + port
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Je suis connecté");

        // 2. Flux d'objets : d'abord la sortie, ensuite l'entrée
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        Scanner clavier = new Scanner(System.in);

        // 3. Saisir l'opération, envoyer l'objet, lire le résultat
        while (true) {
            System.out.print("Premier opérande (ou q pour quitter) : ");
            String saisie = clavier.next();
            if (saisie.equals("q")) {
                out.writeObject(new Operation(0, 'q', 0));
                out.flush();
                break;
            }
            double a = Double.parseDouble(saisie);

            System.out.print("Opérateur (+ - * /) : ");
            char operateur = clavier.next().charAt(0);

            System.out.print("Deuxième opérande : ");
            double b = clavier.nextDouble();

            out.writeObject(new Operation(a, operateur, b));
            out.flush();

            String resultat = (String) in.readObject();
            System.out.println("Résultat : " + resultat);
        }

        // 4. Fermer la socket
        socket.close();
    }
}