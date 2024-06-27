package com.mscs.emr.automation.functional;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
public @interface TestCaseId {
    public static final int MISSING_TEST_CASE_ID = -999999;
    public int value();
    public boolean regression() default true;
    public boolean deletedInALM() default false;
    public boolean notFoundInALM() default false;
}
