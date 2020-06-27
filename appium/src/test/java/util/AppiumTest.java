package util;

import static util.Helpers.appiumDriver;
import com.google.common.collect.ImmutableMap; 
import com.microsoft.appcenter.appium.EnhancedAndroidDriver;
import com.microsoft.appcenter.appium.EnhancedIOSDriver;
import com.microsoft.appcenter.appium.Factory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;

public class AppiumTest {
  @Rule
  public TestWatcher watcher = Factory.createWatcher();

  static {
    // Disable annoying cookie warnings.
    // WARNING: Invalid cookie header
    LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
  }

  /** Page object references. Allows using 'home' instead of 'StartScreen' **/

  public static EnhancedAndroidDriver<MobileElement> androidDriver;
  public static EnhancedIOSDriver<MobileElement> iOSDriver;
  public static AppiumDriver<MobileElement> appiumDriver;
  public static String envPlatform;

  /** wait wraps Helpers.wait **/
  public static WebElement wait(By locator) {
    return Helpers.wait(locator);
  }

  @Rule
  public TestRule printTests = new TestWatcher() {

    protected void starting(Description description) {
      System.out.print("  Test being run currently: " + description.getMethodName() + "\n");
    }
  };

  /** Run before each test **/
  @Before
  public void setUp() throws Exception {
    DesiredCapabilities capabilities = new DesiredCapabilities();

    // TO DO: clean up for local runs, currently only meant for runs on appcenter
    capabilities.setCapability("appium-version", "1.17.0");
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "Pixel_API_29");
    capabilities.setCapability("–session-override", true);
    capabilities.setCapability("app", "");
    capabilities.setCapability("browserName", "Chrome");
    capabilities.setCapability("chromedriverExecutable", "/Users/frozenbe/Downloads/chromedriver-83");
    capabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));

    URL serverAddress;

    serverAddress = new URL("http://127.0.0.1:4723/wd/hub");

    envPlatform = System.getenv("PLATFORM");
    System.out.print("AppiumTest envPlatform: " + envPlatform + "\n");

    switch (envPlatform) {
      case "android":
        // ChromeOptions chromeOptions = new ChromeOptions();
        // chromeOptions.setExperimentalOption("w3c", false);
        // capabilities.merge(chromeOptions);
        androidDriver = Factory.createAndroidDriver(serverAddress, capabilities);
        androidDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Helpers.initAndroid(androidDriver, serverAddress);
        // appiumDriver = androidDriver;
        // Record using shell adb command for local debugging

        break;
      case "ios":
        // capabilities.setCapability("processArguments", "{ args: ['test_darkmode']
        // }");
        iOSDriver = Factory.createIOSDriver(serverAddress, capabilities);
        iOSDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Helpers.initIOS(iOSDriver, serverAddress);
        appiumDriver = iOSDriver;
        break;
      default:
        System.out
            .print("No platform set! Make sure you set the PLATFORM environment variable first (ios or android)!");
        break;
    }
  }

  /** Run after each test **/
  @After
  public void tearDown() throws Exception {
    if (appiumDriver != null)
      appiumDriver.quit();
  }
}
