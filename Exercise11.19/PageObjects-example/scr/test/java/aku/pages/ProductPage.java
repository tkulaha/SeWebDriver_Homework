package aku.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.*;
import java.util.concurrent.TimeUnit;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ProductPage extends Page {

    public ProductPage (WebDriver driver) {
        super(driver);
    }

    public void AddProductToCart() {
        //get number of items in shopping cart before buying
        countOfProducts = Integer.parseInt(driver.findElement(By.className("quantity")).getText());
        //select size if available
        try {
            boolean qchk = driver.findElement(By.name("options[Size]")).isDisplayed();
            driver.findElement(By.name("options[Size]")).sendKeys("Small");
        } catch (NoSuchElementException ex) { }
        //add product to cart
        wait.until(elementToBeClickable(By.name("add_cart_product"))).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //increase number of items in shopping cart
        countOfProducts = countOfProducts + 1;
        wait.until(textToBePresentInElement(driver.findElement(By.className("quantity")), String.valueOf(countOfProducts)));
    }

    public void returnToMainPage() {
        driver.findElement(By.className("fa-home")).click();
        wait.until(titleIs("Online Store | My Store"));
    }

    public int isCountOfProducts() {
        countOfProducts = Integer.parseInt(driver.findElement(By.className("quantity")).getText());
        return countOfProducts;
    }
}
