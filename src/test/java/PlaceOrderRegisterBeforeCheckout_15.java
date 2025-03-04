import org.openqa.selenium.*;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class PlaceOrderRegisterBeforeCheckout_15 {

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
    public void placeOrderRegisterBeforeCheckout() throws InterruptedException, IOException {

        // 4. Click 'Signup / Login' button:
        // 5. Fill all details in Signup and create account:
        // 6. Verify 'ACCOUNT CREATED!' and click 'Continue' button:
        // 7. Verify ' Logged in as username' at top:

        UserRegistrationHelper registrationHelper = new UserRegistrationHelper(driver);
        registrationHelper.registerNewUser();

        System.out.println("4. Clicked on'Signup / Login' button\r\n" + //
                "5. Filled all details in Signup and created account\r\n" + //
                "6. Verified 'ACCOUNT CREATED!' and clicked on 'Continue' button\r\n" + //
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

        // 11. Click 'Proceed To Checkout':
        driver.findElement(By.cssSelector("a.check_out")).click();
        System.out.println("11. Clicked 'Proceed To Checkout'");

        // 12. Verify Address Details and Review Your Order:
        WebElement textAddressDetails = driver.findElement(By.xpath("//*[contains(text(),'Address Details')]"));
        WebElement textReviewYourOrder = driver.findElement(By.xpath("//*[contains(text(),'Review Your Order')]"));

        String textAddressDetailsValue = textAddressDetails.getText();
        String textReviewYourOrderValue = textReviewYourOrder.getText();

        if (!textAddressDetailsValue.isEmpty() && !textReviewYourOrderValue.isEmpty()) {
            System.out.println("12. Verified: " + textAddressDetailsValue + " and " + textReviewYourOrderValue);
        } else {
            System.out.println("DOM elements not present");
        }

        // 13. Enter description in comment text area and click 'Place Order':
        driver.findElement(By.cssSelector("textarea[name='message']")).sendKeys("Lorem ipsum");
        driver.findElement(By.cssSelector("a[href='/payment']")).click();

        System.out.println("13. Description entered and clicked on a 'Place Order'");

        // 14. Enter payment details: Name on Card, Card Number, CVC, Expiration date:
        driver.findElement(By.cssSelector("input[name='name_on_card']")).sendKeys("FULL NAME");
        driver.findElement(By.cssSelector("input[name='card_number']")).sendKeys("3782822463100052");
        driver.findElement(By.cssSelector("input[name='cvc']")).sendKeys("466");
        driver.findElement(By.cssSelector("input[name='expiry_month']")).sendKeys("12");
        driver.findElement(By.cssSelector("input[name='expiry_year']")).sendKeys("2027");

        System.out.println("14. Entered Payment details");

        // 15. Click 'Pay and Confirm Order' button:
        driver.findElement(By.cssSelector("[data-qa='pay-button']")).click();
        System.out.println("15. Clicked on a button 'Pay and Confirm Order'");

        // 16. Verify success message 'Your order has been placed successfully!':
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String successMessageOrderPlaced = (String) js
                .executeScript("return document.querySelector('.alert-success').textContent.trim();");

        System.out.println("16. Verified: " + successMessageOrderPlaced);

        // 17. Click 'Delete Account' button:
        driver.findElement(By.cssSelector("a[href='/delete_account']")).click();
        System.out.println("17. Clicked on a 'Delete Account' button");

        // 18. Verify 'ACCOUNT DELETED!' and click 'Continue' button:
        WebElement messageAccountDeleted = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Account Deleted!')]")));

        String messageAccountDeletedValue = messageAccountDeleted.getText();
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();

        System.out.println("18. Verified: " + messageAccountDeletedValue + " and clicked on a 'Continue' button");
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}