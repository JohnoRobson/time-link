package com.timelink.pageobjects;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginPage {
    WebDriver driver;
    
    By username = By.className("s-username");

    By password = By.className("s-password");

    By titleText = By.className("s-titleText");

    By loginButton = By.className("s-btnLogin");
    
    public LoginPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void setUsername(String username) {
        driver.findElement(this.username).sendKeys(username);;
    }

    public void setPassword(String password) {
        driver.findElement(this.password).sendKeys(password);;
    }
    
    public void clickLogin(){
        driver.findElement(this.loginButton).click();
    }
    

    public String getLoginTitle(){
        return driver.findElement(this.titleText).getText();
    }

    public void executeLogin(String strUserName,String strPasword) {
        this.setUsername(strUserName);
        this.setPassword(strPasword);
        this.clickLogin();        
    }


}
