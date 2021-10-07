package Composants;

import java.io.*;
import java.net.*;

import Serveur.*;

public class ClientListener extends Thread {
    private ServerDispatcher mServerDispatcher;
    private ClientInfo mClientInfo;
    private BufferedReader mIn;

    public ClientListener(ClientInfo client, ServerDispatcher sd)
            throws IOException {
        mClientInfo = client;
        mServerDispatcher = sd;

        /*Recuperation du socket du client */
        Socket socket = client.mSocket;
        mIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /* Lecture en boucle des messages reçus, puis transmission de ces derniers au Dispatcher.
    Le dispatcher va les ajouter à sa liste */
    public void run() {
        try {
            while (!isInterrupted()) {
                String str = mIn.readLine();
                if (str == null)
                    break;
                mServerDispatcher.dispatchMessage(mClientInfo, str);
            }
        } catch (IOException ioex) {
            System.out.println("Erreur");
        }

        //Erreur de communication du socket : Arrêt des tâches
        mClientInfo.mClientSender.interrupt();
        mServerDispatcher.deleteClient(mClientInfo);
    }
}
