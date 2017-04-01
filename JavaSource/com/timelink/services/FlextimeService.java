package com.timelink.services;

import javax.inject.Inject;

import com.timelink.ejbs.Hours;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.TimesheetRow;
import com.timelink.enums.TimesheetStatus;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.interfaces.FlextimeServiceInterface;

@SuppressWarnings("serial")
public class FlextimeService implements FlextimeServiceInterface {
  
  @Inject private HRProjectService hrps;
  @Inject private WorkPackageManager wpm;
  
  /**
   * Applies changes to the flextime in the timesheet and also
   * changes the banked flextime in the employee.
   */
  @Override
  public void applyFlextime(Timesheet timesheet) {
    if (timesheet.getStatus().equals(TimesheetStatus.WAITINGFORAPPROVAL.name())
        || timesheet.getStatus().equals(TimesheetStatus.REJECTED.name())) {
      float flex = timesheet.getEmployee().getFlexTime();
      flex += timesheet.getFlextime();
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

}
