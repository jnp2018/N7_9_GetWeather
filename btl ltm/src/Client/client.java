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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import org.json.JSONObject;
import org.w3c.dom.Document;

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

    public client() {
        port = 80;
        socket = null;
        listCapital = new ArrayList<>();
        jsonReceived = null;
        xmlReceived = null;
        ois = null;
        oos = null;
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

    public void requestGetWeather(String city) {
        if (this.socket != null) {
            try {
//                ObjectOutputStream out = new ObjectOutputStream(this.socket.getOutputStream());
                this.oos.writeObject(city);
                this.oos.flush();
//                out.close();
            } catch (Exception ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void receiveWeatherJson() {
        JSONObject json = null;
        if (this.socket != null) {
            try {
//                ObjectInputStream oi = new ObjectInputStream(this.socket.getInputStream());
                Object o = this.ois.readObject();
                json = new JSONObject(o.toString());
//                oi.close();
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
//                ObjectInputStream oi = new ObjectInputStream(this.socket.getInputStream());
                Object o = this.ois.readObject();
                doc = (Document) o;
//                oi.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        this.xmlReceived = doc;
    }

    public void getCapitalCity() {
        if (this.socket != null) {
            try {
//                ObjectInputStream oi = new ObjectInputStream(this.socket.getInputStream());
                this.listCapital = (ArrayList<Pair<String, String>>) this.ois.readObject();
//                oi.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

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

    public void setXmlReceived(Document xmlReceived) {
        this.xmlReceived = xmlReceived;
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
    
    public JSONObject parseXmlToJson(Document doc) {
        JSONObject json = null;

        return json;
    }

    public Document parseJsontoXmlDocument(JSONObject json) {
        Document doc = null;

        return doc;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void closeConnection() throws IOException {
        this.ois.close();
        this.oos.close();
        this.socket.close();

    }

}
