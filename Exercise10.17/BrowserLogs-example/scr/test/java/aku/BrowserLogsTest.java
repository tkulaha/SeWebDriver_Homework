package aku;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import java.lang.String;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BrowserLogsTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String username = "admin";
    private String psw = "admin";
    private List<WebElement> products;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void BrowserLogsTest() {
        Log_in_Test();
        ViewProductPage();
        Log_out_Test();
    }

    public void Log_in_Test() {
        //open litecart and log in
        driver.get("http://localhost/litecart/litecart-1.3.7/public_html/admin/login.php");
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(psw);
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
    }

    public void Log_out_Test() {
        driver.findElement(By.className("fa-sign-out")).click();
        wait.until(titleIs("My Store"));
    }

    public void getBrowserLogs() {
        System.out.println("Browser Logs: " + driver.manage().logs().get("browser").getAll());
    }
    public void ViewProductPage() {
        // check if menu is displayed
        try {
            driver.findElement(By.className("list-vertical")).isDisplayed();
            driver.findElement(By.linkText("Catalog")).click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            // read header text
            String header_text = driver.findElement(By.xpath("//h1[contains(text(),Text)]")).getText();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            System.out.print("Menu line '" + header_text + "' opened. ");
            driver.findElement(By.linkText("Rubber Ducks")).click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            // get list of products and open each product
            int n = driver.findElements(By.className("row")).size() - 3;
            System.out.println("Number of products is " + n);
            for (int i = 0; i < n; i++) {
                products = driver.findElements(By.className("row")).subList(3, n + 3);
                System.out.println("Product name: " + products.get(i).getText());
                products.get(i).findElement(By.className("fa-pencil")).click();
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                getBrowserLogs();
                driver.navigate().back();
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                getBrowserLogs();
            }
            getBrowserLogs();
        } catch (NoSuchElementException ex) {
            getBrowserLogs();
            //exception will be raised if no menu displayed.
            throw ex;
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
