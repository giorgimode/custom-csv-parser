/*
package com.javacodegeeks.enterprise.rest.resteasy;

import java.util.*;

public class CategoryMedianCalculator {
    private static Map<String, List<Float>> medianFloat = new HashMap<>();
    private final Map<String, List<Row>> rowMap = new HashMap<>();
    private final Map<String, List<List<Float>>> attributeMap = new HashMap<>();
    private List<Row> rows = new ArrayList<>();

    private static Map<String, List<Float>> getMedianValues(Map<String, List<List<Float>>> m) {
        for (Map.Entry entry : m.entrySet())


            return medianFloat;

    }

    public static double Median(ArrayList<Float> values) {
        Collections.sort(values);

        if (values.size() % 2 == 1)
            return values.get((values.size() + 1) / 2 - 1);
        else {
            double lower = values.get(values.size() / 2 - 1);
            double upper = values.get(values.size() / 2);

            return (lower + upper) / 2.0;
        }
    }

    public void getMedianz() {

        for (int i = 0; i < rows.size(); i++) {
            String label = rows.get(i).getLabel();
            Row currentRow = rows.get(i);

            for (int j = 0; j < currentRow.getAttributes().size(); i++) {
                if (attributeMap.get(label) == null)
                    attributeMap.put(label, new ArrayList<>());

                if (attributeMap.get(label).get(j) == null)
                    attributeMap.get(label).add(new ArrayList<>());

                attributeMap.get(label).get(j).add(currentRow.getAttributes().get(j));


            }


        }


    }

*/
/*    private static Row getMedian(List<Row> rows) {
        // Collections.sort(rows);
        int index = rows.size() / 2;
        if (rows.size() % 2 == 0) {
            return rows.get(index - 1).stdDev < rows.get(index).stdDev ? rows.get(index - 1).value : rows.get(index)
            .value;
        } else {
            return rows.get(index).value;
        }
    }*//*


    public void addRow(String label, List<Float> attributes) {
        //  rows = rowMap.get(label);
        if (rows == null) {
            rows = new ArrayList<>();
            //   rowMap.put(label, rows);
        }
        rows.add(new Row(label, attributes));
    }

    */
/*public Map<String, Double> getMedians() {
        Map<String, Row> result = new TreeMap<>();
        for (Map.Entry<String, List<Row>> entry : rowMap.entrySet()) {
            attributeMap.put(entry.getKey(), )

            result.put(entry.getKey(), getMedian(entry.getValue()));
        } return result;
    }*//*

}*/
