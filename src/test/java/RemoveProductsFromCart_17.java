import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RemoveProductsFromCart_17 {

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
    public void removeProductsFromCart() throws InterruptedException {

        // 4. Add products to cart:
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement firstProductAddToCart = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-product-id='1']")));
        firstProductAddToCart.click();

        Thread.sleep(1000);
        driver.findElement(By.cssSelector("[data-dismiss='modal']")).click();

        WebElement secondProductAddToCart = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-product-id='2']")));
        secondProductAddToCart.click();

        Thread.sleep(1000);

        System.out.println("4. Added products to cart");

        driver.findElement(By.cssSelector("[data-dismiss='modal']")).click();

        // 5. Click 'Cart' button:
        driver.findElement(By.cssSelector("a[href='/view_cart']")).click();
        System.out.println("5. Clicked on a cart button");

        // 6. Verify that cart page is displayed:
        String cartPageURL = driver.getCurrentUrl();
        System.out.println("6. Cart page displayed: " + cartPageURL);

        // 7. Click 'X' button corresponding to particular product:
        driver.findElement(By.cssSelector("[data-product-id='1'].cart_quantity_delete")).click();
        driver.findElement(By.cssSelector("[data-product-id='2'].cart_quantity_delete")).click();

        System.out.println("7. Clicked on a 'X' button corresponding to particular product");

        // 8. Verify that products are removed from the cart:
        boolean productRemoved1 = wait
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[data-product-id='1']")));
        boolean productRemoved2 = wait
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[data-product-id='2']")));

        if (productRemoved1 && productRemoved2) {
            System.out.println("8. Products have been removed from the cart");
        } else {
            System.out.println("8. Products are still present");
        }
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}