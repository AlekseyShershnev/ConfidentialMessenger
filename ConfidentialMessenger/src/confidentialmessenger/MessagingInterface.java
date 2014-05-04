package confidentialmessenger;

import java.math.BigInteger;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Shershnev
 */
public interface MessagingInterface extends Remote {
    void receiveMessage(BigInteger c, BigInteger sign) throws RemoteException;
    void startReceiveMessage() throws RemoteException;
    void endReceiveMessage() throws RemoteException;
}
