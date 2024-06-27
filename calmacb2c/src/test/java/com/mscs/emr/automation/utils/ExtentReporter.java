package com.mscs.emr.automation.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.mscs.emr.automation.functional.BaseTestPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReporter implements IReporter {

	public static ExtentReports extent;
	public static ExtentTest test;
	private static final String uuid = BaseTestPage.uuid;
	private static SimpleDateFormat simpleDateFormat = BaseTestPage.sdf;
	private static String reportFileName = "Extent Report_" + simpleDateFormat.format(new Date()) + "_" + uuid + ".html";
	private static File reportFile = new File(BaseTestPage.reportingLocation(), reportFileName);
	public static String fileName = reportFile.toString();

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		extent = new ExtentReports(reportFile + File.separator );

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				buildTestNodes(context.getPassedTests(), LogStatus.PASS);
				buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
				buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
			}
		}

		extent.flush();
		extent.close();
	}

	private void buildTestNodes(IResultMap tests, LogStatus status) {
		
		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				test = extent.startTest(result.getMethod().getMethodName());

				test.setStartedTime(getTime(result.getStartMillis()));
				test.setEndedTime(getTime(result.getEndMillis()));

				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				if (result.getThrowable() != null) {
					test.log(status, result.getThrowable());
				} else {
//					test.log(status, "Test " + status.toString().toLowerCase() + "ed");
				}

				if (result.getStatus() == ITestResult.FAILURE) {
					test.log(LogStatus.FAIL, "Test Case FAILED is " + result.getName());
					test.log(LogStatus.FAIL, "Test Case FAILED is " + result.getThrowable());
//					test.log(LogStatus.FAIL, test.addScreenCapture(InvokedMethodListener.finalDestination));
				}

				else if (result.getStatus() == ITestResult.SKIP) {
					test.log(LogStatus.SKIP, "Test Case SKIPPED is " + result.getName());
				} else if (result.getStatus() == ITestResult.SUCCESS) {
					test.log(LogStatus.PASS, "Test Case PASSED is " + result.getName());
					test.log(LogStatus.PASS, result.getMethod().getDescription());
				}

				extent.endTest(test);
			}
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

}
