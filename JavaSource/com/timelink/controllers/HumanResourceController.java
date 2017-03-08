package com.timelink.controllers;

import com.timelink.ejbs.Credentials;
import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Role;
import com.timelink.managers.EmployeeManager;
import com.timelink.managers.RoleManager;
import com.timelink.roles.RoleEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("serial")
@SessionScoped
@Named("HRController")
public class HumanResourceController implements Serializable {
  @Inject EmployeeManager em;
  @Inject RoleManager rm;
  
  /** Current Employee being edited. */
  private Employee editingEmployee;
  
  private String userId;
  private String firstName;
  private String lastName;
  private String email;
  private RoleEnum jobTitle;
  private String password;
  private String confirmPassword;
  private Date effectiveFrom;
  
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
  
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public RoleEnum getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(RoleEnum jobTitle) {
    this.jobTitle = jobTitle;
  }
  
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public Date getEffectiveFrom() {
    return effectiveFrom;
  }

  public void setEffectiveFrom(Date effectiveFrom) {
    this.effectiveFrom = effectiveFrom;
  }

  public List<Employee> getEmployees() {
    return em.getAll();
  }
  
  /**
   * Return all available roles for a new Employee.
   * @return RoleEnum list of potential roles
   */
  public List<RoleEnum> getRoles() {
    ArrayList<RoleEnum> ptRoles = new ArrayList<RoleEnum>();
    ptRoles.add(RoleEnum.EMPLOYEE);
    ptRoles.add(RoleEnum.HUMAN_RESOURCES);
    return ptRoles;
  }
  
  /**
   * Return roles to change an Employee to, default is current role.
   * @return RoleEnum list of roles
   */
  public List<RoleEnum> getChangeRoles() {
    ArrayList<RoleEnum> chRoles = new ArrayList<RoleEnum>();
    if (editingEmployee.hasRole(RoleEnum.HUMAN_RESOURCES)) {
      chRoles.add(RoleEnum.HUMAN_RESOURCES);
      chRoles.add(RoleEnum.EMPLOYEE);
    } else {
      chRoles.add(RoleEnum.EMPLOYEE);
      chRoles.add(RoleEnum.HUMAN_RESOURCES);
    }
    return chRoles;
  }
  
  /**
   * Edit an Employee.
   * @param employee to edit
   * @return navigation string to edit employee page
   */
  public String edit(Employee employee) {
    editingEmployee = employee;
    userId = employee.getUserId();
    firstName = employee.getFirstName();
    lastName = employee.getLastName();
    email = employee.getEmail();
    return "editemployee";
  }
  
  public String cancel() {
    clear();
    return "humanresources";
  }
  
  /**
   * Clear input fields.
   */
  private void clear() {
    userId = null;
    firstName = null;
    lastName = null;
    email = null;
    jobTitle = null;
    editingEmployee = null;
    effectiveFrom = null;
    password = null;
    confirmPassword = null;
  }
  
  //TODO Update JavaDoc comments
  /**
   * Create new Employee.
   * @return NAVIGATION STRING
   */
  public String createNewEmployee() {
    if (password.equals(confirmPassword)) {
      Employee emp = new Employee();
      emp.setUserId(userId);
      emp.setFirstName(firstName);
      emp.setLastName(lastName);
      emp.setEmail(email);
      emp.setEffectFrom(new Date(effectiveFrom.getTime()));
      
      Role role = new Role(jobTitle);
      role.setEmployee(emp);
      
      Credentials cr = new Credentials();
      cr.setPassword(password);
      cr.setUsername(userId);
      cr.setEmployee(emp);
      emp.setCredentials(cr);
      
      em.persist(emp);
      rm.persist(role);
      clear();
      return "humanresources";
    } else {
      return null;
    }
  }
  
  //TODO Update JavaDoc comments
  /**
   * Save employee.
   * @return NAVIGATION STRING
   */
  public String save() {
    Employee emp = editingEmployee;
    emp.setUserId(userId);
    emp.setFirstName(firstName);
    emp.setLastName(lastName);
    emp.setEmail(email);
    
    //Role role = new Role(jobTitle);
    //role.setEmployee(emp);
    
    em.merge(emp);
    //rm.merge(role);
    clear();
    return "humanresources";
  }
  
  /**
   * Save the new password for the editingEmployee.
   * @return navigation String to reload page.
   */
  public String newPassword() {
    if (password.equals(confirmPassword)) {
      Employee emp = editingEmployee;
      
      Credentials cr = new Credentials();
      cr.setCredentialsId(emp.getCredentials().getCredentialsId());
      cr.setPassword(password);
      cr.setUsername(userId);
      cr.setEmployee(emp);
      emp.setCredentials(cr);
      
      em.merge(emp);
    } 
    return null;
  }
}