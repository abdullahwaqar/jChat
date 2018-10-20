package server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server implements Runnable {

    private ArrayList<ServerClient> clients = new ArrayList<ServerClient>();

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
        run.start();
    }

    public void run() {
        running = true;
        System.out.println("Server started on port: " + this.port);
        manageClients();
        recieve();
    }

    private void recieve() {
        receive = new Thread("[*] Receive Thread Active") {
            public void run() {
                while (running) {
                    byte[] data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    try {
                        socket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String dataStr;
                    try {
                        dataStr = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                        clients.add(new ServerClient("aw", packet.getAddress(), packet.getPort(), 40));
                        System.out.println(clients.get(0).adress.toString() + clients.get(0).port);
                        System.out.println(dataStr);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
					}
                }
            }
        };
        receive.start();
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