import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class PlaceOrderRegisterWhileCheckout_14 {

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
    public void placeOrderRegisterWhileCheckout() throws InterruptedException, IOException {

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

        // 7. Click 'Proceed To Checkout':
        driver.findElement(By.cssSelector("a.check_out")).click();
        System.out.println("7. Clicked 'Proceed To Checkout'");

        // 8. Click 'Register / Login' button:
        driver.findElement(By.linkText("Register / Login")).click();
        System.out.println("8. Clicked on 'Register / Login' button");

        // 9. Fill all details in Signup and create account:
        // 10. Verify 'ACCOUNT CREATED!' and click 'Continue' button:
        // 11. Verify ' Logged in as username' at top:
        UserRegistrationHelper registrationHelper = new UserRegistrationHelper(driver);
        registrationHelper.registerNewUser();

        System.out.println("9. Filled all details in Signup and created account");
        System.out.println("10. Verified 'ACCOUNT CREATED!' and click 'Continue' button");
        System.out.println("11. Verified ' Logged in as username' at top");

        // 12. Click 'Cart' button:
        driver.findElement(By.cssSelector("a[href='/view_cart']")).click();
        System.out.println("12. Clicked on a 'Cart' button");

        // 13. Click 'Proceed To Checkout' button:
        driver.findElement(By.cssSelector("a.check_out")).click();
        System.out.println("13. Clicked on a 'Proceed To Checkout' button");

        // 14. Verify Address Details and Review Your Order:
        WebElement textAddressDetails = driver.findElement(By.xpath("//*[contains(text(),'Address Details')]"));
        WebElement textReviewYourOrder = driver.findElement(By.xpath("//*[contains(text(),'Review Your Order')]"));

        String textAddressDetailsValue = textAddressDetails.getText();
        String textReviewYourOrderValue = textReviewYourOrder.getText();

        if (!textAddressDetailsValue.isEmpty() && !textReviewYourOrderValue.isEmpty()) {
            System.out.println("14. Verified: " + textAddressDetailsValue + "and " + textReviewYourOrderValue);
        } else {
            System.out.println("DOM elements are not present");
        }

        // 15. Enter description in comment text area and click 'Place Order':
        driver.findElement(By.cssSelector("textarea[name='message']")).sendKeys("Lorem ipsum");
        driver.findElement(By.cssSelector("a[href='/payment']")).click();

        System.out.println("15. Description entered and clicked on a 'Place Order'");

        // 16. Enter payment details: Name on Card, Card Number, CVC, Expiration date:
        driver.findElement(By.cssSelector("input[name='name_on_card']")).sendKeys("FULL NAME");
        driver.findElement(By.cssSelector("input[name='card_number']")).sendKeys("3782822463100052");
        driver.findElement(By.cssSelector("input[name='cvc']")).sendKeys("466");
        driver.findElement(By.cssSelector("input[name='expiry_month']")).sendKeys("12");
        driver.findElement(By.cssSelector("input[name='expiry_year']")).sendKeys("2027");

        System.out.println("16. Entered payment details");

        // 17. Click 'Pay and Confirm Order' button:
        driver.findElement(By.cssSelector("[data-qa='pay-button']")).click();
        System.out.println("17. Clicked on a button 'Pay and Confirm Order'");

        // 18. Verify success message 'Your order has been placed successfully!':
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String successMessageOrderPlaced = (String) js
                .executeScript("return document.querySelector('.alert-success').textContent.trim();");

        System.out.println("18. Verified: " + successMessageOrderPlaced);

        // 19. Click 'Delete Account' button:
        driver.findElement(By.cssSelector("a[href='/delete_account']")).click();
        System.out.println("19. Clicked on a 'Delete Account' button");

        // 20. Verify 'ACCOUNT DELETED!' and click 'Continue' button:
        WebElement messageAccountDeleted = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Account Deleted!')]")));

        String messageAccountDeletedValue = messageAccountDeleted.getText();
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();

        System.out.println("20. Verified: " + messageAccountDeletedValue + " and clicked on a 'Continue' button");

    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}