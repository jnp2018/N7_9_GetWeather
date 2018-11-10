/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

/**
 *
 * @author tun
 */
public class onConnectingClient extends Thread {

    private final String defaultApi = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final String appId = "appid=30fa7a7a0f80ef002f526dea5f2a8c3c";
    private final String xmlMode = "mode=xml";
    private final String cityApi = "https://restcountries.eu/rest/v2/all";

    private Socket socket;
    private ObjectInputStream ois;
    public ObjectOutputStream oos;
    private ArrayList<Pair<String, String>> listCapital;

    public onConnectingClient(Socket socket) throws IOException {
        this.listCapital = new ArrayList<>();
        this.socket = socket;
        DataInputStream dis = new DataInputStream(this.socket.getInputStream());
        ois = new ObjectInputStream(dis);
        oos = new ObjectOutputStream(this.socket.getOutputStream());
    }

    public JSONObject getCurrentWeather(String city) {
        JSONObject jsOb = null;
        try {
            URL apiWeather = new URL(this.defaultApi + city + "&" + this.appId);
            URLConnection conn = apiWeather.openConnection();
            BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String jsonStr = "";
            if ((jsonStr = bf.readLine()) != null) {
                jsOb = new JSONObject(jsonStr);
            }
        } catch (Exception ex) {
            Logger.getLogger(myServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsOb;
    }

    public Document getCurrentWeatherXml(String city) {
        Document xml = null;
        try {
            URL apiWeather = new URL(this.defaultApi + city + "&" + this.appId + "&" + this.xmlMode);
            URLConnection conn = apiWeather.openConnection();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            xml = db.parse(conn.getInputStream());
        } catch (Exception ex) {
            Logger.getLogger(myServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xml;
    }

    public String xmlString(String city) {
        String s = "";
        try {
            URL apiWeather = new URL(this.defaultApi + city + "&" + this.appId + "&" + this.xmlMode);
            BufferedReader br = new BufferedReader(new InputStreamReader(apiWeather.openStream()));
            URLConnection conn = apiWeather.openConnection();
            String str = "";
            while ((str = br.readLine()) != null) {
                s += str;
            }
            System.out.println(s);
        } catch (Exception ex) {
            Logger.getLogger(myServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public void senCapital(Object o) {
        try {
            oos.writeObject(o);
            oos.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    @Override
    public void run() {
        try {
            this.listCapital = getAllCapital(cityApi);
            this.senCapital(this.listCapital);
            System.out.println("Send an object: listCapital, size: " + this.listCapital.size()
                    + " to: " + socket.getLocalAddress());
            while (!socket.isClosed()) {
                Object o = null;
                if (ois.available() >= 0) {
                    try {
                        o = ois.readObject();
                        System.out.println(o.toString());
                        JSONObject json = getCurrentWeather(o.toString());
                        Document xml = getCurrentWeatherXml(o.toString());
                        oos.writeObject(json.toString());
                        System.out.println("Send Json Done!");
                        oos.flush();
                        oos.writeObject(xml);
                        oos.flush();
                    } catch (Exception ex) {
                        this.socket.close();
                    }
                } else {
                    System.out.println("No Object request");
                    socket.close();
                }
                if (socket.isClosed()) {
                    break;
                }
            }
            System.out.println("Client quit!!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            try {
                this.socket.close();
            } catch (Exception ex1) {
                System.out.println(ex1.getMessage());
            }
        }
    }
}
