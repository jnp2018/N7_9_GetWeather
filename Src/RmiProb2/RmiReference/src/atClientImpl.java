/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.RemoteException;

/**
 *
 * @author tun
 */
public class atClientImpl implements atClient{

    @Override
    public void callAMethod(String mess) throws RemoteException {
        System.out.println(mess);
    }
    
}
