package com.mscs.emr.automation.functional;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class ScreenshotMaker implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {
            takeScreenshot(iTestResult);
        }
    }

    private void takeScreenshot(ITestResult testResult) {

        Method method = testResult.getMethod().getConstructorOrMethod().getMethod();
        Optional<Integer> caseID = Optional.ofNullable(method.getAnnotation(TestCaseId.class).value());
        try {
            WebDriver driver = DriverManager.getDriver();
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotName = String.format("[TC%d] %s.png", caseID.orElse(-999999), method.getName());
            File destFile = new File("rpscreenshots/" + screenshotName);
            FileUtils.copyFile(screenshot, destFile);

        } catch (IOException e) {
            System.out.println("Failed to make screenshot! " + e.getMessage());
        }
    }
}