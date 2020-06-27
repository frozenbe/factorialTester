package util;

import static io.appium.java_client.touch.offset.PointOption.point;

import com.microsoft.appcenter.appium.EnhancedAndroidDriver;
import com.microsoft.appcenter.appium.EnhancedIOSDriver;
import com.microsoft.appcenter.appium.Factory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Helpers {
  public static EnhancedAndroidDriver<MobileElement> androidDriver;
  public static EnhancedIOSDriver<MobileElement> iosDriver;
  public static AppiumDriver<MobileElement> appiumDriver;

  public static URL serverAddress;
  private static WebDriverWait driverWait;

  /**
   * Initialize the android driver. Must be called before using any helper
   * methods. *
   */
  public static void initAndroid(
    EnhancedAndroidDriver<MobileElement> webDriver,
    URL driverServerAddress
  ) {
    appiumDriver = webDriver;
    serverAddress = driverServerAddress;
    int timeoutInSeconds = 60;
    driverWait = new WebDriverWait(webDriver, timeoutInSeconds);
  }

  /**
   * Initialize the android driver. Must be called before using any helper
   * methods. *
   */
  public static void initIOS(EnhancedIOSDriver<MobileElement> webDriver, URL driverServerAddress) {
    appiumDriver = webDriver;
    serverAddress = driverServerAddress;
    int timeoutInSeconds = 90;
    driverWait = new WebDriverWait(webDriver, timeoutInSeconds);
  }

  /**
   * Set implicit wait in seconds *
   */
  public static void setWait(int seconds) {
    appiumDriver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
  }

  /**
   * Return an element by locator *
   */
  public static WebElement element(By locator) {
    return appiumDriver.findElement(locator);
  }

  /**
   * Swipe from element A to element B
   *
   * @throws InterruptedException
   */
  public static void swipeFromElementAtoElementB(WebElement elementA, WebElement elementB)
    throws InterruptedException {
    Point locationFirstSymptom = elementA.getLocation();
    Point locationLastSymptom = elementB.getLocation();

    System.out.printf("Coordinates: x: %s y: %s\n", locationFirstSymptom.x, locationFirstSymptom.y);
    new TouchAction(appiumDriver)
      .press(point(locationLastSymptom.x, locationLastSymptom.y))
      .waitAction()
      .moveTo(point(locationFirstSymptom.x, locationFirstSymptom.y))
      .release()
      .perform();
    Thread.sleep(3000);
  }

  /**
   * Return a list of elements by locator *
   */
  public static List<MobileElement> elements(By locator) {
    return appiumDriver.findElements(locator);
  }

  /**
   * Press the back button *
   */
  public static void back() {
    appiumDriver.navigate().back();
  }

  /**
   * Return a list of elements by tag name *
   */
  public static List<MobileElement> tags(String tagName) {
    return elements(for_tags(tagName));
  }

  /**
   * Return a tag name locator *
   */
  public static By for_tags(String tagName) {
    return By.className(tagName);
  }

  /**
   * Return a static text element by xpath index *
   */
  public static WebElement s_text(int xpathIndex) {
    return element(for_text(xpathIndex));
  }

  /**
   * Return a static text locator by xpath index *
   */
  public static By for_text(int xpathIndex) {
    return By.xpath("//android.widget.TextView[" + xpathIndex + "]");
  }

  /**
   * Return a static text element that contains text * (android)
   */
  public static WebElement textAndroid(String text) {
    return element(for_text(text));
  }

  /**
   * Return a static text element that contains text * (android)
   */
  public static WebElement textIOS(String text) {
    return element(for_text_ios(text));
  }

  /**
   * Return a static text locator that contains text * (android)
   */
  public static By for_text(String text) {
    By elementToReturnLocator = By.xpath(
      "//android.widget.TextView[contains(@text, '" + text + "')]"
    );
    wait(elementToReturnLocator);
    return elementToReturnLocator;
  }

  /**
   * Return a static text locator that contains text * (ios)
   */
  public static By for_text_ios(String text) {
    By elementToReturnLocator = By.xpath("//*[@label='" + text + "']");
    wait(elementToReturnLocator);
    return elementToReturnLocator;
  }

  /**
   * Return a static text element by exact text *
   */
  public static WebElement text_exact(String text) {
    return element(for_text_exact(text));
  }

  /**
   * Return a static text locator by exact text *
   */
  public static By for_text_exact(String text) {
    return By.xpath("//android.widget.TextView[@text='" + text + "']");
  }

  public static By for_find_nth_match(String value, int nthmatch) {
    return By.xpath(
      "(//*[@content-desc=\"" +
      value +
      "\" or @resource-id=\"" +
      value +
      "\" or @name=\"" +
      value +
      "\"])[" +
      nthmatch +
      "] | " +
      " (//*[contains(translate(@content-desc,\"" +
      value +
      "\",\"" +
      value +
      "\"), \"" +
      value +
      "\") or contains(translate(@text,\"" +
      value +
      "\",\"" +
      value +
      "\"), \"" +
      value +
      "\") or @resource-id=\"" +
      value +
      "\"])[" +
      nthmatch +
      "]"
    );
  }

  public static By for_find(String value) {
    return By.xpath(
      "(//*[@content-desc=\"" +
      value +
      "\" or @resource-id=\"" +
      value +
      "\" or @text=\"" +
      value +
      "\"]) |" +
      " (//*[contains(translate(@content-desc,\"" +
      value +
      "\",\"" +
      value +
      "\"), \"" +
      value +
      "\") or contains(translate(@text,\"" +
      value +
      "\",\"" +
      value +
      "\"), \"" +
      value +
      "\") or @resource-id=\"" +
      value +
      "\"])"
    );
  }

  public static WebElement find(String value) {
    return element(for_find(value));
  }

  /**
   * Wait 30 seconds for locator to find an element *
   */
  public static WebElement wait(By locator) {
    return driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  /**
   * Wait 60 seconds for locator to find all elements *
   */
  public static List<WebElement> waitAll(By locator) {
    return driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
  }

  /**
   * Wait 60 seconds for locator to not find a visible element *
   */
  public static boolean waitInvisible(By locator) {
    return driverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }
}
