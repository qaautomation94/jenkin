package com.mscs.emr.automation.utils;

/**
 * I'd love to use an enum here, but the TestNG @Test(groups=TestGroup.Unit) annotation requires a completely constant expression.
 * Oh Well.
 */
@IgnoreForTestCoverageCheck
public abstract class TestGroup {
    public static final String Unit="Unit";
    public static final String Integration="Integration";
    public static final String Generated="Generated";
    public static final String AccessorMutator="AccessorMutator";
    public static final String SystemRegression="SystemRegression";
    public static final String SystemFileUploadNeeded="SystemFileUploadNeeded";
    public static final String SystemHealthCheck="SystemHealthCheck";
    public static final String FocusUnit="FocusUnit";    
    public static final String FocusIntegration="FocusIntegration";
    public static final String FocusSystem="FocusSystem";
    public static final String SystemTestStatusGenerated="SystemTestStatusGenerated";
    public static final String SystemTestStatusObsolete="SystemTestStatusObsolete";
    public static final String SystemTestStatusActive="SystemTestStatusActive";
}
