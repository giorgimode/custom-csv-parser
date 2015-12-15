package csv.resteasyclient;

import org.primefaces.model.UploadedFile;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class FileUploadView implements Serializable{
    private UploadedFile file;
    private String fileContent;

    @Inject
    FileUploadController fileUploadController;

    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        setFileContent(new String(file.getContents()));
        this.file = file;
    }

    public void upload() {
        fileUploadController.upload(getFile().getFileName(), getFileContent());
    }

    public String getFileContent() {

        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}