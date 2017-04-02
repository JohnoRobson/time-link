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
import com.timelink.pagetests.LoginPageTests;
import com.timelink.pagetests.TimesheetTests;

@RunWith(Suite.class)
@SuiteClasses({
    LoginPageTests.class,
    TimesheetTests.class,
})
public class AllSeleniumTests {

}
