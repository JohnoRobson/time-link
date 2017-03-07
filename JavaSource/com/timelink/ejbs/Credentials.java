package com.timelink.ejbs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "credential")
public class Credentials implements Serializable {
  
  @Id
  @Column(name = "cre_id")
  private int credentialsId;
  
  @Column(name = "cre_emp_user_id")
  private String username;
  
  @OneToOne
  @JoinColumn(name = "cre_emp_id")
  private Employee employee;
  
  @Column(name = "cre_pw")
  private String password;
  
  /**
   * Returns the credentialsId.
   * @return the credentialsId
   */
  public int getCredentialsId() {
    return credentialsId;
  }
  
  /**
   * Sets the credentialsId to credentialsId.
   * @param credentialsId the credentialsId to set
   */
  public void setCredentialsId(int credentialsId) {
    this.credentialsId = credentialsId;
  }
  
  /**
   * Returns the username.
   * @return the username
   */
  public String getUsername() {
    return username;
  }
  
  /**
   * Sets the username to username.
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }
  
  /**
   * Returns the password.
   * @return the password
   */
  public String getPassword() {
    return password;
  }
  
  /**
   * Sets the password to password.
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  public int getEmployeeId() {
    return employee.getEmployeeId();
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }
}
