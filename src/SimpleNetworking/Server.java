package SimpleNetworking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Timer;

public class Server implements Runnable {
    final boolean verbose = false;

    final int timeout = 250;
    ArrayList<ClientRep> clients;
    boolean connecting;
    ServerSocket serverSocket;
    
    // ***********************************************************************
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("failed to create serverSocket");
            return;
        }

        try {
            serverSocket.setSoTimeout(timeout);
        } catch (SocketException e) {
            System.out.println("failed to set socket.accept() timeout");
        }

        connecting = true;
        clients = new ArrayList<>();
        (new Thread(this)).start();        
    }

    // ***********************************************************************
    public boolean isOK(){
        return connecting;
    }

    // ***********************************************************************
    public void releaseAll(){
        if(verbose) System.out.println("Server.releaseAll");
        connecting = false;
        for (ClientRep cr: clients){
            cr.messenger.releaseAll();
        }
        try {
            serverSocket.close(); // this will break out of accept() and close all connections
            serverSocket = null;
        } catch (IOException e) {
            System.out.println("failed to close serverSocket");
        }
    }

    // ***********************************************************************
    public ArrayList<ClientRep> getClientList(){
        if(verbose) System.out.println("Server.getClientList");

        return clients;
    }

    // ***********************************************************************
    public ArrayList<ClientRep> waitForNextClient(){
        int previousSize = clients.size();
        Timer timer = new Timer();
        if(verbose) System.out.println("Server.waitforNewClient");

        while (true){
            synchronized (timer){try {timer.wait(100);} catch (InterruptedException e) {e.printStackTrace();}};
            timer.cancel();
            int size = clients.size();
            if (size > previousSize){
                break;
            }
        }

        return clients;
    }

    // ***********************************************************************
    public void sendObject(ClientRep clientRep, Object message){
        if(verbose) System.out.print("Server: ");
        clientRep.messenger.sendObject(message);
    }

    // ***********************************************************************
    public ArrayList<Object> getObjects(ClientRep clientRep){
        if(verbose) System.out.print("Server: ");
        return clientRep.messenger.getReceivedObjects();
    }

    // ***********************************************************************
    public ArrayList<Object> waitForNextObject(ClientRep clientRep){
        return clientRep.messenger.waitForNextObject();
    }

    // ***********************************************************************
    @Override
    public void run() {
        if(verbose) System.out.println("Server.run start");
        
        while (true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                // timeout reaches here
                // socket remains null
            }
        
            if (!connecting){
                break;
            }
            else if (socket == null){
                continue;
            }

            // System.out.println("Server runner adding player, creating Messenger");
            Messenger messenger = new Messenger(socket);
            // System.out.println("Server runner Messenger creation completed");
            clients.add(new ClientRep(socket, messenger));
        }

        if(verbose) System.out.println("Server.run exit");
    }
}