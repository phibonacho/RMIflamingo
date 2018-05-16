package RMIServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SharedInterface extends Remote {
    String SharedMethod()throws RemoteException;
    int SharedIntMethod()throws RemoteException;
    void NotifyClient(String message, SharedInterface Client) throws RemoteException;
}
