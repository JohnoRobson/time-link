package com.timelink.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HeaderSection {
    WebDriver driver;
    Actions actions;
    
    By logoutButton = By.className("s-logoutButton");

    By timesheetButton = By.className("s-timesheet");
    By timesheetHoverSection = By.className("s-timesheetMenu");

    public HeaderSection(WebDriver webDriver) {
        this.driver = webDriver;
        actions = new Actions(webDriver);
    }
    
    public void executeLogout() {
        driver.findElement(this.logoutButton).click();
    }

    public void goToTimesheet() {
        WebElement timesheetHover = driver.findElement(this.timesheetHoverSection);
        actions.moveToElement(timesheetHover);
        //driver.findElement(this.timesheetButton).click();
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('s-timesheet')[0].click();");

    }

}
