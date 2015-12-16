package csv;

import csv.client.FileUploadController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
/**
 * Created by giorgi on 15.12.15.
 */
public class FileUploadIT {
    FileUploadController cut;

    @Before
    public void init() {
        cut = new FileUploadController();
    }

    @Test(expected=RuntimeException.class)
    public void testParserException() {
        String testFilename = "test.pdf";

        String testContent = "This is a content in a wrong type of file";

        cut.upload(testFilename, testContent);
    }

    @Test
    public void testParser() {
        FileUploadController spy = spy(new FileUploadController());
        doNothing().when(spy).facesMessage(anyString(), anyString());
        String testFilename = "test.csv";

        String testContent = "\"a1\";\"a2\";\"a3\";\"a4\";\"id\";\"label\"\n" +
                "5.1;3.5;1.4;0.2;\"id_2\";\"Iris-setosa\"\n" +
                "4.9;3.0;1.4;0.2;\"id_2\";\"Iris-setosa\"\n" +
                "5.0;3.3;1.4;0.2;\"id_50\";\"Iris-setosa\"\n" +
                "7.0;3.2;4.7;1.4;\"id_51\";\"Iris-versicolor\"\n" +
                "5.1;2.5;3.0;1.1;\"id_99\";\"Iris-versicolor\"\n" +
                "5.7;2.8;4.1;1.3;\"id_99\";\"Iris-versicolor\"\n" +
                "6.3;3.3;6.0;2.5;\"id_101\";\"Iris-virginica\"\n" +
                "5.8;2.7;5.1;1.9;\"id_102\";\"Iris-virginica\"\n";

        spy.upload(testFilename, testContent);

        assertTrue(spy.getQueryHistory().containsValue(testFilename));
    }


}