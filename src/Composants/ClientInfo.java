package Composants;

import java.net.Socket;

public class ClientInfo {

    /*Tache sender du client*/
    public ClientSender mClientSender = null;

    /*Tache sender du serveur*/
    public ClientListener mClientListener = null;

    /*Socket du client*/
    public Socket mSocket = null;

}
