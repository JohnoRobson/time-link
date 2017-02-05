package com.timelink.managers;

import com.timelink.ejbs.Project;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
@Stateless
public class ProjectManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  /**
   * Returns the Project that match the id given,
   * or null if no matching Project are found.
   * 
   * @param id The id of the project to search for/
   * @return Project that matches the given id
   */
  public Project find(int id) {
    return em.find(Project.class, id);
  }
  
  /**
   * Returns all the Project names that are
   * available for an employee, queried by their id,
   * returns null if no matching results.
   * 
   * @param id The EmployeeId to find Projects by.
   * @return List of project names.
   */
  public List findByEmpId(int id) {
    return em.createQuery(
        "SELECT ph.prjh_name"
        + "FROM Prj_Header ph, Prj_Line pl"
        + "WHERE pl.prjl_emp_id = " + id)
        .getResultList();
        
  }
  
  /**
   * Adds project to the database.
   * 
   * @param proj The project to be added
   */
  public void persist(Project proj) {
    em.persist(proj);
  }
  
  /**
   * Updates the project in the database.
   * 
   * @param proj The project to be updated
   */
  public void merge(Project proj) {
    em.merge(proj);
  }
}
