package csv.resteasyclient;

import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@ManagedBean
public class FileUploadView {
     private static final String parserURL = "http://localhost:8080/csvparser/send/";
    private UploadedFile file;
    private String fileContent;

    public UploadedFile getFile() {
        System.out.println("getFile");
        return file;
    }
 
    public void setFile(UploadedFile file) {
        System.out.println("setFile");
        setFileContent(new String(file.getContents()));
        this.file = file;
    }

    public void upload() {
        try {

            URL url = new URL(parserURL+getFile().getFileName());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/csv");

            OutputStream os = conn.getOutputStream();
            os.write(getFileContent().getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            else
            {
                FacesMessage message = new FacesMessage("Success!", file.getFileName() + "uploaded successfully");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }

            conn.disconnect();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public String getFileContent() {

        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}