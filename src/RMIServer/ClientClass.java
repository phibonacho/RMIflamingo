package RMIServer;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientClass{
    // client is a server too...
    public ClientClass(){}

    static void showStackTrace(Exception e){
        Scanner sc = new Scanner(System.in);
        if(sc.nextInt()!='n') e.printStackTrace();
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
        } catch (RemoteException e) {
            System.err.println("Couldn't get registry, maybe you want to check stack trace?[S/n]");
            showStackTrace(e);
        } catch (NotBoundException e) {
            System.err.println("Couldn't lookup for method, maybe you want to check stack trace?[S/n]");
            showStackTrace(e);
        }
    }
}