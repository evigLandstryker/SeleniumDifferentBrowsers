import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.sun.deploy.cache.Cache.copyFile;


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
    public void takeScreenshot(ITestResult result) {
        if (!result.isSuccess()) try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            copyFile(scrFile, new File(result.getName() + "[" + LocalDate.now() + "][" + System.currentTimeMillis() + "].png"));
        } catch (
                IOException e) { e.printStackTrace();

        }
    }

    @AfterClass
    public void closeBrowser () {
        driver.quit();
    }
}