package csv.client;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by giorgi on 17.12.15.
 */
public class FileUploadController implements Serializable {
    private static final String parserURL = "http://localhost:8080/csvparser/send/";
    private Map<String, String> queryHistory = new TreeMap<String, String>();

    public void upload(String filename, String fileContent) {
        try {

            // set connection details here
            URL url = new URL(parserURL + filename);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/csv");

            // make a POST and free resources
            OutputStream os = conn.getOutputStream();
            os.write(fileContent.getBytes());
            os.flush();

            //check for status
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            } else {   // if successful, notify user
                //ideally, FacesMessage belongs to View, rather than Controller
                facesMessage("Success!", filename + " uploaded successfully");
                updateHistory(filename);
            }

            conn.disconnect();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void facesMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }


    private void updateHistory(String filename) {
        //set date format
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String data = df.format(new Date());

     // update query history: key=timestamp, value=filename
        getQueryHistory().put(data, filename);

    }

    public Map<String, String> getQueryHistory() {
        return queryHistory;
    }
}
