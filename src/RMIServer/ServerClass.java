package RMIServer;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ServerClass implements SharedInterface {
    public ServerClass() {
    }

    public static void serverSetUp(int port){
        System.setProperty("java.security.policy", "/tmp/RMIServer.policy");
        if (System.getSecurityManager()==null) System.setSecurityManager(new SecurityManager());
        ServerClass obj = new ServerClass();
        Registry reg;
        String alias = "Shared";
        try {
            reg=setRegistry(port);
            ExportNBind(reg, obj, alias,port);

            System.err.println("Server ready, type something to shutdown...");
            Scanner sc = new Scanner(System.in);
            System.err.println("You typed: "+sc.next());
            shutDown(reg, obj, alias);
        } catch (RemoteException e) {
            System.err.println("Couldn't set registry, maybe you want to check stack trace?[S/n]");
            showStackTrace(e);
        } catch (AlreadyBoundException e) {
            System.err.println("Couldn't export and bind, maybe you want to check stack trace?[S/n]");
            showStackTrace(e);
        } catch (NotBoundException e) {
            System.err.println("Couldn't unexport and unbind, maybe you want to check stack trace?[S/n]");
            showStackTrace(e);
        }
    }

    @Override
    public void NotifyClient(String message, SharedInterface Client){
        System.out.println("Request from: "+message+" accepted");
    }

    static void showStackTrace(Exception e){
        Scanner sc = new Scanner(System.in);
        if(sc.nextInt()!='n') e.printStackTrace();
    }

    public static Registry setRegistry(int port) throws RemoteException {
        try {
            return LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            return LocateRegistry.getRegistry(port);
        }
    }

    public static void ExportNBind(Registry reg, SharedInterface obj, String alias, int port) throws AlreadyBoundException, RemoteException {
        SharedInterface stub = (SharedInterface) UnicastRemoteObject.exportObject(obj, port);
        reg.bind(alias, stub);
    }

    public static void shutDown(Registry reg,SharedInterface obj, String alias) throws RemoteException, NotBoundException {
        reg.unbind(alias);
        UnicastRemoteObject.unexportObject(obj, true);
    }

    public String SharedMethod(){
        return "I'm a shared method, deal with it..";
    }
    public int SharedIntMethod() { return 42; }

    public static void main(String args[]) {
        int port = (args.length<1?3400: Integer.parseInt(args[0]));
        serverSetUp(port);
    }
}