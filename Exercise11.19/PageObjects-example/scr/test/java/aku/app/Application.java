package aku.app;

import aku.pages.MainPage;
import aku.pages.ProductPage;
import aku.pages.ShoppingCartPage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class Application {
    private WebDriver driver;
    private MainPage mainPage;
    private ProductPage productPage;
    private ShoppingCartPage cartPage;

    public Application() {
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new ShoppingCartPage(driver);
    }

    public void quit() { driver.quit(); }

    public void newPurchase(){
        mainPage.open();
        mainPage.enterUsername("tkulaha@gmail.com");
        mainPage.enterPassword("12345");
        mainPage.submitLogin();
        //Buy 1 product
        mainPage.findProductForBuying();
        productPage.AddProductToCart();
        productPage.returnToMainPage();
        //Buy 2 product
        mainPage.findProductForBuying();
        productPage.AddProductToCart();
        productPage.returnToMainPage();
        //Buy 3 product
        mainPage.findProductForBuying();
        productPage.AddProductToCart();
        productPage.returnToMainPage();
        //Empty shopping cart
        cartPage.OpenShoppingCart();
        cartPage.EmptyShoppingCart();
        cartPage.returnToMainPage();
    }
}
