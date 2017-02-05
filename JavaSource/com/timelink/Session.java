package com.timelink;

import com.timelink.ejbs.Employee;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named("Session")
public class Session implements Serializable {
  /** Current Employee. */
  private Employee currentEmployee;
  
  public Session() { }

  /**
   * Return the currentEmployee.
   * @return the currentEmployee
   */
  public Employee getCurrentEmployee() {
    return currentEmployee;
  }

  /**
   * Set the currentEmployee to currentEmployee.
   * @param currentEmployee the currentEmployee to set
   */
  public void setCurrentEmployee(Employee currentEmployee) {
    this.currentEmployee = currentEmployee;
  }
  
}
