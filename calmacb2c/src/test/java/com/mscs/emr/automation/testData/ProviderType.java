package com.mscs.emr.automation.testData;

public enum ProviderType {

    Physician("Physician"),
    Nurse("Nurse"),
    NursePractitioner("Nurse Practitioner"),
    ResearchNurse("Research Nurse"),
    ERx("ERx"),
    UserGroupingProviderUserType("Anesthesiology Assistant");

    public static ProviderType findType(String value) {

        for (ProviderType providerType : ProviderType.values()) {
            if (value.equalsIgnoreCase(providerType.name())) {
                return providerType;
            }
        }

        return null;
    }

    private String value;

    private ProviderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
