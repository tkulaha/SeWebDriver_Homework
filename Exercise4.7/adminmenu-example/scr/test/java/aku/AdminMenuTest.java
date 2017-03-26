package aku;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import java.lang.String;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class AdminMenuTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String username = "admin";
    private String psw = "admin";
    boolean qchk =false;
    private List <WebElement> menu;
    private List <WebElement> menu_child;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
        public void AdminMenuTest() {
        Log_in_Test();
        ClickMenuTree();
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

    public void ClickMenuTree() {
        // check if menu is displayed
        try {
        driver.findElement(By.className("list-vertical")).isDisplayed();
        // calculate number of initial (parent) lines
        int n = driver.findElements(By.cssSelector("[id=app-]")).size();
        System.out.println("Administration menu has " + n + " parent branches.");
        // start to read and click menu items
        int i = 0;
        int n_child = 0;
        while (i < n) {
            menu = driver.findElements(By.cssSelector("[id=app-]"));
            System.out.print("#" + (i + 1) + " " + menu.get(i).findElement(By.cssSelector("[class=name]")).getText());
            // click menu item
            menu.get(i).findElement(By.cssSelector("[class=name]")).click();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            // read header text
            String header_text = driver.findElement(By.xpath("//h1[contains(text(),Text)]")).getText();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            System.out.println(".  Header text is ' " + header_text + " ' ");
            // check if child items are available. If there're no child items n_child value will be set to 0 in block "catch"
            try {
                //calculate number of child lines
                n_child = driver.findElements(By.cssSelector("[id^=doc-]")).size();
                System.out.print("Child menu branches: " + n_child + " found.");
                if (n_child > 0) {
                   System.out.println(" Child menu items are:");
                    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                int i_child = 0;
                while (i_child < n_child) {
                    menu_child = driver.findElements(By.cssSelector("[id^=doc-]"));
                    System.out.print(" #" + (i + 1) + "." + (i_child + 1) + " " + menu_child.get(i_child).findElement(By.cssSelector("[class=name]")).getText());
                    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                    //click child menu item
                    menu_child.get(i_child).findElement(By.cssSelector("[class=name")).click();
                    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                    //read header text for child menu item
                    System.out.println(".  Header text is  ' " + driver.findElement(By.xpath("//h1[contains(text(),Text)]")).getText() + " ' ");
                    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

                i_child++;
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                    }
                }
                else {System.out.println(" No child items exist.");}
            } catch (NoSuchElementException ex2) {
                //if no child element found no exception occur.
                n_child = 0;
            }
            i++;
        }
      } catch (NoSuchElementException ex) {
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