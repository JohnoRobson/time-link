package com.timelink.ejbs.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Timesheet;

public class TimesheetTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConstructor() {
        Employee emp = new Employee();
        emp.setUserId("5");
        Employee tsApprover = new Employee();
        emp.setTimesheetApprover(tsApprover);
        Timesheet ts = new Timesheet(emp);

        assertEquals("5", ts.getEmployee().getUserId());
        assertEquals(ts.getTimesheetApprover(), tsApprover);
        assertEquals("NOTSUBMITTED", ts.getStatus());
    }

}
