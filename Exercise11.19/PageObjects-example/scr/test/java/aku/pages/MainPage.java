package aku.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MainPage extends Page {

    public MainPage (WebDriver driver) {
        super(driver);
    }

    public void open(){
        driver.get("http://localhost/litecart/litecart-1.3.7/public_html/en/");
    }

    public boolean isOnThisPage() {
        return driver.findElements(By.id("box-login")).size() > 0;
    }

    public MainPage enterUsername (String username) {
        driver.findElement(By.name("email")).sendKeys(username);
        return this;
    }

    public MainPage enterPassword(String password) {
        driver.findElement(By.name("password")).sendKeys(password);
        return this;
    }

    public void submitLogin() {
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("Online Store | My Store"));
    }

    public void findProductForBuying() {
        //buy first available product in the shop
        driver.findElement(By.className("image-wrapper")).click();
    }
}
