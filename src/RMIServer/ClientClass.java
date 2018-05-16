package RMIServer;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientClass extends ServerClass{
    // client is a server too...
    public ClientClass(){}

    @Override
    public String SharedMethod() {
        return "I'm a client remote method darling <3";
    }

    @Override
    public int SharedIntMethod() {
        return 81;
    }

    static SharedInterface getRemoteMethod(String host, int port) throws RemoteException, NotBoundException {
        System.err.println("Trying to retrieve registry from host...");
        Registry registry = LocateRegistry.getRegistry(host, port);
        System.err.println("Listing item in registry...");
        return (SharedInterface) registry.lookup("Shared");
    }

    public static void main(String[] args) {
        String host = (args.length < 1) ? "127.0.0.1" : args[0];
        int port = (args.length < 2) ? 3400 : Integer.parseInt(args[1]);
        try {
            SharedInterface sI = getRemoteMethod(host, port);
            // if not specified, returns first method....
            System.out.println("Server methods says: "+sI.SharedMethod());
            System.out.println("Other server method says: "+sI.SharedIntMethod());
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}