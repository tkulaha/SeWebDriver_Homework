package aku;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ShoppingCartTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String email = "tkulaha@gmail.com";
    private String psw = "12345";
    boolean qchk = false;
    private List <WebElement> items;
    private List <WebElement> table_items;

    @Before
    public void start() {
        //open new window
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }


    @Test
    public void ShoppingCartTest () {
        Log_in_Test();
        // add 3 products to shopping cart
        Buy_Product();
        Buy_Product();
        Buy_Product();
        //remove all products
        EmptyShoppingCart();
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

    public void Buy_Product() {
        //get number of items in shopping cart before buying
        int n = Integer.parseInt(driver.findElement(By.className("quantity")).getText());
        //buy something in the shop
        driver.findElement(By.className("image-wrapper")).click();
        //select size if available
        try {
            qchk = driver.findElement(By.name("options[Size]")).isDisplayed();
            driver.findElement(By.name("options[Size]")).sendKeys("Small");
        }  catch (NoSuchElementException ex) {
            System.out.println ("No size selection is available for the product.");
        }
        wait.until(elementToBeClickable(By.name("add_cart_product"))).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //check that number of items in the cart is increased
        n = n+1;
        wait.until(textToBePresentInElement(driver.findElement(By.className("quantity")), String.valueOf(n)));
        System.out.println("Number of items in shopping cart is " + driver.findElement(By.className("quantity")).getText());
        //go back to home page
        driver.findElement(By.className("fa-home")).click();
        wait.until(titleIs("Online Store | My Store"));
    }

    public void EmptyShoppingCart() {
        int k = 0;
        int n = 1;
        //go to the shopping cart
        driver.findElement(By.linkText("Checkout »")).click();
        wait.until(titleIs("Checkout | My Store"));
        //check if list of products is available
        try {
            if (driver.findElement(By.className("shortcuts")).isDisplayed() == true) {
                //find and calculate all position. Note that 1 position can contain more than 1 item
                // positions displayed in shortcut
                k = driver.findElements(By.className("shortcut")).size();
                //positions from the final table
                table_items = driver.findElements(By.xpath("//td[@class='item']"));
                n = table_items.size();
                if (n == k) {
                    System.out.println("Number of positions to be deleted: " + k);
                } else {
                    System.out.println("Error: number of lines in the table differs from the number of positions selected for buying: " + n + " and " + k);
                }
                //delete positions
                for (int i = 0; i < (k - 1); i++) {
                    items = driver.findElements(By.className("shortcut"));
                    items.get(0).click();
                    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                    wait.until(elementToBeClickable(By.xpath("//*[contains(@name,'remove_cart_item')]"))).click();
                    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                    wait.until(visibilityOfAllElementsLocatedBy(By.xpath("//td[@class='item']")));
                    System.out.println("Position " + (i + 1) + " deleted.");
                    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                }
            }
        } catch (NoSuchElementException ex1) {
            if (driver.findElement(By.tagName("EM")).getText().equalsIgnoreCase("There are no items in your cart.") == true) {
                System.out.println("No item(s) found in shopping cart.");
            } else {   //delete the only position (if 3 items are the same)
                wait.until(elementToBeClickable(By.xpath("//*[contains(@name,'remove_cart_item')]"))).click();
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                wait.until(invisibilityOfAllElements(driver.findElements(By.xpath("//td[@class='item']"))));
                System.out.println("Position " + n + " deleted.");
            }
        }
        //delete the last position
        wait.until(elementToBeClickable(By.xpath("//*[contains(@name, 'remove_cart_item')]"))).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait.until(invisibilityOfAllElements(driver.findElements(By.xpath("//td[@class='item']"))));
        System.out.println("Position " + n + " deleted.");
        // go back to home page
        driver.findElement(By.className("fa-home")).click();
        wait.until(titleIs("Online Store | My Store"));
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


