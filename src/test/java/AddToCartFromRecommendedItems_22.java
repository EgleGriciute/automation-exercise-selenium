import java.io.IOException;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AddToCartFromRecommendedItems_22 {
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

    }

    @Test
    public void addToCartFromRecommendedItems() throws InterruptedException, IOException {

        // 3. Scroll to bottom of page:
        WebElement recommendedSectionScroll = driver.findElement(By.cssSelector(".recommended_items"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", recommendedSectionScroll);

        System.out.println("3. Scrolled to bottom of page");

        // 4. Verify 'RECOMMENDED ITEMS' are visible:

        // Get all product names:
        Set<String> productNames = new HashSet<>();

        // Loop to handle carousel movement:
        // The carousel has 2 slides (3 items each):
        for (int i = 0; i < 2; i++) {
            List<WebElement> recommendedItems = driver.findElements(By.cssSelector(
                    ".carousel-inner .item.active .col-sm-4 .productinfo > p"));

            for (WebElement item : recommendedItems) {
                // Store unique product names
                productNames.add(item.getText().trim());
            }

            // Click next button to reveal the next set of products:
            WebElement nextButton = driver.findElement(By.cssSelector("#recommended-item-carousel .right"));
            nextButton.click();
            // Allow time for the transition:
            Thread.sleep(2000);
        }

        // Print all 6 product names:
        System.out.println("4. All Recommended Items:");

        for (String productName : productNames) {
            System.out.println(productName);
        }

        // 5. Click on 'Add To Cart' on Recommended product:
        driver.findElement(By.cssSelector(
                ".carousel-inner .item.active .col-sm-4 .productinfo > a")).click();

        System.out.println("5. Clicked on 'Add to Cart' on Recommended product");

        // 6. Click on 'View Cart' button:
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement viewCartLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("View Cart")));

        viewCartLink.click();

        System.out.println("6. Clicked on 'View Cart' button");

        // 7. Verify that product is displayed in cart page:
        WebElement cartProductName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".cart_description > h4 a")));

        String cartProductNameText = cartProductName.getText();

        if (productNames.contains(cartProductNameText)) {
            System.out.println("7. Product is successfully added to the cart");
        } else {
            System.out.println("7. Product not found in the cart");
        }

    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}