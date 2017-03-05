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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "wp_header")
public class WorkPackage implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "wph_id")
  private int workPackageId;
  
  @OneToOne
  @JoinColumn(name = "wph_prjh_id",
      referencedColumnName = "prjh_id")
  private Project project;
  
  //private WorkPackage parentPackage;
  
  //  @OneToOne
  //  @JoinColumn(name = "responsibleEngineer",
  //      referencedColumnName = "emp_id")
  //  private Employee responsibleEngineer;
  
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable(name = "wp_line",
      joinColumns = @JoinColumn(name = "wpl_wph_id"),
      inverseJoinColumns = @JoinColumn(name = "wpl_emp_id"))
  private List<Employee> assignedEmployees;
  
  @Column(name = "wph_code")
  private String code;
  
  @Column(name = "wph_descr")
  private String description;
  
  //TODO find out if the was the best way to do it.
  @OneToMany(fetch = FetchType.EAGER,
              mappedBy = "workPackage",
              cascade = CascadeType.ALL)
  private Set<PlannedHours> plannedHours;
  
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
  
  /**
   * Returns a List of PlannedHours associated with
   * this WorkPackage.
   * @return A List of PlannedHours.
   */
  public List<PlannedHours> getPlannedHours() {
    if (plannedHours != null) {
      return new ArrayList<PlannedHours>(plannedHours);
    }
    return new ArrayList<PlannedHours>();
  }
  
  public void setPlannedHours(List<PlannedHours> plannedHours) {
    this.plannedHours = new HashSet<PlannedHours>(plannedHours);
  }
  
  /**
   * Returns the total man-hours with the specified labourGradeId
   * that are in this WorkPackage.
   * @param labourGradeId The labourGrade to be searched.
   * @return The total man-hours planned.
   */
  public int getTotalFromGrade(Integer labourGradeId) {
    int total = 0;
    
    for (PlannedHours hour: plannedHours) {
      if (hour.getLabourGrade().getLabourGradeId() == labourGradeId) {
        total = hour.getManDay();
        break;
      }
    }
    return total;
  }
  
  /**
   * Returns the PlannedHour in this WorkPackage that have
   * the specified labourGradeId.
   * @param labourGradeId The labourGrade to be searched.
   * @return The PlannedHour in this WorkPackage with the 
   *     specified Id.
   */
  public PlannedHours getPlannedHourFromLabourGrade(Integer labourGradeId) {
    PlannedHours hour = new PlannedHours();
    
    for (PlannedHours h : plannedHours) {
      if (h.getLabourGrade().getLabourGradeId() == labourGradeId) {
        hour = h;
        break;
      }
    }
    
    if (hour.getLabourGrade() == null) {
      LabourGrade lg = new LabourGrade();
      lg.setLabourGradeId(labourGradeId);
      hour.setLabourGrade(lg);
      plannedHours.add(hour);
      
      for (PlannedHours h : plannedHours) {
        if (h.getLabourGrade().getLabourGradeId() == labourGradeId) {
          return h;
        }
      }
    }
    
    return hour;
  }
  
  /**
   * Removes the plannedHour with the specified labourGradeId.
   * @param labourGradeId The labourGradeId to be searched.
   */
  public void removePlannedHourByLabourGrade(Integer labourGradeId) {
    for (PlannedHours h : plannedHours) {
      if (h.getLabourGrade().getLabourGradeId() == labourGradeId) {
        plannedHours.remove(h);
        break;
      }
    }
  }
  
  /**
   * Returns the total planned hours in this work package.
   * @return A total of planned hours.
   */
  public int getTotalPlannedHours() {
    int total = 0;
    if (plannedHours != null && plannedHours.size() > 0) {
      for (PlannedHours hour : plannedHours) {
        total += hour.getManDay();
      }
    }
    return total;
  }
  
  /**
   * Returns the total costs of the planned hours in
   * this work package.
   * @return A total of planned hour costs.
   */
  public int getTotalPlannedCosts() {
    int totalCost = 0;
    
    if (plannedHours != null  && plannedHours.size() > 0) {
      for (PlannedHours hour : plannedHours) {
        totalCost += (hour.getManDay() * hour.getLabourGrade().getCostRate());
      }
    }
    
    return totalCost;
  }
}
