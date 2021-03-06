package com.timelink.services;

import javax.inject.Inject;

import com.timelink.Session;
import com.timelink.ejbs.Hours;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.TimesheetRow;
import com.timelink.enums.TimesheetStatus;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.interfaces.FlextimeServiceInterface;

@SuppressWarnings("serial")
public class FlextimeService implements FlextimeServiceInterface {
  
  @Inject HRProjectService hrps;
  @Inject WorkPackageManager wpm;
  @Inject EmployeeManager em;
  @Inject Session ses;
  
  public FlextimeService() {}
  
  public FlextimeService(HRProjectService hrps, WorkPackageManager wpm, EmployeeManager em) {
	  this.hrps = hrps;
	  this.wpm = wpm;
	  this.em = em;
  }
  
  /**
   * Reverts changes to the flextime in the timesheet and also
   * changes the banked flextime in the employee.
   */
  @Override
  public void revertFlextime(Timesheet timesheet) {
    if (timesheet.getStatus().equals(TimesheetStatus.WAITINGFORAPPROVAL.name())
        || timesheet.getStatus().equals(TimesheetStatus.REJECTED.name())) {
      float flex = timesheet.getEmployee().getFlexTime();
      flex -= timesheet.getFlextime();
      for (TimesheetRow tsr : timesheet.getRows()) {
        if (hrps.isFlextimeWorkPackage(wpm.find(tsr.getWorkPackageId()))) {
          for (Hours hr : tsr.getHours()) {
            flex += hr.getHour();
          }
        }
      }
      timesheet.getEmployee().setFlexTime(flex);
//    Employee emp = em.find(timesheet.getEmployee().getEmployeeId());
//    emp.setFlexTime((int) flex);
      em.merge(timesheet.getEmployee());
      ses.setCurrentEmployee(timesheet.getEmployee());
    } 
  }

  /**
   * Applies changes to the flextime in the timesheet and also
   * changes the banked flextime in the employee.
   */
  @Override
  public void claimFlextime(Timesheet timesheet) {
    if (timesheet.getStatus().equals(TimesheetStatus.WAITINGFORAPPROVAL.name())
        || timesheet.getStatus().equals(TimesheetStatus.APPROVED.name())) {
      float flex = timesheet.getEmployee().getFlexTime();
      for (TimesheetRow tsr : timesheet.getRows()) {
        if (hrps.isFlextimeWorkPackage(wpm.find(tsr.getWorkPackageId()))) {
          for (Hours hr : tsr.getHours()) {
            flex -= hr.getHour();
          }
        }
      }
      flex += timesheet.getFlextime();
      timesheet.getEmployee().setFlexTime(flex);
//      Employee emp = em.find(timesheet.getEmployee().getEmployeeId());
//      emp.setFlexTime((int) flex);
      em.merge(timesheet.getEmployee());
      ses.setCurrentEmployee(timesheet.getEmployee());
    }
  }

}