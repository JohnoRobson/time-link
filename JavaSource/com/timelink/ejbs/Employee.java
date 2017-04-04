package com.timelink.ejbs;

import com.timelink.enums.RoleEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "employee")
public class Employee implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "emp_id")
  private int employeeId;
  
  @OneToOne//(cascade = CascadeType.MERGE)
  @JoinTable(name = "ts_approver",
      joinColumns = @JoinColumn(name = "tsa_emp_id"),
      inverseJoinColumns = @JoinColumn(name = "tsa_appr_id"))
  private Employee timesheetApprover;
  
  @OneToOne(mappedBy = "employee", cascade = {CascadeType.ALL})
  private Credentials credentials;
  
  @OneToMany(fetch = FetchType.EAGER,
      mappedBy = "employee")
  private List<Role> roles;
  
  @OneToOne//(cascade = CascadeType.ALL)
  @JoinTable(name = "supvemp",
      joinColumns = @JoinColumn(name = "se_emp_id"),
      inverseJoinColumns = @JoinColumn(name = "se_supv_id"))
  private Employee supervisor;
  
  @Column(name = "emp_fname")
  private String firstName;
  @Column(name = "emp_lname")
  private String lastName;
  
  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "employees")
  private Set<Project> projects; 
  
  @JoinColumn(name = "emp_lg_id",
      referencedColumnName = "lg_id")
  @ManyToOne
  private LabourGrade labourGrade;
  
  @Column(name = "emp_user_id")
  private String userId;
  
  @Column(name = "emp_email")
  private String email;
  
  @Column(name = "emp_effect_from")
  private Date effectFrom;
  
  @Column(name = "emp_effect_to")
  private Date effectTo;
  
  @Column(name = "emp_flex_time")
  private Integer flexTime;
  
  @Column(name = "emp_vacation_time")
  private Integer vacationTime;
  
  @Column(name = "emp_vacation_rate")
  private Integer vacationRate;
  
  @JoinColumn(name = "emp_default_tsh_id",
      referencedColumnName = "tsh_id")
  @ManyToOne
  private Timesheet defaultTimesheet;
  
  //private String status;
  
  /**
   * Returns the employeeId.
   * @return the employeeId
   */
  public int getEmployeeId() {
    return employeeId;
  }
  
  /**
   * Sets the employeeId to employeeId.
   * @param employeeId the employeeId to set
   */
  public void setEmployeeId(int employeeId) {
    this.employeeId = employeeId;
  }
  
  /**
   * Returns the timesheetApprover.
   * @return the timesheetApprover
   */
  public Employee getTimesheetApprover() {
    return timesheetApprover;
  }
  
  /**
   * Sets the timesheetApprover to timesheetApprover.
   * @param timesheetApprover the timesheetApprover to set
   */
  public void setTimesheetApprover(Employee timesheetApprover) {
    this.timesheetApprover = timesheetApprover;
  }

  /**
   * Returns the supervisor.
   * @return the supervisor
   */
  public Employee getSupervisor() {
    return supervisor;
  }
  
  /**
   * Sets the supervisor to supervisor.
   * @param supervisor the supervisor to set
   */
  public void setSupervisor(Employee supervisor) {
    this.supervisor = supervisor;
  }
  
  public Credentials getCredentials() {
    return credentials;
  }

  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }

  /**
   * Returns the firstName.
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }
  
  /**
   * Sets the firstName to firstName.
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  /**
   * Returns the lastName.
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }
  
  /**
   * Sets lastName to lastName.
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  /**
   * Returns the labourGrade.
   * @return the labourGrade
   */
  public LabourGrade getLabourGrade() {
    return labourGrade;
  }
  
  /**
   * Sets labourGrade to labourGrade.
   * @param labourGrade the labourGrade to set
   */
  public void setLabourGrade(LabourGrade labourGrade) {
    this.labourGrade = labourGrade;
  }
  
  //  /**
  //   * Returns status.
  //   * @return the status
  //   */
  //  public String getStatus() {
  //    return status;
  //  }
  //  
  //  /**
  //   * Sets status to status.
  //   * @param status the status to set
  //   */
  //  public void setStatus(String status) {
  //    this.status = status;
  //  }
  
  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }
  
  public List<Role> getRoles() {
    return roles;
  }
  
  /**
   * Returns the defaultTimesheet.
   * @return the defaultTimesheet
   */
  public Timesheet getDefaultTimesheet() {
    return defaultTimesheet;
  }

  /**
   * Sets the defaultTimesheet.
   * @param defaultTimesheet the defaultTimesheet to set
   */
  public void setDefaultTimesheet(Timesheet defaultTimesheet) {
    this.defaultTimesheet = defaultTimesheet;
  }

  /**
   * Returns true if the employee has the specified role.
   * @param roleEnum the role to be checked.
   * @return True, if the employee has the specified role.
   */
  public boolean hasRole(RoleEnum roleEnum) {
    if (roleEnum == null) {
      return false;
    }
    
    for (Role r : roles) {
      if (r.getRole() == roleEnum) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Returns a List of Projects that this Employee
   * is assigned to.
   * @return A List of Projects.
   */
  public List<Project> getProjects() {
    if (projects != null) {
      return new ArrayList<Project>(projects);
    }
    return new ArrayList<Project>();
  }
  
  /**
   * Sets the projects of this Employee to projects.
   * @param projects The projects to set.
   */
  public void setProjects(List<Project> projects) {
    this.projects = new HashSet<Project>(projects);
  }

  /**
   * Returns the userId.
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

  /**
   * Sets the userId to userId.
   * @param userId the userId to set
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * Returns the email.
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email to email.
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns the effectFrom.
   * @return the effectFrom
   */
  public Date getEffectFrom() {
    return effectFrom;
  }

  /**
   * Sets the effectFrom to effectFrom.
   * @param effectFrom the effectFrom to set
   */
  public void setEffectFrom(Date effectFrom) {
    this.effectFrom = effectFrom;
  }

  /**
   * Returns the effectTo.
   * @return the effectTo
   */
  public Date getEffectTo() {
    return effectTo;
  }

  /**
   * Sets the effectTo to EffectTo.
   * @param effectTo the effectTo to set
   */
  public void setEffectTo(Date effectTo) {
    this.effectTo = effectTo;
  }

  /**
   * Returns the flexTime.
   * @return the flexTime
   */
  public int getFlexTime() {
    return flexTime;
  }

  /**
   * Sets the flexTime to flexTime.
   * @param flexTime the flexTime to set
   */
  public void setFlexTime(int flexTime) {
    this.flexTime = flexTime;
  }

  /**
   * Returns the vacationTime.
   * @return the vacationTime
   */
  public int getVacationTime() {
    return vacationTime;
  }

  /**
   * Sets the vacationTime to vacationTime.
   * @param vacationTime the vacationTime to set
   */
  public void setVacationTime(int vacationTime) {
    this.vacationTime = vacationTime;
  }

  /**
   * Returns the vacationRate.
   * @return the vacationRate
   */
  public int getVacationRate() {
    return vacationRate;
  }

  /**
   * Sets the vacationRate to vacationRate.
   * @param vacationRate the vacationRate to set
   */
  public void setVacationRate(int vacationRate) {
    this.vacationRate = vacationRate;
  }
  
  /**
   * Returns true if the entered object is the same as this object.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    
    if (!(obj instanceof Employee)) {
      return false;
    }
    
    Employee  emp = (Employee) obj;
    
    if (emp.getUserId() != getUserId()) {
      return false;
    }
    
    return true;
  }
}
