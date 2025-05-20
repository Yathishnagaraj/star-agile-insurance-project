package com.project.staragile.insureme;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import static org.testng.Assert.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class UITests {
    private WebDriver driver;
    private String baseUrl;

    @BeforeClass
    @Parameters({"appUrl"})
    public void setup(@Optional("http://localhost:8080") String appUrl) {
        baseUrl = appUrl;
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");  // Updated to new headless mode
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        System.out.println("Setup complete. Navigating to: " + baseUrl);
    }

    @Test
    public void testHomepageTitle() {
        driver.get(baseUrl);
        String title = driver.getTitle();
        System.out.println("Page title retrieved: " + title);
        assertTrue(title.contains("InsureMe"), 
            "Expected title to contain 'InsureMe', but got: " + title);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Driver closed successfully.");
        }
    }
}
