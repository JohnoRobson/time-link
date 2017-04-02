package com.timelink.services.interfaces;

import com.timelink.ejbs.Hours;
import com.timelink.ejbs.Timesheet;

import java.io.Serializable;
import java.util.List;

public interface FlextimeServiceInterface extends Serializable {
  public void addFlextime(Timesheet timesheet);
  
  public void claimFlextime(Timesheet timesheet, List<Hours> hours);
  
  public void revertFlextime(Timesheet timesheet);
}
