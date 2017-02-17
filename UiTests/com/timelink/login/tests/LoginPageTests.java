package com.timelink.login.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginPageTests {
    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() throws Exception {
        File file = new File("resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        driver = new ChromeDriver();
        baseUrl = "http://localhost:8080/time-link-0.0.1-SNAPSHOT/";
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
	    driver.get(baseUrl);
	    driver.findElement(By.className("username")).sendKeys("Admin");
	    driver.findElement(By.className("password")).sendKeys("Admin");
	    driver.findElement(By.className("login-btn")).click();
	    String titleText = driver.findElement(By.className("page-title")).getText();
	    assertTrue(titleText.toLowerCase().contains("timelink"));
    }

}
