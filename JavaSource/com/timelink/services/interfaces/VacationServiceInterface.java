package com.timelink.services.interfaces;

import java.io.Serializable;
import java.util.List;

import com.timelink.ejbs.Hours;
import com.timelink.ejbs.Timesheet;

public interface VacationServiceInterface extends Serializable {

  public void claimVacation(Timesheet timesheet, List<Hours> hours);
  
  public void revertVacation(Timesheet timesheet);
}
