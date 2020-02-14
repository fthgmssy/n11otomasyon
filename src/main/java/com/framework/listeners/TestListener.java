package com.framework.listeners;

import com.framework.annotations.TestDetails;
import com.framework.base.TestBase;
import com.framework.utils.ExtentManager;
import com.framework.utils.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TestListener extends TestBase implements ITestListener {

    private final Logger log = Logger.getLogger(this.getClass().getName());

    public void onTestStart(ITestResult result) {
        String description = result.getMethod().getDescription() == null ? "" : result.getMethod().getDescription();
        ExtentTestManager.startTest(result.getName(), description);
        TestDetails testDetails = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TestDetails.class);
        if (testDetails != null) {
            for (String author : testDetails.authors()) {
                ExtentTestManager.getTest().assignAuthor(author);
            }
            for (String category : testDetails.categories()) {
                ExtentTestManager.getTest().assignCategory(category);
            }
        }
        logTestResult(result);
        log.info("Test is started!");
        Reporter.log("Test is started!");
    }

    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.endTest();
        logTestResult(result);
        log.info("Test is passed!");
        Reporter.log("Test is passed!");
    }

    public void onTestFailure(ITestResult result) {
        ExtentTestManager.endTest();
        logTestResult(result);
        log.error("Test is failed! Failure reason is ", result.getThrowable());
        Reporter.log("Test is failed! Failure reason is " + result.getThrowable());
    }

    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.endTest();
        logTestResult(result);
        log.warn("Test is skipped! Skip reason is ", result.getThrowable());
        Reporter.log("Test is skipped! Skip reason is " + result.getThrowable());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ExtentTestManager.endTest();
        logTestResult(result);
        log.error("Test is failed but within success percentage! Failure reason is ", result.getThrowable());
        Reporter.log("Test is failed but within success percentage! Failure reason is " + result.getThrowable());
    }

    public void onStart(ITestContext context) {

    }

    public void onFinish(ITestContext context) {
        try(BufferedReader br = new BufferedReader(new FileReader(new File("output.log")))) {
            for(String line; (line = br.readLine()) != null;) {
                ExtentManager.getReporter().setTestRunnerOutput(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExtentManager.getReporter().flush();
    }

    public void logTestResult(ITestResult result) {
        if (result.getStatus() == ITestResult.STARTED) {
            ExtentTestManager.getTest().log(LogStatus.INFO, result.getName() + " Test is started!");
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentTestManager.getTest().log(LogStatus.PASS, result.getName() + " Test is passed!");
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, result.getName() + " Test is skipped! Skip reason is ", result.getThrowable());
        } else if (result.getStatus() == ITestResult.FAILURE) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, result.getName() + " Test is failed! Failure reason is ", result.getThrowable());
            takeScreenShot();
        } else if (result.getStatus() == ITestResult.SUCCESS_PERCENTAGE_FAILURE) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, result.getName() + " Test is failed but within success percentage! Failure reason is ", result.getThrowable());
            takeScreenShot();
        }
    }
}
