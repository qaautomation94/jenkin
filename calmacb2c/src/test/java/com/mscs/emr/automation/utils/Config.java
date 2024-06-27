package com.mscs.emr.automation.utils;

import java.io.File;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;

import com.mscs.emr.automation.utils.fileprocessor.DownloadedFileFolder;
import com.mscs.emr.automation.utils.fileprocessor.DownloadedFileFolderType;

public final class Config {

	private static final String TEST_CONFIG_PROPERTIES_DIR = System.getProperty("user.dir");
	private static final String TEST_CONFIG_PROPERTIES_FILENAME = "testConfig.properties";
	private static final String TEST_COFFIG_PROPERTIES_OVERRIDE_FILENAME = "testConfigOverride.properties";
	private static Configuration config;
	private static final String KEY_ENV_USBANK_USERNAME_SUFFIX = "b2cusername";
	private static final String KEY_ENV_USBANK_PASSWORD_SUFFIX = "b2cpassword";
	private static final String KEY_ENV_ENCURL_SUFFIX_TITLEPRO = "titleportplaceorderurl";
	private static final String KEY_ENV_ENCURL_SUFFIX_TITLEPORT_US_BANK = "titleportplaceorderusbankurl";
	private static final String KEY_ENV_PANTHEON_USERNAME_SUFFIX = "user";
	private static final String KEY_ENV_PANETHEON_PASSWORD_SUFFIX = "password";
	private static final String KEY_ENV_SHEETNAME_SUFFIX = "sheetname";

