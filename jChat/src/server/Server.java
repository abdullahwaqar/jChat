package server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
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
                        process(packet);
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

    private void sendToAll(String message) {
        for (int i = 0; i < clients.size(); i++) {
            ServerClient client = clients.get(i);
            send(message.getBytes(), client.adress, client.port);
        }
    }

    private void send(final byte[] data, final InetAddress adress, int port) {
        send = new Thread("Send") {
            public void run() {
                DatagramPacket packet = new DatagramPacket(data, data.length, adress, port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        send.start();
    }

    private void process(DatagramPacket packet) {
        String string = new String(packet.getData());
        if (string.startsWith("/c/")) {
            int id = UniqueIdentifier.getIdentifier();
            clients.add(new ServerClient(string.substring(3, string.length()), packet.getAddress(), packet.getPort(), id));
            System.out.println(string.substring(3, string.length()));
        } else if(string.startsWith("/m/")){
            // String message = string.substring(3, string.length());
            sendToAll(string);
        } else {
            System.out.println(string);
        }
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