package com.mscs.emr.automation.functional;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
public @interface Environment {
    public BrowserMode browserMode() default BrowserMode.single;
}
