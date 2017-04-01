package com.timelink.pagetests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.timelink.pageobjects.HeaderSection;
import com.timelink.pageobjects.HomePage;
import com.timelink.pageobjects.LoginPage;

public class LoginPageTests {
    
    LoginPage loginPage;
    HomePage homePage;
    HeaderSection header;
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
    public void login_validPageTitle() {
        assertEquals("Login", loginPage.getLoginTitle());
    }

    @Test
    public void login_validCredentials_correctWelcomeMessage() {
        loginPage.executeLogin("pm", "pm");

        homePage = new HomePage(driver);
        assertTrue(homePage.getTitle().toLowerCase().contains("pmfname"));
    }

    @Test
    public void logout_validHomePageTitle() {
        loginPage.executeLogin("pm", "pm");
        homePage = new HomePage(driver);
        header = new HeaderSection(driver);
        header.executeLogout();
        assertEquals("Login", loginPage.getLoginTitle());
    }


}
