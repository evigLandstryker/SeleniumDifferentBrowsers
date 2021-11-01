import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    //Value
    WebDriverFactory webDriverFactory = new WebDriverFactory();
    WebDriver driver;
    String url = "https://www.google.com";
    String query = "SoftServe";
    String softserve = "softserve";

    By searchFieldChromeQuery = By.xpath("//input[@name='q']");
    By resultList = By.xpath("//div[@class='g']");
    By getResultList = By.cssSelector("div[class=g]");
    By getFirstLinkFromResults = By.cssSelector("cite[class*=iUh30]");

    boolean isQueryPresentInResults = false;
    boolean isUrlContainsSoftServe = false;

    @BeforeSuite
    public void chooseDriver() {
        driver = webDriverFactory.createWebDriver(DriverType.CHROME);
    }

    @BeforeMethod
    public void manageBrowser() {
        driver.manage().window().maximize();
    }

    @Test
    public void Test () {

        //Navigate to google.com
        driver.get(url);

        //Enter 'SoftServe' query to search field
        driver.findElement(searchFieldChromeQuery).click();
        WebElement fieldChromeQuery = driver.findElement(searchFieldChromeQuery);
        fieldChromeQuery.clear();
        fieldChromeQuery.sendKeys(query);
        fieldChromeQuery.submit();

        //Create List of the url results
        List<WebElement> results = driver.findElements(resultList);
        List<WebElement> resultsByCss = driver.findElements(getResultList);

        //Verify that SoftServe site was appeared in search result
        for (WebElement result:resultsByCss) {
            if (result.getText().contains(softserve)){
                isQueryPresentInResults = true;
                break;
            }
            Assert.assertTrue(isQueryPresentInResults);
        }

        //Navigate to SoftServe
        WebElement siteSoftServe = driver.findElement(getFirstLinkFromResults);
        siteSoftServe.click();

        //Verify that site url is contains SoftServe address
        String currentUrl = driver.getCurrentUrl();

        if (currentUrl.contains(softserve)) {
            isUrlContainsSoftServe = true;
        }
        Assert.assertTrue(isUrlContainsSoftServe);
    }

    @AfterMethod
    public void closeBrowser () {
        driver.quit();
    }
}
