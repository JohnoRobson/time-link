package com.timelink.ejbs.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.timelink.ejbs.BudgetedProjectHours;
import com.timelink.ejbs.BudgetedWorkPackageHours;
import com.timelink.ejbs.WorkPackage;

public class EstimatedWorkPackageHoursTest {

    BudgetedWorkPackageHours budgetedHours;

    @Before
    public void setUp() throws Exception {
        budgetedHours = new BudgetedWorkPackageHours();
    }

    @After
    public void tearDown() throws Exception {
    }

    //duplicated code
    @Test
    public void setWorkPackageLineId_null_nullWorkPackage() {
        budgetedHours.setWorkPackageLineId(null);
        assertNull(budgetedHours.getWorkPackage());
    }
    
    @Test
    public void setWorkPackageLineId_listBudgetedWorkPackageHours_wpContainsList() {
        WorkPackage wp = new WorkPackage();
        wp.setPlannedHours(new ArrayList<BudgetedWorkPackageHours>());

        budgetedHours.setWorkPackageLineId(wp);
        

        assertTrue(wp.getPlannedHours().contains(budgetedHours));
    }

}
