

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.rmi.*;

/**
 *
 * @author tun
 */
public interface comunicationServer extends Remote{
    public heavy comunication(heavy h) throws RemoteException;
}