	private static final String KEY_APP_NAME = "config.app.name";
	private static final String KEY_FOLDER_PITEST_BASE_PATH = "config.folder.calmac.base.path";
	private static final String KEY_FOLDER_PITEST_RESOURCES_RELATIVEPATH = "config.folder.calmac.resources.relativepath";
	private static final String TEST_DATA_FOLDER_RELATIVEPATH = "config.folder.calmac.resources.relativepath.testdata";
	private static final String KEY_FOLDER_PITEST_DRIVERS_RELATIVEPATH = "config.folder.calmac.drivers.relativepath";
	private static final String KEY_FOLDER_FAILED_SCREENSHOTS_RELATIVEPATH = "config.folder.calmac.screenshots.relativepath";
	private static final String KEY_EXCEL_FILE_NAME_TO_READ_DATA = "config.excel.file.name";
	private static final String KEY_EXCEL_FILE_NAME_TO_READ_XML_DATA = "config.excel.file.xml.testdata";
	private static final String KEY_XML_COLLABORATION_CENTER_FILE_UPLOAD = "config.excel.file.xml.collaborationcenterfileupload";
	private static final String KEY_BROWSER_PRIMARY = "config.browser.primary";
	private static final String KEY_BROWSER_SECONDARY = "config.browser.secondary";
	private static final String KEY_BROWSER_CHROME_HEADLESS = "config.browser.chrome.headless";
	private static final String KEY_RUNMODE = "config.runmode";
	private static final String KEY_GRIDURL = "config.gridurl";
	private static final String KEY_ENV = "config.env";
	private static final String KEY_XPATH_DEBUG = "config.xpath.debug";
	private static final String KEY_ENV_LOGINPAGEURL_SUFFIX = "loginpageurl";
	private static final String KEY_ENV_PANTHEON_LOGINPAGEURL_SUFFIX = "loginpageurl";
	private static final String KEY_ENV_LOGOUTPAGEURL_SUFFIX = "logoutpageurl";
	private static final String KEY_ENV_SLACK_CHANNEL_WEBHOOKURL_SUFFIX = "slackchannelwebhookurl";
	private static final String KEY_ENV_TEAM_CHANNEL_WEBHOOKURL_SUFFIX = "teamchannelwebhookurl";
	private static final String KEY_ADMIN_USER_ACCOUNT = "config.admin.user.account";
	private static final String KEY_ADMIN_USER_EMAIL = "config.admin.user.email";
	private static final String KEY_ADMIN_USER_ACCOUNT_PASSWORD = "config.admin.user.account.password";
	private static final String KEY_ENV_LOGIN_PAGE_TITLE_SUFFIX = "loginpagetitle";
	private static final String KEY_DOWNLOAD_FOLDER = "config.download.folder";
	private static final String KEY_DB = "config.db";
	private static final String KEY_ORACLE_DB_CONNECTION_DRIVERCLASS = "config.db.connection.oracle.driverclass";
	private static final String KEY_ORACLE_DB_HOST_SUFFIX = "oracle.host";
	private static final String KEY_ORACLE_DB_PORT_SUFFIX = "oracle.port";
	private static final String KEY_ORACLE_DB_SERVICE_SUFFIX = "oracle.service";
	private static final String KEY_ORACLE_DB_USER_SUFFIX = "oracle.user";
	private static final String KEY_ORACLE_DB_PASSWORD_SUFFIX = "oracle.password";
	private static final String KEY_MYSQL_DB_CONNECTION_DRIVERCLASS_SUFFIX = "mysql.mySqlDriver";
	private static final String KEY_MYSQL_DB_HOST_SUFFIX = "mysql.host";
	private static final String KEY_MYSQL_DB_USER_SUFFIX = "mysql.user";
	private static final String KEY_MYSQL_DB_PASSWORD_SUFFIX = "mysql.password";
	private static final String KEY_MYSQL_DB_NAME_SUFFIX = "mysql.dbName";
	private static final String KEY_MYSQL_DB_NAME_SUFFIX_OLDB = "mysql.dbNameOLDB";
	private static final String KEY_PARAMS_WAITSECS_SHORT = "config.params.waitsecs.short";
	private static final String KEY_PARAMS_WAITSECS_MEDIUM = "config.params.waitsecs.medium";
	private static final String KEY_PARAMS_WAITSECS_LONG = "config.params.waitsecs.long";
	private static final String KEY_PARAMS_WAITSECS_MAXTIMEOUT = "config.params.waitsecs.maxtimeout";
	private static final String KEY_REPORT_EMAIL_START_ENABLED = "config.report.email.start.enabled";
	private static final String KEY_REPORT_EMAIL_SUCCESS_ENABLED = "config.report.email.success.enabled";
	private static final String KEY_REPORT_EMAIL_FAILURE_ENABLED = "config.report.email.failure.enabled";
	private static final String KEY_REPORT_EMAIL_HOST = "config.report.email.host";
	private static final String KEY_REPORT_EMAIL_PORT = "config.report.email.port";
	private static final String KEY_REPORT_EMAIL_AUTHENTICATOR_ADDRESS = "config.report.email.authenticator.address";
	private static final String KEY_REPORT_EMAIL_AUTHENTICATOR_PASSWORD = "config.report.email.authenticator.password";
	private static final String KEY_REPORT_EMAIL_AUTHENTICATOR_SSL = "config.report.email.authenticator.ssl";
	private static final String KEY_REPORT_EMAIL_TOLIST = "config.report.email.tolist";
	private static final String KEY_REPORT_EMAIL_FROM_ADDRESS = "config.report.email.from.address";
	private static final String KEY_REPORT_EMAIL_FROM_NAME = "config.report.email.from.name";
	private static final String KEY_REPORT_EMAIL_SUBJECT = "config.report.email.subject";
	private static final String KEY_REPORT_SLACK_START_ENABLED = "config.report.slack.start.enabled";
	private static final String KEY_REPORT_SLACK_SUCCESS_ENABLED = "config.report.slack.success.enabled";
	private static final String KEY_REPORT_SLACK_FAILURE_ENABLED = "config.report.slack.failure.enabled";
	private static final String KEY_REPORT_TEAM_START_ENABLED = "config.report.team.start.enabled";
	private static final String KEY_REPORT_TEAM_SUCCESS_ENABLED = "config.report.team.success.enabled";
	private static final String KEY_REPORT_TEAM_FAILURE_ENABLED = "config.report.team.failure.enabled";
	private static final String KEY_REPORT_USER = "config.report.user";
	private static final String KEY_REPORT_OUTPUT_FOLDER = "config.report.output.folder";
	private static final String ENV_VAR_BROWSER = "Browser";
	private static final String ENV_VAR_SC_ENV = "Test_Environment";
	private static final String ENV_VAR_DB_ENV = "DB_ENV";
	private static final String KEY_TEMP_RESULTS_FOLDER = "config.temporarytestresults.folder";
	private static final String KEY_APP_SCREENS_FOLDER = "config.applicationscreenfolder.folder";

