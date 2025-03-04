import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

// HomePageVisibleHelper class executes 3 re-occuring test case steps:

// 1. Launch browser:
// 2. Navigate to url 'http://automationexercise.com':
// 3. Verify that home page is visible successfully:

public class HomePageVisibleHelper {
    public WebDriver driver;

    // 1. Launch browser:
    public WebDriver initializeBrowser(String browser) {
        boolean headless = Boolean.parseBoolean(System.getProperty("selenium.headless", "false"));

        if (browser.equalsIgnoreCase("Chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (headless)
                options.addArguments("--headless");
            options.addArguments("--disable-gpu", "--window-size=1920,1080", "--no-sandbox");
            return new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("Firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (headless)
                options.addArguments("--headless");
            options.addArguments("--width=1920", "--height=1080");
            return new FirefoxDriver(options);
        }
        throw new IllegalArgumentException("Unsupported browser: " + browser);
    }

    // 2. Navigate to url 'http://automationexercise.com':
    public void openHomePage(WebDriver driver) {
        driver.manage().window().maximize();
        String baseUrl = "https://automationexercise.com/";
        driver.get(baseUrl);
        System.out.println("2. Navigated to baseUrl: " + baseUrl);
    }

    // Method to handle cookie consent
    public void handleCookieConsent(WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        WebElement consentButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("button.fc-cta-consent")));
        Thread.sleep(1000);
        consentButton.click();
    }

    // 3. Verify that home page is visible successfully:
    public String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

}