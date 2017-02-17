package com.timelink.controllers.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.timelink.controllers.NavController;

public class NavControllerTest {

    private NavController testController;

    @Before
    public void setUp() {
        testController = new NavController();
    }

    @Test
    public void testHome() {
        assertEquals("home", testController.home());
        assertNotEquals("nothome", testController.home());
    }
    
    @Test
    public void testTimesheet() {
        assertEquals("timesheet", testController.timesheet());
        assertNotEquals("notTimesheet", testController.timesheet());
    }
    
    @Test
    public void testReport() {
        assertEquals("report", testController.report());
        assertNotEquals("notreport", testController.report());
    }

}
