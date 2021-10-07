package Client;

import java.io.*;
import java.net.*;

public class ChatClient {
    public static final int port = 9999;
    public static final String address = "localhost";

    public static void main(String[] args) throws UnknownHostException {
        /*RECUPERATION DES ADRESSES*/
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        System.out.println("Adresse IP : " + hostAddress);
        System.out.println("Adresse du serveur : " + address);

        try {
            /*CONNEXION AU SERVEUR*/

            /*Creation du socket de communication*/
            Socket socket = new Socket(address, port);
            BufferedReader buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter print = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Bien connecté au serveur");
            System.out.println(" ");

            /*FLUX D'ENVOIS*/
            Sender sender = new Sender(print);
            sender.setDaemon(true);
            sender.start();

            /*LECTURE DES MESSAGES*/
            String message = "";
            int numClient;

            while ((message) != null) {
                message = buffr.readLine();
                System.out.println(message);
            }

        } catch (IOException ioe) {
            System.err.println("Erreur de connexion");
            System.exit(-1);
        }
    }
}