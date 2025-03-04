import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.cdimascio.dotenv.Dotenv;

public class VerifyTestCasePage_07 {

    private WebDriver driver;

    Dotenv dotenv = Dotenv.load();

    String dynamicEmail = dotenv.get("EMAIL");

    @BeforeTest
    public void setup() throws InterruptedException {

        // 1. Launch browser:
        HomePageVisibleHelper browserSetup = new HomePageVisibleHelper();
        driver = browserSetup.initializeBrowser("Chrome");

        // 2. Navigate to url 'http://automationexercise.com':
        browserSetup.openHomePage(driver);

        // Handle cookie consent:
        browserSetup.handleCookieConsent(driver);

        // 3. Verify that home page is visible successfully:
        System.out.println("3. Verify that home page is visible successfully: " + browserSetup.getPageTitle(driver));
    }

    @Test
    public void verifyTestCasePage() {

        // 4. Click on 'Test Cases' button:
        driver.findElement(By.cssSelector("a[href='/test_cases']")).click();
        System.out.println("4. Clicked on 'Test Cases' button");

        // 5. Verify user is navigated to test cases page successfully:
        String urlTestCases = driver.getCurrentUrl();
        System.out.println("5. User nevaigted to: " + urlTestCases);
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}
