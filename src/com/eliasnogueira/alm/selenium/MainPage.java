package com.eliasnogueira.alm.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Pagina principal do site PrestaShop
 * 
 * @author Elias Nogueira <elias.nogueira@gmail.com>
 */
public class MainPage {

    private WebDriver driver;
    
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    
    /**
     * Clica no link login da p‡gina principal do PrestShop
     * @return driver corrente
     */
    public void acessaLogin() {
        driver.findElement(By.linkText("Log in")).click();
    }
    
    public void logout() {
        driver.findElement(By.linkText("Log out")).click();
    }
}
