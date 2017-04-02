package com.timelink.services;

import com.timelink.services.interfaces.WeekNumberServiceInterface;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SuppressWarnings("serial")
public class WeekNumberService implements WeekNumberServiceInterface {

  @Override
  public Date getDateFromWeekYear(int week, int year) {
    if (week <= 0 || week > 52 || year < 1970) {
        throw new IllegalArgumentException();
    }
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

  @Override
  //ONLY WORKS FOR THE CURRENT YEAR
  public Date getDateFromWeekNumber(int week) {
    Calendar calendar = new GregorianCalendar();
    Calendar year = new GregorianCalendar();
    calendar.clear();
    calendar.set(Calendar.YEAR, year.get(Calendar.YEAR));
    calendar.set(Calendar.WEEK_OF_YEAR, week);
    return calendar.getTime();
  }

}
