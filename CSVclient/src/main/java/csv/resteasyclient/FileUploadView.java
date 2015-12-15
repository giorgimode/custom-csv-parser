package csv.resteasyclient;

import org.primefaces.model.UploadedFile;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by giorgi on 17.12.15.
 */

@Named
@SessionScoped
public class FileUploadView implements Serializable {
    //since our class is SessionScoped, it needs to be serializable to be passivated


    private UploadedFile file;
    private String fileContent;

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
        // if there is no file or wrong file type attached, user is notified
        // here we do not cover the case if csv file is corrupt or format is wrong
      if (!getFile().getContentType().equals("text/csv")) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Please Upload a CSV File!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else {
            fileUploadController.upload(getFile().getFileName(), getFileContent());


        }
    }

    // get the content of csv file in string format
    private String getFileContent() {
        return fileContent;
    }

    private void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}