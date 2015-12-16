package csv;

import com.opencsv.CSVReader;
import org.junit.Test;

import java.io.FileReader;

import static org.junit.Assert.assertEquals;

/**
 * Created by giorgi on 15.12.15.
 */
public class CSVParserTest {

    @Test
    public void testParser() throws Exception {
        CSVParser csvParser = new CSVParser();
        String myString="\"a1\";\"a2\";\"a3\";\"a4\";\"id\";\"label\"\n" +
                "5.1;3.5;1.4;0.2;\"id_2\";\"Iris-setosa\"\n" +
                "4.9;3.0;1.4;0.2;\"id_2\";\"Iris-setosa\"\n" +
                "5.0;3.3;1.4;0.2;\"id_50\";\"Iris-setosa\"\n" +
                "7.0;3.2;4.7;1.4;\"id_51\";\"Iris-versicolor\"\n" +
                "5.1;2.5;3.0;1.1;\"id_99\";\"Iris-versicolor\"\n" +
                "5.7;2.8;4.1;1.3;\"id_99\";\"Iris-versicolor\"\n" +
                "6.3;3.3;6.0;2.5;\"id_101\";\"Iris-virginica\"\n" +
                "5.8;2.7;5.1;1.9;\"id_102\";\"Iris-virginica\"\n";

        csvParser.parser("myfile.csv", myString);

        //BufferedReader reader1 =  new BufferedReader(new FileReader("myfile.csv"));
       // assertNotNull(reader1.readLine());
        CSVReader reader = new CSVReader(new FileReader("myfile.csv"), ';');
        String[] nextLine;

        nextLine = reader.readNext();
        assertEquals(nextLine[5], "label");

        nextLine = reader.readNext();
        assertEquals(nextLine[0], "5.0");
        assertEquals(nextLine[5], "Iris-setosa");

        nextLine = reader.readNext();
        assertEquals(nextLine[0], "5.7");
        assertEquals(nextLine[5], "Iris-versicolor");

        nextLine = reader.readNext();
        assertEquals(nextLine[0], "6.05");
        assertEquals(nextLine[5], "Iris-virginica");


    }
}