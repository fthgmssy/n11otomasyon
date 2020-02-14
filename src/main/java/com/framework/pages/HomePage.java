package com.framework.pages;

import com.framework.base.TestBase;
import com.framework.utils.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class HomePage extends TestBase {

    @FindBy(className = "btnSignIn")
    WebElement btnSignIn;

    @FindBy(id = "email")
    WebElement txtEmail;

    @FindBy(id = "password")
    WebElement txtPassword;

    @FindBy(id = "loginButton")
    WebElement btnLoginButton;

    public HomePage() {
        PageFactory.initElements(getDriver(), this);
    }



    public HomePage clickSignIn() {

        btnSignIn.click();

        return this;
    }

    public HomePage setLoginEmail(String email) {

        txtEmail.sendKeys(email);

        return this;
    }

    public HomePage setLoginPassword(String password) {

        txtPassword.sendKeys(password);

        return this;
    }

    public HomePage clickLoginButton() {

        btnLoginButton.click();

        return this;
    }

    public HomePage login(String email, String password) {

        clickSignIn()
                .setLoginEmail(email)
                .setLoginPassword(password)
                .clickLoginButton()
                .verifyLoggedIn();

        return this;
    }

    public HomePage goToMenu(String menu) {

        getDriver().findElement(By.xpath("//NAV/UL/LI/A[@title='"+ menu +"']")).click();

        return this;
    }

    public HomePage verifyLoggedIn() {

        try {
            getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement username = getDriver().findElement(By.xpath("//DIV[@class='myAccount']/A[@class='menuLink user']"));
            log.info("User logged in successfully! Username: " + username.getText());
        } catch (Exception e) {
            log.error("User could not log in!\n" + e);
            Assert.assertTrue(false, "User could not log in!");
        }
        finally {
            getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }

        return this;
    }


}
