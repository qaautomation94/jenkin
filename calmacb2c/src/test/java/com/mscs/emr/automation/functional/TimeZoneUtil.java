package com.mscs.emr.automation.functional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import com.mscs.emr.automation.utils.Config;

public class TimeZoneUtil {
    private static final String MAC_OS_EST = "America/New_York";
    private static final String MAC_OS_CST = "America/Chicago";
    //private static final String MAC_OS_MT = "America/Denver";
    private static final String MAC_OS_PST = "America/Los_Angeles";
    private static final String MAC_OS_IST = "Asia/Calcutta";
    private static final String MAC_OS_SAWST = "America/La_Paz";
    private static final String MAC_OS_MST = "Europe/Minsk";  //TODO: check this in ios

    private static final String WINDOWS_PST_SCRIPT = "PST.bat";
    private static final String WINDOWS_CST_SCRIPT = "CST.bat";
    private static final String WINDOWS_EST_SCRIPT = "EST.bat";
    private static final String WINDOWS_IST_SCRIPT = "IST.bat";
    private static final String WINDOWS_SAWST_SCRIPT = "SAWST.bat";
    private static final String WINDOWS_MST_SCRIPT = "BST.bat";

    public static final String PACIFIC_STANDARD_TIME = "Pacific Standard Time";
    public static final String CENTRAL_STANDARD_TIME = "Central Standard Time";
    public static final String EASTERN_STANDARD_TIME = "Eastern Standard Time";
    public static final String INDIAN_STANDARD_TIME = "Indian Standard Time";
    public static final String SA_WESTERN_STANDARD_TIME = "SA Western Standard Time";
    public static final String MOSKOW_STANDARD_TIME = "Moscow Standard Time";
    public static final String PST = "PST";

    protected static void changeSystemTimeZone(String newTimeZone) {
        if (newTimeZone.equalsIgnoreCase(PACIFIC_STANDARD_TIME)) {
            if (SystemUtil.isMacOs()) {
                updateMacTimeZone(MAC_OS_PST);
            } else {
                updateWindowsTimeZone(WINDOWS_PST_SCRIPT);
            }
        } else if (newTimeZone.equalsIgnoreCase(CENTRAL_STANDARD_TIME)) {
            if (SystemUtil.isMacOs()) {
                updateMacTimeZone(MAC_OS_CST);
            } else {
                updateWindowsTimeZone(WINDOWS_CST_SCRIPT);
            }
        } else if (newTimeZone.equalsIgnoreCase(EASTERN_STANDARD_TIME)) {
            if (SystemUtil.isMacOs()) {
                updateMacTimeZone(MAC_OS_EST);
            } else {
                updateWindowsTimeZone(WINDOWS_EST_SCRIPT);
            }
        } else if (newTimeZone.equalsIgnoreCase(INDIAN_STANDARD_TIME)) {
            if (SystemUtil.isMacOs()) {
                updateMacTimeZone(MAC_OS_IST);
            } else {
                updateWindowsTimeZone(WINDOWS_IST_SCRIPT);
            }
        } else if (newTimeZone.equalsIgnoreCase(SA_WESTERN_STANDARD_TIME)) {
            if (SystemUtil.isMacOs()) {
                updateMacTimeZone(MAC_OS_SAWST);
            } else {
                updateWindowsTimeZone(WINDOWS_SAWST_SCRIPT);
            }
        } else if (newTimeZone.equalsIgnoreCase(MOSKOW_STANDARD_TIME)) {
            if (SystemUtil.isMacOs()) {
                updateMacTimeZone(MAC_OS_MST);
            } else {
                updateWindowsTimeZone(WINDOWS_MST_SCRIPT);
            }
        }
    }

    private static void updateMacTimeZone(String timeZone) {
        String filePath = Config.getResourcesFolderPath() + "mac_os_scripts/changeSystemTimeZone.sh";

        try {
            File file = new File(filePath);

            if(!file.isFile()){
                throw new IllegalArgumentException("The file " + filePath + " does not exist");
            }
            else {
                Process p = Runtime.getRuntime().exec("sudo " + filePath + " " + timeZone);

                InputStream errorStream = p.getErrorStream();
                String errorMsg = IOUtils.toString(errorStream, StandardCharsets.UTF_8);
                if(!errorMsg.isEmpty()) {
                    System.out.println("Error: " + errorMsg);
                }

                InputStream infoStream = p.getInputStream();
                String infoMsg = IOUtils.toString(infoStream, StandardCharsets.UTF_8);
                if(!infoMsg.isEmpty()) {
                    System.out.println(infoMsg);
                }
            }
        } catch (IOException e1) {
            System.out.println("Error: Could not open or execute the required script file (" + filePath + ") to change system timezone");
        }
    }


    private static void updateWindowsTimeZone(String timeZoneScript){
        String path = "cmd /c start " + Config.getResourcesFolderPath() + timeZoneScript;
        Runtime rn = Runtime.getRuntime();
        try {
            Process pr = rn.exec(path);
            pr.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}