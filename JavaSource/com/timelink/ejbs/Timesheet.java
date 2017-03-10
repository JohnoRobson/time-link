package com.timelink.ejbs;

import com.timelink.TimesheetStatus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;

@Entity
@Table(name = "ts_header")
public class Timesheet {
  
  @Transient
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tsh_id")
  private int timesheetId;
  
  @Column(name = "tsh_date_created")
  private Date date;
  
  @JoinColumn(name = "tsh_approver_id",
      referencedColumnName = "emp_id")
  @ManyToOne(cascade = CascadeType.MERGE)
  private Employee timesheetApprover;
  
  @JoinColumn(name = "tsh_emp_Id",
      referencedColumnName = "emp_id")
  @ManyToOne(cascade = CascadeType.MERGE)
  private Employee employee;
  
  @Column(name = "tsh_overtime")
  //private BigDecimal overtime;
  private float overtime;
  
  @Column(name = "tsh_flextime")
  //private BigDecimal flextime;
  private float flextime;
  
  @Column(name = "tsh_status")
  //private TimesheetStatus status;
  private String status;
  
  @OneToMany(fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  @JoinColumn(name = "tsl_tsh_id",
      referencedColumnName = "tsh_id")
  @OrderBy("timesheetRowId ASC")
  private Set<TimesheetRow> rows;  
  
  @Column(name = "tsh_rejectreason")
  private String rejectionReason;
  
  //TODO get dates working for time sheets, rows and hours.
  /**
   * The constructor for the timesheet.
   */
  public Timesheet() {
    
  }
  
  /**
   * Constructor used when calling new on this timesheet.
   * @param emp The employee that this timesheet belongs to.
   */
  public Timesheet(Employee emp) {
    setEmployee(emp);
    setTimesheetApprover(emp.getTimesheetApprover());
    setEmployee(emp);
    rows = new HashSet<TimesheetRow>();
    setStatus("0");
  }
  
  /**
   * Adds a timesheetRow to this timesheet.
   */
  public void addRow() {
    rows.add(new TimesheetRow(this));
  }
  
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
   * Returns employee.
   * @return the employee
   */
  public Employee getEmployee() {
    return employee;
  }

  /**
   * Sets employee to employee.
   * @param employee the employee to set
   */
  public void setEmployee(Employee employee) {
    this.employee = employee;
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
    timesheetApprover = getEmployee().getTimesheetApprover();
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
   * Returns the sum of the hours in the rows of this timesheet.
   * @return float Sum of hours.
   */
  public float getTotalHours() { 
    float sum = 0;

    for (TimesheetRow row : getRows()) {
      for (Hours hrs : row.getHours()) {
        sum += hrs.getHour();
      }
    }
    
    return sum;
  }

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
    return TimesheetStatus.values()[Integer.parseInt(status)].toString();
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
    if (rows != null) {
      return new ArrayList<TimesheetRow>(rows);
    }
    return new ArrayList<TimesheetRow>();
  }

  /**
   * Sets rows to rows.
   * @param rows the rows to set
   */
  public void setRows(List<TimesheetRow> rows) {
    this.rows = new HashSet<TimesheetRow>(rows);
  }
  
  /**
   * Returns the rejectionReason.
   * @return the rejectionReason
   */
  public String getRejectionReason() {
    if (rejectionReason != null) {
      return rejectionReason;
    }
    
    return "";
  }

  /**
   * Sets the rejectionReason to rejectionReason.
   * @param rejectionReason the rejectionReason to set
   */
  public void setRejectionReason(String rejectionReason) {
    this.rejectionReason = rejectionReason;
  }

  //TODO Should we put this in HoursManager or this? I feel like
  // if we inject something to use an entity manager when this already
  // has one seems unnecessary.
  /**
   * Searches the database for Hours that match a timesheetId.
   * 
   * @param timesheetId to search by
   * @return Hours[] matching the timesheetId
   */
  public List<Hours> findTotalHours(int timesheetId) {
    TypedQuery<Hours> query = em.createQuery("SELECT h FROM Hours h WHERE "
        + "h.timesheetId = :timesheetId", Hours.class)
        .setParameter("timesheetId", timesheetId);
    return query.getResultList();
  }
  
  /**
   * Calculates the flex and overtime for this timesheet.
   */
  public void calculateFlexAndOvertime() {
    float flex = 40 - getTotalHours();
    float over = getTotalHours() > 40 ? getTotalHours() - 40 : 0;
    
    setFlextime(flex);
    setOvertime(over);
  }
  
  /**
   * Deletes a row from this timesheet.
   * @param row The row to delete.
   */
  public void deleteRow(TimesheetRow row) {
    for (TimesheetRow r : rows) {
      if (r.getProjectId() == row.getProjectId()) {
        rows.remove(r);
        break;
      }
    }
  }
  
  /**
   * Returns the week number of the date of this timesheet.
   * @return A week number for this timesheet.
   */
  public int getWeekNumber() {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    return calendar.get(Calendar.WEEK_OF_YEAR);
  }
}
