package test.java.appiumTests;

import com.microsoft.appcenter.appium.Factory;
import io.appium.java_client.AppiumDriver;
import test.java.pageObject.CalculatorPage;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import util.Helpers;
import util.AppiumTest;

public class InterviewTest extends AppiumTest {
    @Rule
    public TestWatcher watcher = Factory.createWatcher();

    public static String envPlatform = System.getenv("PLATFORM");
    public static String errorText = "Please enter an integer";
    public static String successText = "The factorial of ";
    public static String homepageUrl = "https://qainterview.pythonanywhere.com/";

    @org.junit.Test
    public void Test_Positive_RandomValidInput() throws Exception {
        // Generate a random factorial number input to test that the calculation works
        String randomFactorialValid = RandomStringUtils.randomNumeric(3);
        androidDriver.get(homepageUrl);
        CalculatorPage.calculateFactorial(randomFactorialValid);
        Assert.assertTrue(
                (androidDriver.findElementByXPath("//*[contains(text(),'" + successText + randomFactorialValid + "')]"))
                        .isDisplayed());

    }

    @org.junit.Test
    public void Test_Negative_RandomInvalidInput() throws Exception {
        // Generate a random invalid factorial number input to test that the calculation fails if invalid number is input
        String randomFactorialInvalid = RandomStringUtils.randomAlphabetic(15);
        androidDriver.get(homepageUrl);
        CalculatorPage.calculateFactorial(randomFactorialInvalid);
        Assert.assertTrue((androidDriver.findElementByXPath("//*[text()='" + errorText + "']")).isDisplayed());

    }
}