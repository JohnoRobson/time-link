package com.timelink.services.interfaces;

import java.io.Serializable;
import java.util.Date;

public interface WeekNumberServiceInterface extends Serializable {
  public Date getDateFromWeekYear(int week, int year);
  
  public int getWeekNumber(Date date);
  
  public Date getDateFromWeekNumber(int week);
}
