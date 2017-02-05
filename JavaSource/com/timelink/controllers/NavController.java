package com.timelink.controllers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("NavController")
public class NavController implements Serializable {
  
  /**
   * Sends the user to the home view.
   * @return String for navigation
   */
  public String home() {
    return "home";
  }
  
  /**
   * Sends the user to the timesheet view.
   * @return String for navigation
   */
  public String timesheet() {
    return "timesheet";
  }
  
  /**
   * Sends the user to the report view.
   * @return String for navigation
   */
  public String report() {
    return "report";
  }
  
}
