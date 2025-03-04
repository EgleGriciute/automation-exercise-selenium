import java.io.IOException;

import org.openqa.selenium.*;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class RegisterUser_01 {

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
        System.out.println("3. Page title: " + browserSetup.getPageTitle(driver));

    }

    @Test
    public void registerNewUser() throws InterruptedException, IOException {

        // 4. Click on 'Signup / Login' button:
        // 5. Verify 'New User Signup!' is visible:
        // 6. Enter name and email address:
        // 7. Click 'Signup' button:
        // 8. Verify that 'ENTER ACCOUNT INFORMATION' is visible:
        // 9. Fill details: Title, Name, Email, Password, Date of birth:
        // 10. Select checkbox 'Sign up for our newsletter!':
        // 11. Select checkbox 'Receive special offers from our partners!':
        // 12. Fill details: First name, Last name, Company, Address, Address2, Country,
        // State, City, Zipcode, Mobile Number:
        // 13. Click 'Create Account button':
        // 14. Verify that 'ACCOUNT CREATED!' is visible:
        // 15. Click 'Continue' button:
        // 16. Verify that 'Logged in as username' is visible:

        UserRegistrationHelper registrationHelper = new UserRegistrationHelper(driver);
        registrationHelper.registerNewUser();

        System.out.println("4. Clicked on 'Signup / Login' button \r\n" + //
                "5. Verified 'New User Signup!' is visible \r\n" + //
                "6. Entered name and email address \r\n" + //
                "7. Clicked on 'Signup' button \r\n" + //
                "8. Verified that 'ENTER ACCOUNT INFORMATION' is visible \r\n" + //
                "9. Filled details: Title, Name, Email, Password, Date of birth \r\n" + //
                "10. Selected checkbox 'Sign up for our newsletter!' \r\n" + //
                "11. Selected checkbox 'Receive special offers from our partners!' \r\n" + //
                "12. Filled details: First name, Last name, Company, Address, Address2, Country,\r\n" + //
                "State, City, Zipcode, Mobile Number \r\n" + //
                "13. Clicked on 'Create Account button' \r\n" + //
                "14. Verified that 'ACCOUNT CREATED!' is visible:\r\n" + //
                "15. Clicked on 'Continue' button \r\n" + //
                "16. Verified that 'Logged in as username' is visible ");

        // 17. Click 'Delete Account' button:
        driver.findElement(By.cssSelector("ul.nav li > a[href='/delete_account']")).click();
        System.out.println("17. Delete Account button clicked");

        // 18. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button:
        WebElement accountDeletedAlert = driver.findElement(By.cssSelector("[data-qa='account-deleted']"));
        System.out.println("18. Account Deleted message: " + accountDeletedAlert.getText());
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}
