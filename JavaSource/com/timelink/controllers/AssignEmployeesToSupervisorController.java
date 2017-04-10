package com.timelink.controllers;

import com.timelink.ejbs.Employee;
import com.timelink.enums.RoleEnum;
import com.timelink.managers.EmployeeManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

@SuppressWarnings("serial")
@SessionScoped
@Named("EmployeeToSuperController")
public class AssignEmployeesToSupervisorController implements Serializable {
  @Inject EmployeeManager em;
  
  private Employee selectedSupervisor;

  public AssignEmployeesToSupervisorController() {}
  public AssignEmployeesToSupervisorController(EmployeeManager em){
	  this.em = em;
  }
  /**
   * Returns the selectedSupervisor.
   * @return the selectedSupervisor
   */
  public Employee getSelectedSupervisor() {
    return selectedSupervisor;
  }

  /**
   * Sets the selectedSupervisor.
   * @param selectedSupervisor the selectedSupervisor to set
   */
  public void setSelectedSupervisor(Employee selectedSupervisor) {
    this.selectedSupervisor = selectedSupervisor;
  }
  
  public List<Employee> getSupervisors() {
    return em.getAllEmployeesWithRole(RoleEnum.SUPERVISOR);
  }
  
  /**
   * Return Employees if selectedProject does not equal
   * null.
   * @return DualIstModel of Employees for that selectedProject
   */
  public DualListModel<Employee> getEmployees() {
    List<Employee> employeeSource = new ArrayList<Employee>();
    List<Employee> employeeTarget = new ArrayList<Employee>();
    
    if (selectedSupervisor != null) {
      employeeSource = em.findByNotSupervisor(selectedSupervisor);
      employeeTarget = em.findBySupervisor(selectedSupervisor);
    }
    
    return new DualListModel<Employee>(employeeSource, employeeTarget);
  }
  
  /**
   * Merges all Employees in the target array with
   * the selected project.
   * @param employees that are merged with selectedProject
   */
  public void setEmployees(DualListModel<Employee> employees) {
    if (selectedSupervisor != null) {
      for (Employee e : employees.getTarget()) {
        e.setSupervisor(selectedSupervisor);
        em.merge(e);
      }
      
      for (Employee e : employees.getSource()) {
        if (!employees.getTarget().contains(e)
            && e.getSupervisor() != null
            && e.getSupervisor().getEmployeeId() == selectedSupervisor.getEmployeeId()) {
          e.setSupervisor(null);
          em.merge(e);
        }
      }
    }
  }
}
