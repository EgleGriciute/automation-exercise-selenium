import java.io.IOException;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class VerifyScrollUpWithoutArrowButtonAndScrollDownFunctionality_26 {
    private WebDriver driver;

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
    public void verifyScrollUpWithoutArrowButtonAndScrollDownFunctionality() throws InterruptedException, IOException {

        // 4. Scroll down page to bottom:
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        System.out.println("4. Scrolled down page to bottom");

        // 5. Verify 'SUBSCRIPTION' is visible:
        WebElement textCartPageSubscription = driver.findElement(By.cssSelector(".single-widget h2"));
        System.out.println("5. Verify text 'SUBSCRIPTION': " + textCartPageSubscription.getText());

        // 6. Scroll up page to top:
        js.executeScript("window.scrollTo(0, 0)");
        System.out.println("6. Scrolled up page to top");

        // 7. Verify that page is scrolled up and 'Full-Fledged practice website for
        // Automation Engineers' text is visible on screen:

        Long scrollPosition = (Long) js.executeScript("return window.scrollY;");

        if (scrollPosition == 0) {
            System.out.println("7. Page is scrolled up");
        } else {
            System.out.println("7. Page is scrolled down");
        }

        WebElement h2TextFullFledged = driver.findElement(
                By.xpath("//*[contains(text(), 'Full-Fledged practice website for Automation Engineers')]"));

        try {
            if (h2TextFullFledged.isDisplayed()) {
                System.out
                        .println("'Full-Fledged practice website for Automation Engineers' text is visible on screen");
            } else {
                System.out.println(
                        "'Full-Fledged practice website for Automation Engineers' text is not visible on screen");
            }
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException: " + e);
        }
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}