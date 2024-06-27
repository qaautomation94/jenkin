package com.mscs.emr.automation.functional;

import static java.lang.Integer.parseInt;

import java.awt.Rectangle;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.google.common.collect.Lists;
import com.mscs.emr.automation.utils.CalmacReporter;
import com.mscs.emr.automation.utils.Config;
import com.mscs.emr.automation.utils.Utils;
import com.mscs.emr.automation.utils.pdf.PDFFontType;
import com.mscs.emr.automation.utils.pdf.PDFHelper;
import com.mscs.emr.automation.utils.pdf.TextVerifyPDFProcessor;
@Listeners(TestListener.class)
public class BaseTestPage {
    public static final int MAX_DOM_ELEMENT_WAIT_SEC = 45;
    public static final int MAX_DOM_ELEMENT_FLUENT_WAIT_SEC = 30;
    private static final int MAX_DOM_POLLING_WAIT_SEC = 2;
    private static final long POLLING_INTERVAL = 200;
    public static String systemTimeZone = null;
    public static String loginPageTitle;
 
	private static final String KEY_ENV_LOGINPAGEURL_SUFFIX = "loginpageurl";
    public static String currentUserId = Config.getAdminUserName();
    public static String currentUserEmail = Config.getAdminUserEmail();
    public static String currentUserPassword = Config.getAdminUserPassword();
    public static String adminUser = Config.getAdminUserName();
    public static String adminUserPassword = Config.getAdminUserPassword();
    public static final String uuid = UUID.randomUUID().toString();
    public static final Logger LOGGER = LogManager.getLogger(BaseTestPage.class);
    private static int MAX_ID_FETCHER_TRIES = 3;
    public static String resourcePath = Config.getResourcesFolderPath();
    public static String testDataFolderPath = Config.getTestDataFolderPath();
    public static String driversPath = Config.getDriversFolderPath();
    public static String getDownloadFolderPath= Config.getDownloadFolderPath();
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
    public static String finalReportOutputFolderName = Config.getReportOutputFolder() + Config.getAppName()
            + " Test_Result(" + Config.getEnv().toUpperCase().replace("ESBIntegration", "") + ")_" + sdf.format(new Date());
    public static String testAccountId = "";
    public static String screenShotsPath = Config.getFailedScreenshotsFolderPath();

    public interface WebElementFetcher {
        WebElement fetchWebElement();
    }

    public static class PageRangeValues {

        private int begin;
        private int end;
        private int total;

        public PageRangeValues() {
        }

        public PageRangeValues(int begin, int end, int total) {
            this.begin = begin;
            this.end = end;
            this.total = total;
        }

        public int getBegin() {
            return begin;
        }

        public void setBegin(int begin) {
            this.begin = begin;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String toString() {
            return "begin=" + begin + ", end=" + end + ", total=" + total;
        }
    }

    public WebElement waitUntilPresenceOfElementByClass(String className) {
        return new FluentWait<>(getDriver()).until(t -> getDriver().findElement(By.className(className)));
    }

    public WebElement waitUntilPresenceOfElementByTag(String tagName) {
        return new FluentWait<>(getDriver()).until(t -> getDriver().findElement(By.tagName(tagName)));
    }

