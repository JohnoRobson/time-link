package com.timelink.managers;

import com.timelink.ejbs.Employee;
import com.timelink.roles.RoleEnum;

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
}
