package com.framework.base;

import com.framework.listeners.WebEventListener;
import com.framework.utils.ExtentManager;
import com.framework.utils.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {
    private static final ThreadLocal<EventFiringWebDriver> driver = new ThreadLocal<>();
    public static final Logger log = Logger.getLogger(TestBase.class.getName());

    private static final Properties prop = new Properties();

    public static final Calendar calendar = Calendar.getInstance();
    public static final SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy_MM_dd");
    public static final SimpleDateFormat dateTimeFormater = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");

    static {
        configureExtentReports();
        configureLog4j();
        loadProperties();
    }

    public void init(Method testMethod) {
        Thread.currentThread().setName(getClass().getName() + "." + testMethod.getName());
    }

    public synchronized void setDriver(String browserName) {
        try {
            if (browserName.equalsIgnoreCase("chrome")) {
                if (prop.getProperty("isRemote").equalsIgnoreCase("true")) {
                    EventFiringWebDriver eventDriver = new EventFiringWebDriver(new RemoteWebDriver(new URL(prop.getProperty("gridHubUrl")), new ChromeOptions())).register(new WebEventListener());
                    driver.set(eventDriver);
                } else {
                    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/drivers/chromedriver.exe");
                    EventFiringWebDriver eventDriver = new EventFiringWebDriver(new ChromeDriver()).register(new WebEventListener());
                    driver.set(eventDriver);
                }
            } else if (browserName.equalsIgnoreCase("firefox")) {
                if (prop.getProperty("isRemote").equalsIgnoreCase("true")) {
                    EventFiringWebDriver eventDriver = new EventFiringWebDriver(new RemoteWebDriver(new URL(prop.getProperty("gridHubUrl")), new FirefoxOptions())).register(new WebEventListener());
                    driver.set(eventDriver);
                } else {
                    System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/main/resources/drivers/geckodriver.exe");
                    EventFiringWebDriver eventDriver = new EventFiringWebDriver(new FirefoxDriver()).register(new WebEventListener());
                    driver.set(eventDriver);
                }
            }
            if (prop.getProperty("deleteAllCookies").equalsIgnoreCase("true"))
                driver.get().manage().deleteAllCookies();
            driver.get().manage().window().maximize();
            driver.get().manage().timeouts().implicitlyWait(Integer.parseUnsignedInt(prop.getProperty("implicitlyWaitInSeconds")), TimeUnit.SECONDS);
            driver.get().manage().timeouts().pageLoadTimeout(Integer.parseUnsignedInt(prop.getProperty("pageLoadTimeoutInSeconds")), TimeUnit.SECONDS);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized EventFiringWebDriver getDriver() {
        return driver.get();
    }

    public synchronized void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            getDriver().unregister(new WebEventListener());
            driver.remove();
        }
    }

    private static void configureLog4j() {
        String log4jConfPath = System.getProperty("user.dir") + "/src/main/resources/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
    }

    private static void configureExtentReports() {
        String reportFilePath = System.getProperty("user.dir") + "/src/main/java/com/framework/reports/testResults.html";
        String configFilePath = System.getProperty("user.dir") + "/src/main/resources/extent-reports.xml";
        ExtentManager.getReporter(reportFilePath).loadConfig(new File(configFilePath));
    }

    private static void loadProperties() {
        try {
            FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/resources/default.properties"));
            prop.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takeScreenShot() {
        String screenShot = "data:image/png;base64," + driver.get().getScreenshotAs(OutputType.BASE64);
        ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addBase64ScreenShot(screenShot));
    }

    /*
    public void takeScreenShot() {
        try {
            Calendar calendar = Calendar.getInstance();
            File screenShot = ((TakesScreenshot)driver.get()).getScreenshotAs(OutputType.FILE);
            System.out.println(getClass().getName());
            System.out.println(getClass().getEnclosingMethod().getName());
            new File("screenshots/" + getClass().getSimpleName() + "/" + getClass().getEnclosingMethod().getName()).mkdirs();
            File destFile = new File("screenshots/" + getClass().getSimpleName() + "/" + getClass().getEnclosingMethod().getName() + "/" + dateTimeFormater.format(calendar));
            FileUtils.copyFile(screenShot, destFile);
            ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addScreenCapture(destFile.getAbsolutePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/
}
