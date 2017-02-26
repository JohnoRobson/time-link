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
  
  
  public List<Role> findRoles(Employee emp) {
    TypedQuery<Role> query = em.createQuery("SELECT r FROM Roles AS r WHERE"
        + " r.employeeId = :employeeId", Role.class)
        .setParameter("employeeId", emp.getEmployeeId());
    return query.getResultList();
  }
  
  public void persist(Role r) {
    em.persist(r);
  }
  
  public void merge(Role r) {
    em.merge(r);
  }
}
