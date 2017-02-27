package com.timelink.managers;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Role;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Dependent
@Stateless
public class RoleManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  
  /**
   * Returns a list of all roles in the database that the given employee
   * has.
   * @param emp The employee whose roles will be returned.
   * @return A List of Roles.
   */
  public List<Role> findRoles(Employee emp) {
    TypedQuery<Role> query = em.createQuery("SELECT r FROM Roles AS r WHERE"
        + " r.employeeId = :employeeId", Role.class)
        .setParameter("employeeId", emp.getEmployeeId());
    return query.getResultList();
  }
  
  
  public void persist(Role role) {
    em.persist(role);
  }
  
  public void merge(Role role) {
    em.merge(role);
  }
}
