package com.timelink.ejbs;

import com.timelink.roles.RoleEnum;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "job_title")
public class Role implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "jt_id")
  private int jobTitleId;
  
  @Column(name = "jt_code")
  private RoleEnum role;
  
  /*
   * TODO: this is gross and needs to be fixed in the database.
   */
  @ManyToOne
  @JoinColumn(name = "jt_descr")
  private Employee employee;

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
   * @param role the role to set
   */
  public final void setRole(int role) {
    this.role = RoleEnum.values()[role];
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
