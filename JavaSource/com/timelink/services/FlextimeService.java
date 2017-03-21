package com.timelink.services;

import com.timelink.ejbs.Timesheet;
import com.timelink.enums.TimesheetStatus;
import com.timelink.services.interfaces.FlextimeServiceInterface;

@SuppressWarnings("serial")
public class FlextimeService implements FlextimeServiceInterface {
  
  /**
   * Applies changes to the flextime in the timesheet and also
   * changes the banked flextime in the employee.
   */
  @Override
  public void applyFlextime(Timesheet timesheet) {
    if (timesheet.getStatus().equals(TimesheetStatus.WAITINGFORAPPROVAL.name())
        || timesheet.getStatus().equals(TimesheetStatus.REJECTED.name())) {
      float flex = timesheet.getEmployee().getFlexTime();
      flex -= timesheet.getFlextime();
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
      flex += timesheet.getFlextime();
      timesheet.getEmployee().setFlexTime((int) flex);
    } 
  }

}
