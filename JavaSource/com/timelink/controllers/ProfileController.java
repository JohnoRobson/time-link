package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.Employee;
import com.timelink.managers.EmployeeManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named("ProfileController")
public class ProfileController {
  @Inject Session session;
  @Inject EmployeeManager em;
  
  private Employee employee;
  
  @PostConstruct
  private void init() {
    employee = em.find(session.getCurrentEmployee().getEmployeeId());
  }
  
  public Employee getEmployee() {
    return employee;
  }
}
