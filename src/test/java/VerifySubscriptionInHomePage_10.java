import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.cdimascio.dotenv.Dotenv;
import java.time.Duration;

public class VerifySubscriptionInHomePage_10 {

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
    public void verifySubscriptionInHomePage() {

        // 4. Scroll down to footer:
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        System.out.println("4. Scrolled down to footer");

        // 5. Verify text 'SUBSCRIPTION':
        WebElement footerTextSubscription = driver.findElement(By.xpath("//*[text()='Subscription']"));
        System.out.println("5. Verify text 'SUBSCRIPTION': " + footerTextSubscription.getText());

        // 6. Enter email address in input and click arrow button:
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

        WebElement inputSubscriptionEmail = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("susbscribe_email")));

        inputSubscriptionEmail.sendKeys(dynamicEmail);
        driver.findElement(By.id("subscribe")).click();

        System.out.println("6. Subscription filled: " + dynamicEmail + " -> Arrow button executed");

        // 7. Verify success message 'You have been successfully subscribed!' is
        // visible:
        String messageTextSuccessSubscribed = driver.findElement(By.id("success-subscribe")).getText();

        System.out.println("7. Verify success message 'You have been successfully subscribed!' is visible: "
                + messageTextSuccessSubscribed);
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}
