package com.timelink;

import com.timelink.ejbs.Employee;
import com.timelink.enums.RoleEnum;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@SuppressWarnings("serial")
@SessionScoped
@Named("Session")
public class Session implements Serializable {
  /** Current Employee. */
  private Employee currentEmployee;
  
  /** Employee is a time sheet approver. */
  private boolean isApprover;
  
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  /**
   * Sets the isApprover variable to true or false, depending on if the currently
   * logged in employee is a timesheet approver or not.
   */
  public void isApproverCheck() {
    TypedQuery<Employee> queryOne = em.createQuery("SELECT e FROM Employee AS e "
        + "WHERE e.timesheetApprover = :emp", Employee.class)
        .setParameter("emp", currentEmployee);
    
    isApprover = queryOne.getResultList().size() != 0;
  }

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
    isApproverCheck();
  }
  
  public String endSession() {
    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    return "/index.xhtml?faces-redirect=true";
  }
  
  public boolean isApprover() {
    return isApprover;
  }
  
  public boolean isProjectManager() {
    return currentEmployee.hasRole(RoleEnum.PROJECT_MANAGER);
  }
  
  public boolean isProjectManagerAssistant() {
    return currentEmployee.hasRole(RoleEnum.PROJECT_MANAGER_ASSISTANT);
  }
  
  public boolean isSupervisor() {
    return currentEmployee.hasRole(RoleEnum.SUPERVISOR);
  }
  
  public boolean isHumanResources() {
    return currentEmployee.hasRole(RoleEnum.HUMAN_RESOURCES);
  }
  
  public boolean isResponsibleEngineer() {
    return currentEmployee.hasRole(RoleEnum.RESPONSIBLE_ENGINEER);
  }
}
