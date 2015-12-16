package csv;

import com.opencsv.CSVReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Created by oem on 10.12.15.
 */

public class CSVParser {
    private static Map<String, List<String>> singleRowValues;
    private static Map<String, List<List<String>>> singleColumnValues;
    private static List<Integer> nonNumericIndices;
    private static String[] headers;

    public static void parser(String filename, String csvString) {
        nonNumericIndices = new ArrayList<Integer>();
        singleColumnValues = new HashMap<String, List<List<String>>>();
        try {
            String[] nextLine;

            // parse string content via CSVReader
            /*
            here symbol ';' is used to split values as in sample csv files.
            In many csv files ',' is used to split values, but this app does not cover that scenario
            */
            CSVReader reader = new CSVReader(new StringReader(csvString), ';');

            // get the first row: list of headers and store it
            nextLine = reader.readNext();
            headers = nextLine;
            int labelIndex;
            // column index where label names are listed, usually last column
            labelIndex = nextLine.length - 1;

            // read content line by line
            while ((nextLine = reader.readNext()) != null) {

                //read column by column
                for (int i = 0; i < labelIndex; i++) {
                    // keep the indexes of non numeric Columns
                    if (!isNumeric(nextLine[i]))
                        nonNumericIndices.add(i);


                    // nextLine[] is an array of values from a single row
                    //in singleColumnValues for each key (label) store the columns as lists
                    if (singleColumnValues.get(nextLine[labelIndex]) == null) {
                        singleColumnValues.put(nextLine[labelIndex], new ArrayList<List<String>>());
                    }
                    if (singleColumnValues.get(nextLine[labelIndex]).size() < i + 1) {
                        singleColumnValues.get(nextLine[labelIndex]).add(new ArrayList<String>());
                    }

                    // update the column for this label with new value
                    singleColumnValues.get(nextLine[labelIndex]).get(i).add(nextLine[i]);

                }
            }
            // calculate values
            getModeOrMedian(singleColumnValues);
            //generate output
            createCSV(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void getModeOrMedian(Map<String, List<List<String>>> m) {
       // TreeMap sorts the rows by key (label)
        singleRowValues = new TreeMap<String, List<String>>();
        for (Map.Entry entrySet : m.entrySet()) {
            List<List<String>> columns = (List) entrySet.getValue();

            //if column is of numeric type, find median
            // if columns is of non-numeric type, find mode
            for (int i = 0; i < columns.size(); i++) {
                if (!nonNumericIndices.contains(i)) {
                    Float median = null;
                    Collections.sort(columns.get(i));

                    if (columns.get(i).size() % 2 == 1)
                        median = Float.parseFloat(columns.get(i).get((columns.get(i).size() + 1) / 2 - 1));
                    else {
                        Float lower = Float.parseFloat(columns.get(i).get(columns.get(i).size() / 2 - 1));
                        Float upper = Float.parseFloat(columns.get(i).get(columns.get(i).size() / 2));
                        median = (lower + upper) / 2.0f;
                    }
                    if (singleRowValues.get(entrySet.getKey()) == null)
                        singleRowValues.put((String) entrySet.getKey(), new ArrayList<String>());

                    // in singleRowValues for each key (label) there is one value/median per column
                    singleRowValues.get(entrySet.getKey()).add(Float.toString(median));
                } else {
                    // find mode for non-numeric values
                    HashMap<String, Integer> freqs = new HashMap<String, Integer>();

                    for (String val : columns.get(i)) {
                        Integer freq = freqs.get(val);
                        //main logic, increase frequency to count occurrence
                        freqs.put(val, (freq == null ? 1 : freq + 1));
                    }

                    String mode = null;
                    int maxFreq = 0;

                    // compare occurrences and find mode
                    for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
                        int freq = entry.getValue();
                        if (freq > maxFreq) {
                            maxFreq = freq;
                            mode = entry.getKey();
                        }
                    }

                    if (singleRowValues.get(entrySet.getKey()) == null)
                        singleRowValues.put((String) entrySet.getKey(), new ArrayList<String>());

                    singleRowValues.get(entrySet.getKey()).add(mode);
                }

            }
        }

    }

    // check if string value is of numeric type
    private static boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // creating customized output to maintain CSV format
    private static void createCSV(String filename) {
        try {
            FileWriter writer = new FileWriter(filename, false);
           // creating first line of headers
            for (int i = 0; i < headers.length; i++) {
                writer.append("\"").append(headers[i]).append("\"");
                //for the last column, there is no ';' symbol
                if (!headers[i].equals(headers[headers.length - 1]))
                    writer.append(';');

            }
            for (Map.Entry<String, List<String>> entry : singleRowValues.entrySet()) {
                writer.append("\n");

                for (int i = 0; i < entry.getValue().size(); i++) {
                    if (!nonNumericIndices.contains(i)) {
                        writer.append(entry.getValue().get(i)).append(";");

                    } else {
                        // non-numeric values are enclosed in quotes
                        writer.append("\"").append(entry.getValue().get(i)).append("\"").append(";");
                    }

                }

                writer.append("\"").append(entry.getKey()).append("\"");


            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
