package com.timelink.managers;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;
import com.timelink.enums.RoleEnum;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Dependent
@Stateless
public class EmployeeManager {
  
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  /**
   * Returns an Employee that matches the employeeId given.
   * 
   * @param empId The employeeId to be searched for.
   * @return An employee matching the employeeId searched for,
   *         or null if none is found.
   */
  public Employee find(int empId) {
    return em.find(Employee.class, empId);
  }
  
  /**
   * Adds an employee to the database. 
   * @param emp The employee to be added.
   */
  public void persist(Employee emp) {
    em.persist(emp);
  }
  
  /**
   * Updates an employee in the database with emp.
   * @param emp The employee to be updated.
   */
  public void merge(Employee emp) {
    em.merge(emp);
  }
  
  /**
   * Returns a List of all Employees in the database.
   * @return All employees in the database.
   */
  public List<Employee> getAll() {
    TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
    return query.getResultList();
  }
  
  /**
   * Returns a list of all Employees in the database that have
   * the specified role.
   * @param role The role to be searched for.
   * @return A list of all Employees in the database that have the role.
   */
  public List<Employee> getAllEmployeesWithRole(RoleEnum role) {
    TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e, Role AS r WHERE "
        + "r.role = :role AND e.employeeId = r.employee.employeeId", Employee.class)
        .setParameter("role", role);
    return query.getResultList();
  }
  
  /**
   * Returns a list of Employees that do not belong to a 
   * project.
   * @param project to find Employees that do not exist
   * @return A list of all employees that are not in the project
   */
  public List<Employee> findByNotInProject(Project project) {
    if (project.getEmployees().size() > 0) {
      TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e "
          + "WHERE e "
          + "NOT IN :projectEmployees", Employee.class)
          .setParameter("projectEmployees", project.getEmployees());
      return query.getResultList();
    } else {
      TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e", Employee.class);
      return query.getResultList();
    }
  }
  
  /**
   * Returns a list of Employees that belong to a project,
   * but do not belong to a work package.
   * @param workpackage to find Employees that do not exist
   * @param project to find Employees by
   * @return A list of all employees that are not in the workpackage
   */
  public List<Employee> findByNotInWorkPackage(Project project, WorkPackage workpackage) {
    if (workpackage.getAssignedEmployees().size() > 0 && project.getEmployees().size() > 0) {
      TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e "
          + "WHERE e IN :projectEmployees AND e "
          + "NOT IN :packageEmployees", Employee.class)
          .setParameter("projectEmployees", project.getEmployees())
          .setParameter("packageEmployees", workpackage.getAssignedEmployees());
      return query.getResultList();
    } else if (project.getEmployees().size() > 0) {
      TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e "
          + "WHERE e IN :projectEmployees", Employee.class)
          .setParameter("projectEmployees", project.getEmployees());
      return query.getResultList();
    } else {
      return null;
    }
  }
  
  /**
   * Returns a List of all Employees who have the given supervisor.
   * @param sup The supervisor.
   * @return A List of Employees.
   */
  public List<Employee> findBySupervisor(Employee sup) {
    TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e "
        + "WHERE e.supervisor = :sup", Employee.class)
        .setParameter("sup", sup);
    return query.getResultList();
  }
  
  /**
   * Returns a List of all Employees who do not have the given supervisor.
   * @param sup The supervisor.
   * @return A List of Employees.
   */
  public List<Employee> findByNotSupervisor(Employee sup) {
    TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e "
        + "WHERE e.supervisor != :sup OR "
        + "e.supervisor IS NULL", Employee.class)
        .setParameter("sup", sup);
    return query.getResultList();
  }
  
  /**
   * Returns a List of all Employees who have the given timesheet approver.
   * @param tsa The timesheet approver.
   * @return A List of Employees.
   */
  public List<Employee> findByTimesheetApprover(Employee tsa) {
    TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e "
        + "WHERE e.timesheetApprover = :tsa", Employee.class)
        .setParameter("tsa", tsa);
    return query.getResultList();
  }
  
  /**
   * Returns a List of all Employees who do not have the given timesheet approver.
   * @param tsa The timesheet approver.
   * @return A List of Employees.
   */
  public List<Employee> findByNotTimesheetApprover(Employee tsa) {
    TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e "
        + "WHERE e.timesheetApprover != :tsa OR "
        + "e.timesheetApprover IS NULL", Employee.class)
        .setParameter("tsa", tsa);
    return query.getResultList();
  }
}
