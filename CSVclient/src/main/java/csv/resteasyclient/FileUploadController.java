package csv.resteasyclient;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by giorgi on 17.12.15.
 */
public class FileUploadController implements Serializable {
    private static final String parserURL = "http://localhost:8080/csvparser/send/";

    public void upload(String filename, String fileContent) {
        try {

            // set connection details here
            URL url = new URL(parserURL+filename);
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
            }
            else
            {   // if successful, notify user
                //ideally, FacesMessage belongs to View, rather than Controller
                FacesMessage message = new FacesMessage("Success!", filename + " uploaded successfully");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }

            conn.disconnect();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
