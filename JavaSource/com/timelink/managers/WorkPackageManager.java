package com.timelink.managers;

import com.timelink.ejbs.Employee;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.Timesheet;
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
  
  /**
   * Returns the sick day WorkPackage.
   * @return The sick day WorkPackage.
   */
  public WorkPackage findSickDay() {
    TypedQuery<WorkPackage> query = em.createQuery("SELECT wp FROM WorkPackage AS wp "
        + "WHERE wp.code = :code", WorkPackage.class)
        .setParameter("code", "Sick Day");
    return query.getSingleResult();
  }
  
  /**
   * Returns the parent of the given work package.
   * @param wp The work package whose parent will be returned.
   * @return the parent work package, or null if the given work package was
   *     a top-level work package.
   */
  public WorkPackage findParent(WorkPackage wp) {
    //Check if wp is a top-level work package.
    if (wp.getCode().indexOf('0') == 1) {
      return null;
    }
    
    StringBuilder sb = new StringBuilder(wp.getCode());
    int index = sb.indexOf("0");
    
    //If wp is a lowest level work package
    if (index == -1) {
      sb.setCharAt(sb.length(), '0');
    } else {
      sb.setCharAt(index, '0');
    }
    
    TypedQuery<WorkPackage> query = em.createQuery("SELECT wp FROM WorkPackage AS wp "
        + "WHERE wp.code = :code", WorkPackage.class)
        .setParameter("code", sb.toString());
    return query.getSingleResult();
  }
  
  /**
   * Returns all WorkPackages in the given timesheet.
   * @param ts The timesheet to be searched
   * @return A List of WorkPackages in the given timesheet.
   */
  public List<WorkPackage> getAllInTimesheet(Timesheet ts) {
    TypedQuery<WorkPackage> query = em.createQuery("SELECT DISTINCT wp FROM WorkPackage AS wp, "
        + "Hours AS hour WHERE hour.timesheetId = :tsId AND "
        + "wp.workPackageId = hour.workPackageId", WorkPackage.class)
        .setParameter("tsId", ts.getTimesheetId());
    return query.getResultList();
  }
  
  /**
   * Returns true if the given WorkPackage does not have children.
   * @param wp The WorkPackages to be searched.
   * @return True, if wp has no children.
   */
  public boolean isLeaf(WorkPackage wp) {
    StringBuilder sb = new StringBuilder(wp.getCode());
    int index = sb.indexOf("0");
    if (index == -1) {
      return true;
    } else {
      sb = new StringBuilder(sb.substring(0, index));
    }
    
    TypedQuery<WorkPackage> query = em.createQuery("SELECT wp FROM WorkPackage AS wp "
        + "WHERE wp.project = :workProject AND wp.code LIKE :code "
        + "AND wp.workPackageId != :wpId", WorkPackage.class)
        .setParameter("workProject", wp.getProject())
        .setParameter("code", sb.toString() + "%")
        .setParameter("wpId", wp.getWorkPackageId());
    return query.getResultList().size() == 0;
  }
}
