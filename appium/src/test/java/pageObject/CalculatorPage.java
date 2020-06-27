package test.java.pageObject;

import static util.Helpers.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;


public class CalculatorPage {

    public static WebElement calculateBtn = element(By.id("getFactorial"));
    public static WebElement resultText =  element(By.id("resultDiv"));
    public static WebElement factorialNumInput = element(By.id("number"));
    
    public static void calculateFactorial(String inputValue) {

        // Input factorial value and click on the calculate button
        factorialNumInput.sendKeys(inputValue);
        calculateBtn.click();

    }
}