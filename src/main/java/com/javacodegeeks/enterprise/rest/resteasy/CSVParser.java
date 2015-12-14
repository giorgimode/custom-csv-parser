package com.javacodegeeks.enterprise.rest.resteasy;

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

    public static void parser(String csvString) {
        nonNumericIndices = new ArrayList<Integer>();
        singleColumnValues = new HashMap<String, List<List<String>>>();
        try {
            String[] nextLine;
            int lineNumber = 0;

            CSVReader reader = new CSVReader(new StringReader(csvString), ';');

            nextLine = reader.readNext(); //headers
            headers = nextLine;
            int labelIndex;
            labelIndex = nextLine.length - 1;
            while ((nextLine = reader.readNext()) != null) {
                lineNumber++;
                for (int i = 0; i < labelIndex; i++) {
                    if (!isNumeric(nextLine[i]))
                        nonNumericIndices.add(i);


                    // nextLine[] is an array of values from a single row

                    if (singleColumnValues.get(nextLine[labelIndex]) == null) {
                        singleColumnValues.put(nextLine[labelIndex], new ArrayList<List<String>>());
                    }
                    if (singleColumnValues.get(nextLine[labelIndex]).size() < i + 1) {
                        singleColumnValues.get(nextLine[labelIndex]).add(new ArrayList<String>());
                    }

                    singleColumnValues.get(nextLine[labelIndex]).get(i).add(nextLine[i]);

                }
            }
            createMedians(singleColumnValues);
            createCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void createMedians(Map<String, List<List<String>>> m) {
        singleRowValues = new TreeMap<String, List<String>>();
        for (Map.Entry entrySet : m.entrySet()) {
            List<List<String>> columns = (List) entrySet.getValue();

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

                    singleRowValues.get(entrySet.getKey()).add(Float.toString(median));
                } else {
                    HashMap<String, Integer> freqs = new HashMap<String, Integer>();

                    for (String val : columns.get(i)) {
                        Integer freq = freqs.get(val);
                        freqs.put(val, (freq == null ? 1 : freq + 1));
                    }

                    String mode = null;
                    int maxFreq = 0;

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

    private static boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static void createCSV() {
        try {
            FileWriter writer = new FileWriter("output.csv", false);
            for (int i = 0; i < headers.length; i++) {
                writer.append("\"").append(headers[i]).append("\"");
                if (!headers[i].equals(headers[headers.length - 1]))
                    writer.append(';');

            }
            for (Map.Entry<String, List<String>> entry : singleRowValues.entrySet()) {
                writer.append("\n");

                for (int i = 0; i < entry.getValue().size(); i++) {
                    if (!nonNumericIndices.contains(i)) {
                        writer.append(entry.getValue().get(i)).append(";");

                    } else {
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
