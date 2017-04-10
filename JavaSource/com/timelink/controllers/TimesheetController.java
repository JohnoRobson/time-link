package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Hours;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.Timesheet;
import com.timelink.ejbs.TimesheetRow;
import com.timelink.ejbs.WorkPackage;
import com.timelink.enums.TimesheetStatus;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.ProjectManager;
import com.timelink.managers.TimesheetManager;
import com.timelink.managers.WorkPackageManager;
import com.timelink.services.FlextimeService;
import com.timelink.services.HRProjectService;
import com.timelink.services.TimesheetCopyService;
import com.timelink.services.VacationService;
import com.timelink.services.WeekNumberService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("TimesheetController")
public class TimesheetController implements Serializable {
  private Timesheet selectedTimesheet;
  @Inject TimesheetManager tm;
  @Inject WorkPackageManager wpm;
  @Inject Session ses;
  @Inject ProjectManager pm;
  @Inject WeekNumberService weekNumberService;
  @Inject HRProjectService hrps;
  @Inject EmployeeManager em;
  @Inject FlextimeService fts;
  @Inject VacationService vs;
  @Inject TimesheetCopyService timesheetCopyService; 
  
  //ADD TIMESHEET MODAL STUFF
  private int week;
  private int year;
  
  public TimesheetController() {}
  
  /**
   * A convenience constructor for testing.
   * @param tm a timesheetmanager
   * @param wpm a workpackagemanager
   * @param ses a session
   * @param pm a projectmanager
   * @param weekNumberService a weeknumberservice
   * @param hrps an HRProjectService
   * @param fts a FlextimeService
   * @param vs a VacationService
   * @param timesheetCopyService a timesheetCopyService
   */
  public TimesheetController(TimesheetManager tm, WorkPackageManager wpm, Session ses,
      ProjectManager pm, WeekNumberService weekNumberService, HRProjectService hrps, EmployeeManager em,
      FlextimeService fts, VacationService vs, TimesheetCopyService timesheetCopyService) {
    this.tm = tm;
    this.wpm = wpm;
    this.ses = ses;
    this.pm = pm;
    this.weekNumberService = weekNumberService;
    this.hrps = hrps;
    this.em = em;
    this.fts = fts;
    this.vs = vs;
    this.timesheetCopyService = timesheetCopyService;
  }
  
  /**
   * Returns selectedTimesheet.
   * @return the selectedTimesheet
   */
  public Timesheet getSelectedTimesheet() {
    return selectedTimesheet;
  }

  /**
   * Sets selectedTimesheet to selectedTimesheet.
   * @param selectedTimesheet the selectedTimesheet to set
   */
  public void setSelectedTimesheet(Timesheet selectedTimesheet) {
    this.selectedTimesheet = selectedTimesheet;
  }
  
  /**
   * Save labour grade in hours.
   */
  public void saveHoursLabourGrade() {
    for (TimesheetRow tr : selectedTimesheet.getRows()) {
      for (Hours h : tr.getHours()) {
        h.setLabourGrade(ses.getCurrentEmployee().getLabourGrade());
      }
    }
  }
  
  //TODO make this gud
  /**
   * Saves the current selectedTimesheet and reloads it from the database.
   * @return A null to reload the page.
   */
  public String save() {
    if (selectedTimesheet != null) {
      saveHoursLabourGrade();
      selectedTimesheet.setEmployee(ses.getCurrentEmployee());
      tm.merge(selectedTimesheet);
      selectedTimesheet = tm.find(selectedTimesheet.getTimesheetId());
    }
    //selectedTimesheet = getSelectedTimesheet();
    return null;
  }
  
  public void refresh() {
    selectedTimesheet = null;
    getSelectedTimesheet();
  }
  
  //TODO make this work on a weekly, rather than a daily basis.
  /**
   * Adds a new timesheet for the logged in user.
   * If there is already a timesheet that matches the current day,
   * one will not be created.
   * @return Null, so that the page can be reloaded.
   */
  public String addTimesheet() {
    if (selectedTimesheet != null && selectedTimesheet.getDate().toString()
        .equals(new Date(Calendar.getInstance().getTime().getTime()).toString())) {
      return null;
    }
    
    save();
    
    Timesheet newTimesheet = new Timesheet(ses.getCurrentEmployee());
    newTimesheet.setDate(weekNumberService.getDateFromWeekYear(week, year));
    
    if (ses.getCurrentEmployee().getDefaultTimesheet() != null) {
      Timesheet def = ses.getCurrentEmployee().getDefaultTimesheet();
      
      newTimesheet = timesheetCopyService.copyTimesheet(def, week, year);
      
    }
    
    tm.persist(newTimesheet);
    //Update the selectedTimesheet PK so that it can be added to it's rows and hours.
    selectedTimesheet = tm.findLatest(ses.getCurrentEmployee());
    return null;
    
  }
  
  /**
   * Adds a row to the current selectedTimesheet.
   * @return null, to reload the page.
   */
  public String addRow() {
    selectedTimesheet.addRow();
    return null;
  }
  
