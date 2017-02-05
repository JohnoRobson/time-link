package com.timelink.controllers;

import com.timelink.TimesheetStatus;
import com.timelink.ejbs.Timesheet;
import com.timelink.managers.TimesheetManager;

import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class TimesheetController implements Serializable {
  private Timesheet timesheet;
  @Inject TimesheetManager tm;
  
  /**
   * Returns timesheet.
   * @return the timesheet
   */
  public Timesheet getTimesheet() {
    return timesheet;
  }

  /**
   * Sets timesheet to timesheet.
   * @param timesheet the timesheet to set
   */
  public void setTimesheet(Timesheet timesheet) {
    this.timesheet = timesheet;
  }
  
  //TODO make this gud
  public String save() {
    tm.merge(timesheet);
    return null;
  }
  
  //TODO make this gud
  public String submit() {
    timesheet.setStatus(TimesheetStatus.APPROVED);
    return null;
  }
}
