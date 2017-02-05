package com.timelink.controllers;

import com.timelink.managers.CredentialManager;
import com.timelink.managers.EmployeeManager;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
@Named("LoginController")
public class LoginController implements Serializable {
  
  @Inject CredentialManager cm;
  @Inject EmployeeManager em;
  
  private String username;
  private String password;
  
  //TODO remove this
  public String getHello() {
    return cm.find("Admin", "Admin").getUsername();
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
    return cm.find(username, password) != null;
  }
  
  /**
   * Login to the system.
   * @return String for navigation
   */
  public String login() {
    if (validateUser()) {
      return "home";
    }
    return "null";
  }
  
}
