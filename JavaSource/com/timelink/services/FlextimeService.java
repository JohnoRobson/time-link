package com.timelink.services;

import java.util.List;

import javax.inject.Inject;

import com.timelink.ejbs.Hours;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.TimesheetRow;
import com.timelink.enums.TimesheetStatus;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.interfaces.FlextimeServiceInterface;

@SuppressWarnings("serial")
public class FlextimeService implements FlextimeServiceInterface {
  
  @Inject HRProjectService hrps;
  @Inject WorkPackageManager wpm;
  
  /**
   * Reverts changes to the flextime in the timesheet and also
   * changes the banked flextime in the employee.
   */
  @Override
  public void revertFlextime(Timesheet timesheet) {
    if (timesheet.getStatus().equals(TimesheetStatus.WAITINGFORAPPROVAL.name())
        || timesheet.getStatus().equals(TimesheetStatus.APPROVED.name())) {
      float flex = timesheet.getEmployee().getFlexTime();
      flex -= timesheet.getFlextime();
      for (TimesheetRow tsr : timesheet.getRows()) {
        if (hrps.isFlextimeWorkPacakge(wpm.find(tsr.getWorkPackageId()))) {
          for (Hours hr : tsr.getHours()) {
            flex += hr.getHour();
          }
        }
      }
      timesheet.getEmployee().setFlexTime((int) flex);
    } 
  }

  @Override
  public void addFlextime(Timesheet timesheet) {
    if (timesheet.getStatus().equals(TimesheetStatus.WAITINGFORAPPROVAL.name())
        || timesheet.getStatus().equals(TimesheetStatus.APPROVED.name())) {
      float flex = timesheet.getEmployee().getFlexTime();
      flex += timesheet.getFlextime();
      timesheet.getEmployee().setFlexTime((int) flex);
    }
  }

  /**
   * Applies changes to the flextime in the timesheet and also
   * changes the banked flextime in the employee.
   */
  @Override
  public void claimFlextime(Timesheet timesheet, List<Hours> hours) {
    if (timesheet.getStatus().equals(TimesheetStatus.WAITINGFORAPPROVAL.name())
        || timesheet.getStatus().equals(TimesheetStatus.APPROVED.name())) {
      float flex = timesheet.getEmployee().getVacationTime();
          for (Hours hr : hours) {
            flex -= hr.getHour();
          }
      timesheet.getEmployee().setVacationTime((int) flex);
    }
  }

}
