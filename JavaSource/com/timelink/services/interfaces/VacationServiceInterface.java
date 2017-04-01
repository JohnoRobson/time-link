package com.timelink.services.interfaces;

import com.timelink.ejbs.Timesheet;

public interface VacationServiceInterface {

  public void applyVacation(Timesheet timesheet);
  
  public void revertVacation(Timesheet timesheet);
}
