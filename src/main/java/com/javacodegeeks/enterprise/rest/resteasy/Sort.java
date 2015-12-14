package com.javacodegeeks.enterprise.rest.resteasy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Sort {
    static Map<String, List<String>> singleRowValues = new TreeMap<String, List<String>>();
    static Map<String, List<List<String>>> singleColumnValues = new HashMap<String, List<List<String>>>();
    static String[] headers;
    static List<Integer> nonNumericIndices = new ArrayList<Integer>();

    public static void main(String[] args) {
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
        csvParser.parser(myString);
       /* try {
            //csv file containing data
            String strFile = "gaussian.csv";
            String[] nextLine;
            int lineNumber = 0;

            CSVReader reader = new CSVReader(new FileReader(strFile), ';');

            nextLine = reader.readNext(); //headers
            headers = nextLine;
            int labelIndex;
            labelIndex = nextLine.length - 1;
            while ((nextLine = reader.readNext()) != null) {
               // System.out.println("Line # " + lineNumber);

//                for (String s : nextLine)
//                    System.out.print(s + " ");
//                System.out.println("\n");

                lineNumber++;
                for (int i = 0; i < labelIndex; i++) {
                    if (!isNumeric(nextLine[i]))
                        nonNumericIndices.add(i);


                    // nextLine[] is an array of values from the line

                    if (singleColumnValues.get(nextLine[labelIndex]) == null) {
                        singleColumnValues.put(nextLine[labelIndex], new ArrayList<List<String>>());
                    }
                    if (singleColumnValues.get(nextLine[labelIndex]).size() < i + 1) {
                        singleColumnValues.get(nextLine[labelIndex]).add(new ArrayList<String>());
                    }

                    singleColumnValues.get(nextLine[labelIndex]).get(i).add(nextLine[i]);

                }
            }
            // System.out.println(nonNumericIndices);
           // System.out.println(singleColumnValues);
            createMedians(singleColumnValues);
            System.out.println("median: " + singleRowValues);
            createCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    private static void createMedians(Map<String, List<List<String>>> m) {

        for (Map.Entry entrySet : m.entrySet()) {
            List<List<String>> columns = (List) entrySet.getValue();

            //  for (List<String> values : columns) {
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

    public static boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static void createCSV() {
        System.out.println(headers.length + "zaza");
        try {
            FileWriter writer = new FileWriter("output.csv");
            // for (String header : headers) {
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