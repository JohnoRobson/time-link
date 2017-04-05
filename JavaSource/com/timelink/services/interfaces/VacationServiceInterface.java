package com.timelink.services.interfaces;

import com.timelink.ejbs.Timesheet;

import java.io.Serializable;

public interface VacationServiceInterface extends Serializable {

  public void claimVacation(Timesheet timesheet);
  
  public void revertVacation(Timesheet timesheet);
  
}