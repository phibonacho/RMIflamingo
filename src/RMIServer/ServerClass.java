package RMIServer;

import java.rmi.AlreadyBoundException;
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
    public int SharedIntMethod() { return 42; }

    public static void main(String args[]) {
        System.setProperty("java.security.policy", "/tmp/RMIServer.policy");
        int port = 3400;
        ServerClass obj = new ServerClass();
        SharedInterface stub = null;
        try {
            stub = (SharedInterface) UnicastRemoteObject.exportObject(obj, port);
            
            // Bind the remote object's stub in the registry
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            LocateRegistry.createRegistry(port);
            Registry reg = LocateRegistry.getRegistry(port);
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