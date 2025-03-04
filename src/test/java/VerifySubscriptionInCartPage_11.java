import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.cdimascio.dotenv.Dotenv;

public class VerifySubscriptionInCartPage_11 {

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
    public void verifySubscriptionInCartPage() {

        // 4. Click 'Cart' button:
        driver.findElement(By.cssSelector("a[href='/view_cart']")).click();
        System.out.println("4. Cart button fired");

        // 5. Scroll down to footer:
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        System.out.println("5. Scrolled down to footer");

        // 6. Verify text 'SUBSCRIPTION':
        WebElement textCartPageSubscription = driver.findElement(By.cssSelector(".single-widget h2"));
        System.out.println("6. Verify text 'SUBSCRIPTION': " + textCartPageSubscription.getText());

        // 7. Enter email address in input and click arrow button:
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

        WebElement inputSubscriptionEmail = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("susbscribe_email")));

        inputSubscriptionEmail.sendKeys(dynamicEmail);
        driver.findElement(By.id("subscribe")).click();

        System.out.println("7. Subscription filled: " + dynamicEmail + " -> Arrow button executed");

        // 8. Verify success message 'You have been successfully subscribed!' is
        // visible:
        String messageTextSuccessSubscribed = driver.findElement(By.id("success-subscribe")).getText();

        System.out.println("8. Verify success message 'You have been successfully subscribed!' is visible: "
                + messageTextSuccessSubscribed);
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}