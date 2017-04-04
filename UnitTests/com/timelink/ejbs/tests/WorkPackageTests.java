package com.timelink.ejbs.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.timelink.ejbs.BudgetedWorkPackageHours;
import com.timelink.ejbs.LabourGrade;
import com.timelink.ejbs.WorkPackage;

public class WorkPackageTests {
    
    WorkPackage wp;
    BudgetedWorkPackageHours bh1;
    BudgetedWorkPackageHours bh2;
    @Before
    public void setUp() throws Exception {
        wp = new WorkPackage();
        List<BudgetedWorkPackageHours> hr = new ArrayList<>();
        LabourGrade lg1 = new LabourGrade();
        lg1.setLabourGradeId(1);
        lg1.setCostRate(100);

        LabourGrade lg2 = new LabourGrade();
        lg2.setLabourGradeId(4);
        lg2.setCostRate(400);

        bh1 = new BudgetedWorkPackageHours();
        bh1.setLabourGrade(lg1);
        bh1.setManDay(100);

        bh2 = new BudgetedWorkPackageHours();
        bh2.setLabourGrade(lg2);
        bh2.setManDay(400);

        hr.add(bh1);
        hr.add(bh2);
        
        wp.setPlannedHours(hr);
    }

    @After
    public void tearDown() {
        
    }
    @Test
    public void testGetTotalFromGrade_labourGrade_corespondingTotal() {
        assertEquals(100, wp.getTotalFromGrade(1));
        assertEquals(400, wp.getTotalFromGrade(4));

    }

    @Test
    public void getTotalFromGrade_nonExistentLabourGrade_0() {
        assertEquals(0, wp.getTotalFromGrade(10));
    }
    
    @Test
    public void getPlannedHourFromLabourGrade_labourGrade_corespondingPlannedHour() {
        assertEquals(bh1, wp.getPlannedHourFromLabourGrade(1));
        assertEquals(bh2, wp.getPlannedHourFromLabourGrade(4));
        assertEquals(30, wp.getPlannedHourFromLabourGrade(30).getLabourGrade().getLabourGradeId());
    }

    @Test
    public void removePlannedHourByLabourGrade_nonExistentLabourGrade_samePlannedHours() {
        wp.removePlannedHourByLabourGrade(900);
        assertEquals(2, wp.getPlannedHours().size());
    }

    @Test
    public void removePlannedHourByLabourGrade_existingLabourGrade_plannedHourRemoved() {
        wp.removePlannedHourByLabourGrade(1);
        wp.getPlannedHours();
        assertEquals(1, wp.getPlannedHours().size());
    }

    @Test
    public void getTotalPlannedHours_validPlannedHours_validTotal() {
        assertEquals(500, wp.getTotalPlannedHours());
    }

    @Test
    public void getTotalPlannedHours_nullPlannedHours_0() {
        wp.setPlannedHours(null);
        assertEquals(0, wp.getTotalPlannedHours());
    }

    @Test
    public void getTotalPlannedCosts_validPlannedHours_validTotal() {
        assertEquals(170000, wp.getTotalPlannedCosts());
    }

    @Test
    public void getTotalPlannedCosts_nullPlannedHours_0() {
        wp.setPlannedHours(null);
        assertEquals(0, wp.getTotalPlannedCosts());
    }

    @Test
    public void getTotalPlannedCosts_emptyListPlannedHours_0() {
        wp.setPlannedHours(new ArrayList<BudgetedWorkPackageHours>());
        assertEquals(0, wp.getTotalPlannedCosts());
    }
    
    
    
}
