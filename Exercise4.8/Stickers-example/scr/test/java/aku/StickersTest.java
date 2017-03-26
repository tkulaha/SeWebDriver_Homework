package aku;

        import org.junit.After;
        import org.junit.Test;
        import org.junit.Before;
        import org.openqa.selenium.By;
        import org.openqa.selenium.NoSuchElementException;
        import org.openqa.selenium.WebDriver;
        import java.util.List;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.chrome.ChromeDriver;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
        import java.util.Scanner;
        import java.util.concurrent.TimeUnit;


public class StickersTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String email = "tkulaha@gmail.com";
    private String psw = "12345";
    private List <WebElement> products;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void StickersTest () {
        Log_in_Test();
        CheckStickers();
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

    public void CheckStickers() {
        // find number of ducks on main page
        int n = driver.findElements(By.cssSelector("[class=image-wrapper]")).size();
        System.out.println("Number of products available:" + n);
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        if (n > 0) {
            products = driver.findElements(By.cssSelector("[class=image-wrapper]"));
            driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
            int i = 0;
            while (i < n){
                System.out.print("Duck" + (i+1) + " has a sticker: ");
                //if sticker is available it will be printed, otherwise the message about error will be printed.
             try {
                System.out.println(products.get(i).findElement(By.cssSelector("[class^=sticker]")).getText());
                } catch (NoSuchElementException ex) {
                    System.out.println("Error! No sticker found.");
                }
                i++;
            }
        }
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
