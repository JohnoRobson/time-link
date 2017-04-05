package com.timelink.services.interfaces;

import java.io.Serializable;
import com.timelink.ejbs.Timesheet;

public interface VacationServiceInterface extends Serializable {

  public void claimVacation(Timesheet timesheet);
  
  public void revertVacation(Timesheet timesheet);
}
