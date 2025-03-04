import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class SearchProduct_09 {

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
    public void searchProduct() {

        // 4. Click on 'Products' button:
        driver.findElement(By.cssSelector("a[href='/products']")).click();
        System.out.println("4. Clicked on 'Products' button ");

        // 5. Verify navigation to ALL PRODUCTS page:
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement allProductsElement = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='All Products']")));

        System.out.println("5. Navigated to ALL PRODUCTS page: " + allProductsElement.getText());

        // 6. Enter product name in search input and click search button:
        WebElement inputProductNameSearch = driver.findElement(By.cssSelector("#search_product"));

        String productName = "Top";
        inputProductNameSearch.sendKeys(productName);

        WebElement buttonSubmitSearch = driver.findElement(By.cssSelector("#submit_search"));
        buttonSubmitSearch.click();

        System.out.println("6. Product name entered and search button clicked");

        // 7. Verify 'SEARCHED PRODUCTS' is visible:
        WebElement searchedProductsTitle = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Searched Products']")));

        System.out.println("7. 'SEARCHED PRODUCTS' title is visible: " + searchedProductsTitle.getText());

        // 8. Verify all the products related to search are visible:
        List<WebElement> searchResults = driver.findElements(By.cssSelector(".features_items .col-sm-4"));

        if (searchResults.isEmpty()) {
            System.out.println("8. No products found for the search term '" + productName);
        } else {
            System.out.println(
                    "8. " + searchResults.size() + " products found for the search term " + productName + "\n");

            for (WebElement product : searchResults) {
                String productNameDisplayed = product.findElement(By.cssSelector("p")).getText();
                System.out.println("Product Name: " + productNameDisplayed);

                if (productNameDisplayed.toLowerCase().contains(productName.toLowerCase())) {
                    System.out.println("Product name matches search term" + "\n");
                } else {
                    System.out.println("Product name DOES NOT match search term" + "\n");
                }
            }
        }
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}
