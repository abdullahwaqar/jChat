package server;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Server implements Runnable {

    private DatagramSocket socket;
    private int port;
    private boolean running = false;
    private Thread run, manage, send, receive;

    public Server(int port) {
        this.port = port;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        run = new Thread(this, "[*] Server Thread Active");
    }

    public void run() {
        running = true;
        manageClients();
        recieve();
    }

    private void recieve() {
    }

    private void manageClients() {
        /*
        * manageClients will run in its own Thread
        * Manage clients by pinging them with a set time limit
        * to see if the client connection is still alive if not
        * then send the disconnect packet.
        */
        manage = new Thread("[*] Manage Thread Active") {
            public void run() {
                while (running) {
                    //*? managing logic
                }
            }
        };
        manage.start();
    }
}