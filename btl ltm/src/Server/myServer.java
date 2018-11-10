/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;
import javafx.util.Pair;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author ahihi
 */
public class myServer implements Serializable {

    private final String cityApi = "https://restcountries.eu/rest/v2/all";
    private ServerSocket serverSocket;
    private int port;
    private ArrayList<Pair<String, String>> listCapital;

    public myServer() {
        this.port = 80;
        this.listCapital = new ArrayList<>();

    }

    public myServer(String url, ServerSocket serverSocket, int port) {
        this.serverSocket = serverSocket;
        this.port = port;

    }

    public void run() {
        try {
            serverSocket = new ServerSocket(80);
//            this.listCapital = getAllCapital(cityApi);
            System.out.println("Running...!");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void listenning() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("accept a client!");
                onConnectingClient createThread = new onConnectingClient(socket);
                createThread.start();


            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                System.out.println("Err to connet to client");

            } catch (Exception ex) {
                Logger.getLogger(myServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public ArrayList<Pair<String, String>> getAllCapital(String url) {
        ArrayList<Pair<String, String>> listCapitalCountry = new ArrayList<>();
        try {
            URL connectUrl = new URL(url);
            URLConnection connect = connectUrl.openConnection();
            BufferedReader bf = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            JSONArray jsonArr = new JSONArray(bf.readLine());
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsOb = jsonArr.getJSONObject(i);
                String capital = jsOb.getString("capital");
                String country = jsOb.getString("name");
                Pair p = new Pair(capital, country);
                listCapitalCountry.add(p);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return listCapitalCountry;
    }

    public String jsonToString() {

        return null;
    }

    public void closeServer() {
        try {
            this.serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(myServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
