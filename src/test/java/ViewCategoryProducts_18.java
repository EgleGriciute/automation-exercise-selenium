import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Random;
import java.time.Duration;

public class ViewCategoryProducts_18 {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void setup() throws InterruptedException {

        // 1. Launch browser:
        HomePageVisibleHelper browserSetup = new HomePageVisibleHelper();
        driver = browserSetup.initializeBrowser("Chrome");

        // Initialize WebDriverWait with a timeout of 10 seconds
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 2. Navigate to url:
        browserSetup.openHomePage(driver);

        // Handle cookie consent:
        browserSetup.handleCookieConsent(driver);
    }

    @Test
    public void viewCategoryProducts() throws InterruptedException {
        // 3. Verify categories are visible on the sidebar:
        WebElement categoriesSection = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".panel-group")));
        List<WebElement> categories = categoriesSection.findElements(By.cssSelector(".panel-group .panel-title a"));

        if (!categories.isEmpty()) {
            System.out.println("3. Categories are visible on the left sidebar:");
            for (WebElement category : categories) {
                System.out.println(category.getText());
            }
        } else {
            System.out.println("3. Categories are NOT visible on the left sidebar");
            return;
        }

        // 4. Click on 'Women' category:
        WebElement categoryWomen = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='#Women']")));
        categoryWomen.click();

        // Wait for the dropdown to expand:
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@id='Women']//a[contains(@href, 'category_products')]")));

        System.out.println("4. Clicked on 'Women' category");

        // 5. Click on any subcategory under 'Women'
        List<WebElement> categoryLinksWomen = wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(
                        By.xpath("//div[@id='Women']//a[contains(@href, 'category_products')]")));

        Random rand = new Random();
        WebElement randomSubcategoryWomen = categoryLinksWomen.get(rand.nextInt(categoryLinksWomen.size()));

        randomSubcategoryWomen.click();

        System.out.println("5. Clicked on a random subcategory");

        // 6. Verify that category page is displayed and confirm text 'WOMEN - TOPS
        // PRODUCTS'

        // Wait for the exact text 'WOMEN - TOPS PRODUCTS' to be visible
        WebElement messageCategoryProducts = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Women - Tops Products')]")));

        if (messageCategoryProducts.isDisplayed()) {
            System.out.println("6. Category page is displayed: " + messageCategoryProducts.getText());
        } else {
            System.out.println("6. Different category page displayed");
        }

        // 7. On left side bar, click on any sub-category link of 'Men' category:
        WebElement categoryMen = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='#Men']")));
        categoryMen.click();

        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@id='Men']//a[contains(@href, 'category_products')]")));

        List<WebElement> categoryLinksMen = wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(
                        By.xpath("//div[@id='Men']//a[contains(@href, 'category_products')]")));

        WebElement randomSubcategoryMen = categoryLinksMen
                .get(rand.nextInt(categoryLinksMen.size()));
        randomSubcategoryMen.click();

        System.out.println("7. Clicked on a random subcategory under 'Men'");

        // 8. Verify that user is navigated to that category page:
        String currentUrlAfterCategoryMenClick = driver.getCurrentUrl();

        if (currentUrlAfterCategoryMenClick.contains("/category_products/3")) {
            System.out.println("8. User is navigated to that category page: TSHIRTS");
        } else {
            System.out.println("8. User is navigated to that category page: JEANS");
        }
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}
