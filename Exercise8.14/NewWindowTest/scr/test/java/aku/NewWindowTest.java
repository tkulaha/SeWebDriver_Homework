package aku;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import java.lang.String;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NewWindowTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String username = "admin";
    private String psw = "admin";
    private List<WebElement> fields;

@Before
public void start() {
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, 10);
}

    @Test
    public void NewWindowTest() {
        Log_in_Test();
        CheckCountries();
        Log_out_Test();
    }

    public void Log_in_Test() {
        //open litecart and log in
        driver.get("http://localhost/litecart/litecart-1.3.7/public_html/admin/login.php");
        driver.manage().window().maximize();
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(psw);
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
    }

    public void Log_out_Test() {
        driver.findElement(By.className("fa-sign-out")).click();
        wait.until(titleIs("My Store"));
    }
    public void CheckCountries(){

        // check if menu is displayed
        try {
            driver.findElement(By.className("list-vertical")).isDisplayed();
            driver.findElement(By.linkText("Countries")).click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            // read header text
            String header_text = driver.findElement(By.xpath("//h1[contains(text(),Text)]")).getText();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            System.out.println("Menu line '" + header_text + "' opened. ");

            driver.findElement(By.linkText("Add New Country")).click();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            String originalWindow = driver.getWindowHandle();
            Set <String> existingWindows = driver.getWindowHandles();

            System.out.println("Original Window: " + originalWindow.toString() + "/" + driver.getTitle().toString());
            // check special buttons raise new windows
            fields = driver.findElements(By.className("fa-external-link"));
            int n = driver.findElements(By.className("fa-external-link")).size();
            for (int i = 0; i < n; i++) {
                fields.get(i).click();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                String newWindow = wait.until(anyWindowOtherThan(existingWindows));
                driver.switchTo().window(newWindow);
                System.out.println("New Window: " + newWindow.toString() + "/" + driver.getTitle().toString());
                driver.close();
                driver.switchTo().window(originalWindow);
            }

        } catch (NoSuchElementException ex) {
            //exception will be raised if no menu displayed.
            throw ex;
        }
        driver.findElement(By.name("cancel")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    public ExpectedCondition <String> anyWindowOtherThan (Set <String> oldWindows) {
        return new ExpectedCondition<String>() {
            @Override
            public String apply(WebDriver webDriver) {
                Set <String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next():null;
            }
        };
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}


