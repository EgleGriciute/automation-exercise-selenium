import java.io.IOException;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.cdimascio.dotenv.Dotenv;

public class AddReviewOnProduct_21 {

    private WebDriver driver;

    Dotenv dotenv = Dotenv.load();

    String dynamicEmail = dotenv.get("EMAIL");
    String userName = dotenv.get("NAME");

    @BeforeTest
    public void setup() throws InterruptedException {

        // 1. Launch browser:
        HomePageVisibleHelper browserSetup = new HomePageVisibleHelper();
        driver = browserSetup.initializeBrowser("Chrome");

        // 2. Navigate to url 'http://automationexercise.com':
        browserSetup.openHomePage(driver);

        // Handle cookie consent:
        browserSetup.handleCookieConsent(driver);

    }

    @Test
    public void addReviewOnProduct() throws InterruptedException, IOException {

        // 3. Click 'Products' button:
        driver.findElement(By.cssSelector("a[href='/products']")).click();
        System.out.println("3. Clicked on 'Products' button");

        // 4. Verify user is navigated to ALL PRODUCTS page successfully:
        String currentURL = driver.getCurrentUrl();

        try {
            WebElement allProductsMessage = driver.findElement(By.xpath("//*[contains(text(), 'All Products')]"));
            System.out.println("4. User is navigated to ALL PRODUCTS page: " + allProductsMessage.getText()
                    + " | Current URL: " + currentURL);
        } catch (NoSuchElementException e) {
            System.out.println("'All Products' text not found on the page");
        }

        // 5. Click on 'View Product' button:
        driver.findElement(By.xpath("//*[contains(text(), 'View Product')]")).click();
        System.out.println("5. Clicked on a 'View Product' button");

        // 6. Verify 'Write Your Review' is visible:
        WebElement writeYourReviewMessage = driver.findElement(By.cssSelector("a[href='#reviews']"));

        try {
            if (writeYourReviewMessage.isDisplayed()) {
                System.out.println("6. 'Write Your Review' is visible");
            } else {
                System.out.println("6. 'Write Your Review' is not visible");
            }
        } catch (NoSuchElementException e) {
            System.out.println("No such element found in DOM");
        }

        // 7. Enter name, email and review:
        driver.findElement(By.cssSelector("input#name")).sendKeys(userName);
        driver.findElement(By.cssSelector("input#email")).sendKeys(dynamicEmail);

        driver.findElement(By.cssSelector("textarea#review")).sendKeys("Lorem ipsum");

        System.out.println("7. Entered name, email and review");

        // 8. Click 'Submit' button:
        driver.findElement(By.cssSelector("button#button-review")).click();
        System.out.println("8. Clicked on a submit button");

        // 9. Verify success message 'Thank you for your review.':
        WebElement successMessageThankYouforYourReview = driver
                .findElement(By.xpath("//*[contains(text(), 'Thank you for your review.')]"));

        try {
            if (successMessageThankYouforYourReview.isDisplayed()) {
                System.out.println("9. Success message 'Thank you for your review.' rendered");
            } else {
                System.out.println("9. Success message 'Thank you for your review.' not detected");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Something went wrong" + e);
        }

    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}