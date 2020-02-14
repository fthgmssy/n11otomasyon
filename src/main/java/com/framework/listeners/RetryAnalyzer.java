package com.framework.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Properties prop = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/resources/default.properties"));
            prop.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int counter = 0;
    int retryLimit = Integer.parseInt(prop.getProperty("retryLimit"));

    /*
     * (non-Javadoc)
     * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
     *
     * This method decides how many times a test needs to be rerun.
     * TestNg will call this method every time a test fails. So we
     * can put some code in here to decide when to rerun the test.
     *
     * Note: This method will return true if a tests needs to be retried
     * and false if not.
     *
     */
    public boolean retry(ITestResult result) {
        if(counter < retryLimit) {
            counter++;
            return true;
        }
        return false;
    }
}
