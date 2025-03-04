
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.cdimascio.dotenv.Dotenv;

public class SearchProductsAndVerifyCartAfterLogin_20 {

    private WebDriver driver;

    Dotenv dotenv = Dotenv.load();

    String dynamicEmail = dotenv.get("EMAIL");
    String dynamicPassword = dotenv.get("PASSWORD");

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
    public void searchProductsAndVerifyCartAfterLogin() throws InterruptedException, IOException {

        // 3. Click 'Products' button:
        driver.findElement(By.cssSelector("a[href='/products']")).click();
        System.out.println("3. Products button clicked");

        // 4. Verify user is navigated to ALL PRODUCTS page successfully:
        String currentURL = driver.getCurrentUrl();

        try {
            WebElement allProductsMessage = driver.findElement(By.xpath("//*[contains(text(), 'All Products')]"));
            System.out.println("4. User is navigated to ALL PRODUCTS page: " + allProductsMessage.getText()
                    + " | Current URL: " + currentURL);
        } catch (NoSuchElementException e) {
            System.out.println("'All Products' text not found on the page");
        }

        // 5. Enter product name in search input and click search button:

        List<WebElement> allSearchProducts = driver.findElements(By.cssSelector(".productinfo > p"));

        Random rand = new Random();
        WebElement searchProduct = allSearchProducts.get(rand.nextInt(allSearchProducts.size()));

        String randomSearchProduct = searchProduct.getText().trim();
        String[] words = randomSearchProduct.split("\\s+");

        int numWordsToKeep = 2;
        int startIndex = Math.max(0, words.length - numWordsToKeep);

        String randomSearchProductEntered = String.join(" ", Arrays.copyOfRange(words, startIndex, words.length));

        driver.findElement(By.cssSelector("input#search_product")).sendKeys(randomSearchProductEntered);
        driver.findElement(By.cssSelector("button#submit_search")).click();

        System.out.println("5. Product name filled in and clicked on a search button: " + randomSearchProductEntered);

        // 6. Verify 'SEARCHED PRODUCTS' is visible:

        try {
            WebElement searchedProductsMessage = driver
                    .findElement(By.xpath("//*[contains(text(), 'Searched Products')]"));
            System.out.println("6. 'SEARCHED PRODUCTS' is visible: " + searchedProductsMessage.getText());
        } catch (NoSuchElementException e) {
            System.out.println("6. 'SEARCHED PRODUCTS' not found on a page");

        }

        // 7. Verify all the products related to search are visible:
        List<WebElement> searchedProducts = driver.findElements(By.cssSelector(".productinfo > p"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (WebElement searchedProduct : searchedProducts) {
            String searchedProductResult = searchedProduct.getText();

            if (searchedProductResult.contains(randomSearchProductEntered)) {
                System.out.println("7. Search matches:" + "\n" + "Search entered: " + randomSearchProductEntered
                        + "\n" + "Search result: " + searchedProductResult);

                // 8. Add those products to cart:
                WebElement productToCartBtn = searchedProduct
                        .findElement(By.xpath("./ancestor::div[contains(@class, 'productinfo')]//a"));

                productToCartBtn.click();

                WebElement continueShoppingButton = wait
                        .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-dismiss='modal']")));
                continueShoppingButton.click();

                System.out.println("8. Products added to cart");

            } else {
                System.out.println("7. Search does not match");
            }
        }

        // 9. Click 'Cart' button and verify that products are visible in cart:
        driver.findElement(By.cssSelector("a[href='/view_cart']")).click();

        List<WebElement> productDetailsDescription = driver
                .findElements(By.xpath("//*[contains(@href, '/product_details')]"));

        try {
            for (WebElement productVisible : productDetailsDescription) {
                if (productVisible.isDisplayed()) {
                    System.out.println(productVisible.getText());
                }
            }
            System.out.println("9. Result: Products visible in cart");
        } catch (NoSuchElementException e) {
            System.out.println("9. Result: Products not visible in cart");
        }

        // 10. Click 'Signup / Login' button and submit login details:
        driver.findElement(By.cssSelector("[href='/login']")).click();

        UserRegistrationHelper registrationHelper = new UserRegistrationHelper(driver);
        registrationHelper.registerNewUser();

        // Logout:
        driver.findElement(By.cssSelector("a[href='/logout']")).click();

        driver.findElement(By.cssSelector("[data-qa='login-email']")).sendKeys(dynamicEmail);
        driver.findElement(By.cssSelector("[data-qa='login-password']")).sendKeys(dynamicPassword);

        // Click 'login' button:
        WebElement loginButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-qa='login-button']")));
        loginButton.click();

        System.out.println("10. Clicked on 'Signup / Login' button and submitted login details");

        // 11. Again, go to Cart page:
        driver.findElement(By.cssSelector("a[href='/view_cart']")).click();

        System.out.println("11. Navigated to Cart page");
        // 12. Verify that those products are visible in cart after login as well:
        List<WebElement> productsAfterLogin = driver.findElements(By.xpath("//*[contains(@href, '/product_details')]"));

        try {
            boolean productsVisible = false;
            for (WebElement product : productsAfterLogin) {
                if (product.isDisplayed()) {
                    System.out.println("12. Products rendered: " + product.getText());
                    productsVisible = true;
                }
            }
            if (productsVisible) {
                System.out.println("Result: Products are still visible in the cart after login");
            } else {
                System.out.println("Result: No products visible in the cart after login");
            }
        } catch (NoSuchElementException e) {
            System.out.println("12. Result: Products not found in the cart after login");
        }

    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}