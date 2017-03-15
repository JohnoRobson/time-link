package com.timelink.managers;

import com.timelink.ejbs.Project;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
@Stateless
@Named("ProjectManager")
public class ProjectManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  /**
   * Returns the Project that matches the id given,
   * or null if no matching Project are found.
   * 
   * @param id The id of the project to search for/
   * @return Project that matches the given id
   */
  public Project find(int id) {
    return em.find(Project.class, id);
  }
  
  /**
   * Returns all Projects in the database, or null if
   * none exist.
   * 
   * @return Project list that are in the database
   */
  public List<Project> findAll() {
    return em.createQuery("SELECT p FROM Project as p", Project.class)
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
  
  /**
   * Returns a list of all projects that have a project manager with the specified id.
   * @param empId The id to be searched for.
   * @return A List of all projects managed by an project manager with id id.
   */
  public List<Project> findByProjectManager(int empId) {
    return em.createQuery("SELECT p FROM Project as p "
        + "WHERE p.projectManager.employeeId = :empId", Project.class)
        .setParameter("empId", empId)
        .getResultList();  
  }
  
  public void flush() {
    em.flush();
  }
  
  /**
   * Returns true if there is no project with the same projectName as proj.
   * @return True, if proj's name is unique.
   */
  public boolean projectNameIsUnique(String name) {
    List<Project> list = em.createQuery("SELECT p FROM Project as p "
        + "WHERE p.projectName = :projName", Project.class)
        .setParameter("projName", name)
        .getResultList();
    return list.size() == 0;
  }

}
