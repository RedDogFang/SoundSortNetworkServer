package SimpleNetworking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Messenger implements Runnable{
    boolean verbose = false;
    Socket socket;

    // needs protection 5x
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    
    ArrayList<Object> receivedObjects;

    boolean receiving;
    final int timeout = 250; // millisecs

    // ***********************************************************************
    public Messenger(Socket socket){
        if(verbose) System.out.println("Messenger.constructor");
        this.socket = socket;
        receivedObjects = new ArrayList<>();

        if(verbose) System.out.println("Messenger constructor creating objectOutputStream");
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());//outputStream);
        } catch (IOException e) {
            System.out.println("IOException in Messenger startup 2");
            e.printStackTrace();
        }

        if(verbose) System.out.println("Messenger constructor creating objectInputStream");
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());//inputStream);
        } catch (IOException e) {
            System.out.println("IOException in Messenger startup 1");
            e.printStackTrace();
        }

        if(verbose) System.out.println("Messenger constructor setting timeout");
        try {
            socket.setSoTimeout(timeout);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        receiving = true;
        (new Thread(this)).start();
    }

    // ***********************************************************************
    public boolean isOK(){
        return receiving;
    }

    // ***********************************************************************
    public void releaseAll(){
        if(verbose) System.out.println("Messenger.releaseAll");
        receiving = false;

        try {objectInputStream.close();} catch (IOException e) {System.out.println("IOExcep while closing objectInputStream");}

        try {objectOutputStream.close();} catch (IOException e) {System.out.println("IOExcep while closing objectOutputStream");}

        receivedObjects.clear();
    }

    // ***********************************************************************
    public ArrayList<Object> waitForNextObject(){
        Timer timer = new Timer();
        if(verbose) System.out.println("Messenger.waitforNextObject");
        ArrayList<Object> temp = null;

        while (true){
            temp = getReceivedObjects();
            if (temp.size() > 0){
                break;
            }
            synchronized (timer){try {timer.wait(100);} catch (InterruptedException e) {e.printStackTrace();}};
            timer.cancel();
        }

        return temp;
    }

    ArrayList<Object> empty = new ArrayList<>();
    // ***********************************************************************
    public ArrayList<Object> getReceivedObjects(){
        if (receivedObjects.size() > 0){
            ArrayList<Object> temp = receivedObjects;
            receivedObjects = new ArrayList<>();
            if(verbose) System.out.println("Messenger.getReceivedObjects ");
            return temp;
        }
        if(verbose) System.out.println("Messenger.getReceivedObjects called but none received");
        return empty;
    }

    // ***********************************************************************
    public void sendObject(Object object){
        if(verbose) System.out.println("Messenger.sendObjects "+object.toString());
        // make a bunch of object to send.
        List<Object> objects = new ArrayList<>();
        objects.add(object);
        
        try {
            objectOutputStream.writeObject(objects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    // ***********************************************************************
    public void run() {
        if(verbose) System.out.println("Messenger.run start");

        // @SuppressWarnings("unchecked")
        while (true){
            List<Object> listOfObjects = null;
            try {
                listOfObjects = (List<Object>) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // timeout reaches here
                // just loop again
            }

            if (!receiving){
                break;
            }
            else if (listOfObjects == null ){
                continue;
            }

            if (listOfObjects.size() == 0){
                // System.out.println("messenger runner listOfObjects.size is 0");
                continue;
            }

            receivedObjects.addAll(listOfObjects);
        }
        if(verbose) System.out.println("Messenger.run exit");
    }
}