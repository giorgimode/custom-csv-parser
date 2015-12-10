package com.javacodegeeks.enterprise.rest.resteasy;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Sort {
    /*  public static void main(String[] args) throws Exception {
          BufferedReader reader = new BufferedReader(new FileReader("iris.csv"));
          Map<String, List<String>> map = new TreeMap<String, List<String>>();
          String line = reader.readLine();  //read header
          while ((line = reader.readLine()) != null) {
              String key = getField(line);
              List<String> l = map.get(key);
              if (l == null) {
                  l = new LinkedList<String>();
                  map.put(key, l);
              }
              l.add(line);

          }
          reader.close();
          FileWriter writer = new FileWriter("sorted_numbers.txt");
          writer.write("a1, a2, a3, a4, id, label \n");
          for (List<String> list : map.values()) {
              for (String val : list) {
                  writer.write(val);
                  writer.write("\n");
              }
          }
          writer.close();
      }
  */
    static Map<String, List<Float>> singleColumnValues = new HashMap<String, List<Float>>();
    static Map<String, List<Float>> singleLabelValues = new HashMap<String, List<Float>>();

    static Map<String, List<List<Float>>> singleColumnValues2 = new HashMap<String, List<List<Float>>>();

    public static void main(String[] args) {
        try {
            //csv file containing data
            String strFile = "test.csv";
            String[] nextLine;
            int lineNumber = 0;

            CSVReader reader = new CSVReader(new FileReader(strFile), ';');

            nextLine = reader.readNext(); //headers
            int labelIndex;
            labelIndex = nextLine.length - 1;
            // for (String s : nextLine) {

            while ((nextLine = reader.readNext()) != null) {
                for (String s : nextLine)
                    System.out.print(s + " ");

                lineNumber++;
                for (int i = 0; i < labelIndex; i++) {
                    if (!isNumeric(nextLine[i]))
                        continue;

                    System.out.println("Line # " + lineNumber);

                    // nextLine[] is an array of values from the line

                    if (singleColumnValues2.get(nextLine[labelIndex]) == null) {
                        singleColumnValues2.put(nextLine[labelIndex], new ArrayList<List<Float>>());
                    }
                    if (singleColumnValues2.get(nextLine[labelIndex]).size() < i + 1) {
                        singleColumnValues2.get(nextLine[labelIndex]).add(new ArrayList<Float>());
                    }

                    singleColumnValues2.get(nextLine[labelIndex]).get(i).add(Float.parseFloat(nextLine[i]));

                }
                // System.out.println(singleColumnValues2 + ": map");
            }
            System.out.println(singleColumnValues2);
            createMedians2(singleColumnValues2);
            System.out.println("median: " + singleLabelValues);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void createMedians2(Map<String, List<List<Float>>> m) {

        for (Map.Entry entrySet : m.entrySet()) {
            List<List<Float>> columns = (List) entrySet.getValue();

            for (List<Float> values : columns) {
                Float median = null;
                Collections.sort(values);

                if (values.size() % 2 == 1)
                    median = values.get((values.size() + 1) / 2 - 1);
                else {
                    Float lower = values.get(values.size() / 2 - 1);
                    Float upper = values.get(values.size() / 2);
                    median = (lower + upper) / 2.0f;
                }
                if (singleLabelValues.get(entrySet.getKey()) == null)
                    singleLabelValues.put((String) entrySet.getKey(), new ArrayList<Float>());

                singleLabelValues.get(entrySet.getKey()).add(median);
            }

        }

    }

    private static void createMedians(Map<String, List<Float>> m) {

        for (Map.Entry entrySet : m.entrySet()) {
            List<Float> values = (List) entrySet.getValue();

            Float median = null;
            Collections.sort(values);

            if (values.size() % 2 == 1)
                median = values.get((values.size() + 1) / 2 - 1);
            else {
                Float lower = values.get(values.size() / 2 - 1);
                Float upper = values.get(values.size() / 2);
                median = (lower + upper) / 2.0f;
            }
            if (singleLabelValues.get(entrySet.getKey()) == null)
                singleLabelValues.put((String) entrySet.getKey(), new ArrayList<Float>());

            singleLabelValues.get(entrySet.getKey()).add(median);
        }
        System.out.println(singleLabelValues + ": median");

    }

    public static boolean isNumeric(String str) {
        try {
            Float d = Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static String getField(String line) {
        System.out.println(line.split(",").length);
        return line.split(";")[5];// extract value you want to sort on
    }
}