package com.timelink.ejbs;

import com.timelink.roles.RoleEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
  
  @OneToOne
  @JoinTable(name = "ts_approver",
      joinColumns = @JoinColumn(name = "tsa_appr_emp_id"),
      inverseJoinColumns = @JoinColumn(name = "tsa_emp_id"))
  private Employee timesheetApprover;
  
  @OneToMany(fetch = FetchType.EAGER,
      mappedBy = "employee")
  private List<Role> roles;
  
  //  @OneToOne
  //  @JoinColumn(name = "Supervisor",
  //      referencedColumnName = "EmployeeId")
  //  private Employee supervisor;
  
  @Column(name = "emp_fname")
  private String firstName;
  @Column(name = "emp_lname")
  private String lastName;
  
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "prj_line",
      joinColumns = @JoinColumn(name = "prjl_emp_id"),
      inverseJoinColumns = @JoinColumn(name = "prjl_prjh_id"))
  private Set<Project> projects;
  
  @Column(name = "emp_lg_id")
  private Integer labourGrade;
  
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

  //  /**
  //   * Returns the supervisor.
  //   * @return the supervisor
  //   */
  //  public Employee getSupervisor() {
  //    return supervisor;
  //  }
  //  
  //  /**
  //   * Sets the supervisor to supervisor.
  //   * @param supervisor the supervisor to set
  //   */
  //  public void setSupervisor(Employee supervisor) {
  //    this.supervisor = supervisor;
  //  }
  
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
  public Integer getLabourGrade() {
    return labourGrade;
  }
  
  /**
   * Sets labourGrade to labourGrade.
   * @param labourGrade the labourGrade to set
   */
  public void setLabourGrade(Integer labourGrade) {
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
   * Returns true if the employee has the specified role.
   * @param roleEnum the role to be checked.
   * @return True, if the employee has the specified role.
   */
  public boolean hasRole(RoleEnum roleEnum) {
    boolean bool = false;
    for (Role r : roles) {
      if (r.getRole() == roleEnum) {
        bool = true;
        break;
      }
    }
    return bool;
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
  
}
