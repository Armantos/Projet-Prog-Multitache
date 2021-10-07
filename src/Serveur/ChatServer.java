package Serveur;
import java.net.*;
import java.io.*;
import Composants.*;

public class ChatServer
{
    public static final int port = 9999;



    public static void main(String[] args)
    {
      
        try {
        	
           /*Creation de la socket de communication du serveur */
           ServerSocket serverSocket = new ServerSocket(port); 
           System.out.println("Serveur lancé sur le port : " + port);         
           
           /*CREATION ET LANCEMENT DE LA TACHE DISPACHER */
           ServerDispatcher sDispatcher = new ServerDispatcher();
           sDispatcher.start();
       
           
           /* ACCEPTATION DE TOUTES LES CONNECTIONS CLIENTS*/
           while (true) {
              try {

              /*acceptation de la demandec*/
              Socket socket = serverSocket.accept();

              /*Création d'un nouveau client + tâches Sender et Listener */
              ClientInfo client = new ClientInfo();

              /*INFOS ESSENTIELLES AU CLIENT  : SOCKET + CLIENTLISTENER + CLIENTSENDER*/
              client.mSocket = socket;
              ClientListener clientEcoute = new ClientListener(client, sDispatcher);
              ClientSender clientEnvois = new ClientSender(client, sDispatcher);
              client.mClientListener = clientEcoute;
              client.mClientSender = clientEnvois;
              clientEcoute.start();
              clientEnvois.start();

              /* AJouter le client dans le SERVERDISPATCHER */
              sDispatcher.addClient(client);
                  
              	} catch (IOException ioe) {
                  ioe.printStackTrace();
              	}
       
        }
           } catch (IOException se) {
           System.err.println("Erreur");
           System.exit(-1);
        }
       }
    
    
}

