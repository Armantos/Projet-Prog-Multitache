package Client;

import java.io.*;
import java.net.*;

class Sender extends Thread {
    private PrintWriter mOut;

    public Sender(PrintWriter rec) {
        mOut = rec;
    }


    public void run() {
        BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
        while (!isInterrupted()) {
            String str;
            try {
                str = clavier.readLine();
                mOut.println(str);
                mOut.flush();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}