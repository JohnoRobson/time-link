package com.timelink.services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.timelink.services.interfaces.WeekNumberServiceInterface;

@SuppressWarnings("serial")
public class WeekNumberService implements WeekNumberServiceInterface {

  @Override
  public Date getDateFromWeekYear(int week, int year) {
    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    calendar.set(Calendar.WEEK_OF_YEAR, week);
    calendar.set(Calendar.YEAR, year);
    return calendar.getTime();
  }

  @Override
  public int getWeekNumber(Date date) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    return calendar.get(Calendar.WEEK_OF_YEAR);
  }

}
