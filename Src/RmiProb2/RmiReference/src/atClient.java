/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author tun
 */
import java.rmi.*;
public interface atClient extends Remote{
    public void callAMethod(String mess) throws RemoteException;
}
