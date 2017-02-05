package com.timelink.ejbs;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
//@Entity
//@Table(name = "WorkPackage")
public class WorkPackage implements Serializable {
  private int workPackageId;
  private Project project;
  private WorkPackage parentPackage;
  
 // @OneToOne
 // @JoinColumn(name = "responsibleEngineer",
 //     referencedColumnName = "EmployeeId")
  private Employee responsibleEngineer;
  private List<Employee> assignedEmployees;
  
  /**
   * Returns the workPackageId.
   * @return the workPackageId
   */
  public int getWorkPackageId() {
    return workPackageId;
  }
  
  /**
   * Sets the workPackageId to workPackageId.
   * @param workPackageId the workPackageId to set
   */
  public void setWorkPackageId(int workPackageId) {
    this.workPackageId = workPackageId;
  }
  
  /**
   * Returns the project.
   * @return the project
   */
  public Project getProject() {
    return project;
  }
  
  /**
   * Sets the project to project.
   * @param project the project to set
   */
  public void setProject(Project project) {
    this.project = project;
  }
  
  /**
   * Returns the parentPackage.
   * @return the parentPackage
   */
  public WorkPackage getParentPackage() {
    return parentPackage;
  }
  
  /**
   * Sets the parentPackage to parentPackage.
   * @param parentPackage the parentPackage to set
   */
  public void setParentPackage(WorkPackage parentPackage) {
    this.parentPackage = parentPackage;
  }
  
  /**
   * Returns the responsibleEngineer for this project.
   * @return the responsibleEngineer
   */
  public Employee getResponsibleEngineer() {
    return responsibleEngineer;
  }
  
  /**
   * Sets the responsibleEngineer to responsibleEngineer.
   * @param responsibleEngineer the responsibleEngineer to set
   */
  public void setResponsibleEngineer(Employee responsibleEngineer) {
    this.responsibleEngineer = responsibleEngineer;
  }
  
  /**
   * Returns the assignedEmployees.
   * @return the assignedEmployees
   */
  public List<Employee> getAssignedEmployees() {
    return assignedEmployees;
  }
  
  /**
   * Sets the assignedEmployees to assibleEmployees.
   * @param assignedEmployees the assignedEmployees to set
   */
  public void setAssignedEmployees(List<Employee> assignedEmployees) {
    this.assignedEmployees = assignedEmployees;
  }
}
