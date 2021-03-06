package com.timelink.ejbs;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.timelink.enums.RoleEnum;

@SuppressWarnings("serial")
@Entity
@Table(name = "job_title")
public class Role implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "jt_id")
  private int jobTitleId;
  
  @Column(name = "jt_type")
  private RoleEnum role;
  
  @ManyToOne
  @JoinColumn(name = "jt_emp_id")
  private Employee employee;
  
  public Role() { }
  
  public Role(RoleEnum role) {
    this.role = role;
  }

  /**
   * Returns the jobTitleId.
   * @return the jobTitleId
   */
  public final int getJobTitleId() {
    return jobTitleId;
  }

  /**
   * Sets the jobTitleIt to jobTitleId.
   * @param jobTitleId the jobTitleId to set
   */
  public final void setJobTitleId(int jobTitleId) {
    this.jobTitleId = jobTitleId;
  }

  /**
   * Returns the role.
   * @return the role
   */
  public final RoleEnum getRole() {
    return role;
  }

  /**
   * Sets the role to role.
   * @param roleEnum the role to set
   */
  public final void setRole(RoleEnum roleEnum) {
    this.role = roleEnum;
  }

  /**
   * Returns the employee.
   * @return the employee
   */
  public final Employee getEmployee() {
    return employee;
  }

  /**
   * Sets the employee to employee.
   * @param employeeId the employee to set
   */
  public final void setEmployee(Employee employeeId) {
    this.employee = employeeId;
  }
  
  
}
