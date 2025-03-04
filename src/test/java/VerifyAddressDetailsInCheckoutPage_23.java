import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.cdimascio.dotenv.Dotenv;

public class VerifyAddressDetailsInCheckoutPage_23 {

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
    public void verifyAddressDetailsInCheckoutPage() throws InterruptedException, IOException {
        // 4. Click 'Signup / Login' button:
        driver.findElement(By.cssSelector("[href='/login']")).click();
        System.out.println("4. Clicked on 'Signup / Login' button");

        // 5. Fill all details in Signup and create account:
        // 6. Verify 'ACCOUNT CREATED!' and click 'Continue' button:
        // 7. Verify ' Logged in as username' at top:

        UserRegistrationHelper registrationHelper = new UserRegistrationHelper(driver);
        registrationHelper.registerNewUser();

        System.out.println("5. Filled all details in Signup and created account \n" +
                "6. Verified 'ACCOUNT CREATED!' and clicked 'Continue' button \n" +
                "7. Verified ' Logged in as username' at top");

        // 8. Add products to cart:
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
        System.out.println("8. Added products to cart");

        driver.findElement(By.cssSelector("[data-dismiss='modal']")).click();

        // 9. Click 'Cart' button:
        driver.findElement(By.cssSelector("a[href='/view_cart']")).click();
        System.out.println("9. Clicked on a cart button");

        // 10. Verify that cart page is displayed:
        String cartPageURL = driver.getCurrentUrl();
        System.out.println("10. Cart page displayed: " + cartPageURL);

        // 11. Click Proceed To Checkout:
        driver.findElement(By.cssSelector("a.check_out")).click();
        System.out.println("11. Clicked 'Proceed To Checkout'");

        // 12. Verify that the delivery address is same address filled at the time
        // registration of account:

        Dotenv dotenv = Dotenv.load();

        String dynamicAddress1 = dotenv.get("ADDRESS1");
        String dynamicAddress2 = dotenv.get("ADDRESS2");

        // Delivery address:
        WebElement deliveryAddress1 = driver.findElement(By.cssSelector("ul#address_delivery > li:nth-child(4)"));
        WebElement deliveryAddress2 = driver.findElement(By.cssSelector("ul#address_delivery > li:nth-child(5)"));

        if (dynamicAddress1.equals(deliveryAddress1.getText()) && dynamicAddress2.equals(deliveryAddress2.getText())) {
            System.out.println("12. The delivery address is same address filled at the time registration of account");
        } else {
            System.out
                    .println("12. The delivery address is not same address filled at the time registration of account");
        }

        // 13. Verify that the billing address is same address filled at the time
        // registration of account:

        // Billing address:
        WebElement billingAddress1 = driver.findElement(By.cssSelector("ul#address_invoice > li:nth-child(4)"));
        WebElement billingAddress2 = driver.findElement(By.cssSelector("ul#address_invoice > li:nth-child(5)"));

        if (dynamicAddress1.equals(billingAddress1.getText()) && dynamicAddress2.equals(billingAddress2.getText())) {
            System.out.println("13. The billing address is same address filled at the time registration of account");
        } else {
            System.out
                    .println("13. The billing address is not same address filled at the time registration of account");
        }

        // 14. Click 'Delete Account' button:
        driver.findElement(By.cssSelector("ul.nav li > a[href='/delete_account']")).click();
        System.out.println("14. Delete Account button clicked");

        // 15. Verify 'ACCOUNT DELETED!' and click 'Continue' button:
        WebElement accountDeletedAlert = driver.findElement(By.cssSelector("[data-qa='account-deleted']"));

        System.out.println("15. Account Deleted message: " + accountDeletedAlert.getText());
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}
