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
public class comunicationServerImpl implements comunicationServer{

    @Override
    public heavy comunication(heavy h) throws RemoteException {
        System.out.println("Receive from client: "+h.getweight());
        h.setweight(h.getweight()+1);
        return h;
    }
    
}
