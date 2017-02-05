package com.timelink.ejbs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Timesheet implements Serializable {
  private int timesheetId;
  private int employeeId;
  private Date date;
  private Employee timesheetApprover;
  private BigDecimal overtime;
  private BigDecimal flextime;
  private List<TimesheetRow> rows;
  
  
  
  /**
   * @return the timesheetId
   */
  public int getTimesheetId() {
    return timesheetId;
  }



  /**
   * @param timesheetId the timesheetId to set
   */
  public void setTimesheetId(int timesheetId) {
    this.timesheetId = timesheetId;
  }



  /**
   * @return the employeeId
   */
  public int getEmployeeId() {
    return employeeId;
  }



  /**
   * @param employeeId the employeeId to set
   */
  public void setEmployeeId(int employeeId) {
    this.employeeId = employeeId;
  }



  /**
   * @return the date
   */
  public Date getDate() {
    return date;
  }



  /**
   * @param date the date to set
   */
  public void setDate(Date date) {
    this.date = date;
  }



  /**
   * @return the timesheetApprover
   */
  public Employee getTimesheetApprover() {
    return timesheetApprover;
  }



  /**
   * @param timesheetApprover the timesheetApprover to set
   */
  public void setTimesheetApprover(Employee timesheetApprover) {
    this.timesheetApprover = timesheetApprover;
  }



  /**
   * @return the overtime
   */
  public BigDecimal getOvertime() {
    return overtime;
  }



  /**
   * @param overtime the overtime to set
   */
  public void setOvertime(BigDecimal overtime) {
    this.overtime = overtime;
  }



  /**
   * @return the flextime
   */
  public BigDecimal getFlextime() {
    return flextime;
  }



  /**
   * @param flextime the flextime to set
   */
  public void setFlextime(BigDecimal flextime) {
    this.flextime = flextime;
  }



  /**
   * @return the rows
   */
  public List<TimesheetRow> getRows() {
    return rows;
  }



  /**
   * @param rows the rows to set
   */
  public void setRows(List<TimesheetRow> rows) {
    this.rows = rows;
  }



  class TimesheetRow {
    private int timesheetRowId;
    private Project project;
    private Timesheet timesheet;
    private WorkPackage workPackage;
    private List<Hours> hours;
    
    /**
     * @return the timesheetRowId
     */
    public int getTimesheetRowId() {
      return timesheetRowId;
    }
    
    /**
     * @param timesheetRowId the timesheetRowId to set
     */
    public void setTimesheetRowId(int timesheetRowId) {
      this.timesheetRowId = timesheetRowId;
    }
    
    /**
     * @return the project
     */
    public Project getProject() {
      return project;
    }
    
    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
      this.project = project;
    }
    
    /**
     * @return the timesheet
     */
    public Timesheet getTimesheet() {
      return timesheet;
    }
    
    /**
     * @param timesheet the timesheet to set
     */
    public void setTimesheet(Timesheet timesheet) {
      this.timesheet = timesheet;
    }
    
    /**
     * @return the workPackage
     */
    public WorkPackage getWorkPackage() {
      return workPackage;
    }
    
    /**
     * @param workPackage the workPackage to set
     */
    public void setWorkPackage(WorkPackage workPackage) {
      this.workPackage = workPackage;
    }
    
    /**
     * @return the hours
     */
    public List<Hours> getHours() {
      return hours;
    }
    
    /**
     * @param hours the hours to set
     */
    public void setHours(List<Hours> hours) {
      this.hours = hours;
    }
    
  }
}
