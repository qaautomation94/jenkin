package com.mscs.emr.automation.functional;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;
import com.mscs.emr.automation.utils.CalmacReporter;
import com.mscs.emr.automation.utils.Config;
import com.mscs.emr.automation.utils.MySqlDbUtils;
import com.mscs.emr.automation.utils.Utils;
import com.mscs.emr.automation.utils.fileprocessor.DownloadedFileFolder;
import com.mscs.emr.automation.utils.fileprocessor.DownloadedFileFolderType;

import io.github.bonigarcia.wdm.WebDriverManager;

public class InvokedMethodListener extends BaseTestPage implements IInvokedMethodListener, IReporter, IHookable {

	private static boolean createDownloadFolder = true;
	public static String filePath = Config.getDownloadFolderPath().replace("/","\\");
//	filePath = Config.getProjectPath()+dff.getFolderPath().replace("/","\\");

//	public static String filePath1 = Config.getDownloadedFileFolder(DownloadedFileFolderType.REPORT_SERVLET_PDF);

	public static String finalDestination;
	static String methodname;
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		LOGGER.info("afterInvocation method - " + testResult.getStatus() + " test result status");
		try {
			CalmacTestValues.setEmailInExcel(CalmacTestValues.Email);
//			CalmacTestValues.setValueInExcel(CalmacTestValues.Email, CalmacTestValues.docID, CalmacTestValues.folderID, CalmacTestValues.integrationsResWareFileNumber, CalmacTestValues.integrationsContextId, CalmacTestValues.transactionId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (method.isTestMethod()) {

			DriverManager.usePrimaryDriver();
			WebDriver driver = getDriver();

			if (driver != null) {
				deinitWebDriver(driver, testResult);
			}

			if (DriverManager.isSecondaryDriverCreated()) {

				DriverManager.useSecondaryDriver();
				driver = getDriver();

				if (driver != null) {
					deinitWebDriver(driver, testResult);
				}
			}
		}
		
	}

	private void deinitWebDriver(WebDriver driver, ITestResult testResult) {
		if (ITestResult.FAILURE == testResult.getStatus()) {
			String screenshotName = testResult.getMethod().getMethodName();
			finalDestination = Utils.captureScreenshot(driver, screenshotName, testResult);
		}
//			driver.get(Config.getLogoutPageURL());
//			driver.quit();



	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		LOGGER.info("beforeInvocation method");
		methodname =method.getTestMethod().getMethodName();
		try {
			CalmacTestValues.setResponseToNull();
			MySqlDbUtils.setDatabase(MySqlDbUtils.OLDB);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (SystemUtil.isWindowsOS()) {
				String dir = "reg delete HKEY_CURRENT_USER\\Software\\Policies\\Google\\Chrome\\ExtensionInstallBlacklist /v 1 /f";
				Runtime.getRuntime().exec("cmd /c" + " " + dir);
			}
		} catch (Exception err) {
			err.printStackTrace();
		}

		if (method.isTestMethod()) {
			System.out.println(
					">>>>>>>>>>>>>>>> Currently running test name is : " + method.getTestMethod().getMethodName());
			LOGGER.info(String.format("Current Browser is '%s'\nCurrent Environment is '%s' \nCurrent test suite is ran from '%s'",
					Config.getPrimaryBrowser(), Config.getEnv(), System.getenv("testSuite")));

			WebDriver driver = createInstance(Config.getPrimaryBrowser(), Config.getGridURL());
				initWebDriver(driver, false);
			
			
		

			// See if we are managing a second browser
			if (testResult.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(Environment.class)) {

				Environment[] nlEnv = testResult.getMethod().getConstructorOrMethod().getMethod().getAnnotationsByType(Environment.class);
				if ((nlEnv == null) || (nlEnv.length != 1)) {
					throw new IllegalStateException("Not exactly 1 instance of Environment annotation found");
				}

				BrowserMode browserMode = nlEnv[0].browserMode();

				if (browserMode == BrowserMode.single) {
					driver = createInstance(Config.getSecondaryBrowser(), Config.getGridURL());
					initWebDriver(driver, true);
			}
			}
			}
	}

	private void initWebDriver(WebDriver driver, boolean secondary) {

		if (!secondary) {
			DriverManager.setPrimaryWebDriver(driver);
		} else {
			DriverManager.setSecondaryWebDriver(driver);
		}

		Boolean gwtDevMode = Config.getLoginPageURL().toLowerCase().indexOf("gwt.codesvr") != -1;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		DriverManager.setAppName(Config.getAppName());
		driver.get(Config.getLoginPageURL());

		if (Config.getRunMode().equalsIgnoreCase("grid")) {
			// driver.manage().window().setSize(new Dimension(1600,900));
			driver.manage().window().maximize();
		} else if (Config.getRunMode().equalsIgnoreCase("local")) {
			driver.manage().window().maximize();
		}

		if (gwtDevMode) {
			driver.get(Config.getLoginPageURL());
		}

	}

	private WebDriver createInstance(String browserName, String gridUrl) {
		WebDriver driver = null;
		DesiredCapabilities capability = null;

		if (Config.getRunMode().equalsIgnoreCase("grid")) {
			if (browserName.toLowerCase().contains("firefox")) {
				capability = DesiredCapabilities.firefox();
			} else if (browserName.toLowerCase().contains("ie")) {
				capability = DesiredCapabilities.internetExplorer();
			} else if (browserName.toLowerCase().contains("edge")) {
				capability = DesiredCapabilities.edge();
			} else if (browserName.toLowerCase().contains("chrome")) {
				capability = DesiredCapabilities.chrome();

				if (Config.isChromeHeadless()) {
					ChromeOptions chromeOptions = new ChromeOptions();
									chromeOptions.addArguments("--headless");
					chromeOptions.addArguments("--no-sandbox");
					chromeOptions.addArguments("--disable-extensions");
					chromeOptions.addArguments("--no-proxy-server");
					chromeOptions.setExperimentalOption("useAutomationExtension", false);

					capability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				}

			}

			if (capability != null) {
				try {
					driver = new RemoteWebDriver(new URL(gridUrl), capability);
				} catch (MalformedURLException e) {
					Assert.fail(Arrays.deepToString(e.getStackTrace()));
				}
			}
		} else if (Config.getRunMode().equalsIgnoreCase("local")) {
			String webdriverLibsPath = Config.getProjectPath() + getFileSeparator() + "drivers"
					+ getFileSeparator();

			if (browserName.toLowerCase().contains("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();

			} else if (browserName.toLowerCase().contains("ie")) {
				if (SystemUtil.isMacOs()) {
					throw new RuntimeException("Cannot run IE locally on Mac OS");
				}
				WebDriverManager.iedriver().setup();
				InternetExplorerOptions options = new InternetExplorerOptions();
				options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				options.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				driver = new InternetExplorerDriver(options);


			} else if (browserName.toLowerCase().contains("edge")) {
				WebDriverManager.edgedriver().setup();

				  EdgeOptions options = new EdgeOptions();

			        // Set the download directory
			        options.setCapability("download.default_directory", filePath);
					driver = new EdgeDriver(options);


			} else if (browserName.toLowerCase().contains("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("disable-infobars");
				if (Config.isChromeHeadless()) {
					options.addArguments("--no-sandbox");
					options.addArguments("--disable-dev-shm-usage");
					options.addArguments("--headless");
					options.addArguments("--disable-extensions");
					options.addArguments("--no-proxy-server");
					options.setExperimentalOption("useAutomationExtension", false);
				}

				HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();

				options.setExperimentalOption("prefs", chromeOptionsMap);
				if (createDownloadFolder) {
					DownloadedFileFolder dff = Config
							.getDownloadedFileFolder(DownloadedFileFolderType.REPORT_SERVLET_PDF);
					filePath = Config.getProjectPath()+dff.getFolderPath().replace("/","\\");
					//filePath = Config.getProjectPath() + dff.getFolderPath();
					LOGGER.info(filePath);
					File f = new File(filePath);
					if (!f.exists()) {
						f.mkdirs();
					}
					createDownloadFolder = false;
				}

				chromeOptionsMap.put("download.default_directory", filePath);
				options.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
				options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				options.setCapability(ChromeOptions.CAPABILITY, options);
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(options);
			}
		}
		return driver;
	}
	private static String getFileSeparator() {
		String separator = FileSystems.getDefault().getSeparator();
		return separator;
	}

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		LOGGER.debug("generateReport method");

		CalmacReporter.generateReportAndSend(uuid, Config.getEnv(), xmlSuites, suites);
	}

	/**
	 * Wraps every method annotated as {@link Test} with a database transaction
	 *
	 */
	@Override
	public void run(IHookCallBack callBack, ITestResult testResult) {
		try {
			if (testResult.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(NeedsLogin.class)) {

				NeedsLogin[] nlAnnos = testResult.getMethod().getConstructorOrMethod().getMethod().getAnnotationsByType(NeedsLogin.class);
				if ((nlAnnos == null) || (nlAnnos.length != 1)) {
					throw new IllegalStateException("Not exactly 1 instance of NeedsLogin annotation found");
				}
			}
			callBack.runTestMethod(testResult);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
