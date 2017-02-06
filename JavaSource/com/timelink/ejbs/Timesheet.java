package com.timelink.ejbs;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "ts_header")
public class Timesheet {
  
  @Id
  @Column(name = "tsh_id")
  private int timesheetId;
  
  @Column(name = "tsh_emp_id")
  private int employeeId;
  
  @Column(name = "tsh_date_created")
  private Date date;
  
  //private Employee timesheetApprover;
  
  @Column(name = "tsh_overtime")
  //private BigDecimal overtime;
  private float overtime;
  
  @Column(name = "tsh_flextime")
  //private BigDecimal flextime;
  private float flextime;
  
  @Column(name = "tsh_status")
  //private TimesheetStatus status;
  private String status;
  
  @Transient
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
  /*public Employee getTimesheetApprover() {
    return timesheetApprover;
  }*/

  /**
   * Sets timesheetApprover to timesheetApprover.
   * @param timesheetApprover the timesheetApprover to set
   */
  /*public void setTimesheetApprover(Employee timesheetApprover) {
    this.timesheetApprover = timesheetApprover;
  }*/

  /**
   * Returns overtime.
   * @return the overtime
   */
  public float getOvertime() {
    return overtime;
  }

  /**
   * Sets overtime to overtime.
   * @param overtime the overtime to set
   */
  public void setOvertime(float overtime) {
    this.overtime = overtime;
  }

  /**
   * Returns flextime.
   * @return the flextime
   */
  public float getFlextime() {
    return flextime;
  }

  /**
   * Sets flextime to flextime.
   * @param flextime the flextime to set
   */
  public void setFlextime(float flextime) {
    this.flextime = flextime;
  }
  
  /**.
   * Returns status
   * @return the status
   */
  public String getStatus() {
    return status;
  }
  
  /**
   * Sets status to status.
   * @param status the status to set
   */
  public void setStatus(String status) {
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
