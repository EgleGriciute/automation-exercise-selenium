import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.cdimascio.dotenv.Dotenv;

public class VerifyAllProductsAndProductDetailPage_08 {

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
    public void verifyAllProductsAndProductDetailPage() {

        // 4. Click on 'Products' button:
        driver.findElement(By.cssSelector("a[href='/products']")).click();

        // 5. Verify user is navigated to ALL PRODUCTS page successfully:
        WebElement allProductsElement = driver.findElement(By.xpath("//*[text()='All Products']"));
        System.out.println("5. Navigated to ALL PRODUCTS page: " + allProductsElement.getText());

        // 6. The products list is visible:
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement productListContainer = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".features_items")));

        List<WebElement> products = productListContainer.findElements(By.cssSelector(".features_items .col-sm-4"));

        if (products.size() > 0 && productListContainer.isDisplayed()) {
            System.out.println("6. Product list is visible. Number of products: " + products.size());
        } else {
            System.out.println("6. Product list is either not visible or empty");
        }

        // 7. Click on 'View Product' of the first product:
        driver.findElement(By.cssSelector("a[href='/product_details/1']")).click();
        System.out.println("7. 'View Product' clicked for first product");

        // 8. User is landed to product detail page:
        String currentUrl = driver.getCurrentUrl();
        System.out.println("8. Navigated to product detail page: " + currentUrl);

        // 9. Verify that detail detail is visible: product name, category, price,
        // availability, condition, brand:

        System.out.println("9. Verify product details visibility:" + "\n\n");

        verifyElementVisible(driver, By.cssSelector(".product-information h2"), "Product Name");
        verifyElementVisible(driver, By.xpath("//p[contains(text(), 'Category')]"), "Product Category");
        verifyElementVisible(driver, By.xpath("//span[contains(text(), 'Rs.')]"), "Product Price");
        verifyElementVisible(driver, By.xpath("//div[@class='product-information']//p[contains(.,'Availability')]"),
                "Availability");
        verifyElementVisible(driver, By.xpath("//div[@class='product-information']//p[contains(.,'Condition')]"),
                "Condition");
        verifyElementVisible(driver, By.xpath("//div[@class='product-information']//p[contains(.,'Brand')]"), "Brand");

        System.out.println("\n\n");
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }

    // Utility function to verify element visibility:
    private static void verifyElementVisible(WebDriver driver, By locator, String elementName) {

        try {
            WebElement element = driver.findElement(locator);
            if (element.isDisplayed()) {
                System.out.println(elementName + " is visible: " + element.getText());
            } else {
                System.out.println(elementName + " is not visible.");
            }
        } catch (NoSuchElementException e) {
            System.out.println(elementName + " element not found.");
        }
    }
}
