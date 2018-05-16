package RMIServer;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
public class ServerClass implements SharedInterface {
    public ServerClass(){
    }

    private void showStackTrace(Exception e){
        e.printStackTrace();
    }

    public static Registry setRegistry(int port) throws RemoteException {
        try {
            return LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            return LocateRegistry.getRegistry(port);
        }
    }

    public static void ExportNBind(Registry reg, SharedInterface obj, int port) throws AlreadyBoundException, RemoteException {
        SharedInterface stub = (SharedInterface) UnicastRemoteObject.exportObject(obj, port);
        reg.bind("Shared", stub);
    }

    public String SharedMethod(){
        return "I'm a shared method, deal with it..";
    }
    public int SharedIntMethod() { return 42; }

    public static void main(String args[]) {
        int port = 3400;
        System.setProperty("java.security.policy", "/tmp/RMIServer.policy");
        if (System.getSecurityManager()==null) System.setSecurityManager(new SecurityManager());
        ServerClass obj = new ServerClass();
        try {
            Registry reg=setRegistry(port);
            ExportNBind(reg, obj, port);
        } catch (RemoteException e) {
            System.err.println("Couldn't set registry, maybe you want to check stack trace?[S/n]");
        } catch (AlreadyBoundException e) {
            System.err.println("Couldn't export and bind, maybe you want to check stack trace?[S/n]");
        }

        System.err.println("Server ready");
        while(true);
    }
}