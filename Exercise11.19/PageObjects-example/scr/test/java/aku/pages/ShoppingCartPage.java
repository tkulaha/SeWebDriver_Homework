package aku.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.*;
import java.util.concurrent.TimeUnit;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import java.util.List;

public class ShoppingCartPage extends Page {
   int n = 1;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public void OpenShoppingCart() {
        driver.findElement(By.linkText("Checkout »")).click();
        wait.until(titleIs("Checkout | My Store"));
    }

    public void EmptyShoppingCart() {
        //check if list of products is available
        try {
            if (driver.findElement(By.className("shortcuts")).isDisplayed() == true) {
               int k = driver.findElements(By.className("shortcut")).size(); // <-- positions displayed in shortcut
               int n = driver.findElements(By.xpath("//td[@class='item']")).size(); // <-- positions from the final table
                if (n == k) {
                    System.out.println("Number of positions to be deleted: " + k);
                } else {
                    System.out.println("Error: number of lines in the table differs from the number of positions selected for buying: " + n + " and " + k);
                }
                //delete positions
                for (int i = 0; i < k; i++) {
                    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                    this.deletePosition();
                    System.out.println("Position " + (i + 1) + " deleted.");
                    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                }
            }
        } catch (NoSuchElementException ex1) {
            try {
                if (this.isShoppingCartEmpty() == true) {
                    System.out.println("No item(s) found in shopping cart.");
                }
            } catch(NoSuchElementException ex2)  {}
               //delete the last position
                if (this.isCountOfProducts() > 0) {
                    deleteLastProduct();
            }
        }
    }

    public void deletePosition () {
        wait.until(visibilityOfElementLocated(By.xpath("//*[contains(@name,'remove_cart_item')]"))).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public int isCountOfProducts() {
        countOfProducts = Integer.parseInt(driver.findElement(By.className("quantity")).getText());
        return countOfProducts;
    }

    public boolean isShoppingCartEmpty () {
        return driver.findElement(By.tagName("EM")).getText().equalsIgnoreCase("There are no items in your cart.");
    }
    public void returnToMainPage(){
        driver.findElement(By.className("fa-home")).click();
        wait.until(titleIs("Online Store | My Store"));
    }

    public void deleteLastProduct() {
        //delete the last position
         try {
             driver.findElement(By.xpath("//*[contains(@name, 'remove_cart_item')]")).click();
             driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
         } catch (NoSuchElementException ex3) {
             wait.until(elementToBeClickable(By.xpath("//*[contains(@name, 'remove_cart_item')]"))).click();
             driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
         }
        wait.until(invisibilityOfAllElements(driver.findElements(By.xpath("//td[@class='item']"))));
        System.out.println("Position " + n + " deleted.");
    }
}
