package com.mscs.emr.automation.functional;

import com.mscs.emr.automation.utils.IgnoreForTestCoverageCheck;

/**
 * I'd love to use an enum here, but the TestNG @Test(groups=TestGroup.Unit) annotation requires a completely constant expression.
 * Oh Well.
 */
@IgnoreForTestCoverageCheck
public abstract class TestGroup {
    public static final String Unit="Unit";
    public static final String Integration="Integration";
    public static final String SystemRegression="System";
    public static final String SystemHealthCheck="HealthCheck";   
    public static final String FocusIntegration="FocusIntegration";
    public static final String REGRESSION="Regression";
    public static final String SMOKE="Smoke";
    public static final String PRIORITYONE="PriorityOne";
    public static final String PRIORITYTWO="PriorityTwo";
    public static final String PRIORITYTHREE="PriorityThree";
    public static final String ENCOMPASS="Encompass";
    public static final String LENDINGQB="LendingQB";
    public static final String CALYXPATH="CalyxPath";
    public static final String CALYXPOINT="CalyxPoint";
    public static final String COLLABORATIONCENTER="CollaborationCenter";
    public static final String FCB="Fcb";
    public static final String PANTHEON="Patheon";
    public static final String TITLEPORTPROD="TitlePortSmokeProd";
}
