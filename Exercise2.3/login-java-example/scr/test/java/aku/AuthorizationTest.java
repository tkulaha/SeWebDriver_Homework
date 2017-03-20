package aku;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import java.util.Scanner;


public class AuthorizationTest {
    private WebDriver driver;
    private WebDriverWait wait;
   // private String email;
   // private String psw;
    private String email = "tkulaha@gmail.com";
    private String psw = "12345";
    boolean qchk =false;

    @Before
    public void start() {
        /*
        // Collect login data before start
        Scanner in = new Scanner(System.in);
        System.out.println ("Enter login: ");
        email = in.nextLine();
        System.out.println ("Enter password: ");
        psw = in.nextLine(); */
        //open new window
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void AuthorizationTest () {
        Log_in_Test();
        Buy_Something();
        Log_out_Test();
    }

    public void Log_in_Test() {
        //open litecart and log in
        driver.get("http://localhost/litecart/litecart-1.3.7/public_html/en/");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(psw);
        driver.findElement(By.name("login")).click();
         wait.until(titleIs("Online Store | My Store"));
    }

    public void Buy_Something() {
            //buy something in the shop
            driver.findElement(By.className("image-wrapper")).click();
            //buy 101 ducks
            driver.findElement(By.name("quantity")).sendKeys("10");
            //select size if available
            try {
                qchk = driver.findElement(By.name("options[Size]")).isDisplayed();
                driver.findElement(By.name("options[Size]")).sendKeys("Small");
            }  catch (NoSuchElementException ex) {
                System.out.println ("No size selection is available");
            }
            driver.findElement(By.name("add_cart_product")).click();
        }

    public void Log_out_Test() {
        driver.findElement(By.linkText("Logout")).click();
        wait.until(titleIs("Online Store | My Store"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
