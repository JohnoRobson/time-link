package com.timelink.services;

import com.timelink.Session;
import com.timelink.ejbs.Hours;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.TimesheetRow;
import com.timelink.enums.TimesheetStatus;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.interfaces.VacationServiceInterface;

import javax.inject.Inject;

@SuppressWarnings("serial")
public class VacationService implements VacationServiceInterface {
  
  @Inject HRProjectService hrps;
  @Inject WorkPackageManager wpm;
  @Inject EmployeeManager em;
  @Inject Session ses;
  
public VacationService() {}
  
  public VacationService(HRProjectService hrps, WorkPackageManager wpm, EmployeeManager em) {
	  this.hrps = hrps;
	  this.wpm = wpm;
	  this.em = em;
  }

  @Override
  public void claimVacation(Timesheet timesheet) {
    if (timesheet.getStatus().equals(TimesheetStatus.WAITINGFORAPPROVAL.name())
        || timesheet.getStatus().equals(TimesheetStatus.REJECTED.name())) {
      float vacaTotal = timesheet.getEmployee().getVacationTime();
      for (TimesheetRow tsr : timesheet.getRows()) {
        if (hrps.isVacationWorkPackage(wpm.find(tsr.getWorkPackageId()))) {
          for (Hours hr : tsr.getHours()) {
            vacaTotal -= hr.getHour();
          }
        }
      }
      timesheet.getEmployee().setVacationTime(vacaTotal);
//      Employee emp = em.find(timesheet.getEmployee().getEmployeeId());
//      emp.setVacationTime(vacaTotal);
      em.merge(timesheet.getEmployee());
      ses.setCurrentEmployee(timesheet.getEmployee());
    }
  }

  @Override
  public void revertVacation(Timesheet timesheet) {
    if (timesheet.getStatus().equals(TimesheetStatus.WAITINGFORAPPROVAL.name())
        || timesheet.getStatus().equals(TimesheetStatus.REJECTED.name())) {
      float vacaTotal = timesheet.getEmployee().getVacationTime();
      for (TimesheetRow tsr : timesheet.getRows()) {
        if (hrps.isVacationWorkPackage(wpm.find(tsr.getWorkPackageId()))) {
          for (Hours hr : tsr.getHours()) {
            vacaTotal += hr.getHour();
          }
        }
      }
      timesheet.getEmployee().setVacationTime(vacaTotal);
//    Employee emp = em.find(timesheet.getEmployee().getEmployeeId());
//    emp.setVacationTime(vacaTotal);
      em.merge(timesheet.getEmployee());
      ses.setCurrentEmployee(timesheet.getEmployee());
    }
  }

}
