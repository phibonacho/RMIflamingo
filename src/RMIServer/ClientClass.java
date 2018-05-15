package RMIServer;


import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientClass {

    public ClientClass(){}

    public static void main(String[] args) {

        String host = (args.length < 1) ? "127.0.0.1" : args[0];
        int port = 3400;
        try {
            System.err.println("Trying to retrieve registry from host...");
            Registry registry = LocateRegistry.getRegistry(host, port);
            System.err.println("Listing item in registry...");
            for(String s: registry.list())System.out.println(s);
            SharedInterface sI = (SharedInterface) registry.lookup("Shared");
            // if not specified, returns first method....
            System.out.println("Server methods says: "+sI.SharedMethod());
            System.out.println("Other server method says: "+sI.SharedIntMethod());
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}