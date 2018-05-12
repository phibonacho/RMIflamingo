package RMIServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SharedInterface extends Remote {
    public String SharedMethod()throws RemoteException;
}
