import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.cdimascio.dotenv.Dotenv;
import java.time.Duration;

public class AddProductsInCart_12 {

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
                System.out.println("3. Verify that home page is visible successfully: "
                                + browserSetup.getPageTitle(driver));
        }

        @Test
        public void addProductsInCart() {

                // 4. Click 'Products' button:
                driver.findElement(By.cssSelector("a[href='/products']")).click();
                System.out.println("4. Clicked on 'Products' button ");

                // 5. Hover over first product and click 'Add to cart':
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                WebElement firstProductOfProducts = wait.until(ExpectedConditions
                                .visibilityOfElementLocated(By.cssSelector("img[src='/get_product_picture/1']")));

                // Perform hover action:
                Actions actions = new Actions(driver);
                actions.moveToElement(firstProductOfProducts).perform();

                // Wait for the "Add to cart" button to appear and click
                WebElement addToCartButton_1 = wait.until(
                                ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-product-id='1']")));

                addToCartButton_1.click();

                System.out.println("5. Hovered over the first product and clicked 'Add to Cart'");

                // 6. Click 'Continue Shopping' button after the cart modal appears:
                WebElement continueShoppingButton = wait.until(
                                ExpectedConditions.elementToBeClickable(By.cssSelector("[data-dismiss='modal']")));

                continueShoppingButton.click();

                System.out.println("6. Clicked on 'Continue Shopping' button ");

                // 7. Hover over second product and click 'Add to cart':
                WebElement secondProductOfProducts = wait.until(ExpectedConditions
                                .visibilityOfElementLocated(By.cssSelector("img[src='/get_product_picture/2']")));

                // Perform hover action:
                actions.moveToElement(secondProductOfProducts).perform();

                WebElement addToCartButton_2 = wait.until(
                                ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-product-id='2']")));

                addToCartButton_2.click();

                System.out.println("7. Hovered over the second product and clicked 'Add to Cart'");

                // 8. Click 'View Cart' button:
                WebElement viewCartButton = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(By.xpath("//u[text()='View Cart']")));

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", viewCartButton);

                viewCartButton.click();

                System.out.println("8. Clicked on 'View Cart' button");

                // 9. Verify both products are added to Cart:
                WebElement firstProductTitle = driver.findElement(By.cssSelector("a[href='/product_details/1']"));
                WebElement secondProductTitle = driver.findElement(By.cssSelector("a[href='/product_details/2']"));

                System.out.println("9. Verify both products are added to Cart: " + "\n\n" + "First product title: "
                                + firstProductTitle.getText() + "\n" + "Second product title: "
                                + secondProductTitle.getText() + "\n");

                // 10. Verify their prices, quantity and total price:
                WebElement firstProductPrice = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.cssSelector("#product-1 > td.cart_price > p")));

                WebElement firstProductQuantity = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.cssSelector("#product-1 > td.cart_quantity > button")));

                WebElement firstProductTotalPrice = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.cssSelector("#product-1 > td.cart_total")));

                WebElement secondProductPrice = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.cssSelector("#product-2 > td.cart_price > p")));

                WebElement secondProductQuantity = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.cssSelector("#product-2 > td.cart_quantity > button")));

                WebElement secondProductTotalPrice = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.cssSelector("#product-2 > td.cart_total")));

                System.out.println("10. Verify their prices, quantity and total price:" + "\n\n"
                                + "First product price: " + firstProductPrice.getText() + "\n"
                                + "First product quantity: " + firstProductQuantity.getText() + "\n"
                                + "First product total price: " + firstProductTotalPrice.getText() + "\n\n"
                                + "Second product price: " + secondProductPrice.getText() + "\n"
                                + "Second product quantity: " + secondProductQuantity.getText() + "\n"
                                + "Second product total price: " + secondProductTotalPrice.getText());
        }

        @AfterTest
        public void teardown() throws InterruptedException {
                Thread.sleep(10000);
                driver.quit();
        }
}