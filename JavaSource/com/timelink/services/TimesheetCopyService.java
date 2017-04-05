package com.timelink.services;

import com.timelink.ejbs.Hours;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.TimesheetRow;
import com.timelink.enums.DaysEnum;

import javax.ejb.Stateless;

@Stateless
public class TimesheetCopyService {
  
  /**
   * Returns a copy of the given timesheet with all of its and the elements in its
   * collections' dates changed to match the given week and year numbers.  The Ids
   * are also removed.
   * @param original The Timesheet to be copied.
   * @param week The week number to use.
   * @param year The year to use.
   * @return A copy of the given timesheet.
   */
  public Timesheet copyTimesheet(Timesheet original, int week, int year) {
    WeekNumberService weekNumberService = new WeekNumberService();
    Timesheet newTimesheet = new Timesheet(original.getEmployee());
    
    newTimesheet.setDate(weekNumberService.getDateFromWeekYear(week, year));
    newTimesheet.setFlextime(original.getFlextime());
    newTimesheet.setOvertime(original.getOvertime());
    
    for (int i = 0; i < original.getRows().size(); ++i) {
      newTimesheet.addRow();
    }
    
    int index = 0;
    
    for (TimesheetRow row : newTimesheet.getRows()) {
      row.setNote(row.getNote());
      row.setProjectId(row.getProjectId());
      row.setWorkPackageId(row.getWorkPackageId());
      for (int i = 0; i < 7; ++i) {
        Hours originalHour = original.getRows().get(index).getHourByDay(DaysEnum.values()[i]);
        Hours newHour = row.getHourByDay(DaysEnum.values()[i]);
        
        newHour.setHour(originalHour.getHour());
        newHour.setLabourGrade(originalHour.getLabourGrade());
        newHour.setProjectId(originalHour.getProjectId());
        newHour.setWorkPackageId(originalHour.getWorkPackageId());
      }
      ++index;
    }
    
    return newTimesheet;
  }
}