	private static final String KEY_ENV_ENCURL_SUFFIX_PANTHEON = "placeorderurl";
	private static final String KEY_ENV_USERNAME = "username";
	private static final String KEY_ENV_PASSWORD = "password";
	private static final String IS_REPORT_SHAREPOINT_DASHBOARD = "config.report.sharepoint.dashboard";

	static {
		try {
			initialize();
		} catch (Throwable t) {
			throw new IllegalStateException("Can't initialize Config object", t);
		}
	}

	private static void initialize() throws ConfigurationException {

		if (config != null) {
			return;
		}

		File propertiesFile = getPropertiesFile(TEST_CONFIG_PROPERTIES_DIR, TEST_CONFIG_PROPERTIES_FILENAME);
		File overrideFile = getPropertiesFile(TEST_CONFIG_PROPERTIES_DIR, TEST_COFFIG_PROPERTIES_OVERRIDE_FILENAME);

		config = loadConfigFiles(overrideFile, propertiesFile);
	}

	public static boolean isReportToSharePointEnabled() {
		return getBoolean(IS_REPORT_SHAREPOINT_DASHBOARD);
	}

	private static String get(String propertyName) {
		return get(propertyName, true);
	}

	private static String get(String propertyName, boolean required) {

		String value = null;

		try {
			value = config.getString(propertyName);

			if (required && value == null) {
				throw new IllegalArgumentException("No such property found: " + propertyName);
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}

		return value;
	}

	private static Configuration loadConfigFiles(File... files) throws ConfigurationException {

		CompositeConfiguration config = new CompositeConfiguration();

		for (File file : files) {
			PropertiesConfiguration propConfig = new PropertiesConfiguration();
			FileHandler propConfigFileHandler = new FileHandler(propConfig);
			propConfigFileHandler.load(file);

			config.addConfiguration(propConfig);
		}

		return config;
	}

	public static String getLoginPageTitle() {
		return getEnvDependentProperty(KEY_ENV_LOGIN_PAGE_TITLE_SUFFIX);
	}

	public static DownloadedFileFolder getDownloadedFileFolder(DownloadedFileFolderType type) {
		String configString = get(KEY_DOWNLOAD_FOLDER + "." + type.getTypeName() + "." + type.getExt());
		return DownloadedFileFolder.parseDownloadedFileFolderConfig(configString);
	}

	public static String getDownloadFolderPath() {
		return TEST_CONFIG_PROPERTIES_DIR + get(KEY_DOWNLOAD_FOLDER);

		
//		return get(KEY_DOWNLOAD_FOLDER);
	}

//	public static String getScreenshotsFolderPath() {
//		return TEST_CONFIG_PROPERTIES_DIR + get(KEY_FOLDER_FAILED_SCREENSHOTS_RELATIVEPATH);
//	}
	public static String getAppName() {
		return get(KEY_APP_NAME);
	}

	public static String getG2TestBaseFolder() {
		return get(KEY_FOLDER_PITEST_BASE_PATH);
	}

	public static String getExcelFineNameToReadTestData() {
		return get(KEY_EXCEL_FILE_NAME_TO_READ_DATA);
	}

	public static String getExcelFineNameToReadXMLTestData() {
		return get(KEY_EXCEL_FILE_NAME_TO_READ_XML_DATA);
	}


	public static String getPantheonUserName() {
		return getEnvDependentProperty(KEY_ENV_PANTHEON_USERNAME_SUFFIX);
	}


	public static String getSheetName() {
		return getEnvDependentProperty(KEY_ENV_SHEETNAME_SUFFIX);
	}

	public static String getPantheonPassword() {
		return getEnvDependentProperty(KEY_ENV_PANETHEON_PASSWORD_SUFFIX);
	}

	public static String getCalmacAccessPortalLoginPageURL() {
		return getEnvDependentProperty(KEY_ENV_LOGINPAGEURL_SUFFIX);
	}

	public static String getPantheonPageURL() {
		return getEnvDependentProperty(KEY_ENV_PANTHEON_LOGINPAGEURL_SUFFIX);
	}

	public static String getResourcesFolderPath() {
		return TEST_CONFIG_PROPERTIES_DIR + get(KEY_FOLDER_PITEST_RESOURCES_RELATIVEPATH);
	}

	public static String getTestDataFolderPath() {
		return TEST_CONFIG_PROPERTIES_DIR + get(TEST_DATA_FOLDER_RELATIVEPATH);
	}

	public static String getXMLFileToUpload() {
		return TEST_CONFIG_PROPERTIES_DIR + get(KEY_FOLDER_PITEST_RESOURCES_RELATIVEPATH) + "/"
				+ get(KEY_XML_COLLABORATION_CENTER_FILE_UPLOAD);
	}

	public static String getDriversFolderPath() {
		return TEST_CONFIG_PROPERTIES_DIR + get(KEY_FOLDER_PITEST_DRIVERS_RELATIVEPATH);
	}

	public static String getScreenshotsFolderPath() {
		return TEST_CONFIG_PROPERTIES_DIR + get(KEY_FOLDER_FAILED_SCREENSHOTS_RELATIVEPATH);
	}

	public static String getG2TestResourcesFolder() {
		return getG2TestBaseFolder() + "/" + get(KEY_FOLDER_PITEST_RESOURCES_RELATIVEPATH);
	}

	private static boolean getBoolean(String propertyName) {
		return Boolean.parseBoolean(get(propertyName));
	}

	public static String getPrimaryBrowser() {
		String envProperty = System.getenv(ENV_VAR_BROWSER);

		return (envProperty != null && !envProperty.isEmpty()) ? envProperty : get(KEY_BROWSER_PRIMARY);
	}

	public static String getTemporaryResultsFolderPath() {
		return TEST_CONFIG_PROPERTIES_DIR + get(KEY_TEMP_RESULTS_FOLDER);
	}

	public static String getApplicationScreenShortFolderPath() {
		return getTemporaryResultsFolderPath() + get(KEY_APP_SCREENS_FOLDER);
	}

	public static String getProjectPath() {
		return TEST_CONFIG_PROPERTIES_DIR;
	}

	public static String getFailedScreenshotsFolderPath() {
		return TEST_CONFIG_PROPERTIES_DIR + get(KEY_FOLDER_FAILED_SCREENSHOTS_RELATIVEPATH);
	}

	public static String getSecondaryBrowser() {
		return get(KEY_BROWSER_SECONDARY);
	}

	public static String getDB() {
		String envDBProperty = System.getenv(ENV_VAR_DB_ENV);
		return (envDBProperty != null && !envDBProperty.isEmpty()) ? envDBProperty : get(KEY_DB);
	}

	public static String getOracleDbConnectionDriverClass() {
		return get(KEY_ORACLE_DB_CONNECTION_DRIVERCLASS);
	}

	public static String getMySqlDbConnectionDriverClass() {
		return getDBDependentProperty(KEY_MYSQL_DB_CONNECTION_DRIVERCLASS_SUFFIX);
	}

	private static String getDbLowerCase() {
		return getDB().toLowerCase();
	}

	public static String getOracleDbHost() {
		return getDBDependentProperty(KEY_ORACLE_DB_HOST_SUFFIX);
	}

	public static String getMySqlDbHost() {
		return getDBDependentProperty(KEY_MYSQL_DB_HOST_SUFFIX);
	}

	public static String getMySqlDbName() {
		return getDBDependentProperty(KEY_MYSQL_DB_NAME_SUFFIX);
	}

	public static String getMySqlDbNameOLDB() {
		return getDBDependentProperty(KEY_MYSQL_DB_NAME_SUFFIX_OLDB);
	}

	public static String getOracleDbPassword() {
		return getDBDependentProperty(KEY_ORACLE_DB_PASSWORD_SUFFIX);
	}

	public static String getMySqlDbPassword() {
		return getDBDependentProperty(KEY_MYSQL_DB_PASSWORD_SUFFIX);
	}

	public static int getOracleDbPort() {
		return Integer.parseInt(getDBDependentProperty(KEY_ORACLE_DB_PORT_SUFFIX));
	}

	public static String getOracleDbService() {
		return getDBDependentProperty(KEY_ORACLE_DB_SERVICE_SUFFIX);
	}

	public static String getOracleDbUser() {
		return getDBDependentProperty(KEY_ORACLE_DB_USER_SUFFIX);
	}

	public static String getMySqlDbUser() {
		return getDBDependentProperty(KEY_MYSQL_DB_USER_SUFFIX);
	}

	public static String getEnv() {
		String envProperty = System.getenv(ENV_VAR_SC_ENV);

		return (envProperty != null && !envProperty.isEmpty()) ? envProperty : get(KEY_ENV);
	}

	public static boolean getDebugStatus() {
		return getBoolean(KEY_XPATH_DEBUG);
	}

	private static String getEnvLowerCase() {
		return getEnv().toLowerCase();
	}

	public static String getGridURL() {
		return get(KEY_GRIDURL);
	}

	public static String getLoginPageURL() {
		return getEnvDependentProperty(KEY_ENV_LOGINPAGEURL_SUFFIX);
	}

	public static String getLogoutPageURL() {
		return getEnvDependentProperty(KEY_ENV_LOGOUTPAGEURL_SUFFIX);
	}

	private static long getLong(String propertyName) {
		return Long.parseLong(get(propertyName));
	}

	public static String getReportOutputFolder() {
		return get(KEY_REPORT_OUTPUT_FOLDER);
	}

	public static String getReportUser() {
		return get(KEY_REPORT_USER);
	}

	public static String getRunMode() {
		return get(KEY_RUNMODE);
	}

	public static String getSlackChannelWebHookURL() {
		return getEnvDependentProperty(KEY_ENV_SLACK_CHANNEL_WEBHOOKURL_SUFFIX);
	}

	public static String getTeamChannelWebHookURL() {
		return getEnvDependentProperty(KEY_ENV_TEAM_CHANNEL_WEBHOOKURL_SUFFIX);
	}

	private static File getPropertiesFile(String dir, String filename) {
		File configFileDir = new File(dir);
		if (!configFileDir.exists()) {
			throw new IllegalStateException("Cannot find test properties file; parent dir " + dir + " missing");
		}

		File propertiesFile = new File(configFileDir, filename);
		if (!propertiesFile.exists()) {
			throw new IllegalStateException(
					"Cannot find test properties file; file does not exist: " + propertiesFile.getAbsolutePath());
		}

		return propertiesFile;
	}

	public static long getWaitSecsLong() {
		return getLong(KEY_PARAMS_WAITSECS_LONG);
	}

	public static long getWaitSecsMaxTimeout() {
		return getLong(KEY_PARAMS_WAITSECS_MAXTIMEOUT);
	}

	public static long getWaitSecsMedium() {
		return getLong(KEY_PARAMS_WAITSECS_MEDIUM);
	}

	public static long getWaitSecsShort() {
		return getLong(KEY_PARAMS_WAITSECS_SHORT);
	}

	public static boolean isChromeHeadless() {
		return getBoolean(KEY_BROWSER_CHROME_HEADLESS);
	}

	public static boolean isReportSlackStartEnabled() {
		return getBoolean(KEY_REPORT_SLACK_START_ENABLED);
	}

	public static boolean isReportSlackSuccessEnabled() {
		return getBoolean(KEY_REPORT_SLACK_SUCCESS_ENABLED);
	}

	public static boolean isReportSlackFailureEnabled() {
		return getBoolean(KEY_REPORT_SLACK_FAILURE_ENABLED);
	}

	public static boolean isReportTeamStartEnabled() {
		return getBoolean(KEY_REPORT_TEAM_START_ENABLED);
	}

	public static boolean isReportTeamSuccessEnabled() {
		return getBoolean(KEY_REPORT_TEAM_SUCCESS_ENABLED);
	}

	public static boolean isReportTeamFailureEnabled() {
		return getBoolean(KEY_REPORT_TEAM_FAILURE_ENABLED);
	}

	public static boolean isReportEmailStartEnabled() {
		return getBoolean(KEY_REPORT_EMAIL_START_ENABLED);
	}

	public static boolean isReportEmailSuccessEnabled() {
		return getBoolean(KEY_REPORT_EMAIL_SUCCESS_ENABLED);
	}

	public static boolean isReportEmailFailureEnabled() {
		return getBoolean(KEY_REPORT_EMAIL_FAILURE_ENABLED);
	}

	public static String getReportEmailHost() {
		return get(KEY_REPORT_EMAIL_HOST);
	}

	private static int getInt(String propertyName) {
		return Integer.parseInt(get(propertyName));
	}

	public static int getReportEmailPort() {
		return getInt(KEY_REPORT_EMAIL_PORT);
	}

	public static String getReportEmailAuthenticatorAddress() {
		return get(KEY_REPORT_EMAIL_AUTHENTICATOR_ADDRESS);
	}

	public static String getReportEmailAuthenticatorPassword() {
		return get(KEY_REPORT_EMAIL_AUTHENTICATOR_PASSWORD);
	}

	public static boolean isReportEmailAuthenticatorSSL() {
		return getBoolean(KEY_REPORT_EMAIL_AUTHENTICATOR_SSL);
	}

	public static String getReportEmailToList() {
		return get(KEY_REPORT_EMAIL_TOLIST);
	}

	public static String getReportEmailFromAddress() {
		return get(KEY_REPORT_EMAIL_FROM_ADDRESS);
	}

	public static String getReportEmailFromName() {
		return get(KEY_REPORT_EMAIL_FROM_NAME);
	}

	public static String getReportEmailSubject() {
		return get(KEY_REPORT_EMAIL_SUBJECT);
	}

	private static String getEnvDependentProperty(String suffix) {
		return getDependentProperty(KEY_ENV, getEnvLowerCase(), suffix);
	}

	private static String getDBDependentProperty(String suffix) {
		return getDependentProperty(KEY_DB, getDbLowerCase(), suffix);
	}

	private static String getDependentProperty(String prefix, String infix, String suffix) {
		return get(prefix + "." + infix + "." + suffix);
	}

	public static String getAdminUserName() {
		return get(KEY_ADMIN_USER_ACCOUNT);
	}

	public static String getAdminUserEmail() {
		return get(KEY_ADMIN_USER_EMAIL);
	}

	public static String getAdminUserPassword() {
		return get(KEY_ADMIN_USER_ACCOUNT_PASSWORD);
	}


	public static String getPlaceOrderUrlForPantheon() {
		return getEnvDependentProperty(KEY_ENV_ENCURL_SUFFIX_PANTHEON);
	}


	public static String getUsername() {
		return getEnvDependentProperty(KEY_ENV_USERNAME);
	}

	public static String getPassword() {
		return getEnvDependentProperty(KEY_ENV_PASSWORD);
	}

	public static String getPlaceOrderUrlForTitlePort() {
		return getEnvDependentProperty(KEY_ENV_ENCURL_SUFFIX_TITLEPRO);
	}
	public static String getPlaceOrderUrlForTitlePortUSBank() {
		return getEnvDependentProperty(KEY_ENV_ENCURL_SUFFIX_TITLEPORT_US_BANK);
	}
	public static String getCalmacUser() {
		return getEnvDependentProperty(KEY_ENV_USBANK_USERNAME_SUFFIX);
	}

	public static String getCalmacPassword() {
		return getEnvDependentProperty(KEY_ENV_USBANK_PASSWORD_SUFFIX);
	}
}
