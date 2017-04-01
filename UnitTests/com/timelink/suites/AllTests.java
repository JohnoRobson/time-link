package com.timelink.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.timelink.ejbs.tests.BudgetedHoursTests;
import com.timelink.ejbs.tests.EmployeeTest;
import com.timelink.ejbs.tests.ProjectTest;
import com.timelink.ejbs.tests.TimesheetRowTest;
import com.timelink.ejbs.tests.TimesheetTest;
import com.timelink.ejbs.tests.WorkPackageTests;

@RunWith(Suite.class)
@SuiteClasses({
    BudgetedHoursTests.class,
    EmployeeTest.class,
    ProjectTest.class,
    TimesheetRowTest.class,
    TimesheetTest.class,
    WorkPackageTests.class
})
public class AllTests {

}
