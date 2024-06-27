package com.mscs.emr.automation.testData;

public enum ResourceGroup {
  
    Chemo("Chemo"),   
    Imaging("Imaging"),
    Lab("Lab"),
    MD("MD"),
    Nurse("Nurse"),
    Other("Other");

    private String value;

    private ResourceGroup(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
