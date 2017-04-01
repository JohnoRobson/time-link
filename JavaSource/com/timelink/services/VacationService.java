package com.timelink.services;

import javax.inject.Inject;

import com.timelink.ejbs.Hours;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.TimesheetRow;
import com.timelink.enums.TimesheetStatus;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.interfaces.VacationServiceInterface;

public class VacationService implements VacationServiceInterface {
  
  @Inject private HRProjectService hrps;
  @Inject private WorkPackageManager wpm;

  @Override
  public void applyVacation(Timesheet timesheet) {
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
      timesheet.getEmployee().setVacationTime((int) vacaTotal);
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
      timesheet.getEmployee().setVacationTime((int) vacaTotal);
    }
  }

}
