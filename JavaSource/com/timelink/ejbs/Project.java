package com.timelink.ejbs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "project")
public class Project implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "prj_id")
  private int projectNumber;
  
  @Column(name = "prj_name")
  private String projectName;
  
  @OneToOne
  @JoinColumn(name = "prj_manager_id",
      referencedColumnName = "emp_id")
  private Employee projectManager;
  
  @OneToOne
  @JoinColumn(name = "prj_manager_assist_id",
      referencedColumnName = "emp_id")
  private Employee projectManagerAssistant;
  
  @OneToMany(mappedBy = "project",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL)
  @OrderBy("code ASC")
  private Set<WorkPackage> workPackages;
  
  @Column(name = "prj_customer")
  private String customer;
  
  @Column(name = "prj_descr")
  private String description;
  
  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
  @JoinTable(name = "prj_emp",
      joinColumns = @JoinColumn(name = "pe_prj_id", referencedColumnName = "prj_id"),
      inverseJoinColumns = @JoinColumn(name = "pe_emp_id", referencedColumnName = "emp_id"))
  @OrderBy("lastName ASC")
  private Set<Employee> employees;
  
  @Column(name = "prj_closed")
  private boolean isClosed;
  
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
  
  /**
   * Returns the projectManagerAssistant.
   * @return the projectManagerAssistant
   */
  public Employee getProjectManagerAssistant() {
    return projectManagerAssistant;
  }
  
  /**
   * Sets the projectManagerAssistant to projectManagerAssistant.
   * @param projectManagerAssistant the projectManagerAssistant to set
   */
  public void setProjectManagerAssistant(Employee projectManagerAssistant) {
    this.projectManagerAssistant = projectManagerAssistant;
  }
  
  /**
   * Returns the workPackages.
   * @return the workPackages
   */
  public List<WorkPackage> getWorkPackages() {
    
    if (workPackages != null) {
      return new ArrayList<WorkPackage>(workPackages);
    }
    return new ArrayList<WorkPackage>();
  }
  
  /**
   * Sets the workPackages to workPackages.
   * @param workPackages the workPackages to set
   */
  public void setWorkPackages(List<WorkPackage> workPackages) {
    this.workPackages = new HashSet<WorkPackage>(workPackages);
  }
  
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
   * Return the description.
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description to description.
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }
  
  /**
   * Returns the employees.
   * @return the employees
   */
  public List<Employee> getEmployees() {
    return new ArrayList<Employee>(employees);
  }
  
  /**
   * Sets the employees to employees.
   * @param employees the employees to set
   */
  public void setEmployees(List<Employee> employees) {
    this.employees = new HashSet<Employee>(employees);
  }

  /**
   * Returns isClosed.
   * @return the isClosed
   */
  public boolean isClosed() {
    return isClosed;
  }

  /**
   * Sets isClosed.
   * @param isClosed the isClosed to set
   */
  public void setClosed(boolean isClosed) {
    this.isClosed = isClosed;
  }
  
  
}
