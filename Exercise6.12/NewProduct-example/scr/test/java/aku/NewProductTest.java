package aku;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import java.lang.String;
import java.util.List;
import java.util.concurrent.TimeUnit;



public class NewProductTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String username = "admin";
    private String psw = "admin";
    private List<WebElement> products;
    public String product_name = "Bear";

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void NewProductTest() {
        Log_in_Test();
        AddNewProduct();
        CheckProduct();
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
public void AddNewProduct(){
        String code = "tk0001";
        String quantity = "2";
        String short_description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse sollicitudin ante massa, eget ornare libero porta congue.";
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse sollicitudin ante massa, eget ornare libero porta congue. " +
                "Cras scelerisque dui non consequat sollicitudin. Sed pretium tortor ac auctor molestie. Nulla facilisi. Maecenas pulvinar nibh vitae lectus vehicula semper. " +
                "Donec et aliquet velit. Curabitur non ullamcorper mauris. In hac habitasse platea dictumst. Phasellus ut pretium justo, sit amet bibendum urna. " +
                "Maecenas sit amet arcu pulvinar, facilisis quam at, viverra nisi. Morbi sit amet adipiscing ante. " +
                "Integer imperdiet volutpat ante, sed venenatis urna volutpat a. Proin justo massa, convallis vitae consectetur sit amet, facilisis id libero.";
        String purchase_price = "10";
        String prices_usd = "10";
        String manufacturer = "ACME Corp.";

    // check if menu is displayed
    try {
        driver.findElement(By.className("list-vertical")).isDisplayed();
        driver.findElement(By.linkText("Catalog")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // read header text
        String header_text = driver.findElement(By.xpath("//h1[contains(text(),Text)]")).getText();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.print("Menu line '" + header_text + "' opened. ");
        // Fill in 'General' data
        driver.findElement(By.linkText("Add New Product")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.name("status")).click();
        driver.findElement(By.name("name[en]")).sendKeys(product_name);
        driver.findElement(By.name("code")).sendKeys(code);
        driver.findElement(By.name("quantity")).sendKeys(quantity);
        driver.findElement(By.name("new_images[]")).sendKeys(System.getProperty("user.dir")+"\\scr\\test\\java\\aku\\teddy.jpg");
        driver.findElement(By.name("date_valid_from")).sendKeys("04/01/2017");
        driver.findElement(By.name("date_valid_to")).sendKeys("12/31/2017");
        // Fill in 'Information' data
        driver.findElement(By.linkText("Information")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.name("manufacturer_id")).sendKeys(manufacturer);
        driver.findElement(By.name("short_description[en]")).sendKeys(short_description);
        driver.findElement(By.className("trumbowyg-editor")).sendKeys(description);
        // Fill in 'Prices' data
        driver.findElement(By.linkText("Prices")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.name("purchase_price")).sendKeys(purchase_price);
        driver.findElement(By.name("prices[USD]")).sendKeys(prices_usd);
        //Save
        driver.findElement(By.name("save")).click();
        System.out.println("New product '"+ product_name + "' is added.");
    } catch (NoSuchElementException ex) {
        //exception will be raised if no menu displayed.
        throw ex;
    }

}

    public void CheckProduct() {
        driver.findElement(By.className("list-vertical")).isDisplayed();
        driver.findElement(By.linkText("Catalog")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // get list of products
        products = driver.findElements(By.className("row"));
        int n = products.size();
        for (int i = 0; i < n; i++) {
            if (products.get(i).getText().equalsIgnoreCase(product_name)) {
                System.out.println("New product is listed in Catalog: " + products.get(i).getText());
            }
        }
    }
    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
