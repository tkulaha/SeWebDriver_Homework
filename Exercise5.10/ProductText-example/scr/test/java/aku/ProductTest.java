package aku;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import java.util.concurrent.TimeUnit;

public class ProductTest {
    private WebDriver driver;
    private WebDriverWait wait;
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
    public void ProductTest () {
        Log_in_Test();
        CheckProductData();
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

    public void  CheckProductData() {
        int red = 1;
        int green = 2;
        int blue = 3;
        //find 1st element in 'Campaigns' and collect the data
        //1. Title
        String title1 = driver.findElement(By.cssSelector("div#box-campaigns div.name")).getAttribute("textContent").toString();
        System.out.println("Main page. Name: " + title1);
        //2. Prices color,style,size
        //Regular price
        String price1 = driver.findElement(By.cssSelector("div#box-campaigns s.regular-price")).getAttribute("textContent").toString();
        System.out.println("Regular price: " + price1);
        //Check if the color is GREY
        String price1_color = driver.findElement(By.cssSelector("div#box-campaigns s.regular-price")).getCssValue("color");
        System.out.print("Font color: " + price1_color);
        if ((driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.chrome.ChromeDriver")) == true ||
                (driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.ie.InternetExplorerDriver")) == true ) {
            red = Integer.parseInt(price1_color.substring(5, 8));
            green = Integer.parseInt(price1_color.substring(10, 13));
            blue = Integer.parseInt(price1_color.substring(15, 18));
        } else if ((driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.firefox.FirefoxDriver")) == true ) {
            red = Integer.parseInt(price1_color.substring(4, 7));
            green = Integer.parseInt(price1_color.substring(9, 12));
            blue = Integer.parseInt(price1_color.substring(14, 17));
        }

            if (red == green & green == blue) {
                System.out.println(". The font color is GREY.");

        }
        String price1_fweight = driver.findElement(By.cssSelector("div#box-campaigns s.regular-price")).getCssValue("font-weight");
        System.out.println("Font weight: " + price1_fweight);
        String price1_fsize = driver.findElement(By.cssSelector("div#box-campaigns s.regular-price")).getCssValue("font-size");
        System.out.println("Font size: " + price1_fsize);
        double pr1_fs = Double.parseDouble(price1_fsize.replace("px",""));
        String price1_textdec = driver.findElement(By.cssSelector("div#box-campaigns s.regular-price")).getCssValue("text-decoration");
        System.out.println("Font style: " + price1_textdec);
        if ((driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.chrome.ChromeDriver")) == true) {
        price1_textdec = price1_textdec.substring(0, 10);}
        System.out.println("Price size: " + driver.findElement(By.cssSelector("div#box-campaigns s.regular-price")).getSize());

        //Campaign price
        String disc_price1 = driver.findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getAttribute("textContent").toString();
        System.out.println("Campaign price: " + disc_price1);
        String disc_price1_color = driver.findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getCssValue("color");
        System.out.print("Font color: " + disc_price1_color);
        if ((driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.chrome.ChromeDriver")) == true ||
                (driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.ie.InternetExplorerDriver")) == true ) {
            red = Integer.parseInt(disc_price1_color.substring(5, 8));
            green = Integer.parseInt(disc_price1_color.substring(10, 11));
            blue = Integer.parseInt(disc_price1_color.substring(13, 14));
        } else if ((driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.firefox.FirefoxDriver")) == true ) {
            red = Integer.parseInt(disc_price1_color.substring(4, 7));
            green = Integer.parseInt(disc_price1_color.substring(9, 10));
            blue = Integer.parseInt(disc_price1_color.substring(12, 13));
        }
        if (green == 0 & blue == 0 & red != 0) {
            System.out.println(". The font color is RED.");
        }
        String disc_price1_fweight = driver.findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getCssValue("font-weight");
        System.out.println("Font weight: " + disc_price1_fweight);
        String disc_price1_fsize = driver.findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getCssValue("font-size");
        System.out.println("Font size: " + disc_price1_fsize);
        double dpr1_fs = Double.parseDouble(disc_price1_fsize.replace("px",""));
        if (dpr1_fs > pr1_fs) {
            System.out.println("Font size of price with discount if bigger than of regular price.");
        }
        else {
            System.out.println("Attention! Font size of regular price is bigger than of discount price.");
        }
        String disc_price1_textdec = driver.findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getCssValue("text-decoration");
        System.out.println("Font style: " + disc_price1_textdec);
        if ((driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.chrome.ChromeDriver")) == true) {
        disc_price1_textdec = disc_price1_textdec.substring(0,10);}
        System.out.println("Discount Price size: " + driver.findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getSize());

        //click on product and open product details
        driver.findElement(By.cssSelector("div#box-campaigns div.image-wrapper:nth-child(1)")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //1. Title
        String title2 = driver.findElement(By.cssSelector("h1.title")).getAttribute("textContent").toString();
        System.out.println("Information page. Name: " + title2);
        if (title1.equals(title2)==true) {
                System.out.println("Title is the same on both pages.");
        } else {
            System.out.println("Attention! The title differs from the title on main page.");
        }

        ///2. Prices color,style,size
        //Regular price value
        String price2 = driver.findElement(By.cssSelector("div.content s.regular-price")).getAttribute("textContent").toString();
        System.out.println("Regular price: " + price2);
        if (price1.equalsIgnoreCase(price2) == true) {System.out.println("Regular prices are the same on both pages.");} else {System.out.println("Attention! Regular prices are different.");}
        //Regular price color
        String price2_color = driver.findElement(By.cssSelector("div.content s.regular-price")).getCssValue("color");
        System.out.print("Font color: " + price2_color);
        //Check if the color is GREY
        if ((driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.chrome.ChromeDriver")) == true ||
                (driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.ie.InternetExplorerDriver")) == true ) {
            red = Integer.parseInt(price1_color.substring(5, 8));
            green = Integer.parseInt(price1_color.substring(10, 13));
            blue = Integer.parseInt(price1_color.substring(15, 18));
        } else if ((driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.firefox.FirefoxDriver")) == true ) {
            red = Integer.parseInt(price1_color.substring(4, 7));
            green = Integer.parseInt(price1_color.substring(9, 12));
            blue = Integer.parseInt(price1_color.substring(14, 17));
        }
        if (red == green & green == blue) {
            System.out.println(". The font color is GREY.");
        }
        //Regular price weight
        String price2_fweight = driver.findElement(By.cssSelector("div.content s.regular-price")).getCssValue("font-weight");
        System.out.println("Font weight: " + price2_fweight);
        if (price2_fweight.equalsIgnoreCase(price1_fweight) == true) {
            System.out.println("Font weight is the same on both pages.");
        }
        else {
            System.out.println("Attention! Font weights are different.");
        }
        //Regular price font size
        String price2_fsize = driver.findElement(By.cssSelector("div.content s.regular-price")).getCssValue("font-size");
        System.out.println("Font size: " + price2_fsize);
        double pr2_fs = Double.parseDouble(price2_fsize.replace("px",""));
        //Regular price font style
        String price2_textdec = driver.findElement(By.cssSelector("div.content s.regular-price")).getCssValue("text-decoration");
        System.out.println("Font style: " + price2_textdec);
        if ((driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.chrome.ChromeDriver")) == true) {
        price2_textdec = price2_textdec.substring(0,10);}
        if (price1_textdec.equalsIgnoreCase(price2_textdec) == true) {
            System.out.println("Font styles are the same on both pages.");
        }
        else {
            System.out.println("Attention! Font styles are different.");
        }
        System.out.println("Price size: " + driver.findElement(By.cssSelector("div.content s.regular-price")).getSize());

        //Campaign price value
        String disc_price2 = driver.findElement(By.cssSelector("div.content strong.campaign-price")).getAttribute("textContent").toString();
        System.out.println("Campaign price: " + disc_price2);
        if (disc_price1.equalsIgnoreCase(disc_price2) == true) {
            System.out.println("Campaign prices are the same on both pages.");
        }
        else {
            System.out.println("Attention! Campaign prices are different.");
        }
        //Campaign price color
        String disc_price2_color = driver.findElement(By.cssSelector("div.content strong.campaign-price")).getCssValue("color");
        System.out.print("Font color: " + disc_price2_color);
        if ((driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.chrome.ChromeDriver")) == true ||
                (driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.ie.InternetExplorerDriver")) == true ) {
            red = Integer.parseInt(disc_price2_color.substring(5, 8));
            green = Integer.parseInt(disc_price2_color.substring(10, 11));
            blue = Integer.parseInt(disc_price2_color.substring(13, 14));
        } else if ((driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.firefox.FirefoxDriver")) == true ) {
            red = Integer.parseInt(disc_price2_color.substring(4, 7));
            green = Integer.parseInt(disc_price2_color.substring(9, 10));
            blue = Integer.parseInt(disc_price2_color.substring(12, 13));
        }
        if (green == 0 & blue == 0 & red != 0) {
            System.out.println(". The font color is RED.");
        }
        //Campaign price font weight
        String disc_price2_fweight = driver.findElement(By.cssSelector("div.content strong.campaign-price")).getCssValue("font-weight");
        System.out.println("Font weight: " + disc_price2_fweight);
        if (disc_price2_fweight.equalsIgnoreCase(disc_price1_fweight) == true) {
            System.out.println("Font weight is the same on both pages.");} else {System.out.println("Attention! Font weights are different.");}
        //Campaign price font size
        String disc_price2_fsize = driver.findElement(By.cssSelector("div.content strong.campaign-price")).getCssValue("font-size");
        System.out.println("Font size: " + disc_price2_fsize);
        double dpr2_fs = Double.parseDouble(disc_price2_fsize.replace("px",""));
        if (dpr2_fs > pr2_fs) {
            System.out.println("Font size of price with discount if bigger than of regular price.");
        }
        else {
            System.out.println("Attention! Font size of regular price is bigger than of discount price.");
        }
        //Campaign price font style
        String disc_price2_textdec = driver.findElement(By.cssSelector("div.content strong.campaign-price")).getCssValue("text-decoration");
        System.out.println("Font style: " + disc_price2_textdec);
        if ((driver.getClass().toString().equalsIgnoreCase("class org.openqa.selenium.chrome.ChromeDriver")) == true) {
        disc_price2_textdec = disc_price2_textdec.substring(0,10); }
        if (disc_price1_textdec.equalsIgnoreCase(disc_price2_textdec) == true) {
            System.out.println("Font styles are the same on both pages.");
        }
        else {
            System.out.println("Attention! Font styles are different.");
        }
        System.out.println("Discount Price size: " + driver.findElement(By.cssSelector("div.content strong.campaign-price")).getSize());

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

