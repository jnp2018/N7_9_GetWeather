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
import java.rmi.server.UnicastRemoteObject;
public class client {
    public static void main(String[] args) throws Exception {
        atClient client = new atClientImpl();
        UnicastRemoteObject.exportObject(client);
        atServer server = (atServer) Naming.lookup("rmi://localhost/serverObject");
        server.registerClient(client);
        server.callServerMethod("clientt call server/.....");
    }
}
