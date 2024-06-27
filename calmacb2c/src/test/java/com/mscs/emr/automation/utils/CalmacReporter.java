package com.mscs.emr.automation.utils;

import java.io.File;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.Colors;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.reporters.EmailableReporter;
import org.testng.xml.XmlSuite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mscs.emr.automation.functional.BaseTestPage;
import com.mscs.emr.automation.testData.MockDataUtils;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CalmacReporter {

	protected static final Logger LOGGER = LogManager.getLogger(CalmacReporter.class);
	private static Map<String, String> env = System.getenv();
	private static String triggeredFrom = env.containsKey("JENKINS_HOME") ? "Jenkins" : "Manual";
	private static List<String> slackCommonInfo = new ArrayList<>();
	private static List<String> teamCommonInfo = new ArrayList<>();
	
	private static final String outLookUserEmail="test_automation@comp.com"; 

	private static final String outLookUserName="t_automation"; 

	private static final String outLookUserPassword="mh6QP.9T)*$Z)37=*3-r.S5tqw";
	public static int totalTests=0;
	private static String suiteName=null;
	private static String outLookAuthURL = "adfs.macpak.com";
	private static final String sharePointFormURL="https://forms.office.com/Pages/ResponsePage.aspx?id=eoYPFh_0LEO3cEoHgD5PW8h6jh5KxNlBlf7H5GUlYV1URDNMQ0w4RlJGVk41WDEyNlBCRzVPUUxLRyQlQCN0PWcu";
	

	private enum ReportLevel {
		Status, Success, Warning, Failure
	}

	private static class TestStats {

		private String testName;
		private static int passedCount;
		private static int skippedCount;
		private static int failedCount;
		private static long timeInMillis;
		private List<String> includedGroups = new ArrayList<String>();
		private List<String> excludedGroups = new ArrayList<String>();

		public TestStats() {
		}

		public List<String> getExcludedGroups() {
			return excludedGroups;
		}

		public static int getFailedCount() {
			return failedCount;
		}

		public List<String> getIncludedGroups() {
			return includedGroups;
		}

		public static int getPassedCount() {
			return passedCount;
		}

		public static int getSkippedCount() {
			return skippedCount;
		}

		public String getTestName() {
			return testName;
		}

		public static long getTimeInMillis() {
			return timeInMillis;
		}

		public void setExcludedGroups(List<String> excludedGroups) {
			this.excludedGroups = excludedGroups;
		}

		public void setFailedCount(int failedCount) {
			this.failedCount = failedCount;
		}

		public void setIncludedGroups(List<String> includedGroups) {
			this.includedGroups = includedGroups;
		}

		public void setPassedCount(int passedCount) {
			this.passedCount = passedCount;
		}

		public void setSkippedCount(int skippedCount) {
			this.skippedCount = skippedCount;
		}

		public void setTestName(String testName) {
			this.testName = testName;
		}

		public void setTimeInMillis(long timeInMillis) {
			this.timeInMillis = timeInMillis;
		}
	}

	private static final boolean DEBUG = false;

	private static final DecimalFormat DURATION_HOUR_FORMAT = new DecimalFormat("00");
	private static final DecimalFormat DURATION_MINUTE_FORMAT = new DecimalFormat("00");
	private static final DecimalFormat DURATION_SECOND_FORMAT = new DecimalFormat("00");

	private static EmailableReporter emailableReporter = new EmailableReporter();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");

	public static void beforeSuite(String uuid, String environment, String browserName, ITestContext context) {
		
		if (Config.isReportSlackStartEnabled()) {
			String webHookURL = Config.getSlackChannelWebHookURL();
			ObjectNode jsonMessage = createSimpleMessageAttachmentForSlack(uuid, environment, browserName,
					context.getSuite().getName(), Colors.BLUE.getColorValue().asHex(), "Operation: Tests Started",
					null);
			
			sendMessageToSlackWebHook(webHookURL, jsonMessage);
		}

		if (Config.isReportTeamStartEnabled()) {
			String webHookURL = Config.getTeamChannelWebHookURL();
			ObjectNode jsonMessage = createSimpleMessageAttachmentForTeam(uuid, environment, browserName,
					context.getSuite().getName(), Colors.BLUE.getColorValue().asHex(), "Operation: Tests Started",
					null);
			sendMessageToTeamWebHook(webHookURL, jsonMessage);
		}

		if (Config.isReportEmailStartEnabled()) {

			String subjectDetail = "Tests Started : UUID: " + uuid + "; Environment: " + environment;
			String subject = Config.getReportEmailSubject() + "; " + subjectDetail;

			String emailMessage = "";
			emailMessage += subjectDetail;
			emailMessage += "\n" + "User: Configured: " + Config.getReportUser() + "; System: " + getCurrentUserName();
			emailMessage += "\n" + "Suites: " + context.getSuite().getName();

			try {
				sendMessageToEmail(subject, emailMessage, ReportLevel.Status);
			} catch (Throwable t) {
				System.err.println("Emailing report failed");
				t.printStackTrace(System.err);
			}

		}

	}

	private static ObjectNode createResultsMessageAttachmentForSlack(String uuid, String environment,
			Map<String, List<TestStats>> resultsMap, File reportFile, ReportLevel reportLevel) {

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode jsonMessage = mapper.createObjectNode();
		ArrayNode jsonAttachmentsArray = mapper.createArrayNode();

		jsonMessage.set("attachments", jsonAttachmentsArray);

		ObjectNode jsonAttachment = mapper.createObjectNode();
		jsonAttachmentsArray.add(jsonAttachment);

		List<String> summaryTitle = new ArrayList<>();
		slackCommonInfo.removeIf(i -> i.contains("Suites"));
		summaryTitle.addAll(slackCommonInfo);

		if (reportLevel == ReportLevel.Success) {
			summaryTitle.add(0, "Operation: Tests Completed (All Succeeded)");
		} else {
			summaryTitle.add(0, "Operation: Tests Completed (With Failures)");
		}

		String resultsSummary = getResultsSummaryForSlack(resultsMap, reportFile);
		String title = summaryTitle.stream().collect(Collectors.joining("\n"));

		jsonAttachment.put("fallback", title);
		jsonAttachment.put("color", reportLevel == ReportLevel.Failure ? Colors.RED.getColorValue().asHex()
				: Colors.LIME.getColorValue().asHex());
		jsonAttachment.put("title", title);
		jsonAttachment.put("author_name", "User: " + getCurrentUserName() + "; Run mode: " + triggeredFrom
				+ "; Reported by: " + Config.getReportUser());
		jsonAttachment.put("text", resultsSummary);

		return jsonMessage;
	}

	private static ObjectNode createResultsMessageAttachmentForTeam(String uuid, String environment,
			Map<String, List<TestStats>> resultsMap, File reportFile, ReportLevel reportLevel) {

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode jsonMessage = mapper.createObjectNode();
		ArrayNode jsonAttachmentsArray = mapper.createArrayNode();

		jsonMessage.set("attachments", jsonAttachmentsArray);

		ObjectNode jsonAttachment = mapper.createObjectNode();
		jsonAttachmentsArray.add(jsonAttachment);

		List<String> summaryTitle = new ArrayList<>();
		slackCommonInfo.removeIf(i -> i.contains("Suites"));
		summaryTitle.addAll(teamCommonInfo);

		if (reportLevel == ReportLevel.Success) {
			summaryTitle.add(0, "Operation: Tests Completed (All Succeeded)");
		} else {
			summaryTitle.add(0, "Operation: Tests Completed (With Failures)");
		}

		String browser = "**Browser:** " + Config.getPrimaryBrowser();

		String resultsSummary = getResultsSummaryForTeam(resultsMap, reportFile);
		String title = summaryTitle.stream().collect(Collectors.joining("\n"));

		jsonAttachment.put("type", "MessageCard");
		jsonAttachment.put("color", reportLevel == ReportLevel.Failure ? Colors.RED.getColorValue().asHex()
				: Colors.LIME.getColorValue().asHex());
		jsonAttachment.put("title", title);
		jsonAttachment.put("text",
				"**UUID:** " + uuid + "<br>**User:** " + getCurrentUserName() + ";  **Reported by:** "
						+ Config.getReportUser() + "<br>**Application:** " + Config.getAppName()
						+ ";  **Environment:** " + environment + ";  <br>**Run mode:** " + triggeredFrom + ";  "
						+ browser + "<br>" + resultsSummary);

		return jsonMessage;
	}

	private static ObjectNode createSimpleMessageAttachmentForSlack(String uuid, String environment, String browserName,
			String suiteNames, String color, String summaryPrefix, String text) {

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode jsonMessage = mapper.createObjectNode();
		ArrayNode jsonAttachmentsArray = mapper.createArrayNode();

		jsonMessage.set("attachments", jsonAttachmentsArray);

		ObjectNode jsonAttachment = mapper.createObjectNode();
		jsonAttachmentsArray.add(jsonAttachment);

		List<String> summaryTitle = new ArrayList<String>();

		summaryTitle.add("UUID: " + uuid);
		summaryTitle.add("Environment: " + environment);
		summaryTitle.add(env.containsKey("LAUNCH_NAME") ? "Tests: " + env.get("LAUNCH_NAME") : "Tests: " + suiteNames);
		summaryTitle.add("Browser: " + Config.getPrimaryBrowser());

		if (env.containsKey("JENKINS_URL"))
			summaryTitle.add("Jenkins url: " + env.get("JENKINS_URL"));
		if (env.containsKey("BUILD_URL"))
			summaryTitle.add("Build url: " + env.get("BUILD_URL"));
		if (env.containsKey("BUILD_DISPLAY_NAME"))
			summaryTitle.add("Build number: " + env.get("BUILD_DISPLAY_NAME"));
		if (env.containsKey("BRANCH"))
			summaryTitle.add("Build from branch: " + env.get("BRANCH"));
		if (env.containsKey("SLAVE"))
			summaryTitle.add("Build on node: " + env.get("SLAVE"));
		if (env.containsKey("BUILD_TIMESTAMP"))
			summaryTitle.add("Build timestamp: " + env.get("BUILD_TIMESTAMP"));

		slackCommonInfo.addAll(summaryTitle);

		if (summaryPrefix != null) {
			summaryTitle.add(0, summaryPrefix);
		}

		String title = summaryTitle.stream().collect(Collectors.joining("\n"));

		jsonAttachment.put("fallback", title);
		jsonAttachment.put("color", color);
		jsonAttachment.put("title", title);
		jsonAttachment.put("author_name", "User: " + getCurrentUserName() + "; Run mode: " + triggeredFrom
				+ "; Reported by: " + Config.getReportUser());

		if (text != null) {
			jsonAttachment.put("text", text);
		}

		return jsonMessage;
	}

	private static ObjectNode createSimpleMessageAttachmentForTeam(String uuid, String environment, String browserName,
			String suiteNames, String color, String summaryPrefix, String text) {

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode jsonMessage = mapper.createObjectNode();
		ArrayNode jsonAttachmentsArray = mapper.createArrayNode();

		jsonMessage.set("attachments", jsonAttachmentsArray);

		ObjectNode jsonAttachment = mapper.createObjectNode();
		jsonAttachmentsArray.add(jsonAttachment);

		List<String> summaryTitle = new ArrayList<String>();

		String testTest = env.containsKey("LAUNCH_NAME") ? "**Tests:** " + env.get("LAUNCH_NAME")
				: "<br>**Tests:** " + suiteNames;
		String browser = "**Browser:** " + Config.getPrimaryBrowser();

		if (env.containsKey("JENKINS_URL"))
			summaryTitle.add("Jenkins url: " + env.get("JENKINS_URL"));
		if (env.containsKey("BUILD_URL"))
			summaryTitle.add("Build url: " + env.get("BUILD_URL"));
		if (env.containsKey("BUILD_DISPLAY_NAME"))
			summaryTitle.add("Build number: " + env.get("BUILD_DISPLAY_NAME"));
		if (env.containsKey("BRANCH"))
			summaryTitle.add("Build from branch: " + env.get("BRANCH"));
		if (env.containsKey("SLAVE"))
			summaryTitle.add("Build on node: " + env.get("SLAVE"));
		if (env.containsKey("BUILD_TIMESTAMP"))
			summaryTitle.add("Build timestamp: " + env.get("BUILD_TIMESTAMP"));

		teamCommonInfo.addAll(summaryTitle);

		if (summaryPrefix != null) {
			summaryTitle.add(0, summaryPrefix);
		}

		String title = summaryTitle.stream().collect(Collectors.joining("\n"));

		jsonAttachment.put("type", "MessageCard");
		jsonAttachment.put("color", color);
		jsonAttachment.put("title", title);
		jsonAttachment.put("text",
				"**UUID:** " + uuid + "<br>**User:** " + getCurrentUserName() + ";  **Reported by:** "
						+ Config.getReportUser() + "<br>**Application:** " + Config.getAppName()
						+ ";  **Environment:** " + environment + testTest + "; " + "**Run mode:** " + triggeredFrom
						+ ";  " + browser

		);

		if (text != null) {
			jsonAttachment.put("text", text);
		}

		return jsonMessage;
	}

	public static File generateReportAndSend(String uuid, String environment, List<XmlSuite> xml, List<ISuite> suites) {

		String reportFileName = "TestNG Report_" + sdf.format(new Date()) + "_" + uuid + ".html";
		String reportFolder = BaseTestPage.reportingLocation();

		File reportFile = new File(reportFolder, reportFileName);

		emailableReporter.setFileName(reportFileName);
		emailableReporter.generateReport(xml, suites, reportFolder);

		boolean failuresExist = false;
		Map<String, List<TestStats>> resultsMap = new HashMap<String, List<TestStats>>();

		String allSuiteNamesList = "";

		for (ISuite suite : suites) {

			List<TestStats> testStats = new ArrayList<TestStats>();
			resultsMap.put(suite.getName(), testStats);

			allSuiteNamesList += allSuiteNamesList.isEmpty() ? "" : ",";
			allSuiteNamesList += suite.getName();

			Map<String, ISuiteResult> results = suite.getResults();

			for (Map.Entry<String, ISuiteResult> entry : results.entrySet()) {

				String key = entry.getKey();
				ISuiteResult value = entry.getValue();
				ITestContext testContext = value.getTestContext();

				TestStats testStatsEntry = new TestStats();
				testStats.add(testStatsEntry);

				testStatsEntry.setTestName(key);
				testStatsEntry.setPassedCount(testContext.getPassedTests().size());
				testStatsEntry.setSkippedCount(testContext.getSkippedTests().size());
				testStatsEntry.setFailedCount(testContext.getFailedTests().size());
				testStatsEntry
						.setTimeInMillis(testContext.getEndDate().getTime() - testContext.getStartDate().getTime());
				testStatsEntry.setIncludedGroups(Arrays.asList(testContext.getIncludedGroups()));
				testStatsEntry.setExcludedGroups(Arrays.asList(testContext.getExcludedGroups()));
				failuresExist = failuresExist || testStatsEntry.getFailedCount() > 0;

			}
		}

		ReportLevel reportLevel = failuresExist ? ReportLevel.Failure : ReportLevel.Success;

		if (!failuresExist) {

			if (Config.isReportSlackSuccessEnabled()) {
				ObjectNode jsonSlackMessage = createResultsMessageAttachmentForSlack(uuid, environment, resultsMap,
						reportFile, reportLevel);
				sendMessageToSlackWebHook(environment, jsonSlackMessage);
			}

			if (Config.isReportTeamSuccessEnabled()) {
				ObjectNode jsonTeamkMessage = createResultsMessageAttachmentForTeam(uuid, environment, resultsMap,
						reportFile, reportLevel);
				sendMessageToTeamWebHook(environment, jsonTeamkMessage);
			}

			if (Config.isReportEmailSuccessEnabled()) {

				String subjectDetail = "Tests Finished (All Succeeded) : UUID: " + uuid + "; Environment: "
						+ environment;
				String subject = Config.getReportEmailSubject() + "; " + subjectDetail;

				String emailMessage = "";

				emailMessage += subjectDetail;
				emailMessage += "\n" + "User: Configured: " + Config.getReportUser() + "; System: "
						+ getCurrentUserName();
				emailMessage += "\n" + getResultsSummaryForSlack(resultsMap, reportFile);

				try {
					sendMessageToEmail(subject, emailMessage, reportLevel);
				} catch (Throwable t) {
					System.err.println("Emailing report failed: message=" + emailMessage);
					t.printStackTrace(System.err);
				}

			}
			if(Config.isReportToSharePointEnabled()) {
				suiteName = allSuiteNamesList;
				sendReportToSharePoint();
			}

		} else {

			if (Config.isReportSlackFailureEnabled()) {
				ObjectNode jsonSlackMessage = createResultsMessageAttachmentForSlack(uuid, environment, resultsMap,
						reportFile, reportLevel);
				sendMessageToSlackWebHook(environment, jsonSlackMessage);
			}

			if (Config.isReportTeamFailureEnabled()) {
				ObjectNode jsonTeamMessage = createResultsMessageAttachmentForTeam(uuid, environment, resultsMap,
						reportFile, reportLevel);
				sendMessageToTeamWebHook(environment, jsonTeamMessage);
			}

			if (Config.isReportEmailFailureEnabled()) {

				String subjectDetail = "Tests Finished (With Failures): UUID: " + uuid + "; Environment: "
						+ environment;
				String subject = Config.getReportEmailSubject() + "; " + subjectDetail;

				String emailMessage = "";
				emailMessage += subjectDetail;
				emailMessage += "\n" + "User: Configured: " + Config.getReportUser() + "; System: "
						+ getCurrentUserName();
				emailMessage += "\n" + getResultsSummaryForSlack(resultsMap, reportFile);

				try {
					sendMessageToEmail(subject, emailMessage, reportLevel);
				} catch (Throwable t) {
					System.err.println("Emailing report failed");
					t.printStackTrace(System.err);
				}
			}
			if(Config.isReportToSharePointEnabled()) {
				suiteName = allSuiteNamesList;
			//	sendReportToSharePoint();
			}
		}
		return reportFile;
	}

	private static String getCurrentUserName() {

		String userName;
		String userNamePropertyValue = System.getProperty("user.name");
		String userNameEnvValue = System.getenv("USERNAME");

		if (userNameEnvValue != null) {
			userName = userNameEnvValue;
		} else if (userNamePropertyValue != null) {
			userName = userNamePropertyValue;
		} else {
			userName = "Unknown";
		}

		return userName;
	}

	private static String getHumanReadableDuration(long durationMillis) {

		durationMillis = durationMillis < 0 ? 0 : durationMillis;

		// Round to nearest second
		if (durationMillis > 0) {
			durationMillis = Math.round(durationMillis / 1000.0) * 1000;
		}

		int seconds = (int) (durationMillis / 1000) % 60;
		int minutes = (int) (durationMillis / (1000 * 60) % 60);
		int hours = (int) (durationMillis / (1000 * 60 * 60) % 24);

		String hoursString = DURATION_HOUR_FORMAT.format(hours);
		String minutesString = DURATION_MINUTE_FORMAT.format(minutes);
		String secondsString = DURATION_SECOND_FORMAT.format(seconds);

		return hoursString + "h" + minutesString + "m" + secondsString + "s";

	}

	private static String getListAsString(List<String> list) {

		StringBuilder sb = new StringBuilder();
		for (String item : list) {
			if (sb.length() != 0) {
				sb.append(",");
			}
			sb.append(item);
		}

		return sb.toString();
	}

	private static String getResultsSummaryForSlack(Map<String, List<TestStats>> resultsMap, File reportFile) {

		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, List<TestStats>> entry : resultsMap.entrySet()) {

			sb.append("TestNG Suite: " + entry.getKey());
			sb.append("\n");

			for (TestStats stat : entry.getValue()) {

				int totalTests = stat.getPassedCount() + stat.getSkippedCount() + stat.getFailedCount();

				sb.append("TestNG Test: " + stat.getTestName());
				sb.append("\n");
				sb.append("Counts: Total: " + totalTests + "; Passed: " + stat.getPassedCount() + "; Skipped: "
						+ stat.getSkippedCount() + "; Failed: " + stat.getFailedCount());
				sb.append("\n");
				sb.append("Time: " + getHumanReadableDuration(stat.getTimeInMillis()));
				sb.append("\n");
				sb.append("Included Groups: " + getListAsString(stat.getIncludedGroups()));
				sb.append("\n");
				sb.append("Excluded Groups: " + getListAsString(stat.getExcludedGroups()));
				sb.append("\n");
				sb.append("Complete test report available at: " + reportFile.getAbsolutePath());
			}
		}

		return sb.toString();
	}

	private static String getResultsSummaryForTeam(Map<String, List<TestStats>> resultsMap, File reportFile) {

		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, List<TestStats>> entry : resultsMap.entrySet()) {

			sb.append("**TestNG Suite:** " + entry.getKey() + ";  ");
			// sb.append("<br>");

			for (TestStats stat : entry.getValue()) {

				int totalTests = stat.getPassedCount() + stat.getSkippedCount() + stat.getFailedCount();

				sb.append("**TestNG Test:** " + stat.getTestName());
				sb.append("<br>");
				sb.append("**Counts: Total:** " + totalTests + "; Passed: " + stat.getPassedCount() + "; Skipped: "
						+ stat.getSkippedCount() + "; Failed: " + stat.getFailedCount());
				sb.append("<br>");
				sb.append("**Time:** " + getHumanReadableDuration(stat.getTimeInMillis()));
				sb.append("<br>");
				sb.append("**Included Groups:** " + getListAsString(stat.getIncludedGroups()) + ";  ");
				// sb.append("<br>");
				sb.append("**Excluded Groups:** " + getListAsString(stat.getExcludedGroups()));
				sb.append("<br>");
				sb.append("**Complete test report available at:** \\" +  ExtentReporter.fileName);
			}
		}

		return sb.toString();
	}

	private static void sendMessageToEmail(String subject, String message, ReportLevel reportLevel)
			throws EmailException {

		//Create the email message
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(Config.getReportEmailHost());
		email.setSmtpPort(Config.getReportEmailPort());
		email.setAuthenticator(new DefaultAuthenticator(Config.getReportEmailAuthenticatorAddress(),
				Config.getReportEmailAuthenticatorPassword()));
		email.setSSLOnConnect(Config.isReportEmailAuthenticatorSSL());

		String toListString = Config.getReportEmailToList().trim();

		String[] toList = toListString.split(",");
		for (String to : toList) {
			email.addTo(to);
		}

		email.setFrom(Config.getReportEmailFromAddress(), Config.getReportEmailFromName());
		email.setSubject(subject);

		email.setMsg(message);

		email.send();
	}

	private static void sendMessageToSlackWebHook(String environment, ObjectNode jsonMessage) {

		String webHookURL = Config.getSlackChannelWebHookURL();

		HttpPost httpPost = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(webHookURL);
			URI uri = uriBuilder.build();
			httpPost = new HttpPost(uri);
			httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

			String jsonAsText = jsonMessage.toString();
			String jsonAsTextModified = jsonAsText.replace("{\"attachments\":[", "");
			jsonAsTextModified = jsonAsTextModified.replace("]}", "");

			if (DEBUG) {
				LOGGER.info("SENDING TO Team: " + jsonAsTextModified);
			}

			HttpEntity messageEntity = new StringEntity(jsonAsText, "UTF-8");

			httpPost.setEntity(messageEntity);

			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			HttpClient httpClient = httpClientBuilder.build();

			HttpResponse response = httpClient.execute(httpPost);

			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println(
						"Team message failed: statusCode=" + statusCode + ", reason=" + statusLine.getReasonPhrase());
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static void sendMessageToTeamWebHook(String environment, ObjectNode jsonMessage) {

		String webHookURL = Config.getTeamChannelWebHookURL();

		HttpPost httpPost = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(webHookURL);
			URI uri = uriBuilder.build();
			httpPost = new HttpPost(uri);

			httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

			String jsonAsText = jsonMessage.toString();

			String jsonAsTextModified = jsonAsText.replace("{\"attachments\":[", "");
			jsonAsTextModified = jsonAsTextModified.replace("]}", "");

			if (DEBUG) {
				LOGGER.info("SENDING TO SLACK: " + jsonAsTextModified);
			}

			HttpEntity messageEntity = new StringEntity(jsonAsTextModified);

			httpPost.setEntity(messageEntity);

			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			HttpClient httpClient = httpClientBuilder.build();

			HttpResponse response = httpClient.execute(httpPost);

			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println(
						"Slack message failed: statusCode=" + statusCode + ", reason=" + statusLine.getReasonPhrase());
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	private static void sendReportToSharePoint(){
		ChromeOptions options = new ChromeOptions();
		options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		options.setCapability(ChromeOptions.CAPABILITY, options);
		options.addArguments("--incognito");
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(options);
		driver.get(sharePointFormURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@type='email']"))));
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(outLookUserEmail);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		wait.until(ExpectedConditions.urlContains(outLookAuthURL));
		outLookAuthURL = driver.getCurrentUrl().substring(8);
		driver.get("https://" + outLookUserName +":"+ outLookUserPassword + "@" + outLookAuthURL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//UserID
		driver.findElement(By.xpath("(//input[@placeholder='Enter your answer'])[1]")).sendKeys(MockDataUtils.generateNumberString(4,4));
		//Execution Date
		driver.findElement(By.xpath("(//input[@placeholder='Enter your answer'])[2]")).sendKeys(DateUtils.getTodaysDate());
		//Project Name
		driver.findElement(By.xpath("(//input[@placeholder='Enter your answer'])[3]")).sendKeys(Config.getAppName());
		//TotalTests
		totalTests = TestStats.getPassedCount() + TestStats.getSkippedCount() + TestStats.getFailedCount();
		driver.findElement(By.xpath("(//input[@placeholder='Enter your answer'])[4]")).sendKeys(Integer.toString(totalTests));
		//Passed Test
		driver.findElement(By.xpath("(//input[@placeholder='Enter your answer'])[5]")).sendKeys(Integer.toString(TestStats.getPassedCount()));
		//Failed Test
		driver.findElement(By.xpath("(//input[@placeholder='Enter your answer'])[6]")).sendKeys(Integer.toString(TestStats.getFailedCount()));
		//SuiteName
		driver.findElement(By.xpath("(//input[@placeholder='Enter your answer'])[7]")).sendKeys(suiteName);
		//skipped Tests
		driver.findElement(By.xpath("(//input[@placeholder='Enter your answer'])[8]")).sendKeys(Integer.toString(TestStats.getSkippedCount()));
		//ExecutionTime
		driver.findElement(By.xpath("(//input[@placeholder='Enter your answer'])[9]")).sendKeys(getHumanReadableDuration(TestStats.getTimeInMillis()));
		//EnvName
		driver.findElement(By.xpath("(//input[@placeholder='Enter your answer'])[10]")).sendKeys(Config.getEnv());
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		float passPercentageCalculation =(float) TestStats.getPassedCount()/(TestStats.getPassedCount()+TestStats.getFailedCount())*100;
		String passPercentage = String.format("%.2f",passPercentageCalculation);
		driver.findElement(By.xpath("(//input[@placeholder='Enter your answer'])[11]")).sendKeys(passPercentage + " %");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//button[@data-automation-id='submitButton' or contains(text(),'Submit')]")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Assert.assertEquals("Your response was submitted.", driver.findElement(By.xpath("//span[contains(text(), 'Your response was submitted.')]")).getText().toString());
		driver.close();
	}

	private CalmacReporter() {
	}

}