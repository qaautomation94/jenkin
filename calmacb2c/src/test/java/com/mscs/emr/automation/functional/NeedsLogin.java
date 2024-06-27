package com.mscs.emr.automation.functional;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import com.mscs.emr.automation.testData.ProviderType;

@Retention(RUNTIME)
public @interface NeedsLogin {
    public ProviderType providerType() default ProviderType.Physician;
    public int userIndex() default 0;

}
