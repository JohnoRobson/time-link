package com.timelink.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class HomePage {

    WebDriver driver;
    
    By titleText = By.className("s-homeTitleText");
    
    public HomePage(WebDriver webDriver) {
        this.driver = webDriver;
    }
    
    public String getTitle() {
        return driver.findElement(this.titleText).getText();
    }

}
