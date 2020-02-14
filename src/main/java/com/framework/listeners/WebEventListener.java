package com.framework.listeners;

import com.framework.base.TestBase;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class WebEventListener extends TestBase implements WebDriverEventListener {

    private final Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public void beforeAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {

    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {

    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        log.info("Navigating to " + url);
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        log.info("Navigated to " + url);
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        log.info("Trying to navigate back to previous page");
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        log.info("Navigated back to previous page");
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        log.info("Trying to navigate forward to next page");
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        log.info("Navigated forward to next page");
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
        log.info("Trying to refresh the current page");
    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
        log.info("Refreshed the current page");
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        log.info("Trying to locate element " + by);
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        log.info("Found element " + by);
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        log.info("Trying to click on element " + element);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        log.info("Clicked on element " + element);
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        String value = "";
        for (CharSequence c : keysToSend)
            value += c;
        log.info("Trying to change element " + element + " value to '" + value + "'");
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        String value = "";
        for (CharSequence c : keysToSend)
            value += c;
        log.info("Changed element " + element + " value to '" + value + "'");
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {

    }

    @Override
    public void afterScript(String script, WebDriver driver) {

    }

    @Override
    public void beforeSwitchToWindow(String s, WebDriver webDriver) {

    }

    @Override
    public void afterSwitchToWindow(String s, WebDriver webDriver) {

    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {

    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> outputType) {

    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> outputType, X x) {

    }

    @Override
    public void beforeGetText(WebElement webElement, WebDriver webDriver) {

    }

    @Override
    public void afterGetText(WebElement webElement, WebDriver webDriver, String s) {

    }
}