  /**
   * Returns WorkPackages in the given project that the current employee is
   * assigned to.
   * @param projectNumber The projectId to be searched
   * @return A List of WorkPackages that the current employee is assigned to
   *     that are in the given project.
   */
  public List<WorkPackage> getAssignedWorkPackages(Integer projectNumber) {
    ArrayList<WorkPackage> newList = null; 
    if (projectNumber == 10) {
      newList = new ArrayList<WorkPackage>();
      newList.add(getFlextimeWorkPackage());
      newList.add(getSickWorkPackage());
      newList.add(getVacationWorkPackage());
      newList.add(getLongTermDisabilityWorkPackage());
      newList.add(getShortTermDisabilityWorkPackage());
      newList.add(getStatHolidayWorkPackage());
    } else {
      Project pro = pm.find(projectNumber);
      if (pro != null) {
        List<WorkPackage> list = wpm.findAssigned(ses.getCurrentEmployee(), pro);
        newList = new ArrayList<WorkPackage>();
        for (WorkPackage wp : list) {
          if (wpm.isLeaf(wp)) {
            newList.add(wp);
          }
        }
      }
    }
    return newList;
  }

  /**
   * Return the selectedTimesheetId.
   * @return the selectedTimesheetId
   */
  public Integer getSelectedTimesheetId() {
    if (selectedTimesheet == null) {
      return null;
    }
    return selectedTimesheet.getTimesheetId();
  }

  /**
   * Set the selectedTimesheetId to selectedTimesheetId.
   * @param selectedTimesheetId the selectedTimesheetId to set
   */
  public void setSelectedTimesheetId(Integer selectedTimesheetId) {
    if (selectedTimesheetId == null) {
      return;
    }
    
    if (this.selectedTimesheet != null
        && this.selectedTimesheet.getTimesheetId() == selectedTimesheetId) {
      return;
    }
    
    Timesheet ts = tm.find(selectedTimesheetId);
    tm.detach(ts);
    this.selectedTimesheet = tm.find(selectedTimesheetId);
  }
  
  public List<Timesheet> getTimesheets() {
    return tm.findByEmployee(ses.getCurrentEmployee());
  }
  
  public void deleteRow(TimesheetRow row) {
    selectedTimesheet.deleteRow(row);
    save();
  }
  
  private WorkPackage getSickWorkPackage() {
    return hrps.getSickDayWorkPackage();
  }
  
  private WorkPackage getVacationWorkPackage() {
    return hrps.getVacationWorkPackage();
  }
  
  private WorkPackage getStatHolidayWorkPackage() {
    return hrps.getStatHolidayWorkPackage();
  }

  private WorkPackage getShortTermDisabilityWorkPackage() {
    return hrps.getShortTermDisabilityWorkPackage();
  }

  private WorkPackage getLongTermDisabilityWorkPackage() {
    return hrps.getLongTermDisabilityWorkPackage();
  }

  public WorkPackage getFlextimeWorkPackage() {
    return hrps.getFlextimeWorkPackage();
  }
  
  public Project getHRProject() {
    return hrps.getHRProject();
  }
  
  public List<Project> getAssignedProjects() {
    List<Project> list = new ArrayList<Project>();
    list.addAll(ses.getCurrentEmployee().getProjects());
    list.add(hrps.getHRProject());
    return list;
  }
  
  public void setAsDefault() {
    Employee temp = em.find(ses.getCurrentEmployee().getEmployeeId());
    temp.setDefaultTimesheet(selectedTimesheet);
    em.merge(temp);
    ses.setCurrentEmployee(em.find(temp.getEmployeeId()));
  }
  
  public void clearDefault() {
    Employee temp = em.find(ses.getCurrentEmployee().getEmployeeId());
    temp.setDefaultTimesheet(null);
    em.merge(temp);
    ses.setCurrentEmployee(em.find(temp.getEmployeeId()));
  }
  
  public Integer getCurrentWeekNumber() {
    return weekNumberService.getWeekNumber(new Date());
  }
  
  //ADD TIMESHEET MODAL
  /**
   * Returns week.
   * @return the week
   */
  public int getWeek() {
    return week;
  }

  /**
   * Sets week to week.
   * @param week the week to set
   */
  public void setWeek(int week) {
    this.week = week;
  }

  /**
   * Returns week.
   * @return the year
   */
  public int getYear() {
    return year;
  }

  /**
   * Sets week to week.
   * @param year the year to set
   */
  public void setYear(int year) {
    this.year = year;
  }
  
  /**
   * Sets the current selectedTimesheet's status to submitted
   * and saves it.  Also changes the employee's flex hours.
   * @return A null to reload the page.
   */
  public String submit() {
    if (selectedTimesheet.getStatus().equals(TimesheetStatus.NOTSUBMITTED.toString())
        || selectedTimesheet.getStatus().equals(TimesheetStatus.REJECTED.toString())) {
      
      if (selectedTimesheet.isValid()) {
        selectedTimesheet.setStatus("" + TimesheetStatus.WAITINGFORAPPROVAL.ordinal());
        fts.claimFlextime(selectedTimesheet);
        vs.claimVacation(selectedTimesheet);
        //em.merge(selectedTimesheet.getEmployee());
      } else {
        //TODO set to display an error message explaining validation failure
        return null;
      }
      
    }
    save();
    return null;
  }
  
  public int getWeekNumber(Timesheet ts) {
    return weekNumberService.getWeekNumber(ts.getDate());
  }
  
}
