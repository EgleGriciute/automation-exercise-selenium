import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class UserRegistrationHelper {

    private WebDriver driver;

    private String userName;
    private String dynamicEmail;
    private String dynamicPassword;
    private String dynamicAddress1;
    private String dynamicAddress2;

    // Constructor accepts the WebDriver from the calling class:
    public UserRegistrationHelper(WebDriver driver) {
        // Use the existing WebDriver session:
        this.driver = driver;
    }

    public void registerNewUser() throws InterruptedException, IOException {
        System.out.println("\n | Registration started | \n");

        // 4. Click on 'Signup / Login' button:
        driver.findElement(By.cssSelector("[href='/login']")).click();
        System.out.println("Signup/Login button clicked");

        // 5. Verify 'New User Signup!' is visible:
        WebElement h2NewUserSignup = driver.findElement(By.cssSelector(".signup-form h2"));
        System.out.println("Verify 'New User Signup!' is visible: " + h2NewUserSignup.getText());

        // Generate dynamic user data

        userName = "FirstName";
        dynamicEmail = "email" + System.currentTimeMillis() + "@gmail.com";
        dynamicPassword = "pwd" + System.currentTimeMillis();
        dynamicAddress1 = "address1" + System.currentTimeMillis();
        dynamicAddress2 = "address2" + System.currentTimeMillis();

        // Write credentials to .env file:
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(".env"))) {
            writer.write("NAME=" + userName);
            writer.newLine();
            writer.write("EMAIL=" + dynamicEmail);
            writer.newLine();
            writer.write("PASSWORD=" + dynamicPassword);
            writer.newLine();
            writer.write("ADDRESS1=" + dynamicAddress1);
            writer.newLine();
            writer.write("ADDRESS2=" + dynamicAddress2);
            writer.newLine();
        }

        // 6. Enter name and email address:
        driver.findElement(By.cssSelector("[data-qa='signup-name']")).sendKeys(userName);
        driver.findElement(By.cssSelector("[data-qa='signup-email']")).sendKeys(dynamicEmail);
        System.out.println("Input values provided: Name - Lorem, Email - " + dynamicEmail);

        // 7. Click 'Signup' button:
        driver.findElement(By.cssSelector("[data-qa='signup-button']")).click();
        System.out.println("Signup button clicked");

        // 8. Verify that 'ENTER ACCOUNT INFORMATION' is visible:
        WebElement accountInfoHeader = driver.findElement(By.cssSelector(".login-form h2 > b"));
        System.out.println("ENTER ACCOUNT INFORMATION is visible: " + accountInfoHeader.getText());

        // 9. Fill details - Title, Name:
        driver.findElement(By.cssSelector("#uniform-id_gender2")).click();
        driver.findElement(By.cssSelector("[data-qa='name']")).clear();
        driver.findElement(By.cssSelector("[data-qa='name']")).sendKeys(userName);

        // Email:
        WebElement inputEmailSignup = driver.findElement(By.cssSelector("[data-qa='email']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('disabled')", inputEmailSignup);
        inputEmailSignup.clear();
        inputEmailSignup.sendKeys(dynamicEmail);

        // Password:
        driver.findElement(By.cssSelector("[data-qa='password']")).sendKeys(dynamicPassword);

        // Date of birth:
        new Select(driver.findElement(By.cssSelector("[data-qa='days']"))).selectByValue("18");
        new Select(driver.findElement(By.cssSelector("[data-qa='months']"))).selectByValue("7");
        new Select(driver.findElement(By.cssSelector("[data-qa='years']"))).selectByValue("1994");

        System.out.println("Account information filled successfully");

        // 10. Select checkbox 'Sign up for our newsletter!':
        WebElement newsletterCheckbox = driver.findElement(By.cssSelector("#newsletter"));
        if (!newsletterCheckbox.isSelected())
            newsletterCheckbox.click();
        System.out.println("Newsletter checkbox selected");

        // 11. Select checkbox 'Receive special offers from our partners!':
        WebElement offersCheckbox = driver.findElement(By.cssSelector("#optin"));
        if (!offersCheckbox.isSelected())
            offersCheckbox.click();
        System.out.println("Special offers checkbox selected");

        // 12. Fill details: First name, Last name, Company, Address, Address2, Country,
        // State, City, Zipcode, Mobile Number:
        driver.findElement(By.cssSelector("#first_name")).sendKeys(userName);
        driver.findElement(By.cssSelector("#last_name")).sendKeys("LastName");
        driver.findElement(By.cssSelector("#company")).sendKeys("MIT");
        driver.findElement(By.cssSelector("#address1")).sendKeys(dynamicAddress1);
        driver.findElement(By.cssSelector("#address2")).sendKeys(dynamicAddress2);
        new Select(driver.findElement(By.cssSelector("#country"))).selectByValue("United States");
        driver.findElement(By.cssSelector("#state")).sendKeys("Massachusetts");
        driver.findElement(By.cssSelector("#city")).sendKeys("Cambridge");
        driver.findElement(By.cssSelector("#zipcode")).sendKeys("20861");
        driver.findElement(By.cssSelector("#mobile_number")).sendKeys("+1 (555) 123-4567");

        System.out.println("Address details filled");

        // 13. Click 'Create Account button':
        driver.findElement(By.cssSelector("[data-qa='create-account']")).click();
        System.out.println("Create Account button clicked");

        // 14. Verify that 'ACCOUNT CREATED!' is visible:
        WebElement accountCreatedMessage = driver.findElement(By.cssSelector("[data-qa='account-created'] b"));
        System.out.println("Account Created message: " + accountCreatedMessage.getText());

        // 15. Click 'Continue' button:
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();
        System.out.println("Continue button clicked");

        // 16. Verify that 'Logged in as username' is visible:
        String loggedInUser = driver.findElement(By.cssSelector("ul.nav li:last-child a")).getText();
        System.out.println("Verify that 'Logged in as username' is visible: " + loggedInUser);
        System.out.println("\n | Registration completed successfully | \n");
    }
}
