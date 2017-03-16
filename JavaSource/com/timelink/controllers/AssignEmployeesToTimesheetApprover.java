package com.timelink.controllers;

import com.timelink.ejbs.Employee;
import com.timelink.managers.EmployeeManager;
import com.timelink.roles.RoleEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

@SuppressWarnings("serial")
@SessionScoped
@Named("EmployeeToTsaController")
public class AssignEmployeesToTimesheetApprover implements Serializable {
  @Inject EmployeeManager em;
  
  private Employee selectedTimesheetApprover;

  /**
   * Returns the selectedTimesheetApprover.
   * @return the selectedTimesheetApprover
   */
  public Employee getSelectedTimesheetApprover() {
    return selectedTimesheetApprover;
  }

  /**
   * Sets the selectedTimesheetApprover.
   * @param selectedTimesheetApprover the selectedTimesheetApprover to set
   */
  public void setSelectedTimesheetApprover(Employee selectedTimesheetApprover) {
    this.selectedTimesheetApprover = selectedTimesheetApprover;
  }
  
  public List<Employee> getTimesheetApprovers() {
    return em.getAll();
  }
  
  /**
   * Return Employees if selectedProject does not equal
   * null.
   * @return DualIstModel of Employees for that selectedProject
   */
  public DualListModel<Employee> getEmployees() {
    List<Employee> employeeSource = new ArrayList<Employee>();
    List<Employee> employeeTarget = new ArrayList<Employee>();
    
    if (selectedTimesheetApprover != null) {
      employeeSource = em.findByNotTimesheetApprover(selectedTimesheetApprover);
      employeeTarget = em.findByTimesheetApprover(selectedTimesheetApprover);
    }
    
    return new DualListModel<Employee>(employeeSource, employeeTarget);
  }
  
  /**
   * Merges all Employees in the target array with
   * the selected project.
   * @param employees that are merged with selectedProject
   */
  public void setEmployees(DualListModel<Employee> employees) {
    if (selectedTimesheetApprover != null) {
      for (Employee e : employees.getTarget()) {
        e.setTimesheetApprover(selectedTimesheetApprover);
        em.merge(e);
      }
      
      for (Employee e : employees.getSource()) {
        if (!employees.getTarget().contains(e)
            && e.getTimesheetApprover() != null
              && e.getTimesheetApprover().getEmployeeId()
                == selectedTimesheetApprover.getEmployeeId()) {
          e.setSupervisor(null);
          em.merge(e);
        }
      }
    }
  }
}
