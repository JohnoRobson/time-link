package com.timelink.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TimesheetPage {
    WebDriver driver;
    
    By timesheetButton = By.className("s-createTimesheetBtn");

    By newTimesheetYearTextbox = By.className("s-createTimesheetYearText");
    By newTimesheetWeekTextbox = By.className("s-createTimesheetWeekText");
    By confirmCreateTimesheetBtn = By.className("s-confirmCreateTimesheetBtn");

    By timesheetStatus = By.className("s-timesheetStatus");
    

    public TimesheetPage(WebDriver webDriver) {
        this.driver = webDriver;
    }
    
    public void createTimesheet() {
        driver.findElement(this.timesheetButton).click();
    }

    public void setNewTimesheetYear(String year) {
        driver.findElement(this.newTimesheetYearTextbox).sendKeys(year);;
    }

    public void setNewTimesheetWeek(String week) {
        driver.findElement(this.newTimesheetWeekTextbox).sendKeys(week);;
    }

    public void clickConfirmCreateNewTimesheet() {
        driver.findElement(this.newTimesheetWeekTextbox).click();
    }
    
    public void executeCreateNewTimesheet(String year, String week) {
        this.createTimesheet();
        this.setNewTimesheetYear(year);
        this.setNewTimesheetWeek(week);
        this.clickConfirmCreateNewTimesheet();
    }
    
    public String getTimesheetStatus() {
        return driver.findElement(this.timesheetStatus).getText();
    }
}
