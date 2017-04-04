package com.timelink.pagetests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.timelink.pageobjects.HeaderSection;
import com.timelink.pageobjects.LoginPage;
import com.timelink.pageobjects.TimesheetPage;

public class TimesheetTests {

    LoginPage loginPage;
    HeaderSection header;
    TimesheetPage timesheetPage;

    WebDriver driver;

    @Before
    public void setUpClass() throws Exception {
        File chromeDriverFile;

        if (System.getProperty("os.name").startsWith("Windows")) {
            chromeDriverFile = new File("resources/chromedriver.exe");
        } else {
            chromeDriverFile = new File("resources/chromedriver");
        }

        System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
 
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage = new LoginPage(driver);
        driver.get("http://localhost:8080/time-link");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void createTimesheet() {
        loginPage.executeLogin("pm", "pm");
        header = new HeaderSection(driver);
        header.goToTimesheet();
        timesheetPage = new TimesheetPage(driver);
        timesheetPage.executeCreateNewTimesheet("1995", "5");
        for (int i = 0; i < 3; i++) {
            timesheetPage.addRow();
        }
        assertTrue(timesheetPage.getTimesheetStatus().toLowerCase().contains("notsubmitted"));
    }

}
