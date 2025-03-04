import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.cdimascio.dotenv.Dotenv;

public class RegisterUserWithExistingEmail_05 {

    private WebDriver driver;

    Dotenv dotenv = Dotenv.load();

    String userName = dotenv.get("NAME");
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
    public void registerUserWithExistingEmail() {
        // 4. Click on 'Signup / Login' button:
        driver.findElement(By.cssSelector("[href='/login']")).click();
        System.out.println("4. Clicked on Signup/Login button");

        // 5. Verify 'New User Signup!' is visible:
        WebElement h2NewUserSignup = driver.findElement(By.cssSelector(".signup-form h2"));
        System.out.println("5. Verify 'New User Signup!' is visible: " + h2NewUserSignup.getText());

        // 6. Enter name and email address:
        driver.findElement(By.cssSelector("[data-qa='signup-name']")).sendKeys(userName);
        driver.findElement(By.cssSelector("[data-qa='signup-email']")).sendKeys(dynamicEmail);

        System.out.println("6. Input values provided: Name - " + userName + "," + "Email - " + dynamicEmail);

        // 7. Click 'Signup' button:
        driver.findElement(By.cssSelector("[data-qa='signup-button']")).click();
        System.out.println("7. Clicked on Signup button ");

        // 8. Verify error 'Email Address already exist!' is visible:
        WebElement alertEmailAddressAlreadyExist = driver
                .findElement(By.xpath("//*[text()='Email Address already exist!']"));

        System.out.println("8. Verify error 'Email Address already exist!' is visible: "
                + alertEmailAddressAlreadyExist.getText());
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}
