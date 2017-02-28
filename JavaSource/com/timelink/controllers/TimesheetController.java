package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.Timesheet;
import com.timelink.managers.TimesheetManager;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

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
  /**
   * Saves the current timesheet and reloads it from the database.
   * @return A null to reload the page.
   */
  public String save() {
    tm.merge(timesheet);
    timesheet = getTimesheet();
    return null;
  }
  
  //TODO make this gud
  /**
   * Sets the current timesheet's status to submitted
   * and saves it.
   * @return A null to reload the page.
   */
  public String submit() {
    timesheet.setStatus("1");
    save();
    return null;
  }
  
  public void refresh() {
    timesheet = null;
    getTimesheet();
  }
  
  //TODO make this work on a weekly, rather than a daily basis.
  /**
   * Adds a new timesheet for the logged in user.
   * If there is already a timesheet that matches the current day,
   * one will not be created.
   * @return Null, so that the page can be reloaded.
   */
  public String addTimesheet() {
    if (timesheet != null && timesheet.getDate().toString()
        .equals(new Date(Calendar.getInstance().getTime().getTime()).toString())) {
      return null;
    }
    tm.merge(timesheet);
    timesheet = new Timesheet(ses.getCurrentEmployee());
    tm.persist(timesheet);
    //Update the timesheet PK so that it can be added to it's rows and hours.
    timesheet = tm.findLatest(ses.getCurrentEmployee());
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
