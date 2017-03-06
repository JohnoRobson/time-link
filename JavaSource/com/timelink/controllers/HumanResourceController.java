package com.timelink.controllers;

import com.timelink.Session;
import com.timelink.ejbs.Employee;
import com.timelink.managers.EmployeeManager;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("HRController")
public class HumanResourceController implements Serializable {
  @Inject EmployeeManager em;
  
  /** Current Employee being edited. */
  private Employee editingEmployee;
  
  private Integer employeeId;
  private String firstName;
  private String lastName;
  private String username;
  private String email;
  private String jobTitle;
  
  /**
   * Return the editingEmployee.
   * @return the editingEmployee
   */
  public Employee getEditingEmployee() {
    return editingEmployee;
  }

  /**
   * Set the editingEmployee to editingEmployee.
   * @param editingEmployee the editingEmployee to set
   */
  public void setEditingEmployee(Employee editingEmployee) {
    this.editingEmployee = editingEmployee;
  }
  
  public int getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(int employeeId) {
    this.employeeId = employeeId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }
  
  public List<Employee> getEmployees() {
    return em.getAll();
  }
  
  public String edit(Employee employee) {
    editingEmployee = employee;
    firstName = employee.getFirstName();
    lastName = employee.getLastName();
    email = employee.getEmail();
    return "editemployee";
  }
  
  /**
   * Clear input fields.
   */
  private void clear() {
    employeeId = null;
    firstName = null;
    lastName = null;
    username = null;
    email = null;
    jobTitle = null;
    editingEmployee = null;
  }
  
  //TODO Update JavaDoc comments
  /**
   * Create new Employee.
   * @return NAVIGATION STRING
   */
  public String createNewEmployee() {
    Employee emp = new Employee();
    emp.setFirstName(firstName);
    emp.setLastName(lastName);
    emp.setEmail(email);
    
    em.persist(emp);
    clear();
    return "humanresources";
  }
  
  //TODO Update JavaDoc comments
  /**
   * Save employee.
   * @return NAVIGATION STRING
   */
  public String save() {
    Employee emp = new Employee();
    emp.setEmployeeId(editingEmployee.getEmployeeId());
    emp.setFirstName(firstName);
    emp.setLastName(lastName);
    emp.setEmail(email);
    
    em.merge(emp);
    clear();
    return "humanresources";
  }
}
