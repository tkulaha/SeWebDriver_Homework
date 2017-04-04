package aku;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;


import java.util.Random;

public class UserRegistrationTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String new_email;
    private String new_psw;
    private String email = "tkulaha@gmail.com";
    private String psw = "12345";

    @Before
    public void start() {
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver = new InternetExplorerDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void UserRegistrationTest() {
        NewUser();
        Log_out_Test();
        Log_in_Test();
        Log_out_Test();
    }

    public void Log_in_Test() {
        //open litecart and log in
        driver.get("http://localhost/litecart/litecart-1.3.7/public_html/en/");
        //log in with new user name
        email = new_email;
        psw = new_psw;
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(psw);
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("Online Store | My Store"));
    }

    public void NewUser() {
        String firstname = "Tatsiana";
        String lastname = "Kulaha";
        String address1 = "Any str.8-8";
        String postcode = "12345";
        String city = "San Jose";
        String country = "United States";
        String statename = "California";
        String phone = "+123456789";
        //generate random username
        new_email =  String.valueOf(new Random().nextInt())+ "tk@gmail.com";
        new_psw = "12345";
        //register new user - fill in registration data
        driver.get("http://localhost/litecart/litecart-1.3.7/public_html/en/");
        driver.findElement(By.linkText("New customers click here")).click();
        driver.findElement(By.name("firstname")).sendKeys(firstname);
        driver.findElement(By.name("lastname")).sendKeys(lastname);
        driver.findElement(By.name("address1")).sendKeys(address1);
        driver.findElement(By.name("postcode")).sendKeys(postcode);
        driver.findElement(By.name("city")).sendKeys(city);
        //select country
        driver.findElement(By.className("select2-selection__arrow")).click();
        driver.findElement(By.className("select2-search__field")).sendKeys(country + Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
        //select state
        driver.findElement(By.xpath("//select[@name='zone_code']")).sendKeys(statename);
        driver.findElement(By.name("email")).sendKeys(new_email);
        driver.findElement(By.name("phone")).sendKeys(phone);
        driver.findElement(By.name("password")).sendKeys(new_psw);
        driver.findElement(By.name("confirmed_password")).sendKeys(new_psw);
        //click 'create account'
        driver.findElement(By.name("create_account")).click();
        wait.until(titleIs("Online Store | My Store"));
        System.out.println("New user created. User email is " + new_email + " and password is " + new_psw);
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
