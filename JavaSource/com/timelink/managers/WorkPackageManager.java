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
   * Returns a list of WorkPackages that the given employee is assigned to
   * that are in the given Project
   * @param emp The employee
   * @param proj The project
   * @return A list of WorkPackages.
   */
  public List<WorkPackage> findAssigned(Employee emp, Project proj) {
    TypedQuery<WorkPackage> query = em.createQuery("SELECT DISTINCT wph FROM WorkPackage AS wph, "
        + "Project AS proj "
        + "WHERE wph.project.projectNumber = :projNumber "
        + "AND :empPara MEMBER OF wph.assignedEmployees", WorkPackage.class)
        .setParameter("projNumber", proj.getProjectNumber())
        .setParameter("empPara", emp);
    return query.getResultList();
  }
  
  public WorkPackage findParent(WorkPackage wp) {
    StringBuilder sb = new StringBuilder(wp.getCode());
    sb.setCharAt(sb.indexOf("0") - 1, '0');
    TypedQuery<WorkPackage> query = em.createQuery("SELECT wp FROM WorkPackage AS wp "
        + "WHERE wp.code = :code", WorkPackage.class)
        .setParameter("code", sb.toString());
    return query.getSingleResult();
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
  
  public WorkPackage findSickDay() {
    TypedQuery<WorkPackage> query = em.createQuery("SELECT wp FROM WorkPackage AS wp "
        + "WHERE wp.code = :code", WorkPackage.class)
        .setParameter("code", "Sick Day");
    return query.getSingleResult();
  }
}
