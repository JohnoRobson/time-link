package com.timelink.ejbs;

import java.sql.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "ts_header")
public class Timesheet {
  
  @Transient
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tsh_id")
  private int timesheetId;
  
  @Column(name = "tsh_emp_id")
  private int employeeId;
  
  @Column(name = "tsh_date_created")
  private Date date;
  
  @Transient
  private Employee timesheetApprover;
  
  @Transient
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
      cascade=CascadeType.ALL)
  @JoinColumn(name = "tsl_tsh_id",
      referencedColumnName = "tsh_id")
  private List<TimesheetRow> rows;  
  
  public Timesheet() {
  }
  
  @PostConstruct
  public void setUp() {
    setEmployee(
        em.find(
            Employee.class,
            getEmployeeId()));
    System.out.println("setup timesheet");
    setTimesheetApprover(
        getEmployee()
        .getTimesheetApprover());
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
   * Returns employee.
   * @return the employee.
   */
  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
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
}
