package com.framework.test;

import com.framework.annotations.TestDetails;
import com.framework.base.TestBase;
import com.framework.pages.HomePage;
import com.framework.pages.KampanyalarPage;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class Kampanyalar extends TestBase {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    HomePage homePage;
    KampanyalarPage kampanyalarPage;

    @BeforeMethod
    public void beforeMethod(Method testMethod) {
        init(testMethod);
    }

    @Test(description = "N11")
    @TestDetails(authors = {"Fatih Gümüşsoy"}, categories = {"Regression"})
    public void kampanyalar_KategoriBazli() {

        setDriver("chrome");
        getDriver().get("https://www.n11.com");
        homePage = new HomePage();
        kampanyalarPage = new KampanyalarPage();


        homePage
                .login("n11testotomasyon@gmail.com", "Avis3101")
                .goToMenu("Kampanyalar");

        kampanyalarPage
                .getCategoriesAndCampaignsAndWriteToExcel();

    }

    @AfterMethod
    public void afterMethod() {
        quitDriver();
    }
}
