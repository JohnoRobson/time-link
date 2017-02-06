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
@Table(name = "wp_header")
public class WorkPackage implements Serializable {
  
  @Id
  @Column(name = "wph_id")
  private int workPackageId;
  
  @OneToOne
  @JoinColumn(name = "wph_prjh_id",
      referencedColumnName = "prjh_id")
  private Project project;
  
  //TODO Let Edward know that work packages need to reference their parents.
  //private WorkPackage parentPackage;
  
//  @OneToOne
//  @JoinColumn(name = "responsibleEngineer",
//      table = "employee", 
//      referencedColumnName = "emp_id")
//  private Employee responsibleEngineer;
  
  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "wp_line",
      joinColumns = @JoinColumn(name = "wpl_wph_id"),
      inverseJoinColumns = @JoinColumn(name = "wpl_emp_id"))
  private List<Employee> assignedEmployees;
  
  @Column(name = "wph_code")
  private String code;
  
  @Column(name = "wph_descr")
  private String description;
  
  /**
   * Returns the code.
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * Sets code to code.
   * @param code the code to set
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Returns the description.
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets description to desciption.
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

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
  
//  /**
//   * Returns the parentPackage.
//   * @return the parentPackage
//   */
//  public WorkPackage getParentPackage() {
//    return parentPackage;
//  }
//  
//  /**
//   * Sets the parentPackage to parentPackage.
//   * @param parentPackage the parentPackage to set
//   */
//  public void setParentPackage(WorkPackage parentPackage) {
//    this.parentPackage = parentPackage;
//  }
  
//  /**
//   * Returns the responsibleEngineer for this project.
//   * @return the responsibleEngineer
//   */
//  public Employee getResponsibleEngineer() {
//    return responsibleEngineer;
//  }
//  
//  /**
//   * Sets the responsibleEngineer to responsibleEngineer.
//   * @param responsibleEngineer the responsibleEngineer to set
//   */
//  public void setResponsibleEngineer(Employee responsibleEngineer) {
//    this.responsibleEngineer = responsibleEngineer;
//  }
  
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
