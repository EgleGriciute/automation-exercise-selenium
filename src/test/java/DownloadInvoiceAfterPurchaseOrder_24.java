import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DownloadInvoiceAfterPurchaseOrder_24 {

    private WebDriver driver;
    private final String location = System.getProperty("user.dir")
            + "\\src\\test\\downloaded.files";

    // Browser to be used ("Chrome" or "Firefox"):
    private final String browser = "Chrome";

    @BeforeTest
    public void setup() throws IOException, InterruptedException {

        // Initializing the WebDriver based on the selected browser:
        this.driver = getWebDriver(browser);

        System.out.println("1. Browser launched: " + browser);

        // Setting up the path to the GeckoDriver for Firefox:
        String driverPath = Paths.get("drivers").toAbsolutePath().toString();
        System.setProperty("webdriver.gecko.driver", driverPath + "\\geckodriver.exe");

        // Checking if the WebDriver was initialized properly:
        if (driver == null) {
            System.out.println("Invalid browser specified. Exiting test.");
            return;
        }

        // Instantiating HomePageVisibleHelper and opening the home page
        HomePageVisibleHelper browserSetup = new HomePageVisibleHelper();
        browserSetup.openHomePage(driver);

        browserSetup.handleCookieConsent(driver);

        // Logging the title of the page after successful loading
        System.out.println("3. Verify that home page is visible successfully: " + browserSetup.getPageTitle(driver));

        // Cleaning the download folder before the test starts:
        cleanFolder();
    }

    // Method to initialize the WebDriver based on the specified browser:
    public WebDriver getWebDriver(String browser) {
        WebDriver driver = null;

        if (browser.equalsIgnoreCase("Chrome")) {
            // Setting the path for ChromeDriver:
            System.setProperty("webdriver.chrome.driver",
                    System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");

            // Setting up Chrome options for the download folder and other preferences:
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<>();

            // Set the download directory:
            prefs.put("download.default_directory", location);
            // Disable popups:
            prefs.put("profile.default_content_settings.popups", 0);
            // Disable download prompt:
            prefs.put("download.prompt_for_download", false);
            // Allow download directory upgrade:
            prefs.put("download.directory_upgrade", true);

            options.setExperimentalOption("prefs", prefs);

            // Initializing ChromeDriver with the configured options:
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("Firefox")) {
            // Setting the path for GeckoDriver:
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\drivers\\geckodriver.exe");

            // Setting up Firefox options for download preferences:
            FirefoxOptions options = new FirefoxOptions();

            // Set custom download folder:
            options.addPreference("browser.download.folderList", 2);

            // Set download directory:
            options.addPreference("browser.download.dir", location);

            // Define mime types for download:
            options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream,application/pdf");

            // Disable download manager:
            options.addPreference("browser.download.manager.showWhenStarting", false);

            // Initializing FirefoxDriver with the configured options:
            driver = new FirefoxDriver(options);
        }

        return driver;
    }

    // Method to clean the download folder by removing existing files:
    public void cleanFolder() throws IOException {
        File folder = new File(location);
        // Checking if the folder exists and cleaning it:
        if (folder.exists()) {
            FileUtils.cleanDirectory(folder);
        }
    }

    @Test
    public void downloadInvoiceAfterPurchaseOrder() throws InterruptedException, IOException {
        // Creating an explicit wait for elements:
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 4. Add products to cart:

        // Adding the first product to the cart and closing the modal:
        WebElement firstProductAddToCart = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-product-id='1']")));

        firstProductAddToCart.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-dismiss='modal']"))).click();

        // Adding the second product to the cart and closing the modal:
        WebElement secondProductAddToCart = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-product-id='2']")));

        secondProductAddToCart.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-dismiss='modal']"))).click();

        System.out.println("4. Added products to cart");

        // 5. Click 'Cart' button:
        driver.findElement(By.cssSelector("a[href='/view_cart']")).click();
        System.out.println("5. Clicked on a cart button");

        // 6. Verify that cart page is displayed:
        String currentURL = driver.getCurrentUrl();
        String cartPageURL = "https://www.automationexercise.com/view_cart";

        if (currentURL.equals(cartPageURL)) {
            System.out.println("6. Cart page displayed");
        } else {
            System.out.println("6. Other page displayed: " + currentURL);
        }

        // 7. Click Proceed To Checkout:
        driver.findElement(By.cssSelector("a.check_out")).click();
        System.out.println("7. Clicked 'Proceed To Checkout'");

        // 8. Click 'Register / Login' button:
        driver.findElement(By.linkText("Register / Login")).click();
        System.out.println("8. Clicked 'Register / Login' button");

        // 9. Fill all details in Signup and create account:
        // 10. Verify 'ACCOUNT CREATED!' and click 'Continue' button:
        // 11. Verify ' Logged in as username' at top:
        UserRegistrationHelper registrationHelper = new UserRegistrationHelper(driver);
        registrationHelper.registerNewUser();

        System.out.println("9. Filled all details in Signup and created account\n" +
                "10. Verified 'ACCOUNT CREATED!' \n Clicked on 'Continue' button\n" +
                "11. Verified ' Logged in as username' at top");

        // 12.Click 'Cart' button:
        driver.findElement(By.cssSelector("a[href='/view_cart']")).click();
        System.out.println("12. Clicked on 'Cart' button");

        // 13. Click 'Proceed To Checkout' button:
        driver.findElement(By.cssSelector("a.check_out")).click();
        System.out.println("13. Clicked on 'Proceed To Checkout' button");

        // 14. Verify Address Details and Review Your Order:

        WebElement textAddressDetails = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Address Details')]")));

        WebElement textReviewYourOrder = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Review Your Order')]")));

        if (!textAddressDetails.getText().isEmpty() && !textReviewYourOrder.getText().isEmpty()) {
            System.out
                    .println("14. Verified: " + textAddressDetails.getText() + " and " + textReviewYourOrder.getText());
        } else {
            System.out.println("DOM elements not present");
        }

        // 15. Enter description in comment text area and click 'Place Order':
        driver.findElement(By.cssSelector("textarea[name='message']")).sendKeys("Lorem ipsum");
        driver.findElement(By.cssSelector("a[href='/payment']")).click();

        System.out.println("15. Description entered and clicked on 'Place Order'");

        // 16. Enter payment details: Name on Card, Card Number, CVC, Expiration date:
        driver.findElement(By.cssSelector("input[name='name_on_card']")).sendKeys("FULL NAME");
        driver.findElement(By.cssSelector("input[name='card_number']")).sendKeys("378282246310005");
        driver.findElement(By.cssSelector("input[name='cvc']")).sendKeys("466");
        driver.findElement(By.cssSelector("input[name='expiry_month']")).sendKeys("12");
        driver.findElement(By.cssSelector("input[name='expiry_year']")).sendKeys("2027");

        System.out.println("16. Payment details entered");

        // 17. Click 'Pay and Confirm Order' button:
        driver.findElement(By.cssSelector("[data-qa='pay-button']")).click();
        System.out.println("17. Clicked 'Pay and Confirm Order'");

        // 18. Verify success message 'Your order has been placed successfully!':
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String successMessageOrderPlaced = (String) js
                .executeScript("return document.querySelector('.alert-success').textContent.trim();");

        System.out.println("18. Verified: " + successMessageOrderPlaced);

        // 19. Click 'Download Invoice' button and verify invoice is downloaded
        // successfully:
        driver.findElement(By.cssSelector(".check_out")).click();
        Assert.assertTrue(verifyDownloadedFile(), "Invoice file was not downloaded");

        System.out.println("19. Clicked on 'Download Invoice' button and verified invoice is downloaded successfully");

        // 20. Click 'Continue' button:
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();
        System.out.println("20. Clicked on 'Continue' button");

        // 21. Click 'Delete Account' button:
        driver.findElement(By.cssSelector("ul.nav li > a[href='/delete_account']")).click();
        System.out.println("21. Clicked on 'Delete Account' button ");

        // 22. Verify 'ACCOUNT DELETED!' and click 'Continue' button:
        WebElement accountDeletedAlert = driver.findElement(By.cssSelector("[data-qa='account-deleted']"));
        System.out.println(
                "22. Account Deleted message: " + accountDeletedAlert.getText()
                        + "\nClicked on 'Continue' button");

        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();
    }

    // Method to verify if the invoice file is downloaded:
    public boolean verifyDownloadedFile() {
        File folder = new File(location);
        // Checking if the folder exists:
        if (!folder.exists()) {
            System.out.println("Download folder does not exist");
            return false;
        }

        // Listing files in the folder:
        File[] fileList = folder.listFiles();
        if (fileList == null || fileList.length == 0) {
            System.out.println("No files found in the download directory");
            return false;
        }

        // Attempting to find the downloaded file (Invoice file) up to 10 times:
        for (int i = 0; i < 10; i++) {
            System.out.println("Attempt " + (i + 1) + " to find file...");

            // Checking each file in the folder"
            for (File file : fileList) {
                System.out.println("Found file: " + file.getName());
                // Checking if the file is an invoice (by name and extension):
                if (file.getName().contains("invoice") && file.getName().endsWith(".txt")) {
                    System.out.println("Invoice file found: " + file.getName());
                    return true;
                }
            }

            try {
                // Waiting 1 second before the next attempt:
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Re-listing files in case new ones are added during the waiting period:
            fileList = folder.listFiles();
        }
        // Return false if file is not found after 10 attempts:
        return false;
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}
