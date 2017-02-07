package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.Timesheet;
import com.timelink.managers.TimesheetManager;

import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
@Named("TimesheetController")
public class TimesheetController implements Serializable {
  private Timesheet timesheet;
  @Inject TimesheetManager tm;
  @Inject Session ses;
  
  public TimesheetController() {
    
  }
  
  /**
   * Returns timesheet.  If there isn't a timesheet for the current employee
   * one will be created.
   * @return the timesheet
   */
  public Timesheet getTimesheet() {
    if (timesheet == null) {
      timesheet = tm.findLatest(ses.getCurrentEmployee());
    }
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
    timesheet.setStatus("gud");
    return null;
  }
  
  /**
   * Adds a new timesheet for the logged in user.
   * @return Null, so that the page can be reloaded.
   */
  public String addTimesheet() {
    tm.merge(timesheet);
    timesheet = new Timesheet(ses.getCurrentEmployee());
    tm.persist(timesheet);
    return null;
  }
  
  /**
   * Adds a row to the current timesheet.
   * @return null, to reload the page.
   */
  public String addRow() {
    timesheet.addRow();
    return null;
  }
}
