/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tun
 */
public class atServerImpl implements atServer{
    atClient client;
    @Override
    public void registerClient(atClient cl) throws RemoteException {
        client = cl;
    }

    @Override
    public void callServerMethod(String mess) throws RemoteException {
        System.out.println(mess);
        for(int i =0; i<50000; i++) {
            try {
                String messFromServer = "Server response: "+i;
                this.client.callAMethod(i+mess);
                wait(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(atServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
