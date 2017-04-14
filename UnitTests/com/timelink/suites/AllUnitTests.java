package com.timelink.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.timelink.controllers.tests.AdminMessageControllerTest;
import com.timelink.controllers.tests.ApproverControllerTest;
import com.timelink.controllers.tests.AssignEmployeesControllerTest;
import com.timelink.controllers.tests.AssignEmployeesToSupervisorControllerTest;
import com.timelink.controllers.tests.AssignEmployeesToTimesheetApproverTest;
import com.timelink.controllers.tests.AssignWorkPackageControllerTest;
import com.timelink.controllers.tests.BudgetWorkPackageControllerTest;
import com.timelink.controllers.tests.EstimateWorkPackageEffortControllerTest;
import com.timelink.controllers.tests.HumanResourceControllerTest;
import com.timelink.controllers.tests.LabourReportControllerTest;
import com.timelink.controllers.tests.LoginControllerTest;
import com.timelink.controllers.tests.MonthlyReportControllerTest;
import com.timelink.controllers.tests.NavControllerTest;
import com.timelink.controllers.tests.NewProjectControllerTest;
import com.timelink.controllers.tests.ProjectPlanningControllerTest;
import com.timelink.controllers.tests.TimesheetControllerTest;
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
    WorkPackageTests.class,
    AdminMessageControllerTest.class,
    ApproverControllerTest.class,
    AssignEmployeesControllerTest.class,
    AssignEmployeesToSupervisorControllerTest.class,
    AssignEmployeesToTimesheetApproverTest.class,
    AssignWorkPackageControllerTest.class,
    BudgetWorkPackageControllerTest.class,
    EstimateWorkPackageEffortControllerTest.class,
    HumanResourceControllerTest.class,
    LabourReportControllerTest.class,
    LoginControllerTest.class,
    MonthlyReportControllerTest.class,
    NavControllerTest.class,
    NewProjectControllerTest.class,
    ProjectPlanningControllerTest.class,
    TimesheetControllerTest.class
})
public class AllUnitTests {

}
