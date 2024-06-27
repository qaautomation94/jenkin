package com.mscs.emr.automation.functional;

import java.util.Objects;
import java.util.Set;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class DriverManager {
    
    private enum DriverInUse {
        Primary,
        Secondary
    }
    
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
    private static ThreadLocal<WebDriver> secondaryDriver = new ThreadLocal<WebDriver>();
    private static ThreadLocal<String> appName = new ThreadLocal<String>();
    
    private static DriverInUse driverInUse = DriverInUse.Primary;
    
    public static String getAppName() {
        return appName.get();
    }
    
    public static void usePrimaryDriver() {
        driverInUse = DriverInUse.Primary;
    }
    
    public static void useSecondaryDriver() {
        
        if (secondaryDriver.get() == null) {
            throw new IllegalStateException("Can't use secondary driver; was never set");
        }
        
        driverInUse = DriverInUse.Secondary;
    }
    
    public static boolean isSecondaryDriverCreated() {
        return secondaryDriver.get() != null;
    }
    
    public static WebDriver getDriver() {
        return (driverInUse == DriverInUse.Primary) ? getPrimaryDriver() : getSecondaryDriver();
    }
    
    private static WebDriver getPrimaryDriver() {
        return webDriver.get();
    }
    
    private static WebDriver getSecondaryDriver() {
        return secondaryDriver.get();
    }
    
    public static void setAppName(String value) {
        appName.set(value);
    }
    
    static void setPrimaryWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }
    
    static void setSecondaryWebDriver(WebDriver driver) {
        secondaryDriver.set(driver);
    }

    public static BasicCookieStore getDriverCookieStore() {
        Set<Cookie> cookies = getDriver().manage().getCookies();
        BasicCookieStore cookieStore = new BasicCookieStore();

        for (Cookie c : cookies) {
            BasicClientCookie cookie = new BasicClientCookie(c.getName(), c.getValue());
            Objects.requireNonNull(cookie).setDomain(c.getDomain());
            cookie.setPath(c.getPath());
            cookieStore.addCookie(cookie);
        }
        return cookieStore;
    }
}