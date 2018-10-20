package server;

import java.net.InetAddress;

public class ServerClient {

    public String name;
    public InetAddress adress;
    public int port;
    private final int ID;
    public int attempt = 0;

    public ServerClient(String name, InetAddress adress, int port, final int ID) {
        this.name = name;
        this.adress = adress;
        this.ID = ID;
        this.port = port;
    }

    public int getID() { return ID; }
}