package com.timelink.ejbs;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "prj_header")
public class Project implements Serializable {
  
  @Id
  @Column(name = "prjh_id")
  private int projectNumber;
  
  @Column(name = "prjh_name")
  private String projectName;
  
  @OneToOne
  @JoinColumn(name = "prjh_manager_id",
      referencedColumnName = "emp_id")
  private Employee projectManager;
  
  //TODO Get this figured out
  /*
  @OneToOne
  @JoinColumn(name = "projectManagerAssistant",
      referencedColumnName = "EmployeeId")
  private Employee projectManagerAssistant;
  */
  
//  @JoinColumn(name = "prjh_wp_id",
//      table = "wp_header",
//      referencedColumnName = "wph_id")
//  @OneToMany(cascade = {CascadeType.ALL},
//            mappedBy = "prjh_header")
//  private List<WorkPackage> workPackages;
  
  @Column(name = "prjh_customer")
  private String customer;
  
  
  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "prj_line",
      joinColumns = @JoinColumn(name = "prjl_prjh_id"),
      inverseJoinColumns = @JoinColumn(name = "prjl_emp_id"))
  private List<Employee> employees;
  
  /**
   * Returns the projectNumber.
   * @return the projectNumber
   */
  public int getProjectNumber() {
    return projectNumber;
  }
  
  /**
   * Sets the projectNumber to projectNumber.
   * @param projectNumber the projectNumber to set
   */
  public void setProjectNumber(int projectNumber) {
    this.projectNumber = projectNumber;
  }
  
  /**
   * Returns the projectName.
   * @return the projectName
   */
  public String getProjectName() {
    return projectName;
  }
  
  /**
   * Sets the projectName to projectName.
   * @param projectName the projectName to set
   */
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
  
  /**
   * Returns the projectManager.
   * @return the projectManager
   */
  public Employee getProjectManager() {
    return projectManager;
  }
  
  /**
   * Sets the projectManager to projectManager.
   * @param projectManager the projectManager to set
   */
  public void setProjectManager(Employee projectManager) {
    this.projectManager = projectManager;
  }
  
//  /**
//   * Returns the projectManagerAssistant.
//   * @return the projectManagerAssistant
//   */
//  public Employee getProjectManagerAssistant() {
//    return projectManagerAssistant;
//  }
//  
//  /**
//   * Sets the projectManagerAssistant to projectManagerAssistant.
//   * @param projectManagerAssistant the projectManagerAssistant to set
//   */
//  public void setProjectManagerAssistant(Employee projectManagerAssistant) {
//    this.projectManagerAssistant = projectManagerAssistant;
//  }
  
//  /**
//   * Returns the workPackages.
//   * @return the workPackages
//   */
//  public List<WorkPackage> getWorkPackages() {
//    return workPackages;
//  }
//  
//  /**
//   * Sets the workPackages to workPackages.
//   * @param workPackages the workPackages to set
//   */
//  public void setWorkPackages(List<WorkPackage> workPackages) {
//    this.workPackages = workPackages;
//  }
  
  /**
   * Returns the customer.
   * @return the customer
   */
  public String getCustomer() {
    return customer;
  }
  
  /**
   * Sets the customer to customer.
   * @param customer the customer to set
   */
  public void setCustomer(String customer) {
    this.customer = customer;
  }
  
  /**
   * Returns the employees.
   * @return the employees
   */
  public List<Employee> getEmployees() {
    return employees;
  }
  
  /**
   * Sets the employees to employees.
   * @param employees the employees to set
   */
  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }
  
  
}
