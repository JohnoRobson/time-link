package com.timelink.services;

import java.util.List;

import javax.inject.Inject;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Hours;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.TimesheetRow;
import com.timelink.enums.TimesheetStatus;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.interfaces.VacationServiceInterface;

@SuppressWarnings("serial")
public class VacationService implements VacationServiceInterface {
  
  @Inject HRProjectService hrps;
  @Inject WorkPackageManager wpm;
  @Inject EmployeeManager em;

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
    }
  }

}
