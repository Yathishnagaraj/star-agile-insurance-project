package com.project.staragile.insureme;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class UITests {
    WebDriver driver;

    @BeforeClass
    @Parameters({"appUrl"})
    public void setup(@Optional("http://your-app-test-url") String appUrl) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    @Parameters({"appUrl"})
    public void testHomepageTitle(@Optional("http://your-app-test-url") String appUrl) {
        driver.get(appUrl);
        String title = driver.getTitle();
        assertTrue(title.contains("InsureMe"), "Homepage title does not contain 'InsureMe'. Actual: " + title);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
