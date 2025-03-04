import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import io.github.cdimascio.dotenv.Dotenv;

public class VerifyProductQuantityInCart_13 {

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
    public void verifyProductQuantityInCart() throws InterruptedException {

        // 4. Click 'View Product' for any product on home page:
        List<WebElement> viewProductButtons = driver.findElements(By.xpath("//a[contains(text(),'View Product')]"));

        Random rand = new Random();
        int randomIndex = rand.nextInt(viewProductButtons.size());
        viewProductButtons.get(randomIndex).click();

        System.out.println("4. Click fired on a random 'View Product' button");

        // 5. Verify product detail is opened:
        List<WebElement> productDetailDiv = driver.findElements(By.cssSelector(".product-details"));
        String productDetailURL = driver.getCurrentUrl();

        if (!productDetailDiv.isEmpty()
                && productDetailURL.contains("https://automationexercise.com/product_details/")) {
            System.out.println("5. Product detail page is opened: " + productDetailURL);
        } else {
            System.out.println("5. Product detail page is not opened");
        }

        // 6. Increase quantity to 4:
        WebElement quantityInput = driver.findElement(By.id("quantity"));

        quantityInput.clear();
        quantityInput.sendKeys("4");

        System.out.println("6. Input quantity increased to 4");

        // 7. Click 'Add to cart' button:d
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        WebElement addToCartButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("div.product-information > span > button")));

        addToCartButton.click();

        System.out.println("7. Clicked on a 'Add to cart' button ");

        // 8. Click 'View Cart' button:
        WebElement viewCartLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("View Cart")));
        viewCartLink.click();

        System.out.println("8. Clicked on a 'View Cart' button");

        // 9. Verify that product is displayed in cart page with exact quantity:
        WebElement cartProduct = driver.findElement(By.cssSelector(".cart_info .cart_description"));
        WebElement cartQuantity = driver.findElement(By.cssSelector(".cart_quantity button"));

        String cartProductName = cartProduct.getText();
        String cartQuantityValue = cartQuantity.getText();

        if (cartProductName != null && cartProductName.length() > 0 && cartQuantityValue.equals("4")) {
            System.out.println("9. Product is displayed in the cart with the correct quantity: " + cartQuantityValue);
        } else {
            System.out.println("9. Product is not displayed correctly in the cart, or quantity is incorrect.");
        }
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}
