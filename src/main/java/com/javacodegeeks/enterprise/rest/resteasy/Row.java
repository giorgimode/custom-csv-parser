package com.javacodegeeks.enterprise.rest.resteasy;

import java.util.List;

/**
 * Created by oem on 10.12.15.
 */
public class Row {
    private String label;
    private List<Float> attributes;


    public Row(String label, List<Float> attributes){


    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Float> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Float> attributes) {
        this.attributes = attributes;
    }
}
