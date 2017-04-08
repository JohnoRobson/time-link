package com.timelink.ejbs.tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.timelink.ejbs.Hours;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.TimesheetRow;

public class TimesheetRowTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConstructor() {
        Timesheet ts = new Timesheet();
        ts.setTimesheetId(5);
        ts.setDate(new Date());
        TimesheetRow row = new TimesheetRow(ts);
        
        for(Hours hour : row.getHours()) {
            assertEquals(5, hour.getTimesheet().getTimesheetId());
        }
        

    }

}
