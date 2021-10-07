package Serveur;

import java.net.*;
import java.util.*;

import Composants.*;

/*Ecoute les messages des clients et les renvoie à tous les clients*/
public class ServerDispatcher extends Thread {
    /*Nombre de client connectés*/
    int nbClient = 0;

    /*Liste des clients*/
    private Vector VectClients = new Vector();

    /*Tableau des messages*/
    private Vector VectMessageQueue = new Vector();
    private ClientSender mServerDispatcher;

    /*Permet d'ajouter un client dans la liste de clients du serveur*/
    public synchronized void addClient(ClientInfo client) {
        /* Ajout d'un nouveau client dans la listeClients*/
        VectClients.add(client);
        nbClient++;
        System.out.println(nbClient + " client(s) connecté(s)");
    }

    /*Supprime le client donné en paramètre de la listeClients*/
    public synchronized void deleteClient(ClientInfo client) {
        /*Vérifies si l'index du client n'est pas dans la listeClient*/
        if ((VectClients.indexOf(client)) == -1) {
            System.out.println("Client non trouvé");
        }

        /* On vérifie si l'index du client est dans la liste puis on supprime dans ce cas*/
        else if ((VectClients.indexOf(client)) != -1) {
            VectClients.removeElementAt((VectClients.indexOf(client)));
            nbClient--;
        }
    }

    private synchronized String getNextMessageFromQueue() throws InterruptedException {
        while (VectMessageQueue.size() == 0)
            /*reste en mode zombi tant qu'on ne recoit pas de message*/
            wait();

        /*on recupere le dernier message de la liste*/
        String message = (String) VectMessageQueue.get(0);

        /*on vide la liste*/
        VectMessageQueue.removeElementAt(0);

        /*On retourne le message*/
        return message;
    }

    public synchronized void dispatchMessage(ClientInfo client, String str) {
        /*on recupere le socket du client */
        Socket socket = client.mSocket; //

        /*on recupere l'ID et le message*/
        str = "Client n° " + VectClients.indexOf(client) + ":" + str;

        /*on l'ajoute dans la liste*/
        VectMessageQueue.add(str);

        notify();
    }

    /*ENVOI DU MESSAGE A TOUS LES AUTRES CLIENTS DE LA LISTE*/
    private synchronized void sendMessageToAllClients(String message) {
        int nbClient = VectClients.size();
        int i = 0;

        while (i < nbClient) {
            ClientInfo client = (ClientInfo) VectClients.get(i);
            client.mClientSender.sendMessage(message);
            i++;
        }
    }

    /*Ecoute et lit les messages + envoi */
    public void run() {
        while (true) {
            String message;
            try {
                /*On recupere le dernier message*/
                message = getNextMessageFromQueue();

                /*on l'envoie à tous*/
                sendMessageToAllClients(message);

                /*sinon Erreur*/
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
