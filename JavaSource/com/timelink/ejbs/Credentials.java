package com.timelink.ejbs;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Credentials implements Serializable {
  
  private int employeeId;
  private String username;
  private String password;
  
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
}
