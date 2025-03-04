import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.time.Duration;
import io.github.cdimascio.dotenv.Dotenv;

public class ContactUsForm_06 {

    private WebDriver driver;

    Dotenv dotenv = Dotenv.load();

    String dynamicEmail = dotenv.get("EMAIL");
    String userName = dotenv.get("NAME");

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
    public void contactUsForm() {

        // 4. Click on 'Contact Us' button:
        driver.findElement(By.cssSelector("[href='/contact_us']")).click();
        System.out.println("4. Clicked on 'Contact Us' button");

        // 5. Verify 'GET IN TOUCH' is visible:
        WebElement h2GetInTouch = driver.findElement(By.cssSelector(".contact-form h2"));
        System.out.println("5. Verify 'GET IN TOUCH' is visible: " + h2GetInTouch.getText());

        // 6. Enter name, email, subject and message:
        driver.findElement(By.xpath("//form[@id='contact-us-form']//input[@data-qa='name']")).sendKeys(userName);
        driver.findElement(By.xpath("//form[@id='contact-us-form']//input[@data-qa='email']")).sendKeys(dynamicEmail);

        driver.findElement(By.xpath("//form[@id='contact-us-form']//input[@data-qa='subject']"))
                .sendKeys("SubjectLoremIpsum");

        driver.findElement(By.xpath("//form[@id='contact-us-form']//textarea[@data-qa='message']"))
                .sendKeys("MessageLoremIpsum");

        System.out.println("6. Name, email, subject and message values provided");

        // 7. Upload file:
        WebElement uploadElement = driver.findElement(By.xpath("//input[@type='file' and @name='upload_file']"));

        String filePath = new File("src/test/resources/test.txt").getAbsolutePath();
        uploadElement.sendKeys(filePath);

        System.out.println("7. File uploaded");

        // 8. Click 'Submit' button:
        driver.findElement(By.cssSelector("[data-qa='submit-button']")).click();
        System.out.println("8. Clicked on 'Submit' button ");

        // 9. Click OK button:
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();
        alert.accept();

        System.out.println("9. Alert accepted");

        // 10. Verify success message 'Success! Your details have been submitted
        // successfully.' is visible:
        WebElement messageSuccessYourDetailsHaveBeenSubmiElement = driver
                .findElement(By.xpath("//*[text()='Success! Your details have been submitted successfully.']"));

        System.out.println("10. Verify success message: " + messageSuccessYourDetailsHaveBeenSubmiElement.getText());

        // 11. Click 'Home' button and verify that landed to home page successfully:
        driver.findElement(By.cssSelector("a[href='/']")).click();
        String currentUrl = driver.getCurrentUrl();

        System.out.println("11. Page navigated to home page: " + currentUrl);
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();

    }
}