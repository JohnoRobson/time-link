package com.timelink;

import com.timelink.ejbs.Employee;

import java.io.Serializable;

import javax.annotation.PostConstruct;
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
  
  /** Employee is a timesheet approver. */
  private boolean isApprover;
  
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  public Session() { }
  
  public void isApproverCheck() {
    TypedQuery<Employee> queryOne = em.createQuery("SELECT e FROM Employee AS e, "
        + "TimesheetApprover AS tsa "
        + "WHERE e.employeeId = tsa.approveeEmployeeId "
        + "AND :empid = tsa.approverEmployeeId", Employee.class)
        .setParameter("empid", currentEmployee.getEmployeeId());
    
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
  
}
