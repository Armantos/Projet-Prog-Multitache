package Composants;

import java.io.*;

import Serveur.*;

import java.net.*;
import java.util.*;

public class ClientSender extends Thread {
    /*Tableau (dynamique) des messages*/
    private Vector mMessageQueue = new Vector();

    private ServerDispatcher mServerDispatcher;
    private ClientInfo mClientInfo;
    private PrintWriter mOut;

    public ClientSender(ClientInfo client, ServerDispatcher sd)
            throws IOException {
        mClientInfo = client;
        mServerDispatcher = sd;
        Socket socket = client.mSocket;
        mOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public synchronized void sendMessage(String message) {
        /* Ajout d'un nouveau client dans la liste de message */
        mMessageQueue.add(message);
        notify();
    }

    private synchronized String getNextMessageFromQueue() throws InterruptedException {
        while (mMessageQueue.size() == 0)
            wait();

        /*on recupere le dernier message de la liste*/
        String str = (String) mMessageQueue.get(0);

        /*on libère la liste*/
        mMessageQueue.removeElementAt(0);

        /*retourne le message*/
        return str;
    }

    /*Envoi du message en paramètre au socket du client*/
    private void sendMessageToClient(String message) {
        mOut.println(message);
        mOut.flush();
    }

    /*Lecture en boucle des messages de la liste et envoi vers le socket du client concerné*/
    public void run() {
        try {
            while (!isInterrupted()) {
                String message = getNextMessageFromQueue();
                sendMessageToClient(message);
            }
        } catch (Exception e) {
            System.out.println("Erreur");
        }

        //Si y'a un probleme de communication de socket, on stoppe les tâches
        mClientInfo.mClientListener.interrupt();
        mServerDispatcher.deleteClient(mClientInfo);
    }
}
