package com.timelink.managers;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Dependent
@Stateless
@Named("WorkPackageManager")
public class WorkPackageManager {
  
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  /**
   * Returns a WorkPackage from the database with id wpId.
   * 
   * @param wpId The ID to be searched for.
   * @return The workpackage matching the given ID,
   *         or null if one is not found.
   */
  public WorkPackage find(int wpId) {
    return em.find(WorkPackage.class, wpId);
  }
  
  /**
   * Returns all workpackages assigned to proj.
   * 
   * @param proj The project whose workpackages will
   *             be returned.
   * @return A list of workpackages within proj.
   */
  public List<WorkPackage> find(Project proj) {
    TypedQuery<WorkPackage> query = em.createQuery("SELECT wp FROM WP_Header"
        + "WHERE prj_header_id = :proId", WorkPackage.class)
        .setParameter("proId", proj.getProjectNumber());
    return query.getResultList();
  }
  
  /**
   * Returns all work packages within a project that the employee emp
   * has been assigned to.
   * @param proj The project to be searched.
   * @param emp The employee whose work packages will be returned.
   * @return All work packages within a project that emp is assigned to.
   */
  public List<WorkPackage> find(Project proj, Employee emp) {
    TypedQuery<WorkPackage> query = em.createQuery("SELECT DISTINCT wp "
        + "FROM WorkPackage AS wp, WorkPackageLine AS wpl "
        + "WHERE wpl.workPackageEmployeeId = :empId "
        + "AND wpl.workPackageId = wp.workPackageId "
        + "AND wp.project.projectNumber = :projectId", WorkPackage.class)
        .setParameter("projectId", proj.getProjectNumber())
        .setParameter("empId", emp.getEmployeeId());
    return query.getResultList();
  }
  
  /**
   * Adds wp to the database.
   * 
   * @param wp the workpackage to be added to the database.
   */
  public void persist(WorkPackage wp) {
    em.persist(wp);
  }
  
  /**
   * Updates wp in the database.
   * 
   * @param wp the workpackage to be updated in the database.
   */
  public void merge(WorkPackage wp) {
    em.merge(wp);
  }
}
