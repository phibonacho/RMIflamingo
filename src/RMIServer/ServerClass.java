package RMIServer;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
public class ServerClass implements SharedInterface {
    public ServerClass(){
    }

    public String SharedMethod(){
        return "I'm a shared method, deal with it..";
    }

    public static void main(String args[]) throws RemoteException, AlreadyBoundException {
        ServerClass obj = new ServerClass();
        SharedInterface stub = null;
        try {
            stub = (SharedInterface) UnicastRemoteObject.exportObject(obj, 9000);
            // Bind the remote object's stub in the registry
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Registry reg = null;
        try {
            reg = LocateRegistry.getRegistry(9000);
            System.err.println("Binding Shared to Registry...");
            reg.bind("Shared", stub);
        }catch (ConnectException ce){
            reg = LocateRegistry.createRegistry(9000);
            System.err.println("Retrying to bind Shared to registry...");
            reg.bind("Shared", stub);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
        System.err.println("Server ready");
        while(true);
    }
}

