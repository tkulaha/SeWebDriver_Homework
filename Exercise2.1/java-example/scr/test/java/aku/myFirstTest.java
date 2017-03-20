package aku;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;

public class myFirstTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait (driver, 10);
    }

    @Test
    public void myFirstTest(){
        driver.get("https://ya.ru");
        driver.findElement(By.name("text")).sendKeys(new CharSequence[]{"webdriver"});
        driver.findElement(By.className("search2__button")).click();
        wait.until(titleContains("webdriver"));
        ///
    }
    @After
    public void stop(){
        driver.quit();
        driver = null;

    }

}
