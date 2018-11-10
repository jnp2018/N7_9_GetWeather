/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author ahihi
 */
import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.json.*;

public class serverRun {

    public static void main(String[] args) {
        try {

            myServer server = new myServer();
            server.run();
            server.listenning();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
   
}
