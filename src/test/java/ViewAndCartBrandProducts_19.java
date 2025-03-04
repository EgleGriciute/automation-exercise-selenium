import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ViewAndCartBrandProducts_19 {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void setup() throws InterruptedException {

        // 1. Launch browser:
        HomePageVisibleHelper browserSetup = new HomePageVisibleHelper();
        driver = browserSetup.initializeBrowser("Chrome");

        // 2. Navigate to url:
        browserSetup.openHomePage(driver);

        // Handle cookie consent:
        browserSetup.handleCookieConsent(driver);

        // Initialize explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Ensure this line is present
    }

    @Test
    public void viewAndCartBrandProducts() {
        // 3. Click on 'Products' button:
        driver.findElement(By.cssSelector("a[href='/products']")).click();
        System.out.println("3. Clicked on 'Products' button");

        // 4. Verify that Brands are visible on the left sidebar:
        WebElement brandsVisible = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Brands')]")));

        if (brandsVisible.isDisplayed()) {
            System.out.println("4. 'Brands' section is visible on the left sidebar: " + brandsVisible.getText());
        } else {
            System.out.println("4. 'Brands' section is not visible on the left sidebar.");
        }

        // 5. Click on any brand name:
        List<WebElement> allBrandNames = driver.findElements(By.cssSelector(".brands-name .nav-stacked li"));

        Random rand = new Random();

        WebElement randomBrandName = allBrandNames.get(rand.nextInt(allBrandNames.size()));
        String brandNameText = randomBrandName.getText();
        randomBrandName.click();

        System.out.println("5. Any brand name clicked: " + brandNameText);

        // 6. Verify that user is navigated to brand page and brand products are
        // displayed:
        String brandPageCurrentURL = driver.getCurrentUrl();
        List<WebElement> brandPageProducts = driver.findElements(By.cssSelector(".productinfo > p"));

        if (!brandPageProducts.isEmpty()) {
            System.out.println("6. User navigated to URL: " + brandPageCurrentURL);

            for (WebElement product : brandPageProducts) {
                if (product.isDisplayed()) {
                    System.out.println("Brand Product displayed: " + product.getText());
                } else {
                    System.out.println("Brand Products are not visible");
                }
            }
        }

        // 7. On left side bar, click on any other brand link:

        List<WebElement> allBrandLinks = driver.findElements(By.xpath("//a[contains(@href, '/brand_products/')]"));

        WebElement randomBrandLink = allBrandLinks.get(rand.nextInt(allBrandLinks.size()));
        String expectedBrandURL = randomBrandLink.getDomAttribute("href");
        randomBrandLink.click();

        System.out.println("7. Clicked on a random brand link");

        // 8. Verify that user is navigated to that brand page and can see products:

        String currentURL = driver.getCurrentUrl();

        if (currentURL.contains(expectedBrandURL)) {
            System.out.println("8. Navigated to the correct brand page: " + currentURL);
        } else {
            System.out.println("8. Navigation handled incorrectly");
        }
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }

}