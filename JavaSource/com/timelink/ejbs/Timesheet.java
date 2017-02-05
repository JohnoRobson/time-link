package com.timelink.ejbs;

import com.timelink.TimesheetStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

//import javax.persistence.Entity;
//import javax.persistence.Table;

@SuppressWarnings("serial")
//@Entity
//@Table(name = "Timesheet")
public class Timesheet implements Serializable {
  private int timesheetId;
  private int employeeId;
  private Date date;
  private Employee timesheetApprover;
  private BigDecimal overtime;
  private BigDecimal flextime;
  private TimesheetStatus status;
  private List<TimesheetRow> rows;  
  
  /**
   * Returns timesheetId.
   * @return the timesheetId
   */
  public int getTimesheetId() {
    return timesheetId;
  }

  /**
   * Sets timesheetId to timesheetId.
   * @param timesheetId the timesheetId to set
   */
  public void setTimesheetId(int timesheetId) {
    this.timesheetId = timesheetId;
  }

  /**
   * Returns employeeId.
   * @return the employeeId
   */
  public int getEmployeeId() {
    return employeeId;
  }

  /**
   * Sets employeeId to employeeId.
   * @param employeeId the employeeId to set
   */
  public void setEmployeeId(int employeeId) {
    this.employeeId = employeeId;
  }

  /**
   * Returns date.
   * @return the date
   */
  public Date getDate() {
    return date;
  }

  /**
   * Sets date to date.
   * @param date the date to set
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * Returns timesheetApprover.
   * @return the timesheetApprover
   */
  public Employee getTimesheetApprover() {
    return timesheetApprover;
  }

  /**
   * Sets timesheetApprover to timesheetApprover.
   * @param timesheetApprover the timesheetApprover to set
   */
  public void setTimesheetApprover(Employee timesheetApprover) {
    this.timesheetApprover = timesheetApprover;
  }

  /**
   * Returns overtime.
   * @return the overtime
   */
  public BigDecimal getOvertime() {
    return overtime;
  }

  /**
   * Sets overtime to overtime.
   * @param overtime the overtime to set
   */
  public void setOvertime(BigDecimal overtime) {
    this.overtime = overtime;
  }

  /**
   * Returns flextime.
   * @return the flextime
   */
  public BigDecimal getFlextime() {
    return flextime;
  }

  /**
   * Sets flextime to flextime.
   * @param flextime the flextime to set
   */
  public void setFlextime(BigDecimal flextime) {
    this.flextime = flextime;
  }
  
  /**.
   * Returns status
   * @return the status
   */
  public TimesheetStatus getStatus() {
    return status;
  }
  
  /**
   * Sets status to status.
   * @param status the status to set
   */
  public void setStatus(TimesheetStatus status) {
    this.status = status;
  }

  /**
   * Returns rows.
   * @return the rows
   */
  public List<TimesheetRow> getRows() {
    return rows;
  }

  /**
   * Sets rows to rows.
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
     * Returns timesheetRowId.
     * @return the timesheetRowId
     */
    public int getTimesheetRowId() {
      return timesheetRowId;
    }
    
    /**
     * Set timesheetRowId to timesheetRowId.
     * @param timesheetRowId the timesheetRowId to set
     */
    public void setTimesheetRowId(int timesheetRowId) {
      this.timesheetRowId = timesheetRowId;
    }
    
    /**
     * Returns project.
     * @return the project
     */
    public Project getProject() {
      return project;
    }
    
    /**
     * Sets project to project.
     * @param project the project to set
     */
    public void setProject(Project project) {
      this.project = project;
    }
    
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
    
    /**
     * Returns workpackage.
     * @return the workPackage
     */
    public WorkPackage getWorkPackage() {
      return workPackage;
    }
    
    /**
     * Sets workpackage to workpackage.
     * @param workPackage the workPackage to set
     */
    public void setWorkPackage(WorkPackage workPackage) {
      this.workPackage = workPackage;
    }
    
    /**
     * Returns hours.
     * @return the hours
     */
    public List<Hours> getHours() {
      return hours;
    }
    
    /**
     * Sets hours to hours.
     * @param hours the hours to set
     */
    public void setHours(List<Hours> hours) {
      this.hours = hours;
    }
    
  }
}
