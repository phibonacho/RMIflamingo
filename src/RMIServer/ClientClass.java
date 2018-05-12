package RMIServer;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientClass {

    public ClientClass(){}

    public static void main(String[] args) {

        String host = (args.length < 1) ? "192.168.1.110" : args[0];
        try {
            System.err.println("Trying to retrieve registry from host...");
            Registry registry = LocateRegistry.getRegistry(host, 9000);
            System.err.println("Listing item in registry...");
            registry.list();
            SharedInterface stub = (SharedInterface) registry.lookup("Shared");
            String response = stub.SharedMethod();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}