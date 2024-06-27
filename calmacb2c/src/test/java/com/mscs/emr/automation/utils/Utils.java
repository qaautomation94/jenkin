package com.mscs.emr.automation.utils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;

import com.mscs.emr.automation.functional.BaseTestPage;
import com.mscs.emr.automation.functional.TestCaseId;


public class Utils {

    protected static final Logger LOGGER = LogManager.getLogger(Utils.class);
    BaseTestPage BP = new BaseTestPage();

    public static String captureScreenshot(WebDriver driver, String fileName, ITestResult testResult) {
        String filePath = "";
        String folderPath =  Config.getFailedScreenshotsFolderPath();
        Method method = testResult.getMethod().getConstructorOrMethod().getMethod();
        Optional<Integer> testCaseID = Optional.ofNullable(method.getAnnotation(TestCaseId.class).value());
        String timeStamp = new Timestamp(System.currentTimeMillis()).toString();
        timeStamp = timeStamp.replace("-", "_").replace(" ", "_").replace(":", " ").replace(".", " ");
        timeStamp = timeStamp.replace(" ", "");
        fileName = StringUtils.left(fileName, 20);
        if (!StringUtils.isNumeric(fileName)) {
            fileName = StringUtils.left(fileName, 20);
        }
        fileName = "TC_" + testCaseID + "_" + fileName + "_" + timeStamp;
        fileName = fileName.replace("Optional[", "");
        fileName = fileName.replace("]", "");
        try {
            TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
            File source = takeScreenshot.getScreenshotAs(OutputType.FILE);
            File file = new File(folderPath);
            if (!(file.exists() && file.isDirectory())) {
                file.mkdir();
            }
            filePath = folderPath + fileName + ".png";
            org.apache.commons.io.FileUtils.copyFile(source, new File(folderPath + fileName + ".png"));
        } catch (Exception e) {
            LOGGER.warn("Exception while taking screenshot " + e.getMessage());
        }
        return filePath;
    }

    public static void captureFullDesktopScreenShot(String pageTitle) {
        try {
            Robot robot = new Robot();
            Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String screenName = sdf.format(new Date());
            File file = new File(Config.getApplicationScreenShortFolderPath() + pageTitle + "_" + screenName + ".png");
            boolean status = ImageIO.write(bufferedImage, "png", file);
            System.out.println("Screen Captured ? " + status + " File Path:- " + file.getAbsolutePath());
            Thread.sleep(3000);
        } catch (AWTException | IOException | InterruptedException ex) {
            System.err.println(ex);
        }

    }

    static void copyPasteRobotActions(Robot robot, String value) {
        StringSelection stringSelection = new StringSelection(value);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        robot.delay(3000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
    }

    public static List<Long> extractListOfLong(ResultSet rs, String columnName) throws SQLException {
        List<Long> ret = new ArrayList<Long>();
        while (rs.next()) {
            ret.add(rs.getLong(columnName));
        }
        return ret;
    }

    public static String formatTextToInitCap(String actualText) {
        String formatedText = actualText.substring(0, 1).toUpperCase() + actualText.substring(1).toLowerCase();
        return formatedText;
    }

    public static String getHostName() {
        String hostInfo;
        InetAddress myHost;
        try {
            myHost = InetAddress.getLocalHost();
            String hostname = replaceSpecialCharacters(myHost.getHostName());
            hostInfo = hostname;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            hostInfo = "Hostname can not be resolved";
        }

        return hostInfo;
    }

    /**
     * Added to call attention to known issue. WebElement.getText() returns empty
     * String if element is first invisible element after a scrollbar appears.
     *
     * https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/6387
     *
     * @param el
     * @return
     */
    public static String getTextChildOfElement(WebElement el) {
        return el.getAttribute("textContent");
    }

    public static String removeLeadingZerosFromString(String string) {
        return string.replaceFirst("^0+(?!$)", "");
    }

    public static String replaceSpecialCharacters(String actualText) {
        return actualText.replaceAll("[^a-zA-Z0-9]+", "");
    }

    static void robotPressEnter(Robot robot) {
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public static void setRobotValue(String value, boolean isPressEnter) {
        Robot robot;
        try {
            robot = new Robot();
            copyPasteRobotActions(robot, value);
            if (isPressEnter) {
                robotPressEnter(robot);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    public static Object[][] to2DArray(List list) {
        Object[][] ret = new Object[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            ret[i][0] = i;
            ret[i][1] = list.get(i);
        }
        return ret;
    }
 
    public static boolean hasDuplicateInAList(List<String> list) {
        Set<String> set = new HashSet<>();
        for (String each : list) {
            if (!set.add(each)) {
                return true;
            }
        }
        return false;
    }
    
    public static String readSystemProperty(String propertyName){       
        String propertyValue = null;
        
        try {
            String userDir = Config.getProjectPath()+"//testConfigurations.Properties";
            FileInputStream fis = new FileInputStream(userDir);
            Properties prop = new Properties();
            prop.load(fis);
            propertyValue = prop.getProperty(propertyName);
            
        } catch (FileNotFoundException e) {         
            e.printStackTrace();
        } catch (IOException e) {           
            e.printStackTrace();
        }
        return propertyValue;
    }

}
