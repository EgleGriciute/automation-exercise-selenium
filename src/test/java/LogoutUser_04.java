import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import io.github.cdimascio.dotenv.Dotenv;

public class LogoutUser_04 {

    private WebDriver driver;

    @BeforeTest
    public void setup() throws InterruptedException, IOException {

        // 1. Launch browser:
        HomePageVisibleHelper browserSetup = new HomePageVisibleHelper();
        driver = browserSetup.initializeBrowser("Chrome");

        // 2. Navigate to url 'http://automationexercise.com':
        browserSetup.openHomePage(driver);

        // Handle cookie consent:
        browserSetup.handleCookieConsent(driver);

        // 3. Verify that home page is visible successfully:
        System.out.println("3. Verify that home page is visible successfully: " + browserSetup.getPageTitle(driver));

        // 4. Click on 'Signup / Login' button:
        System.out.println("4. Clicked on 'Signup / Login' button");
        UserRegistrationHelper registrationHelper = new UserRegistrationHelper(driver);
        registrationHelper.registerNewUser();

        driver.findElement(By.cssSelector("[href='/logout']")).click();

        // 5. Verify 'Login to your account' is visible:
        WebElement h2NewUserSignup = driver.findElement(By.cssSelector(".login-form h2"));
        System.out.println("5. Verify 'Login to your account' is visible: " + h2NewUserSignup.getText());
    }

    @Test
    public void logoutUser() {

        // Load environment variables:
        Dotenv dotenv = Dotenv.load();

        String email = dotenv.get("EMAIL");
        String password = dotenv.get("PASSWORD");

        // 6. Enter correct email address and password:
        driver.findElement(By.cssSelector("[data-qa='login-email']")).sendKeys(email);
        driver.findElement(By.cssSelector("[data-qa='login-password']")).sendKeys(password);
        System.out.println("6. Correct email address and password entered");

        // 7. Click 'login' button:
        driver.findElement(By.cssSelector("[data-qa='login-button']")).click();
        System.out.println("7. Login button fired");

        // 8. Verify that 'Logged in as username' is visible:
        WebElement loggedinUsername = driver.findElement(By.cssSelector("ul > li:nth-child(10)"));
        System.out.println("8. Verify that 'Logged in as username' is visible: " + loggedinUsername.getText());

        // 9. Click 'Logout' button:
        driver.findElement(By.cssSelector("a[href='/logout']")).click();
        System.out.println("9. Clicked on logout button ");

        // 10. Verify that user is navigated to login page:
        String urlLoginPage = driver.getCurrentUrl();
        System.out.println("10. Verify that user is navigated to login page: " + urlLoginPage);

    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}