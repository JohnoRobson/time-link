package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.Credentials;
import com.timelink.managers.CredentialManager;
import com.timelink.managers.EmployeeManager;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("LoginController")
public class LoginController implements Serializable {
  
  @Inject CredentialManager cm;
  @Inject EmployeeManager em;
  @Inject Session ss;
  
  private String username;
  private String password;
  private int employeeId;
  Credentials cr;
  
  public LoginController() {}
  
  public LoginController(CredentialManager cm, EmployeeManager em, Session ses) {
	  this.cm = cm;
	  this.em = em;
	  this.ss = ses;
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
  
  /**
   * Validates username and password with Credentials
   * Manager.
   * @return bool if validated or not
   */
  public boolean validateUser() {
    cr = cm.find(username, password);
    if (cr != null) {
      employeeId = cr.getEmployeeId();
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Login to the system.
   * @return String for navigation
   */
  public String login() {
    if (validateUser()) {
      ss.setCurrentEmployee(em.find(employeeId));
      return "home";
    }
    return "null";
  }
  
}
