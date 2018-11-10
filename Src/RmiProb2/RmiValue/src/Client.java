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
public class Client {
    public static void main(String[] args) throws Exception {
        heavy h = new heavy(10);
        comunicationServer newComunication = (comunicationServer) Naming.lookup("rmi://localhost/communication");
        System.out.println("Before send: "+h.getweight());
        heavy h2 = newComunication.comunication(h);
        System.out.println("Receive from server: "+h2.getweight());
    }
}
