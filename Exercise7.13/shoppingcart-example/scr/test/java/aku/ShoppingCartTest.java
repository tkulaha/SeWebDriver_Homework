package aku;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    //private int n = 0;
    private List <WebElement> items;
    /*
    private static ShoppingCartTest ourInstance = new ShoppingCartTest();

    public static ShoppingCartTest getInstance() {
        return ourInstance;
    }*/

    @Before
    public void start() {
        //open new window
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }


    @Test
    public void ShoppingCartTest () {
        Log_in_Test();
        // add 3 products
        Buy_Something();
        Buy_Something();
        Buy_Something();
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

    public void Buy_Something() {
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
        //go to the shopping cart
        driver.findElement(By.linkText("Checkout »")).click();
        wait.until(titleIs("Checkout | My Store"));
        //check if list of products is available
        try {
        wait.until(visibilityOfElementLocated(By.className("shortcuts")));
        //find and calculate all position. Note that 1 position can contain more than 1 item.
        items = driver.findElements(By.className("shortcut"));
        k = driver.findElements(By.className("shortcut")).size();
        System.out.println("Number of positions to be deleted: " + k);
        //delete items
        for (int j = 0; j < (k-1); j++) {
            wait.until(elementToBeClickable(By.className("shortcut")));
            items = driver.findElements(By.className("shortcut"));
            //delete the first position from the list
            items.get(0).click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            try {
                wait.until(elementToBeClickable(By.xpath("//*[contains(@name, 'remove_cart_item')]"))).click();
                System.out.println("Position " + (j + 1) + " deleted.");
            } catch (NoSuchElementException ex1) {
                if (driver.findElement(By.cssSelector("There are no positions in your cart.")).isDisplayed() == true) {
                    System.out.println("No item(s) found.");
                } else {
                    System.out.println("Error appeared" + ex1.getAdditionalInformation());
                }
            }
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
        } catch (NoSuchElementException ex2) {
                if (driver.findElement(By.cssSelector("There are no positions in your cart.")).isDisplayed() == true) {
                    System.out.println("No item(s) found.");
                } else {
                    System.out.println("Error appeared" + ex2.getAdditionalInformation());
                }
            }
           //delete the last element (no 'shortcuts' view is displaying)
            driver.findElement(By.className("fa-home")).click();
            wait.until(titleIs("Online Store | My Store"));
            driver.findElement(By.linkText("Checkout »")).click();
            wait.until(titleIs("Checkout | My Store"));
            try {
            wait.until(elementToBeClickable(By.xpath("//*[contains(@name, 'remove_cart_item')]"))).click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            System.out.println ("Position " + (k) + " deleted.");
            wait.until(titleIs("Checkout | My Store"));
            } catch (NoSuchElementException ex3) {
            if (driver.findElement(By.cssSelector("There are no positions in your cart.")).isDisplayed() == true) {
                System.out.println("No item(s) found.");
            } else {
                System.out.println("Error appeared" + ex3.getAdditionalInformation());
            }
        }
        System.out.println("The shopping cart is empty.");
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


