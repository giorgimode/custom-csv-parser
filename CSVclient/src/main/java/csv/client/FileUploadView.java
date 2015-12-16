package csv.client;

import org.primefaces.model.UploadedFile;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * Created by giorgi on 17.12.15.
 */

@Named
@SessionScoped
public class FileUploadView implements Serializable {
    //since our class is SessionScoped, it needs to be serializable to be passivated


    private UploadedFile file;
    private String fileContent;
    private Map<String, String> queryHistory = new TreeMap<String, String>();

    @Inject
    FileUploadController fileUploadController;

    public UploadedFile getFile() {
        return file;
    }

    // when user attaches the file, csv contents are retrieved in string format
    public void setFile(UploadedFile file) {
        setFileContent(new String(file.getContents()));
        this.file = file;
    }

    public void upload() {
        // if there is no file attached or wrong file type attached, user is notified
        // here we do not cover the case if csv file is corrupt or format is wrong
      if (!getFile().getContentType().equals("text/csv")) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Please Upload a CSV File!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else {
            fileUploadController.upload(getFile().getFileName(), getFileContent());
        }
    }

    // redirect and retrieve queries
    public String redirectToQueries() {
        setQueryHistory();
        return "/history.xhtml?faces-redirect=true";
    }

    private void setQueryHistory() {
        this.queryHistory = fileUploadController.getQueryHistory();
    }

    // get the content of csv file in string format
    private String getFileContent() {
        return fileContent;
    }

    private void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    // convert map of queries to Arraylist to use it in datatable
    public List<Map.Entry<String, String>> getQueries() {
        Set<Map.Entry<String, String>> productSet = queryHistory.entrySet();
        return new ArrayList<Map.Entry<String, String>>(productSet);
    }

}