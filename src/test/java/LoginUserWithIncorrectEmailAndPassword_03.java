import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import io.github.cdimascio.dotenv.Dotenv;

public class LoginUserWithIncorrectEmailAndPassword_03 {

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
    public void loginUserWithInvalidCredentials() {
        // Load environment variables:
        Dotenv dotenv = Dotenv.load();

        String email = dotenv.get("EMAIL");
        String password = dotenv.get("PASSWORD");

        // 6. Enter incorrect email address and password:
        driver.findElement(By.cssSelector("[data-qa='login-email']")).sendKeys("*" + email);
        driver.findElement(By.cssSelector("[data-qa='login-password']")).sendKeys("*" + password);

        System.out.println("6. Incorrect email address and password provided");

        // 7. Click 'login' button:
        driver.findElement(By.cssSelector("[data-qa='login-button']")).click();
        System.out.println("7. Login button clicked");

        // 8. Verify error 'Your email or password is incorrect!' is visible:
        WebElement messageYourEmailOrPasswordIsIncorrect = driver
                .findElement(By.xpath("//p[text()='Your email or password is incorrect!']"));

        System.out.println("8. Verify error 'Your email or password is incorrect!' is visible: "
                + messageYourEmailOrPasswordIsIncorrect.getText());
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}
