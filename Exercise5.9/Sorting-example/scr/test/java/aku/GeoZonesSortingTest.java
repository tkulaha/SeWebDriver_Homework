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

public class GeoZonesSortingTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String username = "admin";
    private String psw = "admin";
    private List <WebElement> geozones;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void GeoZonesSortingTest() {
        Log_in_Test();
        DisplayGeoZones();
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

    public void DisplayGeoZones() {
        // check if menu is displayed
        try {
            driver.findElement(By.className("list-vertical")).isDisplayed();
            driver.findElement(By.linkText("Geo Zones")).click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            // read header text
            String header_text = driver.findElement(By.xpath("//h1[contains(text(),Text)]")).getText();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            System.out.print("Menu line '" + header_text + "' opened. Number of Geo Zones found: ");

            //read list of Geo Zones if it's available
            if (driver.findElement(By.name("geo_zones_form")).isDisplayed()) {
                geozones = driver.findElements(By.className("row"));
                int n = driver.findElements(By.className("row")).size();
                String[] ms2 = new String[n];
                System.out.println(n);
                for (int i = 0; i < n; i++) {
                    ms2[i] = geozones.get(i).getAttribute("textContent").substring(16,29).toString().replaceAll("[^A-Za-zА-Яа-я]", "");
                    System.out.println("Geo Zone:" + ms2[i]);
                    geozones.get(i).findElement(By.className("fa-pencil")).click();
                    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                    DisplayCountryZones();
                    geozones = driver.findElements(By.className("row"));
                }
                //validate if sorting of Geo Zones is also ok
                SortingTest(ms2, n);
            }

        } catch (NoSuchElementException ex) {
            //exception will be raised if no menu displayed.
            throw ex;
        }
    }

    public void DisplayCountryZones() {
        List <WebElement> gzones;
        // read header text
        String header_text = driver.findElement(By.xpath("//h1[contains(text(),Text)]")).getText();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        System.out.print("Menu line '" + header_text + "' opened.");
        //find the elements with zones
        if (driver.findElement(By.id("table-zones")).isDisplayed()) {
            int k = driver.findElements(By.cssSelector("td:nth-child(3)")).size();
            gzones = driver.findElements(By.cssSelector("td:nth-child(3)")).subList(1,k);
            k = gzones.size();
            String[] ms1 = new String[k];
            System.out.println(" Number of Zones: " + k);
            for (int i = 0; i < k; i++) {
                //fill in array with zones names
                ms1[i] = gzones.get(i).findElement(By.cssSelector("[selected=selected]")).getAttribute("textContent").toString();
                System.out.println("#" + (i+1) + "  " + ms1[i]);
            }
            //Check if zones sorting is ok
            SortingTest(ms1,k);
        }
        //go back to Geo Zones page
        driver.findElement(By.cssSelector("[name=cancel]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // read header text
        header_text = driver.findElement(By.xpath("//h1[contains(text(),Text)]")).getText();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        System.out.println("Menu line '" + header_text + "' opened.");
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


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}


