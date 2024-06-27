package com.mscs.emr.automation.functional;

public class SystemUtil {
    private static final String osName = System.getProperty("os.name").toLowerCase();
    
    private static final String MAC_OS = "mac os x";
    private static final String WINDOWS = "windows";
    
    protected static boolean isMacOs() {
        return osName.startsWith(MAC_OS);
    }
    
    protected static boolean isWindowsOS() {
        return osName.startsWith(WINDOWS);
    }
}