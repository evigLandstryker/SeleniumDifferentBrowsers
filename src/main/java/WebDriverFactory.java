import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverFactory {
    public WebDriver createWebDriver (DriverType driverType) {
        WebDriver driver;
        switch(driverType){
            case CHROME:
                driver = createChromeDriver();
                break;
            case FIREFOX:
                driver = createFireFoxDriver();
                break;
            default:
                throw new RuntimeException("Not supported webdriver");
        }
        return driver;
    }

    private WebDriver createChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }

    private WebDriver createFireFoxDriver() {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }
}
