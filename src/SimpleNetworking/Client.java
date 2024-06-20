package SimpleNetworking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
    boolean verbose = false;
    Socket socket;
    Messenger messenger;

    // ***********************************************************************
    public Client(String hostIP, int port){
        if(verbose) System.out.println("Client constructor");

        try {
            socket = new Socket(hostIP, port);
        } catch (UnknownHostException e) {
            System.out.println("Client.startUp fails to create socket 1");
        } catch (IOException e) {
            System.out.println("Client.startUp fails to create socket 2");
        }

        if (socket != null){
            // System.out.println("Client connected successfully, creating Messenger");
            messenger = new Messenger(socket);
            // if (messenger.isOK()){
            //     System.out.println("Client Messenger created successfully");
            // }
            // else{
            //     System.out.println("Client Messenger creation failed");
            // }
        }
    }

    // ***********************************************************************
    public boolean isOK(){
        return socket != null;
    }
    
    // ***********************************************************************
    public void releaseAll(){
        if(verbose) System.out.println("Client releaseAll");
        if (messenger != null){
            messenger.releaseAll();
        }

        if (socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket = null;
        }
    }

    // ***********************************************************************
    public void sendObject(Object object){
        if (messenger != null){
            if(verbose) System.out.print("Client: ");
            messenger.sendObject(object);
        }
        else {
            if(verbose) System.out.print("Client sendObject but no messenger ");
        }
    }

    public ArrayList<Object> getReceivedObjects(){
        if(verbose) System.out.println("Client getObjects");
        if (messenger != null){
            return messenger.getReceivedObjects();
        }

        return null;
    }

    public ArrayList<Object> waitForNextObject(){
        return messenger.waitForNextObject();
    }
}
