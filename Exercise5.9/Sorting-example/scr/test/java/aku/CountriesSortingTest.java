package aku;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CountriesSortingTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String username = "admin";
    private String psw = "admin";
    private List <WebElement> countries;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void CountriesSortingTest() {
        Log_in_Test();
        DisplayCountries();
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

    public void DisplayCountries() {
        // check if menu is displayed
        try {
            driver.findElement(By.className("list-vertical")).isDisplayed();
            driver.findElement(By.linkText("Countries")).click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            // read header text
            String header_text = driver.findElement(By.xpath("//h1[contains(text(),Text)]")).getText();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            System.out.print("Menu line '" + header_text + "' opened. Number of countries found: ");

            //read list of countries if it's available
            if (driver.findElement(By.name("countries_form")).isDisplayed()) {
                countries = driver.findElements(By.className("row"));
                int n = driver.findElements(By.className("row")).size();
                System.out.println(n);
                String[][] ms = new String[n][4];
                String[] ms1 = new String [n];
                for (int i=0; i < n; i++) {
                    String row = countries.get(i).getAttribute("textContent").toString().replaceAll("[^A-Za-zА-Яа-я0-9]", "");
                   // System.out.print("##" + row); //not parsed rows

                    //Parse textContent to create array of strings
                    int r_length = row.length();
                    ms[i][0] = row.substring(0,3).replaceAll("[^0-9]",""); //number
                    ms[i][1] = row.substring(1,5).replaceAll("[^A-ZА-Я]", "").substring(0,2); //country code
                    ms[i][2] = row.substring(1,r_length).replaceAll("[^A-Za-zА-Яа-я]", "").substring(2); //country name
                    ms1[i] = ms[i][2];
                    ms[i][3] = row.substring(r_length-2,r_length).replaceAll("[^0-9]",""); //zones
                   System.out.println(".  Parsed Data: " + ms[i][0] + "/ " + ms[i][1] + " /" + ms[i][2] + "/ " + ms[i][3]);
                    //Check Zones sorting
                    if (ms[i][3].equalsIgnoreCase("0" ) == false) {
                        countries.get(i).findElement(By.className("fa-pencil")).click();
                        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                        DisplayZones ();
                        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                        //reinitialize countries List to avoid Stale element exception
                        countries = driver.findElements(By.className("row"));
                    }
                   }
                System.out.println("End of list.");
                //validate if sorting of countries is ok
                SortingTest(ms1, n);
                }
            } catch (NoSuchElementException ex) {
            //exception will be raised if no menu displayed.
            throw ex;
        }
    }

    public void SortingTest(String[] ms1, int m) {
        String[] ms2 = new String[m];
        boolean err = false;

        for (int i = 0; i < m; i++) {
            ms2[i] = ms1[i]; //copy input data
        }
        Arrays.sort(ms2); //sort data separately to compare with initial array
        for (int j = 0; j < m; j++)  {
                if (ms2[j].equalsIgnoreCase(ms1[j]) == false) { // if sorted array is not equal to initial array then the sorting error exists
                    System.out.println("ERROR! Sorting is incorrect! Wrong initial element: " + ms1[j]);
                    err = true;
                }
        }
        if (err == false) {
            System.out.println("ELEMENTS SORTING IS CORRECT!");
        }
    }

    public void DisplayZones() {
        List <WebElement> zones;
        // read header text
        String header_text = driver.findElement(By.xpath("//h1[contains(text(),Text)]")).getText();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        System.out.print("Menu line '" + header_text + "' opened.");
        //find the elements with zones
        if (driver.findElement(By.id("table-zones")).isDisplayed()) {
            int k = driver.findElements(By.cssSelector("td:nth-child(3)")).size();
            zones = driver.findElements(By.cssSelector("td:nth-child(3)")).subList(1,(k-1));
            //recalculate number of lines (zones)
            k = zones.size();
            //create array for sorting validation
            String[] ms1 = new String[k];
            System.out.println(" Number of Zones: " + k);
            for (int i = 0; i < k; i++) {
                ms1[i] = zones.get(i).getAttribute("textContent").toString(); //fill in array with zones names
                System.out.println("#" + (i+1) + "  " + ms1[i]);
            }
            //Check if zones sorting is ok
            SortingTest(ms1,k);
        }
        //go back to page with countries
        driver.findElement(By.cssSelector("[name=cancel]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // read header text
        header_text = driver.findElement(By.xpath("//h1[contains(text(),Text)]")).getText();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        System.out.println("Menu line '" + header_text + "' opened.");
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}


