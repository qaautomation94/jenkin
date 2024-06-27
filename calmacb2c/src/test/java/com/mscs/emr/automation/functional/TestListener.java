package com.mscs.emr.automation.functional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {

    private static final Logger LOGGER = LogManager.getLogger(TestListener.class);

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        LOGGER.info("onStart method - " + iTestContext.getName());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        LOGGER.info("onFinish method - " + iTestContext.getName());
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LOGGER.info("onTestStart method - " +  getTestMethodName(iTestResult) + " start");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LOGGER.info("onTestSuccess method - " +  getTestMethodName(iTestResult) + " succeed");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        LOGGER.info("onTestSkipped method - " +  getTestMethodName(iTestResult) + " skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        LOGGER.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.info("Test FAILED: " + result.getName());
        LOGGER.info("onTestFailure method - " + getTestMethodName(result) + " failed");
    }
}