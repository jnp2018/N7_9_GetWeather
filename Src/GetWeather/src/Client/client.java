/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XML11Serializer;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ahihi
 */
public class client implements Serializable {

    private Socket socket;
    private int port;
    private ArrayList<Pair<String, String>> listCapital;
    private JSONObject jsonReceived;
    private Document xmlReceived;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private weatherOfCity weather;
    private ArrayList<weatherOfCity> listWeekWeather;

    public client() {
        port = 80;
        socket = null;
        listCapital = new ArrayList<>();
        jsonReceived = null;
        xmlReceived = null;
        ois = null;
        oos = null;
        weather = new weatherOfCity();
        listWeekWeather = new ArrayList<>();
    }

    public void connect(String host, int port) {
        try {
            this.socket = new Socket(host, port);
            DataOutputStream dos = new DataOutputStream(this.socket.getOutputStream());
            this.oos = new ObjectOutputStream(dos);
            DataInputStream dis = new DataInputStream(this.socket.getInputStream());
            this.ois = new ObjectInputStream(dis);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void getCapitalCity() {
        if (this.socket != null) {
            try {
                this.listCapital = (ArrayList<Pair<String, String>>) this.ois.readObject();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public void requestGetWeather(String city) {
        if (this.socket != null) {
            try {
                this.oos.writeObject(city);
                this.oos.flush();
            } catch (Exception ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void receiveWeatherJson() {
        JSONObject json = null;
        if (this.socket != null) {
            try {
                Object o = this.ois.readObject();
                json = new JSONObject(o.toString());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        this.jsonReceived = json;
    }

    public void receiveWeatherXml() {
        Document doc = null;
        if (this.socket != null) {
            try {
                Object o = this.ois.readObject();
                doc = (Document) o;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        this.xmlReceived = doc;
    }

    public String formatXml(Document doc) {
        Writer wt = null;
        try {
            OutputFormat out = new OutputFormat(doc);
            out.setLineWidth(65);
            out.setIndenting(true);
            out.setIndent(5);
            wt = new StringWriter();
            XMLSerializer serializer = new XML11Serializer(wt, out);
            serializer.serialize(doc);

        } catch (IOException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wt.toString();
    }

    public void readXml(Document doc) {
        Element root = doc.getDocumentElement();
        NodeList listNodeChile = root.getChildNodes();
        for (int i = 0; i < listNodeChile.getLength(); i++) {
            Node node = listNodeChile.item(i);
            NamedNodeMap attrs = node.getAttributes();
            switch (node.getNodeName()) {
                case "city":
                    this.weather.setCityName(attrs.getNamedItem("name").getNodeValue());
                    break;
                case "temperature":
                    this.weather.setNhietDo(Float.parseFloat(attrs.getNamedItem("value").getNodeValue()) - 272);
                    break;
                case "humidity":
                    this.weather.setDoAm(Float.parseFloat(attrs.getNamedItem("value").getNodeValue()));
                    break;
                case "pressure":
                    this.weather.setApSuat(Float.parseFloat(attrs.getNamedItem("value").getNodeValue()));
                    break;
                case "clouds":
                    this.weather.setMay(attrs.getNamedItem("name").getNodeValue());
                    break;
                case "visibility":
                    try {
                        this.weather.setTamNhin(Float.parseFloat(attrs.getNamedItem("value").getNodeValue()));
                    } catch (NullPointerException ex) {
                        break;
                    }
                    break;
                case "lastupdate":
                    this.weather.setLastUpdate(attrs.getNamedItem("value").getNodeValue());
                    break;
                case "weather":
                    this.weather.setThoitiet(attrs.getNamedItem("value").getNodeValue());
                    break;

            }
        }

    }

    public void readWeekWeather(Document doc) {

    }

    public JSONObject parseXmlToJson(String xml) {
        return XML.toJSONObject(xml);

    }

    public String parseJsontoXmlStrting(JSONObject json) {
        return XML.toString(json);
    }

    public void closeConnection() throws IOException {
        this.ois.close();
        this.oos.close();
        this.socket.close();

    }

    public ArrayList<Pair<String, String>> getListCapital() {
        return listCapital;
    }

    public void setListCapital(ArrayList<Pair<String, String>> listCapital) {
        this.listCapital = listCapital;
    }

    public JSONObject getJsonReceived() {
        return jsonReceived;
    }

    public void setJsonReceived(JSONObject jsonReceived) {
        this.jsonReceived = jsonReceived;
    }

    public Document getXmlReceived() {
        return xmlReceived;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setXmlReceived(Document xmlReceived) {
        this.xmlReceived = xmlReceived;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public weatherOfCity getWeather() {
        return weather;
    }

    public void setWeather(weatherOfCity weather) {
        this.weather = weather;
    }

}
