package com.timelink.services.interfaces;

import com.timelink.ejbs.Timesheet;

import java.io.Serializable;

public interface FlextimeServiceInterface extends Serializable {
  
  public void claimFlextime(Timesheet timesheet);
  
  public void revertFlextime(Timesheet timesheet);
}
