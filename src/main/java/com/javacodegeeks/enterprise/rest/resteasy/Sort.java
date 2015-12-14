package com.javacodegeeks.enterprise.rest.resteasy;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Sort {
    static Map<String, List<String>> singleLabelValues = new HashMap<String, List<String>>();

    static Map<String, List<List<String>>> singleColumnValues = new HashMap<String, List<List<String>>>();
static String[] headers;
static List<Integer> nonNumericIndices=new ArrayList<Integer>();

    public static void main(String[] args) {
        try {
            //csv file containing data
            String strFile = "test.csv";
            String[] nextLine;
            int lineNumber = 0;

            CSVReader reader = new CSVReader(new FileReader(strFile), ';');

            nextLine = reader.readNext(); //headers
            headers = nextLine;
            int labelIndex;
            labelIndex = nextLine.length - 1;
            while ((nextLine = reader.readNext()) != null) {
                System.out.println("Line # " + lineNumber);

                for (String s : nextLine)
                    System.out.print(s + " ");
                System.out.println("\n");

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
            System.out.println(singleColumnValues);
            createMedians(singleColumnValues);
            System.out.println("median: " + singleLabelValues);
            createCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void createMedians(Map<String, List<List<String>>> m) {

        for (Map.Entry entrySet : m.entrySet()) {
            List<List<String>> columns = (List) entrySet.getValue();

          //  for (List<String> values : columns) {
                for (int i = 0; i < columns.size(); i++){
                    if (!nonNumericIndices.contains(i)) {
                        Float median = null;
                        Collections.sort(columns.get(i));

                        if (columns.get(i).size() % 2 == 1)
                            median = Float.parseFloat(columns.get(i).get((columns.get(i).size() + 1) / 2 - 1));
                        else {
                            Float lower =Float.parseFloat(columns.get(i).get(columns.get(i).size() / 2 - 1));
                            Float upper = Float.parseFloat(columns.get(i).get(columns.get(i).size() / 2));
                            median = (lower + upper) / 2.0f;
                        }
                        if (singleLabelValues.get(entrySet.getKey()) == null)
                            singleLabelValues.put((String) entrySet.getKey(), new ArrayList<String>());

                        singleLabelValues.get(entrySet.getKey()).add(Float.toString(median));
                    }
                    else {


                    }

            }
        }

    }

    public static boolean isNumeric(String str) {
        try {
            Float d = Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

private static void createCSV(){
    System.out.println(headers.length + "zaza");
   /* try {
        FileWriter writer = new FileWriter("");



        writer.flush();
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }*/


}

}