    private void manageReportFolder() {
    	
        String sourceFolderPath = Config.getTestDataFolderPath();
        String destinationFolderPath = finalReportOutputFolderName;

        File theDir = new File(destinationFolderPath);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        File sourceDirectory = new File(sourceFolderPath);
        File destinationDirectory = new File(destinationFolderPath);

        try {
            FileUtils.copyDirectory(sourceDirectory, destinationDirectory);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String reportingLocation() {
        String folderPath = "";

        if (Config.isReportTeamStartEnabled()) {
            folderPath = BaseTestPage.finalReportOutputFolderName;
        } else {
            folderPath = Config.getTemporaryResultsFolderPath();
        }

        return folderPath;
    }

    @AfterSuite
    public void afterSuite() {
        LOGGER.debug("AfterSuite Method");
        afterSuiteHelper();
        if (Config.isReportTeamStartEnabled())
            manageReportFolder();
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        System.setProperty("current.date.time", dateFormat.format(new Date()));
        LOGGER.debug("BeforeSuite method");
        beforeSuiteHelper(context);
        createTemporaryResultsFolder();
    }

    private void createTemporaryResultsFolder() {
        File resultsFolder = new File(Config.getTemporaryResultsFolderPath());
        File screenShotsFolder = new File(Config.getApplicationScreenShortFolderPath());

        if (resultsFolder.exists()) {
            try {
                FileUtils.forceDelete(resultsFolder);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            resultsFolder.mkdir();
        } else {
            resultsFolder.mkdir();
        }

        screenShotsFolder.mkdir();
    }

    // TODO - Update TimeZone Settings
    private void beforeSuiteHelper(ITestContext context) {
        if (Config.getAppName().contains("ATS ")) {

            CalmacReporter.beforeSuite(uuid, Config.getEnv(), Config.getPrimaryBrowser(), context);
            loginPageTitle = Config.getLoginPageTitle();
        }

        //Save current user's timezone to revert to later after test execution
        systemTimeZone = TimeZone.getDefault().getDisplayName();

        //Tests will run in CST, so manually change for the duration of test execution
        if (!systemTimeZone.equals(TimeZoneUtil.CENTRAL_STANDARD_TIME)) {
            /*
             * commenting out to prevent time zone change LOGGER.
             * info("Timezone updated to PST During the Test and will be Reverted back to Original after the Test."
             * ); TimeZoneUtil.changeSystemTimeZone(TimeZoneUtil.CENTRAL_STANDARD_TIME);
             */
        } else {
            LOGGER.info("Timezone already in PST; will keep for duration of tests");
        }
    }

    public void checkAllCheckboxes(String ids) {
        List<WebElement> elements = getDriver().findElements(By.xpath(ids));
        for (int i = 0; i < elements.size(); i++) {
            if (!elements.get(i).isSelected()) {
                click(elements.get(i));
            }
        }
    }

    public void clearTextBoxValueByIds(String... ids) {
        waitUntilElementToBeClickable(ids);
        click(ids);
        getDriver().findElement(By.xpath(createXPath(ids))).clear();
    }

    private void click(WebElement webElement) {
        webElement.click();
    }

    public void clickAllElements(String... ids) {

        waitUntilPresenceOfElement(ids);
        List<WebElement> elements = getDriver().findElements(By.xpath(createXPath(ids)));
        for (int i = 0; i < elements.size(); i++) {
            click(elements.get(i));
        }
    }
    public boolean isDisplayed(String... ids) {
    	waitUntilPresenceOfElement(ids);    
    	return getDriver().findElement(By.xpath(createXPath(ids))).isDisplayed();
    	
    }

    public void clickTab(String... ids) {
        getDriver().findElement(By.xpath(createXPath(ids))).sendKeys(Keys.TAB);
    }

    public void clickByLinkText(String linkText) {
        getDriver().findElement(By.partialLinkText(linkText)).click();
    }

    public void closeWindow() {
        getDriver().close();
    }

    public List<String> createAndSortList(String[] unsortedArray, String order) {
        List<String> sortedList = new ArrayList<>(Arrays.asList(unsortedArray));
        if (order.equalsIgnoreCase("descending")) {
            sortedList.sort(Collections.reverseOrder());
        } else {
            Collections.sort(sortedList);
        }
        return sortedList;
    }

    public List<String> createAndSortListInSmallCase(String[] unsortedArray, String order) {
        List<String> sortedList = new ArrayList<>();
        for (int i = 0; i < unsortedArray.length; i++) {
            sortedList.add(unsortedArray[i].trim().toLowerCase());
        }
        if (order.equalsIgnoreCase("descending")) {
            Collections.sort(sortedList, Collections.reverseOrder());
        } else {
            Collections.sort(sortedList);
        }
        return sortedList;
    }

    public List<String> createAndSortListToIgnoreCase(String[] unsortedArray, String order) {
        List<String> unsortedList = new ArrayList<>(Arrays.asList(unsortedArray));
        return createAndSortListToIgnoreCase(unsortedList, order);
    }

    public List<String> createAndSortListToIgnoreCase(List<String> unsortedList, String order) {
        List<String> sortedList = new ArrayList<>(unsortedList);

        Comparator<String> comparatorToIgnoreCase = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        };

        if (order.equalsIgnoreCase("descending")) {
            sortedList.sort(Collections.reverseOrder(comparatorToIgnoreCase));
        } else {
            Collections.sort(sortedList, comparatorToIgnoreCase);
        }
        return sortedList;
    }

    private static String toLowerCaseAttribute(String attributeName){
        @SuppressWarnings("SpellCheckingInspection")
        final String LAT_CHARS_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String LAT_CHARS_LOWER = LAT_CHARS_UPPER.toLowerCase();

        return String.format("translate(%s, '%s', '%s')",
                attributeName.contains("text") ? attributeName + "()" : "@" + attributeName, LAT_CHARS_UPPER, LAT_CHARS_LOWER);
    }

    public static String getXPathExpressionForGeneratedIdTableCellValue(String appName, String baseID, String columnId, String itemValue, String itemType) {
        final String startsWith = appName.toLowerCase() + (baseID + textToId(itemValue) + textToId(columnId)).toLowerCase();
        final String translatedId = toLowerCaseAttribute("id");
        StringBuilder xPath = new StringBuilder(".//*[")
                .append(String.format("starts-with(%s, '%s')", translatedId, startsWith))
                .append(String.format(" and contains(%s, '%s')", translatedId, textToId(itemValue).toLowerCase()));

        if (itemType != null) {
            xPath.append(String.format(" and contains(%s, '%s')", translatedId, itemType));
        }

        return xPath.append("]").toString();
    }

    public String createXPathWithStartsAndContains(String startsWith, String contains) {
        final String translatedId = toLowerCaseAttribute("id");
        final String appName = DriverManager.getAppName().toLowerCase();

        Function<String, String> convertAttribute = (s) -> {
            s = s.toLowerCase();
            if (s.startsWith(appName)) {
                return appName + s;
            }
            return s;
        };

        StringBuilder xPath = new StringBuilder()
                .append(String.format(".//*[starts-with(%s, %s') and contains(%s, %s')]",
                        translatedId,
                        convertAttribute.apply(startsWith),
                        translatedId,
                        convertAttribute.apply(contains)));

        return xPath.toString();
    }

    public String createXPath(String... ids) {
        if (ids[0].contains("//")) {
            String xPath = ids[0];

            if (Config.getDebugStatus()) {
                LOGGER.info("xpath: " + xPath);
            }

            return xPath;
        }

        StringBuilder xPath = new StringBuilder(String.format("//%s[(", ids[0]));

        if (ids.length == 3) {
            xPath.append(String.format("%s = '%s'", toLowerCaseAttribute(ids[1]), ids[2].toLowerCase()));
        } else if (ids.length > 3) {
            xPath.append(String.format("contains(%s, '%s')", toLowerCaseAttribute(ids[1]), ids[2].toLowerCase()));

            for (int i = 3; i < ids.length; i++) {
                xPath.append(String.format(" and contains(%s, '%s')", toLowerCaseAttribute(ids[1]), ids[i].toLowerCase()));
            }
        }

        xPath.append(") and not(ancestor::div[contains(@style,'display: none')]");
        xPath.append(")]");

        if (Config.getDebugStatus()) {
            LOGGER.info("xpath: " + xPath);
        }

        return xPath.toString();
    }
  
    public void deleteAllPdfFileFromDownloadFolder() {
        String path = "cmd /c start " + resourcePath + "Delete_all_PDF_file.bat";
        Runtime rn = Runtime.getRuntime();
        try {
            Process pr = rn.exec(path);
            pr.getErrorStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mediumWait();
    }

    public void deletePdfAndWorldFiles() {
        String path = "cmd /c start " + resourcePath + "Delete_all_PDF_file.bat";
        Runtime rn = Runtime.getRuntime();
        try {
            Process pr = rn.exec(path);
            pr.getErrorStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void deletePngFilesFromScreenShotFolder() {
        File file = new File(screenShotsPath);
        if (file.exists()) {
            String path = "cmd /c start " + resourcePath + "Delete_all_png_File.bat";
            Runtime rn = Runtime.getRuntime();
            try {
                Process pr = rn.exec(path);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public <DTO> DTO extractData(String id) {
        return null;
    }

    public <DTO> List<DTO> extractDataList(String id) {
        return null;
    }

    public List<WebElement> fetchElementsById(String ids) {// TODO: Refactor, wait until elements exist...
        waitUntilPresenceOfElement(ids);
        return getDriver().findElements(By.xpath(createXPath(ids)));
    }

    public List<WebElement> fetchElementsById(String... ids) {// TODO: Refactor, wait until elements exist...
        waitUntilPresenceOfElement(ids);
        return getDriver().findElements(By.xpath(createXPath(ids)));
    }

    public List<WebElement> fetchElementsAndMoveToLastElement(String... ids) {
        List<WebElement> elementsListBeforeMove = findElements(ids);
        new Actions(getDriver()).moveToElement(elementsListBeforeMove.get(elementsListBeforeMove.size() - 1)).perform();
        return findElements(ids);
    }

    public Rectangle fetchRectangleFromCheckbox(String... ids) {
        WebElement element = fetchElementsById(ids).get(0);
        Dimension dim = element.getSize();
        Point loc = element.getLocation();
        return new Rectangle(loc.getX(), loc.getY(), dim.getWidth(), dim.getHeight());
    }

    public java.awt.Point getCoordinatePoint(String... ids) {
        WebElement element = getDriver().findElement(By.xpath(createXPath(ids)));
        java.awt.Point point = new java.awt.Point(element.getLocation().getX(), element.getLocation().getY());
        return point;
    }

    public List<String> getAllListValues(String... ids) {
        List<String> listValues = new ArrayList<String>();
        waitUntilElementClickable(ids);
        WebElement listElement = getDriver().findElement(By.xpath(createXPath(ids)));
        List<WebElement> options = listElement.findElements(By.tagName("option"));
        for (int i = 0; i < options.size(); i++) {
            listValues.add(options.get(i).getText());
        }
        return listValues;
    }

    public boolean checkIfDuplicateExistInAList(List<String> listValues) {
        Boolean hasDuplicate = Utils.hasDuplicateInAList(listValues);
        return hasDuplicate;
    }

    public String getAttributeValue(String attributeName, String... ids) {
        waitUntilPresenceOfElement(ids);
        return getDriver().findElement(By.xpath(createXPath(ids))).getAttribute(attributeName);
    }

    /**
     * Waits for attribute has expected value
     *
     * @param attributeName - name of required attribute to get
     * @param value         - value to wait
     * @param ids           - composite id of element
     */
    public boolean waitForAttributeValue(String attributeName, String value, String... ids) {
        return waitForAttributeValue(getMaxWaitTimeOut(), attributeName, value, ids);
    }

    /**
     * Waits for attribute has expected value with required timeout
     *
     * @param timeout       - time to wait
     * @param attributeName - name of required attribute to get
     * @param value         - value to wait
     * @param ids           - composite id of element
     */
    public boolean waitForAttributeValue(long timeout, String attributeName, String value, String... ids) {
        try {
            return new WebDriverWait(getDriver(), timeout)
                    .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                    .until(ExpectedConditions.attributeToBe(findElement(ids), attributeName, value));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Waits for attribute dose not have empty value
     *
     * @param attributeName - name of required attribute to get
     * @param ids           - composite id of element
     */
    public boolean waitForAttributeNotEmpty(String attributeName, String... ids) {
        return waitForAttributeNotEmpty(getMaxWaitTimeOut(), attributeName, ids);
    }

    /**
     * Waits for attribute dose not have empty value with required timeout
     *
     * @param timeout       - time to wait
     * @param attributeName - name of required attribute to get
     * @param ids           - composite id of element
     */
    public boolean waitForAttributeNotEmpty(long timeout, String attributeName, String... ids) {
        return new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.attributeToBeNotEmpty(findElement(ids), attributeName));
    }

    public String getDisabledAttributeValue(String attributeName, String... ids) {
        return getDriver().findElement(By.xpath(createXPath(ids))).getAttribute(attributeName);
    }

    public String getCellValueFromWebTable(int rowNumber, int columnNumber, String... ids) {
        waitUntilWebTableLoaded(ids);
        shortWait();
        WebElement table = getDriver().findElement(By.xpath(createXPath(ids)));
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        List<WebElement> tableColumns = tableRows.get(rowNumber).findElements(By.tagName("td"));
        String cellValue = tableColumns.get(columnNumber).findElement(By.xpath(".//*")).getText();
        return cellValue;
    }

    public List<String> getColumnData(String... ids) {
        List<String> columnData = new ArrayList<>();
        List<WebElement> elements = getDriver().findElements(By.xpath(createXPath(ids)));
        int eSize = elements.size();
        long shortWaitTimeSecs = Config.getWaitSecsShort();
        for (int i = 0; i < eSize; i++) {
            for (int j = 0; j < shortWaitTimeSecs; j++) {
                try {
                    if (!elements.get(i).getTagName().equalsIgnoreCase("th")) {
                        columnData.add(elements.get(i).getText());
                    }
                    break;
                } catch (Exception e) {
                    elements = getDriver().findElements(By.xpath(createXPath(ids)));
                }
            }
        }
        return columnData;
    }

    public Integer getCountOfElements(String... ids) {
        waitUntilPresenceOfElement(ids);
        List<WebElement> elements = getDriver().findElements(By.xpath(createXPath(ids)));
        return elements.size();
    }

    public String getCSSValue(String cssAttribute, String... ids) {
        waitUntilElementVisibleBeta(getMaxWaitTimeOut(), ids);
        return findElement(ids).getCssValue(cssAttribute);
    }

    public Integer getElementHeight(String... ids) {
        waitUntilPresenceOfElement(ids);
        return getDriver().findElement(By.xpath(createXPath(ids))).getSize().height;
    }

    public List<WebElement> getElements(String... ids) {
        waitUntilPresenceOfElement(ids);
        return getElementsWithOutWait(ids);
    }
    
    public List<WebElement> getElementsWithOutWait(String... ids) {
        List<WebElement> elements = getDriver().findElements(By.xpath(createXPath(ids)));
        return elements;
    }
    public List<WebElement> getElementsFromParentElement(WebElement element, String... ids) {
        return element.findElements(By.xpath("." + createXPath(ids)));
    }

    public WebElement getElement(String... ids) {
        waitUntilPresenceOfElement(ids);
        return getDriver().findElement(By.xpath(createXPath(ids)));
    }

    public List<WebElement> getListElements(WebElement list) {
        return list.findElements(By.tagName("li"));
    }

    public Integer getElementWidth(String... ids) {
        waitUntilPresenceOfElement(ids);
        return getDriver().findElement(By.xpath(createXPath(ids))).getSize().width;
    }

    public String getLatestDateTime(List<String> dateStrings) throws java.text.ParseException {
        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
        TreeSet<Date> dateSet = new TreeSet<Date>();
        for (String s : dateStrings) {
            dateSet.add(sdf.parse(s));
        }
        Date latestDateTime = dateSet.last();
        return sdf.format(latestDateTime);
    }

    public long getLongWaitTime() {
        return Config.getWaitSecsLong();
    }

    public long getMaxWaitTimeOut() {
        return Config.getWaitSecsMaxTimeout();
    }

    public long getMediumWaitTime() {
        return Config.getWaitSecsMedium();
    }

    public List<WebElement> getOptions(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), MAX_DOM_ELEMENT_WAIT_SEC);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element.findElements(By.tagName("option"));
    }

    public int getSizeOfDropdownList(String id) {
        Select droplist = new Select(getDriver().findElement(By.xpath(createXPath(id))));
        List<WebElement> option = droplist.getOptions();
        int size = option.size();
        return size;
    }

    public WebElement getParent(String... id) {
        WebElement element = waitUntilPresenceOfElement(id);
        return getParent(element);
    }

    public WebElement getParent(WebElement webElement) {
        return webElement.findElement(By.xpath("./.."));
    }

    public WebElement getChild(String... id) {
        WebElement element = waitUntilPresenceOfElement(id);
        return getChild(element);
    }

    public WebElement getChild(WebElement webElement) {
        return webElement.findElement(By.xpath("./*[1]"));
    }

    public List<WebElement> getChildren(WebElement element, String... ids) {
        List<WebElement> foundedElements = null;
        try {
            foundedElements = element.findElements(By.xpath("." + createXPath(ids)));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return foundedElements;
    }

    public WebElement getFirstParentOfTagName(WebElement webElement, String tagName) {
        do {
            webElement = getParent(webElement);
        } while ((webElement != null) && !webElement.getTagName().equalsIgnoreCase(tagName));

        return webElement;
    }

    public String getParentWindowHandle() {
        String parentHandle = getDriver().getWindowHandle();
        return parentHandle;
    }

    public void openNewTabWithURL(String url) {
        //waitUntilPresenceOfElement(".loginPage.userName").sendKeys(Keys.CONTROL +"t");
        ((JavascriptExecutor) getDriver()).executeScript("window.open('" + url + "');");
    }

    public String getSelectedValueFromList(String... ids) {
        //added to avoid stale exception
        justWait(2);
        Select droplist = new Select(getDriver().findElement(By.xpath(createXPath(ids))));
        WebElement option = droplist.getFirstSelectedOption();
        String valueSelected = option.getText();
        return valueSelected;
    }

    public long getShortWaitTime() {
        return Config.getWaitSecsShort();
    }

    public String getText(String... ids) {
        WebElement element = waitUntilPresenceOfElement(ids);
        return element.getText();
    }

    public List<String> getTexts(String... ids) {
        waitUntilElementVisibleBeta(ids);
        List<WebElement> elements = getElements(ids);
        List<String> textList = elements.stream().map(webElement -> {
            scrollElementIntoView(webElement);
            return webElement.getText();
        }).collect(Collectors.toList());
        return textList;
    }

    public String getDisabledText(String... ids) {
        WebElement element = waitUntilPresenceOfElement(ids);
        return element.getText();
    }

    public String getTitleOfPage() {
        return getDriver().getTitle();
    }

    /**
     * @deprecated {Please use @link #getValuesFromDropDownBeta(String...) or #getValuesFromDropdownBeta(long, String...)}
     */
    @Deprecated
    public String[] getValuesFromDropDown(String... ids) {
        Select droplist = new Select(getDriver().findElement(By.xpath(createXPath(ids))));
        List<WebElement> option = droplist.getOptions();
        String[] valuesInDropDown = new String[option.size()];
        for (int i = 0; i <= option.size() - 1; i++) {
            valuesInDropDown[i] = option.get(i).getText();
        }
        return valuesInDropDown;
    }

    public boolean validateValueInDropDown(String value, String... ids) {
        Select droplist = new Select(getDriver().findElement(By.xpath(createXPath(ids))));
        List<WebElement> option = droplist.getOptions();
        String[] valuesInDropDown = new String[option.size()];
        boolean present = false;
        for (int i = 0; i <= option.size() - 1; i++) {
            valuesInDropDown[i] = option.get(i).getText();
            if (valuesInDropDown[i].equalsIgnoreCase(value)) {
                present = true;
                break;
            }
        }
        return present;
    }

    public int getWebTableColumnCount(String... ids) {
        waitUntilWebTableLoaded(ids);
        WebElement table = getDriver().findElement(By.xpath(createXPath(ids)));
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        for (int i = 0; i < tableRows.size(); i++) {
            List<WebElement> tableColumns = tableRows.get(i).findElements(By.tagName("td"));
            if (tableColumns.size() > 0) {
                return tableColumns.size();
            }
        }
        return 0;
    }

    public String[] getWebTableColumnData(int columnNumber, String... ids) {
        String[] columnData = null;
        waitUntilWebTableLoaded(ids);
        shortWait();
        WebElement table = getDriver().findElement(By.xpath(createXPath(ids)));
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        if (tableRows.size() > 0) {
            columnData = new String[tableRows.size()];
            for (int i = 0; i < tableRows.size(); i++) {
                List<WebElement> tableColumns = tableRows.get(i).findElements(By.tagName("td"));
                if (tableColumns.size() > 0) {
                    String text = tableColumns.get(columnNumber).findElement(By.xpath(".//*")).getText();
                    columnData[i] = text;
                }
            }
            return columnData;
        } else {
            return columnData;
        }
    }

    public String[] getDataGridColumnData(int columnNumber, String... ids) {
        String[] columnData = null;
        waitUntilWebTableLoaded(ids);
        shortWait();
        WebElement div = getDriver().findElement(By.xpath(createXPath(ids)));
        WebElement tbody = div.findElement(By.xpath(".//tbody"));
        List<WebElement> tableRows = tbody.findElements(By.tagName("tr"));
        if (tableRows.size() > 0) {
            columnData = new String[tableRows.size()];
            for (int i = 0; i < tableRows.size(); i++) {
                List<WebElement> tableColumns = tableRows.get(i).findElements(By.tagName("td"));
                if (tableColumns.size() > 0) {
                    String text = tableColumns.get(columnNumber).findElement(By.xpath(".//*")).getText();
                    columnData[i] = text;
                }
            }
            return columnData;
        } else {
            return columnData;
        }
    }

    public String[][] getWebTableData(String... ids) {
        waitUntilWebTableLoaded(ids);
        shortWait();
        WebElement table = getDriver().findElement(By.xpath(createXPath(ids)));
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        List<WebElement> tableColumns = tableRows.get(tableRows.size() - 2).findElements(By.tagName("td"));
        String[][] webTableData = new String[tableRows.size()][tableColumns.size()];
        for (int i = 0; i < tableRows.size(); i++) {
            List<WebElement> tableColumns1 = tableRows.get(i).findElements(By.tagName("td"));
            for (int j = 0; j < tableColumns.size(); j++) {
                if (tableColumns1.size() > 0) {
                    String text = tableColumns1.get(j).findElement(By.xpath(".//*")).getText();
                    webTableData[i][j] = text;
                } else {
                    break;
                }
            }
        }
        return webTableData;
    }

    public int getWebTableRowCount(String... ids) {
        waitUntilWebTableLoaded(ids);
        WebElement table = getDriver().findElement(By.xpath(createXPath(ids)));
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        return tableRows.size();
    }


    public static String currentdate = null;

    public void setCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        //get current date time with Date()
        Date date = new Date();

        // Now format the date
        this.currentdate = dateFormat.format(date);
    }


    public int getWidth(String... ids) {
        WebElement webElement = findElement(ids);
        if (webElement == null) {
            return 0;
        } else {
            return webElement.getSize().getWidth();
        }
    }

    public void waitForElementWidthIsEqualOrMoreThan(int width, String... ids) {
        waitForElementWidthIsEqualOrMoreThan(getMaxWaitTimeOut(), width, ids);
    }

    public void waitForElementWidthIsEqualOrMoreThan(long timeout, int width, String... ids) {
        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        WebElement element = findElement(ids);
                        return element.getSize().getWidth() >= width;
                    }

                    @Override
                    public String toString() {
                        return String.format("element (%s) width to be equal or more than '%d'", createXPath(ids), width);
                    }
                });
    }

    public void hoverOver(String... ids) {
        hoverOver(waitUntilPresenceOfElement(ids));
    }

    public WebElement hoverOver(WebElement webElement) {
        Actions builder = new Actions(getDriver());
        Actions hoverOverRegistrar = builder.moveToElement(webElement);
        hoverOverRegistrar.perform();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return webElement;
    }

    /**
     * @deprecated please use {@link #isCheckedBeta(String...) or #isCheckedBeta(long, String...)}
     */
    @Deprecated
    public boolean isCheckboxChecked(String... ids) {
        waitUntilPresenceOfElement(ids);
        if (isElementExists(ids)) {
            return getDriver().findElement(By.xpath(createXPath(ids))).isSelected();
        } else {
            return false;
        }
    }

    /**
     * @deprecated please use {@link #isCheckedBeta(String...) or #isCheckedBeta(long, String...)}
     */
    @Deprecated
    public boolean isCheckboxChecked(WebElement element) {
        return element.isSelected();
    }

    public boolean isElementClickable(long maxTimeOut, String... ids) {
        WebDriverWait wait = new WebDriverWait(getDriver(), maxTimeOut);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(createXPath(ids))));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementClickable(String... ids) {
        WebDriverWait wait = new WebDriverWait(getDriver(), MAX_DOM_ELEMENT_WAIT_SEC);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(createXPath(ids))));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementEnabled(String... ids) {
        return isElementEnabled(true, ids);
    }

    public boolean isElementEnabled(boolean enabled, String... ids) {
        waitUntilPresenceOfElement(ids);
        WebElement element = getDriver().findElement(By.xpath(createXPath(ids)));

        boolean isEnabled = element.isEnabled()
                && !StringUtils.containsIgnoreCase(element.getAttribute(CalmacTestValues.ATTRIBUTE_CLASS), CalmacTestValues.VALUE_DISABLED_ANCHOR)
                && !StringUtils.containsIgnoreCase(element.getAttribute(CalmacTestValues.ATTRIBUTE_ARIA), CalmacTestValues.VALUE_TRUE);

        return enabled == isEnabled;
    }

    /**
     * @deprecated {Please use @link #isElementVisibleBeforeWait(String... ids)}
     */
    @Deprecated
    public boolean isElementVisible(String... ids) {
        int width = 0;
        int height = 0;
        List<WebElement> elements = getDriver().findElements(By.xpath(createXPath(ids)));
        if (!elements.isEmpty()) {
            for (int i = 0; i < MAX_DOM_ELEMENT_WAIT_SEC; i++) { // To handle stale element exception
                try {
                    width = elements.get(0).getSize().width;
                    height = elements.get(0).getSize().height;
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return width > 0 && height > 0;
    }

    public boolean isEnabled(String... ids) {
        waitUntilPresenceOfElement(ids);
        return getDriver().findElement(By.xpath(createXPath(ids))).isEnabled();
    }

    public boolean isRadioButtonSelected(String... ids) {
        waitUntilPresenceOfElement(ids);
        if (isElementExists(ids)) {
            return getDriver().findElement(By.xpath(createXPath(ids))).isSelected();
        } else {
            return false;
        }
    }

    public void javaScriptClick(String... ids) {
        JavascriptExecutor js;
        js = (JavascriptExecutor) getDriver();
        WebElement element = getDriver().findElement(By.xpath(createXPath(ids)));
        js.executeScript("arguments[0].click();", element);
    }

    public void javaScriptScrollToView(String... ids) {
        JavascriptExecutor js;
        js = (JavascriptExecutor) getDriver();
        WebElement element = getDriver().findElement(By.xpath(createXPath(ids)));
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public BaseTestPage justWait(long secs) {
        try {
            //LOGGER.info("Waiting " + secs + " Seconds for App Refresh");
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //LOGGER.info("Finished waiting " + secs + " Seconds");
        return this;
    }

    public void longWait() {
        long secs = Config.getWaitSecsLong();
        try {
            LOGGER.info("Waiting " + secs + " Seconds for App Refresh");
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("Finished waiting " + secs + " Seconds");
    }

    public void mediumWait() {
        long secs = Config.getWaitSecsMedium();
        try {
            LOGGER.info("Waiting " + secs + " Seconds for App Refresh");
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("Finished waiting " + secs + " Seconds");
    }

    public void pressDelete(String... ids) {
        waitUntilElementToBeClickable(ids).sendKeys(Keys.CONTROL + "a");
        waitUntilElementToBeClickable(ids).sendKeys(Keys.DELETE );
        waitUntilElementToBeClickable(ids).sendKeys(Keys.BACK_SPACE);
    }

    public void pressDownKey(String ids) {
        waitUntilElementToBeClickable(ids).sendKeys(Keys.DOWN);
    }

    public void pressBackSpace(String... ids) {
        waitUntilElementToBeClickable(ids).sendKeys(Keys.BACK_SPACE);
    }

    public void pressEnter(String... ids) {
        waitUntilElementToBeClickable(ids).sendKeys(Keys.ENTER);
    }

    public void pressEscape(String... ids) {
        waitUntilElementToBeClickable(ids).sendKeys(Keys.ESCAPE);
    }

    public void pressReturn(String... ids) {
        waitUntilElementToBeClickable(ids).sendKeys(Keys.RETURN);
    }

    public void pressUpKey(String ids) {
        waitUntilElementToBeClickable(ids).sendKeys(Keys.UP);
    }

    public void pressControlAndEnterKey(String ids) { waitUntilElementToBeClickable(ids).sendKeys(Keys.CONTROL,Keys.ENTER); }

    public void pressTab(String... ids) {
        waitUntilElementToBeClickable(ids).sendKeys(Keys.TAB);
    }

    public boolean selectElementInList(String value, String... ids) {
        WebElement droplist = getDriver().findElement(By.xpath(createXPath(ids)));
        List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
        LOGGER.info(droplist_cotents.size() + "is drop down value count");

        for (int i = 0; i <= droplist_cotents.size(); i++) {
            String strOption = droplist_cotents.get(i).getText().trim().toLowerCase();
            LOGGER.info("Actual ===>" + strOption);
            LOGGER.info("Expected ===>" + value);
            if (strOption.contains(value.toLowerCase())) {
                // if (strOption.startsWith(data.toLowerCase())){
                LOGGER.info(strOption + ":" + value);
                droplist_cotents.get(i).click();
                break;
            }

            if (i == droplist_cotents.size()) {
                return false;
            }
        }
        return true;
    }

    public void selectListBoxByIndex(String webElementId, int index) {
        selectListBoxByIndexMultipleIds(index, webElementId);
    }

    public void selectListBoxByIndexMultipleIds(int index, String... webElementIds) {
        waitUntilElementToBeClickable(webElementIds);

        WebElement selectElement = hoverOver(waitUntilPresenceOfElement(webElementIds));
        if (selectElement != null) {
            int count = 0;
            for (WebElement optionElement : getOptions(selectElement)) {
                if (count == index) {
                    optionElement.click();
                    break;
                }
                count++;
            }
        }
    }

    public boolean validateListBoxValueExists(String value, String id) {
        waitUntilElementToBeClickable(id);

        WebElement selectElement = hoverOver(waitUntilPresenceOfElement(id));

        if (selectElement != null) {
            for (WebElement optionElement : getOptions(selectElement)) {
                if (optionElement.getText() != null && optionElement.getText().equalsIgnoreCase(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isListBoxValuesContainsValue(String value, String... ids) {
        WebDriverWait wait = new WebDriverWait(getDriver(), getMaxWaitTimeOut());
        WebElement selectElement = wait.ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.elementToBeClickable(findElement(ids)));
        List<WebElement> selectOptions = new Select(selectElement).getOptions();
        return selectOptions.stream().anyMatch(option -> option.getText().equalsIgnoreCase(value));
    }

    public void setTextBoxValueAndPressEnter(String value, String... ids) {
        setValue(value, ids);
        pressEnter(ids);
    }

    /*
    *
    *
    * @Author
     */
    public void setInnerTextValueByIds(String value, String... ids) {
        WebElement element = getDriver().findElement(By.xpath(createXPath(ids)));
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].value='"+ value +"';", element);
    }

    public void shortWait() {
        long secs = Config.getWaitSecsShort();
        try {
            LOGGER.info("Waiting " + secs + " Seconds for App Refresh");
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private WebElement smartWait(WebDriver driver, String... ids) {
        // wait until glass panel goes away
        // wait for loading icon to disappear
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(MAX_DOM_ELEMENT_FLUENT_WAIT_SEC))
                .pollingEvery(Duration.ofSeconds(MAX_DOM_POLLING_WAIT_SEC))
                .ignoring(NoSuchElementException.class);

        WebElement element = wait.until(driver1 -> driver1.findElement(By.xpath(createXPath(ids))));
        return element;
    }

    public List<String> SortList(List<String> listOfString, String order) {
        if (order.equalsIgnoreCase("descending")) {
            Collections.sort(listOfString, Collections.reverseOrder());
        } else {
            Collections.sort(listOfString);
        }
        return listOfString;
    }

    public void switchToAlertsAndValidatingAlertMessage(String alertExpectedMessage) {
        Alert alert = getDriver().switchTo().alert();
        String alertActualMessage = alert.getText();
        Assert.assertEquals(alertActualMessage, alertExpectedMessage, "Alert Message " + alertExpectedMessage + " is not matched");
        alert.accept();
    }

    public void switchToAlertsAndAcceptAlert() {
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
        getDriver().switchTo().parentFrame();
    }

    public void switchToChildWindow(String parentWindow) {
        Set<String> handles = getDriver().getWindowHandles();
        for (String handle : handles) {
            if (handle.equals(parentWindow)) {
                getDriver().switchTo().window(handle);
                waitForPageLoaded();
                System.out.println(getDriver().getTitle());
                break;
            }
        }
    }

    public void switchToRequiredTab(String requiredTabTitle) {
        //waitForPageLoaded();
        Set<String> handles = getDriver().getWindowHandles();
        for (String handle : handles) {
            getDriver().switchTo().window(handle);
            if(getDriver().getTitle().contains(requiredTabTitle)) {
                waitForPageLoaded();
                break;
            }
        }
    }

    public void switchToChildWindowWithoutWait(String parentWindow) {
        Set<String> handles = getDriver().getWindowHandles();

        for (String handle : handles) {
            if (!handle.equals(parentWindow)) {
                getDriver().switchTo().window(handle);
                break;
            }
        }
    }

    public void switchToChildWindowByIndex(int i) {
        ArrayList<String> tabs = new ArrayList<String>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(i));
    }

    public boolean isExistChildWindow(String parentWindow) {
        try {
            new WebDriverWait(getDriver(), getMediumWaitTime()).until(webDriver -> {
                Set<String> handles = getDriver().getWindowHandles();
                for (String handle : handles) {
                    if (!handle.equals(parentWindow)) {
                        return true;
                    }
                }
                return false;
            });
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public void switchToDefaultFromFrame() {
        getDriver().switchTo().defaultContent();
    }

    public void switchToFrame(String... ids) {
        int size = getDriver().findElements(By.xpath(createXPath(ids))).size();
        WebElement element = getDriver().findElements(By.xpath(createXPath(ids))).get(size - 1);
        if (element.isDisplayed()) {
            getDriver().switchTo().frame(element);
        }
    }

    public void switchToParentWindow(String parentWindow) {
        getDriver().switchTo().window(parentWindow);
    }

    public boolean validateAttribute(String attributeValueToValidate, String attributeName, String... ids) {
        waitUntilElementVisible(ids);
        String actualText = getAttributeValue(attributeName, ids);
        return attributeValueToValidate.equals(actualText);
    }

    public boolean validateDisabledAttribute(String attributeValueToValidate, String attributeName, String... ids) {
        String actualText = getDisabledAttributeValue(attributeName, ids);
        return attributeValueToValidate.equals(actualText);
    }

    public boolean validateAttributeContainsValue(String valueToValidate, String attributeName, String... ids) {
        waitUntilElementVisibleBeta(ids);
        String actualAttributeValue = getAttributeValue(attributeName, ids);
        return actualAttributeValue.contains(valueToValidate);
    }

    public boolean validatePartialText(String textToBeValidated, String... ids) {
        waitUntilElementVisibleBeta(ids);
        String actualText = getText(ids).toLowerCase();
        return actualText.contains(textToBeValidated.toLowerCase());
    }

    public void validatePDF(File file, String... textToValidate) {
        Assert.assertTrue(textToValidate != null && textToValidate.length > 0 && file != null,
                "File or text to validate is null: File='" + file + "' Text To Validate='" + textToValidate + "'");
        String pdfText = getPDFText(file).toUpperCase();
        for (String txt : textToValidate) {
            Assert.assertTrue(pdfText != null && pdfText.contains(txt.toUpperCase()),
                    "PDF does not contain the expected value: " + txt);
        }
    }

    public String getPDFText(File file) {
        String pdfText = "";
        final String REGION = "REGION";
        try {
            PDDocument document = PDDocument.load(file);
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            Rectangle rect = new Rectangle(0, 0, 500, 500); //coordinates of region
            stripper.addRegion(REGION, rect);
            PDPageTree allPages = document.getDocumentCatalog().getPages();
            PDPage firstPage = allPages.get(0);
            stripper.extractRegions(firstPage);
            pdfText = stripper.getTextForRegion(REGION);
        } catch (IOException e) {
            Assert.fail();
            e.printStackTrace();
        }
        return pdfText;
    }

    public boolean validateTextCheckMulti(String textToBeValidated, String... ids) {

        WebElement element = waitUntilPresenceOfElement(ids);
        if (element == null) {
            return false;
        }

        List<WebElement> elements = getElements(ids);

        if (elements != null) {

            for (WebElement el : elements) {

                if (safeEquals(el.getText(), textToBeValidated, false, true)
                        || safeEquals(el.getAttribute("title"), textToBeValidated, false, true)
                        || safeEquals(el.getAttribute("value"), textToBeValidated, false, true)) {

                    return true;
                }

            }
        }

        return false;
    }

    public boolean validateText(String textTobeValidated, String... ids) {
        return safeEquals(getText(ids), textTobeValidated, false, true)
                || safeEquals(getAttributeValue("title", ids), textTobeValidated, false, true)
                || safeEquals(getAttributeByValue("value", ids), textTobeValidated, false, true);
    }

    public boolean validateDisabledText(String textTobeValidated, String... ids) {
        String actualText = getDisabledText(ids).toLowerCase();
        textTobeValidated = textTobeValidated.toLowerCase();
        return textTobeValidated.equals(actualText);
    }

    public boolean validateTextOnlyAndLength(String textTobeValidated, String... ids) {
        String actualText = getTextBeta(ids);
        return textTobeValidated.equalsIgnoreCase(actualText);
    }

    public void waitForPopupPanelGlassInvisible() {
        waitUntilElementInvisible(Config.getWaitSecsMaxTimeout(), "//div[@class='gwt-PopupPanelGlass']");
    }

    // Loading spinner image, usually is displayed on AddEditAppointmentPage when manipulate with search inputs
    public void waitForLoadingSpinnerInvisible() {
        waitUntilElementInvisible(Config.getWaitSecsMaxTimeout(), "//*[contains(@class, 'gwt-Image loadingMessage')]/..");
    }

    public void waitForLoadingRulesPopupInvisible() {
        waitUntilElementInvisible(Config.getWaitSecsMaxTimeout(), "//*[@class='statusPopupPanelText']");
    }

    public void waitForPopupPanelGlassVisible() {
        waitUntilElementVisibleBeta(Config.getWaitSecsMaxTimeout(), "//div[@class='gwt-PopupPanelGlass']");
    }

    /**
     * @deprecated please use {@link #click(long, String...)} / {@link #click(String...)}
     */
    public void waitUntilAnimationInvisibleThenClick(String... ids) {
        int counter = 0;
        while (counter <= MAX_DOM_ELEMENT_WAIT_SEC) {
            try {
                click(ids);
                break;
            } catch (WebDriverException exception) {
                if (exception.getMessage().contains("unknown error:")) {
                    justWait(1);
                } else {
                    throw exception;
                }
            }
            counter++;
        }
    }

    public void waitUntilElementClickable(long maxTimeOutInSeconds, String... ids) {
        WebDriverWait wait = new WebDriverWait(getDriver(), maxTimeOutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(createXPath(ids))));
    }

    /**
     * @deprecated please use {@link #waitUntilElementToBeClickable(String...)}
     */
    @Deprecated
    public void waitUntilElementClickable(String... ids) {
        WebDriverWait wait = new WebDriverWait(getDriver(), MAX_DOM_ELEMENT_WAIT_SEC);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(createXPath(ids))));
    }

    public void waitUntilElementDisabled(String... ids) {
        int counter = 0;
        WebElement element = getDriver().findElement(By.xpath(createXPath(ids)));
        try {
            while (element.isEnabled() && counter <= MAX_DOM_ELEMENT_WAIT_SEC) {
                justWait(2);
                counter++;
            }

            if (element.isEnabled() && counter > MAX_DOM_ELEMENT_WAIT_SEC) {
                throw new Exception("Element is still enabled");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitUntilElementEnabled(String... ids) {
        int counter = 0;
        WebElement element = getDriver().findElement(By.xpath(createXPath(ids)));
        try {
            while (!element.isEnabled() && counter <= MAX_DOM_ELEMENT_WAIT_SEC) {
                justWait(2);
                counter++;
            }
            if (!element.isEnabled() && counter > MAX_DOM_ELEMENT_WAIT_SEC) {
                throw new Exception("Element is still disabled");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean waitUntilElementInvisible(long maxTimeOutInSeconds, String... ids) {
        WebDriverWait wait = new WebDriverWait(getDriver(), maxTimeOutInSeconds, POLLING_INTERVAL);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(createXPath(ids))));
    }

    public boolean waitUntilElementInvisible(String... ids) {
        return waitUntilElementInvisible(getMaxWaitTimeOut(), ids);
    }

    /**
     * Avoid using directly in derived classes
     *
     * @param ids
     * @return
     */
    public WebElement waitUntilElementToBeClickable(String... ids) {
        WebDriverWait wait = new WebDriverWait(getDriver(), MAX_DOM_ELEMENT_WAIT_SEC);
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(createXPath(ids))));
    }

    /**
     * Wait until the visible documents number in list and number items of the current page will be the same
     *
     * @param expectedNumber
     * @param ids
     * @return
     */
    public boolean waitUntilPagingChanged(int expectedNumber, String ids) {
        return new WebDriverWait(getDriver(), getShortWaitTime())
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        int actualNumber = BaseTestPage.this.getPageRangeValues(ids).getEnd() - BaseTestPage.this.getPageRangeValues(
                                ids).getBegin() + 1;
                        return expectedNumber == actualNumber;
                    }

                    @Override
                    public String toString() {
                        return String.format("pagination to be changed to '%d' for element (%s)", expectedNumber, createXPath(ids));
                    }
                });
    }

    /**
     * Wait until the all items number and parameter will be the same
     *
     * @param expectedNumber
     * @param ids
     * @return
     */
    public boolean waitUntilPagingAllNumberChanged(int expectedNumber, String ids) {
        return new WebDriverWait(getDriver(), getShortWaitTime())
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        int actualNumber = BaseTestPage.this.getPageRangeValues(ids).getTotal();
                        return expectedNumber == actualNumber;
                    }

                    @Override
                    public String toString() {
                        return String.format("pagination to be changed to '%d' for element (%s)", expectedNumber, createXPath(ids));
                    }
                });
    }

    /**
     * @deprecated please use {@link #waitUntilElementVisibleBeta(String...) or  #waitUntilElementVisibleBeta(long, String...)}
     */
    @Deprecated
    public void waitUntilElementVisible(long maxTiemOutInSeconds, String... ids) {
        WebDriverWait wait = new WebDriverWait(getDriver(), maxTiemOutInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(createXPath(ids))));
    }

    /**
     * @deprecated please use {@link #waitUntilElementVisibleBeta(String...) or  #waitUntilElementVisibleBeta(long, String...)}
     */
    @Deprecated
    public void waitUntilElementVisible(String... ids) {
        WebDriverWait wait = new WebDriverWait(getDriver(), MAX_DOM_ELEMENT_WAIT_SEC);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(createXPath(ids))));
    }

    public void waitUntilListLoaded(String... ids) {
        int counter = 0;
        WebElement element = getDriver().findElement(By.xpath(createXPath(ids)));
        Select listElement = new Select(element);
        List<WebElement> listOptions = listElement.getOptions();
        try {
            while (listOptions.size() < 1 && counter <= MAX_DOM_ELEMENT_WAIT_SEC) {
                justWait(2);
                counter++;
            }
            if (listOptions.size() < 1 && counter > MAX_DOM_ELEMENT_WAIT_SEC) {
                throw new Exception("List is not loaded");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitUntilLoadingPopupContentInvisible(long maxTimeoutInSeconds) {
        waitUntilElementInvisible(maxTimeoutInSeconds, "//div[@class='popupContent']//img[@class='gwt-Image']");
    }

    public void waitUntilLoadingPopupInvisible(long maxTimeoutInSeconds) {
        waitUntilElementInvisible(maxTimeoutInSeconds, "//div[contains(@class,'LoadingPopup')]//img[@class='gwt-Image']");
    }

    public void waitUntilPopupPanelInvisible(long maxTimeoutInSeconds) {
        waitUntilElementInvisible(maxTimeoutInSeconds, ".//div[contains(@class,'PopupPanelGlass')]");
    }

    public WebElement waitUntilPresenceOfElement(String... ids) {
        // WebDriverWait wait = new WebDriverWait(driver,
        // MAX_DOM_ELEMENT_WAIT_SEC);
        return smartWait(getDriver(), ids);
    }

    /**
     * Avoid using directly in derived classes
     *
     * @param id
     * @return
     */
    public WebElement waitUntilPresenceOfElement1(String id) {
        id = DriverManager.getAppName() + id;

        getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        WebElement we = null;
        int tries = 0;
        while (tries < 10) {
            try {
                we = getDriver().findElement(By.xpath("//*[contains(@id, '" + id + "')]"));
                if (we != null && we.isDisplayed() && we.isEnabled() && we instanceof RemoteWebElement) {
                    break;
                }
            } catch (StaleElementReferenceException | ElementNotVisibleException | NoSuchElementException ex) {
            } finally { // use finally block to increment count regardless of
                // error
                tries += 1;
            }
        }
        return we;
    }

    public void waitUntilUpdatingElementInvisible(long maxTimeoutInSeconds) {
        waitUntilElementInvisible(maxTimeoutInSeconds, ".//span[@class='updateTheClassName]");
    }

    public void waitUntilWebTableLoaded(String... ids) {
        int counter = 0;
        boolean flag = false;
        List<WebElement> tableRows = null;
        WebElement table = null;
        for (int k = 0; k < MAX_DOM_ELEMENT_WAIT_SEC; k++) {
            try {
                table = getDriver().findElement(By.xpath(createXPath(ids)));
                tableRows = table.findElements(By.tagName("tr"));
                //tableColumns = tableRows.get(0).findElements(By.tagName("td"));
                flag = true;

                while (counter++ <= MAX_DOM_ELEMENT_WAIT_SEC && tableRows.size() < 1) {
                    tableRows = table.findElements(By.tagName("tr"));
                    shortWait();
                }
                if (counter == MAX_DOM_ELEMENT_WAIT_SEC && tableRows.size() < 1) {
                    throw new NoSuchElementException("web Table Not Loaded");
                }
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();

            }
            if (flag) {
                break;
            }
        }
    }

    public void changeSystemTimeZone(String timeZone) {
        //Change system timezone back to user's original
        LOGGER.info("Timezone updated to Actual: " + timeZone);
        TimeZoneUtil.changeSystemTimeZone(timeZone);
        mediumWait();
    }

    private void afterSuiteHelper() {
        //Change system timezone back to user's original
        LOGGER.info("Timezone updated to Actual: " + systemTimeZone);
       // TimeZoneUtil.changeSystemTimeZone(systemTimeZone);
        mediumWait();

        //Delete Downloaded PDF files at the End of Execution:
        //Not Needed for PI Project
        //deletePdfAndWorldFiles();
    }

    //Will be used to Navigate to Tableau Site
    /*public void switchToCvpIntegrationLoginPage() {
        getDriver().get(Config.getCvpIntegrationUrl());
    }*/

    public void switchToPracticeInsightsLoginPage() {
        getDriver().get(Config.getLoginPageURL());
    }

    public void scrollElementIntoView(WebElement element) {
        String id = element.getAttribute("id");

        try {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Throwable t) {
            Assert.fail("Could not scroll element into view: id=" + id);
        }
    }

    public void scrollElementIntoViewAndVerifyExistsAndIsVisible(String id) {
        String idWithAppPrefix = getIdWithAppPrefix(id);
        String idWithoutAppPrefix = getIdWithoutAppPrefix(id);

        WebElement element = null;

        try {
            element = getDriver().findElement(By.id(idWithAppPrefix));
        } catch (NoSuchElementException e) {
            Assert.fail("No such element: id=" + idWithAppPrefix);
        }

        Assert.assertNotNull(element, "No such element: id=" + idWithAppPrefix);

        try {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Throwable t) {
            Assert.fail("Could not scroll element into view: id=" + idWithAppPrefix);
        }
        try {
            element = getDriver().findElement(By.id(idWithAppPrefix));
        } catch (NoSuchElementException e) {
            Assert.fail("No such element: id=" + idWithAppPrefix);
        }
        Assert.assertTrue(element.isDisplayed());
        Assert.assertTrue(isElementVisible(idWithoutAppPrefix));
    }

    public WebElement findNthGeneratedIdTableCell(String baseId, String columnId, int n) {
        List<WebElement> elements = getElements(baseId, columnId + n);
        Assert.assertTrue((elements != null) && (elements.size() == 1), "Found 0 or >1 elements for ids=" + baseId + ", " + columnId + n);
        return elements.get(0);
    }

    public WebElement findGeneratedIdTableCell(String baseID, String columnId, String itemValue) {
        return findGeneratedIdTableCell(baseID, columnId, itemValue, null);
    }

    /**
     * Find WebElement from partial match when item lives in a DataGrid and we don't know the row number.
     *
     * @param baseID
     * @param columnId
     * @param itemValue
     * @param itemType
     * @return
     */
    public WebElement findGeneratedIdTableCell(String baseID, String columnId, String itemValue, String itemType) {
        String appName = DriverManager.getAppName().toLowerCase();

        String xPath = getXPathExpressionForGeneratedIdTableCellValue(appName, baseID, columnId, itemValue, itemType);
        List<WebElement> elements = getDriver().findElements(By.xpath(xPath));

        if ((elements == null) || elements.isEmpty()) {
            return null;
        } else if (elements.size() > 1) {
            throw new IllegalStateException("Found more than one match for expression=" + xPath + "; values=" + getWebElementsSummary(elements));
        }
        return elements.get(0);
    }

    public static String textToId(String text) {
        if (text != null && !text.equals("")) {
            return "." + text.replaceAll("[^A-Za-z0-9]", "");
        } else {
            return "";
        }
    }


    /**
     * Get 1st and last item on page, and total items.
     * If can't find pager page range, return null;
     *
     * @param idPrefix
     * @return
     */
    public PageRangeValues getPageRangeValues(String idPrefix) {
        int begin = 0;
        int end = 0;
        int total = 0;

        String pageRangeId = idPrefix + CalmacTestValues.PAGE_RANGE;

        if (!isElementExists(pageRangeId)) {
            return null;
        }

        String paginationText = getText(pageRangeId);

        if (paginationText.isEmpty()) {
            begin = 0;
            end = 0;
            total = 0;
        } else {
            String[] paginationContent = paginationText.split(" ");
            String rangeStr = paginationContent[0];
            String totalStr = paginationContent[2].replaceAll(",", "");

            // Get both sides of the range, and remove extraneous characters
            String[] rangeArray = rangeStr.split("-");
            rangeArray[0] = rangeArray[0].replaceAll(",", "");
            rangeArray[1] = rangeArray[1].replaceAll(",", "");

            begin = parseInt(rangeArray[0]);
            end = parseInt(rangeArray[1]);
            total = parseInt(totalStr);
        }
        return new PageRangeValues(begin, end, total);
    }

    public String getIdWithAppPrefix(String id) {
        String appName = DriverManager.getAppName().toLowerCase();
        if (!id.startsWith(appName + ".")) {
            if (!id.startsWith(".")) {
                id = "." + id;
            }
            id = appName + id;
        }
        return id;
    }

    public String getIdWithoutAppPrefix(String id) {
        String appName = DriverManager.getAppName().toLowerCase();
        if (id.startsWith(appName + ".")) {
            id = id.substring(appName.length());
        }
        return id;
    }

    public WebElement getFocusedElement() {
        return getDriver().switchTo().activeElement();
    }

    public void switchToPracticeInsightsLoginPageWithCredentials(String userName, String password) {
        String loginUrl = Config.getLoginPageURL() + "?username=" + userName + "&" + "password=" + password;
        getDriver().get(loginUrl);
    }

    public static String getNumberWithSuffix(int number) {
        int mod = number;
        if (number > 13) {
            mod = number % 10;
        }
        switch (mod) {
            case 1:
                return number + "st";
            case 2:
                return number + "nd";
            case 3:
                return number + "rd";
            default:
                return number + "th";
        }
    }

    public void doubleClick(String... ids) {
        Actions action = new Actions(getDriver());
        waitUntilElementClickable(ids);
        WebElement element = getDriver().findElement(By.xpath(createXPath(ids)));
        action.doubleClick(element).build().perform();
    }

    /**
     * Check GWT grid paging button to see if it is enabled.
     * Unfortunately, no method on WebElement tells us this; we can only tell
     * by some CSS properties on the button image.
     *
     * @param pagingButton
     * @return
     */
    public boolean isPagingButtonEnabled(WebElement pagingButton) {
        String cursor = pagingButton.getCssValue("cursor");
        return (cursor != null) && cursor.equalsIgnoreCase("pointer");
    }

    private String getWebElementsSummary(List<WebElement> elements) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < elements.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            WebElement el = elements.get(i);
            sb.append("<" + el.getTagName() + " id='" + el.getAttribute("id") + "'>");
        }
        return sb.toString();
    }

    /**
     * If str is all upper case, return lower case.
     * If str is all lower case, return upper case.
     * If str is mixed case, return upper case.
     *
     * @param str
     * @return
     */
    public String toggleCase(String str) {
        if (str.toUpperCase().equals(str)) {
            str = str.toLowerCase();
        } else if (str.toLowerCase().equals(str)) {
            str = str.toUpperCase();
        } else {
            str = str.toUpperCase();
        }
        return str;
    }

    /**
     * Gets text of the given element
     *
     * @param timeout - maximum time to wait
     * @param ids     - composite id of element
     */
    public String getTextBeta(long timeout, String... ids) {
        waitForPageLoaded(timeout);
        return new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<String>() {
                    @Override
                    public String apply(WebDriver driver) {
                        WebElement element = findElement(ids);
                        javaScriptScrollToView(ids);
                        return element.getText();
                    }

                    @Override
                    public String toString() {
                        return String.format("get text for element (%s)", createXPath(ids));
                    }
                });
    }

    /**
     * Gets text of the given element within default timeout
     *
     * @param ids - composite id of element
     */
    public String getTextBeta(String... ids) {
        return getTextBeta(getMaxWaitTimeOut(), ids);
    }

    /**
     * Clicks on the elements and checks that page starts loading
     *
     * @param timeout - maximum time to wait
     * @param ids     - composite id of element
     */
    public void click(long timeout, String... ids) {
        waitForPageLoaded(timeout);
        Integer[] attempt = {0};
        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        try {
                            WebElement element = findElement(ids);
                            if (element.isEnabled()) {
                                String prev = getDriver().getPageSource();
                                findElement(ids).click();
                                attempt[0]++;
                                return (!prev.equals(getDriver().getPageSource()) || attempt[0] > 0);
                            }
                        } catch (NoSuchElementException e) {
                            if (attempt[0] > 0)
                                return true;
                        }
                        return false;
                    }

                    @Override
                    public String toString() {
                        return String.format("element (%s) to be clicked", createXPath(ids));
                    }
                });
    }

    public void sendArrowKeys(long timeout, String... ids) {
        waitForPageLoaded(timeout);
        Integer[] attempt = { 0 };
        new WebDriverWait(getDriver(), timeout).ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        try {
                            WebElement element = findElement(ids);
                            if (element.isEnabled()) {
                                String prev = getDriver().getPageSource();
                                findElement(ids).sendKeys(Keys.ARROW_DOWN);
                                attempt[0]++;
                                return (!prev.equals(getDriver().getPageSource()) || attempt[0] > 0);
                            }
                        } catch (NoSuchElementException e) {
                            if (attempt[0] > 0)
                                return true;
                        }
                        return false;
                    }

                    @Override
                    public String toString() {
                        return String.format("element (%s) to be clicked", createXPath(ids));
                    }
                });
    }

    public void sendKey(long timeout, String... ids) {
        waitForPageLoaded(timeout);
        Integer[] attempt = {0};
        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        try {
                            WebElement element = findElement(ids);
                            if (element.isEnabled()) {
                                String prev = getDriver().getPageSource();
                                findElement(ids).sendKeys(Keys.ENTER);
                                attempt[0]++;
                                return (!prev.equals(getDriver().getPageSource()) || attempt[0] > 0);
                            }
                        } catch (NoSuchElementException e) {
                            if (attempt[0] > 0) return true;
                        }
                        return false;
                    }

                    @Override
                    public String toString() {
                        return String.format("element (%s) to be clicked", createXPath(ids));
                    }
                });
    }

    /**
     * Clicks on the elements and wait that other element will appear
     *
     * @param elementToClick - element to click
     * @param ids            - composite id of element to wait for appear
     */
    public void clickAndWaitForElementById(WebElement elementToClick, String... ids) {
        clickAndWaitForElementById(getMaxWaitTimeOut(), elementToClick, ids);
    }

    /**
     * Clicks on the elements and wait that other element will appear
     *
     * @param timeout        - maximum time to wait
     * @param elementToClick - element to click
     * @param ids            - composite id of element to wait for appear
     */
    public void clickAndWaitForElementById(long timeout, WebElement elementToClick, String... ids) {
        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        elementToClick.click();
                        return findElement(ids).isDisplayed();
                    }

                    @Override
                    public String toString() {
                        return String.format("visibility of element (%s), after clicking on element %s", createXPath(ids), elementToClick);
                    }
                });
    }

    public boolean isEnabledBeta(long timeout, String... ids) {
        return new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        WebElement element = findElement(ids);
                        return element.isEnabled();
                    }

                    @Override
                    public String toString() {
                        return String.format("element (%s) to be enabled", createXPath(ids));
                    }
                });
    }

    public boolean isDisabledBeta(long timeout, String... ids) {
        return new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        WebElement element = findElement(ids);
                        return !element.isEnabled();
                    }

                    @Override
                    public String toString() {
                        return String.format("element (%s) to be disabled", createXPath(ids));
                    }
                });
    }

    public boolean isEnabledBeta(String... ids) {
        return isEnabledBeta(getMaxWaitTimeOut(), ids);
    }

    public boolean isTextPartiallyHighlightedWithRightColor(WebElement element, String color) {
        WebElement highlightedPart = element.findElement(By.xpath(".//span"));
        String displayedColor = Color.fromString(highlightedPart.getCssValue(CalmacTestValues.BACKGROUND_COLOR)).asHex();
        return StringUtils.equalsIgnoreCase(displayedColor, color);
    }

    public String getBackgroundColorAsHex(String... ids) {
        return Color.fromString(getCSSValue(CalmacTestValues.BACKGROUND_COLOR, ids)).asHex();
    }

    public String getTextColorAsHex(String... ids) {
        return Color.fromString(getCSSValue(CalmacTestValues.ATTRIBUTE_COLOR, ids)).asHex();
    }

    public boolean isPartialTextPresent(String inactivated, String... ids) {
        WebElement element = getDriver().findElement(By.xpath(createXPath(ids)));
        WebElement spanElement = element.findElement(By.xpath(".//span"));
        String spanValue = spanElement.getText();
        return spanValue.toUpperCase().contains(inactivated.toUpperCase());
    }

    public boolean isElementHasRightColor(String color, String... ids) {
        WebElement highlightedText = findElement(ids);
        color = Color.fromString(color).asHex();
        String displayedColor = Color.fromString(highlightedText.getCssValue("color")).asHex();
        return StringUtils.equalsIgnoreCase(displayedColor, color);
    }

    public boolean isElementHasRightBackgroundColor(String color, String... ids) {
        WebElement highlightedText = findElement(ids);
        return isElementHasRightBackgroundColor(color, highlightedText);
    }

    public boolean isElementHasRightBorderColor(String color, String... ids) {
        WebElement highlightedText = findElement(ids);
        return isElementHasRightBorderColor(color, highlightedText);
    }

    public boolean isElementHasRightBorderColor(String color, WebElement element) {
        color = Color.fromString(color).asHex();
        String displayedColor = Color.fromString(element.getCssValue(CalmacTestValues.BORDER_TOP_COLOR)).asHex();
        return StringUtils.equalsIgnoreCase(displayedColor, color);
    }

    public boolean isElementHasRightBackgroundColor(String color, WebElement element) {
        color = Color.fromString(color).asHex();
        String displayedColor = Color.fromString(element.getCssValue(CalmacTestValues.BACKGROUND_COLOR)).asHex();
        return StringUtils.equalsIgnoreCase(displayedColor, color);
    }

    public boolean isElementHasRightColorForChildElement(String color, String childNodeTagName, String... parentElementIds) {
        WebElement highlightedText = findElement(parentElementIds).findElement(By.xpath(".//" + childNodeTagName + ""));
        color = Color.fromString(color).asHex();
        String displayedColor = Color.fromString(highlightedText.getCssValue(CalmacTestValues.ATTRIBUTE_COLOR)).asHex();
        return StringUtils.equalsIgnoreCase(displayedColor, color);
    }

    public boolean validateTextPresentInChildElements(String childNodeTagName, String textToValidate, String... parentElementIds) {
        WebElement parentNode = findElement(parentElementIds);
        List<WebElement> childNodes = parentNode.findElements(By.xpath(".//" + childNodeTagName + ""));
        Iterator<WebElement> elementIterator = childNodes.iterator();

        for (Iterator<WebElement> iter = elementIterator; iter.hasNext(); ) {
            WebElement element = elementIterator.next();
            if (textToValidate.equalsIgnoreCase(element.getText())) {
                return true;
            }
        }
        return false;
    }

    public boolean isTextShownWithRightColor(WebElement element, String attribute, String color) {
        WebElement spanPart = element.findElement(By.xpath(".//span"));
        WebElement fontPart = spanPart.findElement(By.xpath(".//font"));
        String displayedColor = Color.fromString(fontPart.getCssValue(attribute)).asHex();
        return StringUtils.equalsIgnoreCase(displayedColor, color);
    }

    public boolean isTextShownWithClassErrorMessage(WebElement element) {
        WebElement spanPart = element.findElement(By.xpath(".//span"));
        String val = spanPart.getAttribute("class");
        return StringUtils.equalsIgnoreCase("errorMessage", val);
    }


    /**
     * Clicks on the elements and checks that page starts loading with default timeout
     *
     * @param ids - composite id of element
     */
    public void click(String... ids) {
        click(Config.getWaitSecsMaxTimeout(), ids);
    }

    public void sendenterKey(String... ids) {
        sendKey(Config.getWaitSecsMaxTimeout(), ids);
    }


    /**
     * Hovers over mouse until element class attribute becomes containing value
     * then clicks on appeared element, default timeout used
     *
     * @param value - value of class attribute
     * @param ids   - composite id of element
     */
    public void hoverAndClickBeta(String value, String... ids) {
        hoverAndClickBeta(Config.getWaitSecsMedium(), value, ids);
    }

    /**
     * Hovers over mouse until element class attribute becomes containing value
     * then clicks on appeared element (applicable to menu items)
     *
     * @param timeout - maximum time to wait
     * @param value   - value of class attribute
     * @param ids     - composite id of element
     */
    public void hoverAndClickBeta(long timeout, String value, String... ids) {
        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        new Actions(getDriver()).moveToElement(findElement(ids)).perform();
                        if (getAttributeByValue(CalmacTestValues.ATTRIBUTE_CLASS, ids).contains(value)) {
                            WebElement element = findElement(ids);
                            if (element.isEnabled()) {
                                element.sendKeys(Keys.ENTER);
                                // element.click();
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public String toString() {
                        return String.format("element (%s) to be hovered and clicked", createXPath(ids));
                    }
                });
    }

    /**
     * Gets the list of selected values from Multiple CheckBox list
     *
     * @param ids - composite id of element
     * @return list of selected values
     */
    public List<String> getSelectedMultipleCheckBoxListValues(String... ids) {
        List<String> selectedList = new ArrayList<>();

        List<String> idsInputElementsList = new ArrayList<>();
        List<String> idsLabelElementsList = new ArrayList<>();
        for (String idItem : ids) {
            idsInputElementsList.add(idItem);
            idsLabelElementsList.add(idItem);
        }

        idsInputElementsList.add("-input");
        idsLabelElementsList.add("-label");

        List<WebElement> inputElementsList = findElements(idsInputElementsList.toArray(new String[idsInputElementsList.size()]));
        List<WebElement> labelElementsList = findElements(idsLabelElementsList.toArray(new String[idsInputElementsList.size()]));
        for (int i = 0; i < inputElementsList.size(); i++) {
            if (inputElementsList.get(i).isSelected()) {
                selectedList.add(labelElementsList.get(i).getText());
            }
        }
        return selectedList;
    }

    /**
     * Gets the list of selected values from a dropdown list
     *
     * @param ids - composite id of element
     * @return list of current/pre-selected values by visible text (when Value is not Available)
     */

    public List<String> getSelectedMultipleValuesListByText(String... ids) {
        List<String> listValues = new ArrayList<String>();
        waitUntilElementClickableBeta(ids);
        Select listElement = new Select(getDriver().findElement(By.xpath(createXPath(ids))));
        List<WebElement> options = listElement.getAllSelectedOptions();

        for (int i = 0; i < options.size(); i++) {
            String value = options.get(i).getAttribute("text");
            listValues.add(value);
        }

        return listValues;
    }

    /**
     * Gets the list of selected values from a dropdown list
     *
     * @param ids - composite id of element
     * @return list of current/pre-selected values by Value
     */

    public List<String> getSelectedMultipleValuesListByValue(String... ids) {
        List<String> listValues = new ArrayList<String>();
        waitUntilElementClickableBeta(ids);
        Select listElement = new Select(getDriver().findElement(By.xpath(createXPath(ids))));
        List<WebElement> options = listElement.getAllSelectedOptions();

        for (int i = 0; i < options.size(); i++) {
            String value = options.get(i).getAttribute("value");
            listValues.add(value);
        }

        return listValues;
    }

    private void waitUntilElementClickableBeta(String[] ids) {
        // TODO Auto-generated method stub

    }

    /**
     * Gets the list of values of given dropdown, default timeout used
     *
     * @param ids - composite id of element
     * @return list of dropdown options
     */
    public List<String> getValuesFromDropDownBeta(String... ids) {
        return getValuesFromDropdownBeta(Config.getWaitSecsMedium(), ids);
    }

    /**
     * Gets the list of values of given dropdown
     *
     * @param timeout - maximum time to wait
     * @param ids     - composite id of element
     * @return list of dropdown options
     */
    public List<String> getValuesFromDropdownBeta(long timeout, String... ids) {
        List<String> values = Lists.newArrayList();
        return new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<List<String>>() {
                    @Override
                    public List<String> apply(WebDriver driver) {
                        Select dropList = new Select(findElement(ids));
                        dropList.getOptions().forEach(option -> values.add(option.getText()));
                        return values;
                    }

                    @Override
                    public String toString() {
                        return String.format("all available options to be collected for element (%s)", createXPath(ids));
                    }
                });
    }

    /**
     * Gets the selected value from given dropdown
     *
     * @param ids - composite id of element
     * @return selected value
     */
    public String getSelectedValueFromDropdownBeta(String... ids) {
        return getSelectedValueFromListBeta(Config.getWaitSecsMedium(), findElement(ids));
    }

    public String getSelectedValueFromDropdownBeta(WebElement element) {
        return getSelectedValueFromListBeta(Config.getWaitSecsMedium(), element);
    }

    public String getSelectedValueFromListBeta(long timeout, WebElement element) {
        return new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<String>() {
                    @Override
                    public String apply(WebDriver driver) {
                        Select dropDown = new Select(element);
                        try {
                            WebElement option = dropDown.getFirstSelectedOption();
                            return option.getText();
                        } catch (NoSuchElementException e) {
                            return "";
                        }
                    }

                    @Override
                    public String toString() {
                        return String.format("get current selected option for element %s", element);
                    }
                });
    }

    /**
     * Waits for default option is selected and displayed in the dropdown
     *
     * @param defaultValue - value to wait
     * @param ids          - composite id of element
     */
    public boolean waitForDefaultSelectedValueForListIsDisplayed(String defaultValue, String... ids) {
        return waitForDefaultSelectedValueForListIsDisplayed(Config.getWaitSecsMaxTimeout(), defaultValue, ids);
    }

    /**
     * Waits for default option is selected and displayed in the dropdown
     *
     * @param timeout      - maximum time to wait
     * @param defaultValue - value to wait
     * @param ids          - composite id of element
     */
    public boolean waitForDefaultSelectedValueForListIsDisplayed(long timeout, String defaultValue, String... ids) {
        return waitForStateOfDefaultSelectedOption(true, timeout, defaultValue, ids);
    }

    /**
     * Waits for default option is not selected and displayed in the dropdown
     *
     * @param timeout      - maximum time to wait
     * @param defaultValue - value to wait
     * @param ids          - composite id of element
     */
    public boolean isDefaultSelectedValueForListIsNotDisplayed(long timeout, String defaultValue, String... ids) {
        try {
            waitForStateOfDefaultSelectedOption(false, timeout, defaultValue, ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Waits for default option is selected and displayed in the dropdown
     *
     * @param defaultValue - value to wait
     * @param ids          - composite id of element
     */
    public void waitForDefaultSelectedValueForListIsNotDisplayed(String defaultValue, String... ids) {
        isDefaultSelectedValueForListIsNotDisplayed(Config.getWaitSecsMaxTimeout(), defaultValue, ids);
    }

    /**
     * Waits while page to be loaded
     */
    public void waitForPageLoaded() {
        waitForPageLoaded(getMaxWaitTimeOut());
    }

    /**
     * Waits while page to be loaded and wait for given timeout
     */
    public void waitForPageLoaded(long timeout) {
        new WebDriverWait(getDriver(), timeout)
                .until((ExpectedCondition<Boolean>) driver -> ((JavascriptExecutor) Objects.requireNonNull(driver))
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }

    /**
     * Waits for element to be visible
     *
     * @param ids - composite id of element
     * @return element that required for wait
     */
    public WebElement waitUntilElementVisibleBeta(String... ids) {
        return waitUntilElementVisibleBeta(Config.getWaitSecsLong(), ids);
    }

    /**
     * Waits for element to be visible
     *
     * @param timeout - maximum time to wait
     * @param ids     - composite id of element
     * @return element that required for wait
     */
    public WebElement waitUntilElementVisibleBeta(long timeout, String... ids) {
        return new WebDriverWait(getDriver(), timeout, POLLING_INTERVAL)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(createXPath(ids))));
    }

    /**
     * Waits for expected Number Of Windows to be visible
     *
     * @param expectedNumberOfWindows - expected Number Of Windows
     */
    public void waitUntilNumberOfWindows(int expectedNumberOfWindows) {
        waitUntilNumberOfWindows(Config.getWaitSecsLong(), expectedNumberOfWindows);
    }

    /**
     * Waits for expected Number Of Windows to be visible
     *
     * @param timeout                 - maximum time to wait
     * @param expectedNumberOfWindows - expected Number Of Windows
     */
    public void waitUntilNumberOfWindows(long timeout, int expectedNumberOfWindows) {
        new WebDriverWait(getDriver(), timeout, POLLING_INTERVAL)
                .ignoring(WebDriverException.class)
                .until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows));
    }

    public boolean isElementInvisibleBeforeWait(long timeout, String... ids) {
        return new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(createXPath(ids))));
    }

    /**
     * Checks if element is visible or not before required to wait
     *
     * @param timeout - maximum time to wait
     * @param ids     - composite id of element
     * @return element that required for wait
     */
    public boolean isElementVisibleBeforeWait(long timeout, String... ids) {
        try {
            WebElement element = waitUntilElementVisibleBeta(timeout, ids);
            return element != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if element is visible or not before required to wait
     *
     * @param ids - composite id of element
     * @return element that required for wait
     */
    public boolean isElementVisibleBeforeWait(String... ids) {
        return isElementVisibleBeforeWait(Config.getWaitSecsLong(), ids);
    }

    public boolean waitTitleContainsText(String text, String... ids) {
        return waitTitleContainsText(text, getShortWaitTime(), ids);
    }

    /**
     * Waits title in element.
     *
     * @param text    - expected text
     * @param timeout - maximum time to wait
     * @param ids     - composite id of element
     */
    public boolean waitTitleContainsText(String text, long timeout, String... ids) {
        try {
            return new WebDriverWait(getDriver(), timeout)
                    .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                    .until((ExpectedCondition<Boolean>) driver -> {
                        WebElement element = findElement(ids);
                        return StringUtils.equalsIgnoreCase(text, element.getAttribute("title"));
                    });
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * Sets the value to input field such as input, checkbox, radio buttons etc.
     *
     * @param timeout - maximum time to wait
     * @param value   - value to set
     * @param ids     - composite id of element
     */
    public void setValue(long timeout, String value, String... ids) {
        if (null == value) return;

        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        WebElement element = findElement(ids);
                        element.clear();
                        element.sendKeys(value);
                        return (endsWith(value, getAttributeByValue("value", ids)) ||
                                startsWith(value, getAttributeByValue("value", ids)) ||
                                startsWith(value, getAttributeByValue("title", ids)) ||
                                startsWith(value, element.getText()));
                    }

                    @Override
                    public String toString() {
                        return String.format("text '%s' to be set in element (%s)", value, createXPath(ids));
                    }
                });
    }

    public void setValueWithOutVerification( String value, String... ids) {
        if (null == value) return;

        new WebDriverWait(getDriver(), Config.getWaitSecsMaxTimeout())
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        WebElement element = findElement(ids);
                        element.clear();
                        element.sendKeys(value);
                        return true;
                    }

                    @Override
                    public String toString() {
                        return String.format("text '%s' to be set in element (%s)", value, createXPath(ids));
                    }
                });
    }

    public void addValue(long timeout, String value, String... ids) {
        if (null == value) return;

        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        WebElement element = findElement(ids);
                        element.click();
                        element.sendKeys(value);
                        return getText(ids).contains(value) ||
                                getAttributeByValue(CalmacTestValues.ATTRIBUTE_TITLE, ids).contains(value) ||
                                getAttributeByValue(CalmacTestValues.ATTRIBUTE_VALUE, ids).contains(value);
                    }

                    @Override
                    public String toString() {
                        return String.format("text '%s' to be added in element (%s)", value, createXPath(ids));
                    }
                });
    }

    public void setValueForFilePath(String value, String... ids) {
        setValueForFilePath(getMaxWaitTimeOut(), value, ids);
    }

    public void setValueForFilePath(long timeout, String value, String... ids) {
        if (null == value) return;

        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        WebElement element = findElement(ids);
                        element.clear();
                        element.sendKeys(value);
                        String actualValue = getAttributeByValue("value", ids);
                        return value.contains(actualValue.substring(actualValue.lastIndexOf("\\") + 1));
                    }

                    @Override
                    public String toString() {
                        return String.format("text '%s' to be set in element (%s)", value, createXPath(ids));
                    }
                });
    }

    /**
     * Sets the value to input field such as input, checkbox, radio buttons etc.
     *
     * @param timeout - maximum time to wait
     * @param value   - value to set
     * @param ids     - composite id of element
     */
    public void setValueWithWaitAfterClear(long timeout, String value, String... ids) {
        if (null == value) return;

        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        WebElement element = findElement(ids);
                        element.clear();
                        waitForLoadingSpinnerInvisible();
                        element.sendKeys(value);
                        return (startsWith(value, getAttributeByValue("value", ids)) ||
                                startsWith(value, getAttributeByValue("title", ids)) ||
                                startsWith(value, element.getText()));
                    }

                    @Override
                    public String toString() {
                        return String.format("text '%s' to be set in element (%s)", value, createXPath(ids));
                    }
                });
    }

    private boolean startsWith(String string, String substring) {
        if (null == substring) return false;
        if (string.isEmpty() && substring.isEmpty()) return true;
        if (!string.isEmpty() && substring.isEmpty()) return false;
        return string.startsWith(substring);
    }

    private boolean endsWith(String string, String substring) {
        if (null == substring) return false;
        if (string.isEmpty() && substring.isEmpty()) return true;
        if (!string.isEmpty() && substring.isEmpty()) return false;
        return string.endsWith(substring);
    }

    /**
     * Sets the value to input field such as input, checkbox, radio buttons etc.
     *
     * @param value - value to set
     * @param ids   - composite id of element
     */
    public void setValue(String value, String... ids) {
        setValue(Config.getWaitSecsMaxTimeout(), value, ids);
    }

    public void addValue(String value, String... ids) {
        addValue(Config.getWaitSecsMaxTimeout(), value, ids);
    }

    /**
     * Sets the value to input field such as input, checkbox, radio buttons etc.
     *
     * @param value - value to set
     * @param ids   - composite id of element
     */
    public void setValueWithWaitAfterClear(String value, String... ids) {
        setValueWithWaitAfterClear(Config.getWaitSecsMaxTimeout(), value, ids);
    }

    public void setValue(String value, WebElement element) {
        element.clear();
        element.sendKeys(value);
    }

    /**
     * Selects the value from dropdown and waits the given timeout
     *
     * @param timeout - maximum time to wait
     * @param value   - value to be selected (Exact Match)
     * @param ids     - composite id of element
     */
    public void selectListBoxByValue(long timeout, String value, String... ids) {
        if (null == value) return;
        Integer[] attempt = {0};
        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        try {
                            Select dropDown = new Select(findElement(ids));
                            String valueToSelect = getExactDropdownValue(dropDown.getOptions(), value);
                            if (valueToSelect == null) return false;
                            dropDown.selectByVisibleText(valueToSelect);
                            attempt[0]++;
                            String selectedValue = dropDown.getFirstSelectedOption().getText();
                            return valueToSelect.equals(selectedValue);

                        } catch (NoSuchElementException | StaleElementReferenceException e) {
                            if (attempt[0] > 0) return true;
                        }
                        return false;
                    }

                    @Override
                    public String toString() {
                        return String.format("option to be selected by visible text '%s' for element (%s)", value, createXPath(ids));
                    }
                });
    }

    //TODO
    public void deselectListBoxByValue(long timeout, String value, String... ids) {
        Select dropDown = new Select(getDriver().findElement(By.xpath(createXPath(ids))));
        dropDown.deselectByVisibleText(value);
    }


    //TODO
    public void deselectAllItems(long timeout, String... ids) {
        Select dropDown = new Select(getDriver().findElement(By.xpath(createXPath(ids))));
        dropDown.deselectAll();
    }


    /**
     * Selects the value from dropdown and waits the given timeout
     *
     * @param timeout - maximum time to wait
     * @param value   - value to be selected (Exact Match)
     * @param ids     - composite id of element
     */
    public void deselectListBoxByValueOld(long timeout, String value, String... ids) {
        if (null == value) return;
        Integer[] attempt = {0};
        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        try {
                            Select dropDown = new Select(findElement(ids));
                            dropDown.deselectByValue(value);
                            return true;

                        } catch (NoSuchElementException | StaleElementReferenceException e) {
                            if (attempt[0] > 0) return true;
                        }
                        return false;
                    }

                });
    }

    /**
     * Selects the option from dropdown by value attribute
     *
     * @param value - value attribute
     * @param ids   - composite id of element
     */
    public void selectListBoxByAttributeValue(String value, String... ids) {
        selectListBoxByAttributeValue(getMaxWaitTimeOut(), value, ids);
    }

    /**
     * Selects the option from dropdown by value attribute
     *
     * @param timeout - maximum time to wait
     * @param value   - value attribute
     * @param ids     - composite id of element
     */
    public void selectListBoxByAttributeValue(long timeout, String value, String... ids) {
        if (null == value) return;
        Integer[] attempt = {0};
        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        try {
                            Select dropDown = new Select(findElement(ids));
                            dropDown.selectByValue(value);
                            attempt[0]++;
                            String selectedValue = dropDown.getFirstSelectedOption().getAttribute(CalmacTestValues.ATTRIBUTE_VALUE);
                            return value.equals(selectedValue);

                        } catch (NoSuchElementException e) {
                            if (attempt[0] > 0) return true;
                        }
                        return false;
                    }

                    @Override
                    public String toString() {
                        return String.format("option to be selected by attribute value '%s' for element (%s)", value, createXPath(ids));
                    }
                });
    }

    private String getExactDropdownValue(List<WebElement> options, String value) {
        try {
            return options.stream().filter(option -> value.equalsIgnoreCase(option.getText())).findFirst().get().getText();
        } catch (java.util.NoSuchElementException e) {
            return null;
        }
    }

    public void selectListBoxByMultipleValues(long timeout, List<String> values, String... ids) {
        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        Select dropDown = new Select(findElement(ids));
                        values.forEach(dropDown::selectByVisibleText);
                        List<String> selectedOptions = Lists.newArrayList();
                        dropDown.getAllSelectedOptions()
                                .forEach(option -> selectedOptions.add(option.getText()));
                        return (!selectedOptions.isEmpty() && CollectionUtils.isEqualCollection(values, selectedOptions));
                    }

                    @Override
                    public String toString() {
                        return String.format("options to be selected by visible text '%s' for element (%s)", Arrays.toString(values.toArray()),
                                createXPath(ids));
                    }
                });
    }

    public void deselectAllAndSelectMultipleListBoxByVisibleText1(long timeout, List<String> values, String... ids) {
        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        Select dropDown = new Select(findElement(ids));
                        dropDown.deselectAll();
                        values.forEach(dropDown::selectByVisibleText);
                        List<String> selectedOptions = Lists.newArrayList();
                        dropDown.getAllSelectedOptions()
                                .forEach(option -> selectedOptions.add(option.getText()));
                        return (!selectedOptions.isEmpty() && CollectionUtils.isEqualCollection(values, selectedOptions));
                    }

                    @Override
                    public String toString() {
                        return String.format("options to be selected by visible text '%s' for element (%s)", Arrays.toString(values.toArray()),
                                createXPath(ids));
                    }
                });
    }


    /**
     * Selects the value from dropdown and waits the given timeout
     *
     * @param timeout - maximum time to wait
     * @param value   - value to be selected by Partial Text
     * @param ids     - composite id of element
     */
    public void selectListBoxByPartialText(long timeout, String value, String... ids) {
        if (null == value) return;

        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        String valueToSelect = null;
                        Select dropDown = new Select(findElement(ids));

                        for (WebElement option : dropDown.getOptions()) {
                            if (StringUtils.containsIgnoreCase(option.getText(), value)) {
                                valueToSelect = option.getText();
                                break;
                            }
                        }
                        if (valueToSelect == null) return false;
                        dropDown.selectByVisibleText(valueToSelect);
                        return valueToSelect.equals(dropDown.getFirstSelectedOption().getText());
                    }

                    @Override
                    public String toString() {
                        return String.format("option to be selected by partial text '%s' for element (%s)", value, createXPath(ids));
                    }
                });
    }

    /**
     * Selects the value from dropdown with waits the default timeout
     *
     * @param value - value to be selected (Exact Match)
     * @param ids   - composite id of element
     */
    public void selectListBoxByValue(String value, String... ids) {
        waitForPageLoaded(Config.getWaitSecsMaxTimeout());
        selectListBoxByValue(Config.getWaitSecsMaxTimeout(), value, ids);
    }

    /**
     * Selects the value from dropdown with waits the default timeout
     *
     * @param value - value to be selected (Partial Match)
     * @param ids   - composite id of element
     */
    public void selectListBoxByPartialText(String value, String... ids) {
        waitForPageLoaded(Config.getWaitSecsMaxTimeout());
        selectListBoxByPartialText(Config.getWaitSecsMaxTimeout(), value, ids);
    }

    /**
     * Selects value from dropdown by index with waits the given timeout
     *
     * @param timeout - maximum time to wait
     * @param index   - index of option to be selected
     * @param ids     - composite id of element
     */
    public void selectListBoxByIndexBeta(long timeout, int index, String... ids) {
        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        Select dropDown = new Select(findElement(ids));
                        dropDown.selectByIndex(index);
                        return true;
                    }

                    @Override
                    public String toString() {
                        return String.format("option to be selected by index '%d' for element (%s)", index, createXPath(ids));
                    }
                });
    }

    /**
     * Selects value from dropdown by index and waits the default timeout
     *
     * @param index - index of option to be selected
     * @param ids   - composite id of element
     */
    public void selectListBoxByIndexBeta(int index, String... ids) {
        selectListBoxByIndexBeta(getMaxWaitTimeOut(), index, ids);
    }

    /**
     * Checks the either element checked or not
     *
     * @param ids - composite id of element
     * @return boolean statement
     */
    public boolean isCheckedBeta(String... ids) {
        return isElementExists(ids) && findElement(ids).isSelected();
    }

    /**
     * Checks if checkbox is checked or not before required to wait
     *
     * @param selected - selection state to validate
     * @param ids      - composite id of element
     * @return boolean statement
     */
    public boolean isCheckedOrUnchecked(boolean selected, String... ids) {
        return isCheckedOrUnchecked(getMaxWaitTimeOut(), selected, ids);
    }

    /**
     * Checks if checkbox is checked or not before required to wait
     *
     * @param timeout  - maximum time to wait
     * @param selected - selection state to validate
     * @param ids      - composite id of element
     * @return boolean statement
     */
    public boolean isCheckedOrUnchecked(long timeout, boolean selected, String... ids) {
        return new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.elementSelectionStateToBe(findElement(ids), selected));
    }

    /**
     * Checks or unchecks the checkbox dependant on check flag
     *
     * @param check - check/uncheck flag
     * @param ids   - composite id of element
     */
    public void checkCheckbox(long timeout, boolean check, String... ids) {
        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        WebElement element = findElement(ids);

                        if (check == element.isSelected() || !element.isEnabled())
                            return true;
                        String xpath = createXPath(ids).replace("-input", "-label")
                                .replace("//input", "//label");
                        findElement(xpath).click();
                        element = findElement(ids);
                        return (check == element.isSelected());
                    }

                    @Override
                    public String toString() {
                        return String.format("element (%s) to be %s", createXPath(ids), (check ? "checked" : "unchecked"));
                    }
                });
    }

    public void waitTextMatchesPattern(String pattern, String... ids) {
        waitTextMatchesPattern(getShortWaitTime(), pattern, ids);
    }

    /**
     * wait text matches pattern
     *
     * @param pattern - expected pattern
     * @param ids     - composite id of element
     */
    public void waitTextMatchesPattern(long timeout, String pattern, String... ids) {
        new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        WebElement element = findElement(ids);
                        String text = element.getText();
                        Matcher matcher = Pattern.compile(pattern).matcher(text);
                        return matcher.matches();
                    }

                    @Override
                    public String toString() {
                        return String.format("text in element (%s) to match pattern '%s'", createXPath(ids), pattern);
                    }
                });
    }

    public void checkCheckbox(boolean check, String... ids) {
        checkCheckbox(getMaxWaitTimeOut(), check, ids);
    }

    /**
     * Checks if element exists or not
     *
     * @param ids - composite id of element
     * @return boolean statement
     */
    public boolean isElementExists(String... ids) {
        return isElementExists(getMaxWaitTimeOut(), ids);
    }

    /**
     * Checks if element exists or not, wait for given timeout
     *
     * @param ids     - composite id of element
     * @param timeout - maximum time to wait
     * @return boolean statement
     */
    public boolean isElementExists(long timeout, String... ids) {
    	isElementVisible(ids);
        waitForPageLoaded(timeout);
        return !findElements(ids).isEmpty();
    }

    /**
     * Checks if element not exist
     *
     * @param ids - composite id of element
     * @return boolean statement
     */
    public boolean isElementNotExists(String... ids) {
        return !isElementExists(ids);
    }

    public boolean waitForTextIsNotDisplayedForElementByIds(String text, String... ids) {
        return waitForTextIsNotDisplayedForElementByIds(getMaxWaitTimeOut(), text, ids);
    }

    public boolean waitForTextIsNotDisplayedForElementByIds(long timeout, String text, String... ids) {
        return new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        String currentValue = findElement(ids).getText();
                        return !StringUtils.containsIgnoreCase(currentValue, text);
                    }

                    @Override
                    public String toString() {
                        return String.format("text '%s' not to be present in element (%s)", text, createXPath(ids));
                    }
                });
    }

    public boolean waitForTextIsDisplayedForElementByIds(String text, String... ids) {
        return waitForTextIsDisplayedForElementByIds(getMaxWaitTimeOut(), text, ids);
    }

    public boolean waitForTextIsDisplayedForElementByIds(long timeout, String text, String... ids) {
        return new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(ExpectedConditions.textToBePresentInElement(findElement(ids), text));
    }

    public void selectTextByPressCtrlA(String... ids) {
        WebElement element = waitUntilElementVisibleBeta(ids);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.chord(Keys.COMMAND, "a"));
    }

    public void clickOnElementAndSendKeysByActions(Keys keys, String... ids) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(waitUntilElementToBeClickable(ids));
        actions.click();
        actions.sendKeys(keys);
        actions.build().perform();
    }

    public void moveToElementAndClickByActions(String... ids) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(findElement(ids));
        actions.click();
        actions.build().perform();
    }

    public WebElement findElement(String... ids) {
        return getDriver().findElement(By.xpath(createXPath(ids)));
    }

    public List<WebElement> findElements(String... ids) {
        return getDriver().findElements(By.xpath(createXPath(ids)));
    }

    private String getAttributeByValue(String attributeName, String... ids) {
        return findElement(ids).getAttribute(attributeName);
    }

    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    public void usePrimaryDriver() {
        DriverManager.usePrimaryDriver();
    }

    public void useSecondaryDriver() {
        DriverManager.useSecondaryDriver();
    }

    private boolean waitForStateOfDefaultSelectedOption(boolean isSelected, long timeout, String defaultValue, String... ids) {
        return new WebDriverWait(getDriver(), timeout)
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        Select dropDown = new Select(BaseTestPage.this.findElement(ids));
                        WebElement option = dropDown.getFirstSelectedOption();
                        return isSelected == defaultValue.equalsIgnoreCase(option.getText());
                    }

                    @Override
                    public String toString() {
                        return String.format("option '%s' %sto be selected by default for element (%s)", defaultValue, (isSelected ? "" : "not "),
                                createXPath(ids));
                    }
                });
    }


    public String[] addStringToArray(String srtToAdd, String... srtArray) {
        List<String> strList = new ArrayList<>(Arrays.asList(srtArray));
        strList.add(srtToAdd);
        return strList.toArray(new String[0]);
    }

    /**
     * For some reason, WebElements become stale even if there hasn't been any obvious DOM change.
     * Not sure why this happens, but this method makes it easier to get around this condition.
     *
     * @param el
     * @param fetcher
     * @return
     */
    public String getIdFromPossiblyStaleWebElement(WebElement el, WebElementFetcher fetcher) {
        String id = null;
        int count = 0;
        do {
            try {
                id = el.getAttribute("id");
            } catch (StaleElementReferenceException e) {
                shortWait();
                el = fetcher.fetchWebElement();
            }
        } while ((++count < MAX_ID_FETCHER_TRIES) && (id == null));
        return id;
    }

    public String getFirstDisplayedIdOfPattern(String... ids) {
        List<WebElement> elements = getElements(ids);
        if ((elements != null) && !elements.isEmpty()) {
            WebElement el = elements.get(0);
            return el.getAttribute("id");
        }
        return null;
    }

    public boolean safeEquals(String s1, String s2, boolean nullsAreEqual, boolean ignoreCase) {
        return ((s1 == s2) && (nullsAreEqual || (s1 != null))) || ((s1 != null) && (s2 != null) && (ignoreCase ? s1.equalsIgnoreCase(s2) : s1.equals(s2)));
    }

    public void waitUntilBackgroundColorChanged(String bgColor, String... ids) {
        new WebDriverWait(getDriver(), getMaxWaitTimeOut())
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        return !BaseTestPage.this.isElementHasRightBackgroundColor(bgColor, ids);
                    }

                    @Override
                    public String toString() {
                        return String.format("background color to be changed to '%s' for element (%s)", bgColor, createXPath(ids));
                    }
                });
    }

    public WebElement findElementByClassName(String className) {
        return getDriver().findElement(By.className(className));
    }

    public boolean isScrollVisibleForPopup() {
        WebElement webElement = getDriver().findElement(By.xpath("//div[@class='popupContent']/div"));
        return isScrollVisibleForElement(webElement);
    }

    public boolean isScrollVisibleForElement(String... ids) {
        return isScrollVisibleForElement(findElement(ids));
    }

    public boolean isScrollVisibleForElement(WebElement element) {
        int scrollHeight = parseInt(element.getAttribute(CalmacTestValues.ATTRIBUTE_SCROLL_HEIGHT));
        int offsetHeight = parseInt(element.getAttribute(CalmacTestValues.ATTRIBUTE_OFFSET_HEIGHT));
        return scrollHeight > offsetHeight;
    }

    public void navigateBack() {
        getDriver().navigate().back();
        waitForPageLoaded(getMaxWaitTimeOut());
    }

    public WebElement getElementFromIFrame(String iFrameId, String elementId) {
        new WebDriverWait(getDriver(), getMaxWaitTimeOut())
                .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                .until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        if (iFrameId != null) {
                            BaseTestPage.this.switchToFrame(iFrameId);
                        }
                        WebElement webElement = BaseTestPage.this.findElement(elementId);
                        return webElement.isDisplayed();
                    }

                    @Override
                    public String toString() {
                        return String.format("visibility of element (%s) in iFrame (%s)", elementId, iFrameId);
                    }
                });
        return findElement(elementId);
    }

    public String getPDFServerUrl() {
        String parentWindow = getParentWindowHandle();
        boolean isChildWindow = isExistChildWindow(parentWindow);
        if (isChildWindow) {
            switchToChildWindow(parentWindow);
            return getPDFServerUrl(null, "//*[@id='plugin']");
        } else {
            return getPDFServerUrl("//iframe[@class='gwt-Frame']", "//*[@id='plugin']");
        }
    }

    private synchronized String getPDFServerUrl(String iFrameReportViewerId, String elementId) {
        WebElement pdfFileReportViewerElement = getElementFromIFrame(iFrameReportViewerId, elementId);
        return pdfFileReportViewerElement.getAttribute("src");
    }

    public synchronized String getPDFContentFromReportViewer() {
        String pdfContent = PDFHelper.getPDFContent(getPDFServerUrl());

        switchToDefaultFromFrame();

        return pdfContent;
    }

    public synchronized List<RenderedImage> getPDFImagesFromReportViewer() {
        List<RenderedImage> pdfImages = PDFHelper.getPDFImages(getPDFServerUrl());
        switchToDefaultFromFrame();
        return pdfImages;
    }

    public synchronized List<List<TextPosition>> getPDFCharactersByArticleFromReportViewer() {
        List<List<TextPosition>> pdfCharactersByArticle = PDFHelper.getPDFCharactersByArticle();

        switchToDefaultFromFrame();

        return pdfCharactersByArticle;
    }

    public synchronized boolean isContainsPDFAllStrings(String... data) {
        return new TextVerifyPDFProcessor(data).doProcess(getPDFContentFromReportViewer());
    }

    public synchronized boolean isContainsPDFDataFontForString(PDFFontType font, String... data) {
        Assert.assertTrue(isContainsPDFAllStrings(data), String.format("PDF content does not have all data from list %s", Arrays.toString(data)));
        return new TextVerifyPDFProcessor(data).doProcess(font, getPDFContentFromReportViewer(), getPDFCharactersByArticleFromReportViewer());
    }

    public synchronized boolean isContainsPDFDataSizeForString(int font, String... data) {
        Assert.assertTrue(isContainsPDFAllStrings(data), String.format("PDF content does not have all data from list %s", Arrays.toString(data)));
        return new TextVerifyPDFProcessor(data).doProcess(font, getPDFContentFromReportViewer(), getPDFCharactersByArticleFromReportViewer());
    }

    public boolean hasSpecialChar(String str) {
        boolean hasSpecialCharacter = false;

        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(str);

        if (!matcher.matches()) {
            hasSpecialCharacter = true;
        }
        return hasSpecialCharacter;
    }

    public BaseTestPage validateAnyAlertIsVisibleOrNot(boolean isPresent) {
        String alertXpath = String.format("//*[contains(@id,'%s')]", "ID.SomeThing" + "ID.ALERT");
        Assert.assertEquals(isElementVisibleBeforeWait(alertXpath), isPresent,
                String.format("Error is%s visible", isPresent ? " NOT" : ""));
        return this;
    }

    public BaseTestPage validateDialogBoxIsNotDisplayed() {
        waitUntilElementInvisible("ID.DIALOG_BOX");
        Assert.assertFalse(isElementExists("ID.DIALOG_BOX"), "Dialog box is present.");
        return this;
    }

    public boolean waitUntilOnlyOneItemIsDisplayed(String... ids) {
        return waitUntilOnlyOneItemIsDisplayed(getShortWaitTime(), ids);
    }

    /**
     * Waits Only one element displayed
     *
     * @param timeout - maximum time to wait
     * @param ids     - composite id of elements
     */
    public boolean waitUntilOnlyOneItemIsDisplayed(long timeout, String... ids) {
        try {
            return new WebDriverWait(getDriver(), timeout)
                    .ignoring(StaleElementReferenceException.class, WebDriverException.class)
                    .until((ExpectedCondition<Boolean>) driver -> {
                        List<WebElement> elements = findElements(ids);
                        return elements.size() == 1;
                    });
        } catch (Exception exception) {
            return false;
        }
    }

    public void switchBetweenTheFrame(String frameName) {
        getDriver().switchTo().frame(frameName);
    }

    public void switchToPatientFrame() {
        getDriver().switchTo().parentFrame();
        justWait(3);
    }

    public final void waitNewWindowsToBePresent(int timeout) {
        new WebDriverWait(getDriver(), timeout)
                .until ((ExpectedCondition<Boolean>) driver -> {
                    return driver.getWindowHandles().size() > 1;
                });
    }
    
	public void clickAcceptCookies() {
		if (isElementClickable(3, "span", "text", "Accept Cookies")) {
			click("span", "text", "Accept Cookies");
		}
	}

    public void setAttributeValueUsingJavaScript(String attributeName,String attributeValue,String ids) {
        JavascriptExecutor js;
        js = (JavascriptExecutor) getDriver();
       // document.getElementById('vestingEditorButton').setAttribute('class', 'btn btn');
        js.executeScript("document.getElementById('"+ids+"').setAttribute('"+attributeName+"', '"+attributeValue+"')");
    }



}
