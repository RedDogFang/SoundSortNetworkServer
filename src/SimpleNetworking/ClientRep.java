package SimpleNetworking;

import java.net.Socket;

public class ClientRep {
    boolean verbose = false;
    public Socket socket;
    public Messenger messenger;

    public ClientRep(Socket s, Messenger m){
        if(verbose) System.out.println("ClientRep.constructor");

        socket = s;
        messenger = m;
    }
}