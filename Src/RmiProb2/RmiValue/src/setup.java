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
import java.rmi.server.*;
public class setup {
    public static void main(String[] args) throws Exception {
        comunicationServer server = new comunicationServerImpl();
        UnicastRemoteObject.exportObject(server);
        Naming.bind("rmi://localhost/communication", server);
        System.out.println("Wait for request....");
       
    }
}
