package com.timelink.services.tests;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.timelink.services.WeekNumberService;

public class WeekNumberServiceTest {

    WeekNumberService weekNumService;
    @Before
    public void setUp() throws Exception {
        weekNumService = new WeekNumberService();
    }

    @Test(expected=IllegalArgumentException.class)
    public void getDateFromWeekYear_negWeek_throwsException() {
        weekNumService.getDateFromWeekYear(-1, 1990);
    }

    @Test(expected=IllegalArgumentException.class)
    public void getDateFromWeekYear_tooManyWeeks_throwsException() {
        weekNumService.getDateFromWeekYear(54, 1990);
    }

    @Test(expected=IllegalArgumentException.class)
    public void getDateFromWeekYear_tooLowYear_throwsException() {
        weekNumService.getDateFromWeekYear(51, 1920);
    }

}
