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
                    process(packet);
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
            String ID = "/c/" + id;
            send(ID.getBytes(), packet.getAddress(), packet.getPort());
        } else if(string.startsWith("/m/")){
            sendToAll(string);
        } else if (string.startsWith("/d/")) {
            String id = string.split("/d/|/e/")[1].trim();
            disconnect(Integer.parseInt(id), true);
        } else {
            System.out.println(string);
        }
    }

    private void disconnect(int id, boolean status) {
        ServerClient c = null;
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getID() == id) {
                c = clients.get(i);
                clients.remove(i);
                break;
            }
        }
        String message = "";
        if (status) {
            message = "Client " + c.name + " (" + c.getID() + ") @ " + c.adress.toString() + ":" + c.port + " Disconnected.";
        } else {
            message = "Client " + c.name + " (" + c.getID() + ") @ " + c.adress.toString() + ":" + c.port + " Timed Out.";
        }
        System.out.println(message);
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