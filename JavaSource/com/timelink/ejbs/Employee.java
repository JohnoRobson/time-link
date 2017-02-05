package com.timelink.ejbs;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Employee implements Serializable {

  private int employeeId;
  private Employee timesheetApprover;
  private Employee supervisor;
  private String firstName;
  private String lastName;
  private String labourGrade;
  private String status;
  
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
  public String getLabourGrade() {
    return labourGrade;
  }
  
  /**
   * Sets labourGrade to labourGrade.
   * @param labourGrade the labourGrade to set
   */
  public void setLabourGrade(String labourGrade) {
    this.labourGrade = labourGrade;
  }
  
  /**
   * Returns status.
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
  
  
}
