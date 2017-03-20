package com.timelink.managers;

import com.timelink.ejbs.EstimatedWorkPackageHours;
import com.timelink.ejbs.Project;
import com.timelink.ejbs.WorkPackage;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

@Dependent
@Stateless
public class EstimatedWorkPackageHoursManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  public EstimatedWorkPackageHours find(int id) {
    return em.find(EstimatedWorkPackageHours.class, id);
  }
  
  /**
   * Returns an EstimatedWorkPackageHours that is for the given parameters.
   * @param wp The WorkPackage that the BudgetedHours is in.
   * @param date The date for the Estimation.
   * @return A List of EstimatedWorkPackageHours, or null if one is not found.
   */
  public List<EstimatedWorkPackageHours> find(WorkPackage wp, Date date) {
    try {
      TypedQuery<EstimatedWorkPackageHours> query
          = em.createQuery("SELECT ew FROM EstimatedWorkPackageHours AS ew WHERE "
          + "ew.workpackage.workPackageId = :wpId "
          + "AND ew.dateCreated = :date "
          + "ORDER BY ew.labourGrade.labourGradeId ASC", EstimatedWorkPackageHours.class)
          .setParameter("wpId", wp.getWorkPackageId())
          .setParameter("date", date, TemporalType.DATE);
      if (query.getResultList().size() > 0) {
        return query.getResultList();
      } else {
        return null;
      }
    } catch (PersistenceException ex) {
      return null;
    }
  }
  
  /**
   * Returns a List EstimatedWorkPackageHours that is for the given parameters.
   * @param pro The Project that the EstimatedWorkPackageHours is for.
   * @param labourGradeId The LabourGrade of the BudgetedHours.
   * @return A List of EstimatedWorkPackageHours, or null if one is not found.
   */
  public List<EstimatedWorkPackageHours> find(Project pro, int labourGradeId) {
    try {
      TypedQuery<EstimatedWorkPackageHours> query
          = em.createQuery("SELECT DISTINCT ew FROM EstimatedWorkPackageHours AS ew, "
          + "Project AS proj WHERE "
          + "proj.projectNumber = :proId "
          + "AND ew.workpackage MEMBER OF proj.workPackages "
          + "AND ew.labourGrade.labourGradeId = :lgId", EstimatedWorkPackageHours.class)
          .setParameter("proId", pro.getProjectNumber())
          .setParameter("lgId", labourGradeId);
      if (query.getResultList().size() > 0) {
        return query.getResultList();
      } else {
        return null;
      }
    } catch (PersistenceException ex) {
      return null;
    }
  }
  
  /**
   * Returns the latest EstimatedWorkPackageHours that is for the given parameters.
   * @param pro The Project that the EstimatedWorkPackageHours is for.
   * @param labourGradeId The LabourGrade of the BudgetedHours.
   * @return A List of EstimatedWorkPackageHours, or null if one is not found.
   */
  public EstimatedWorkPackageHours findLatest(Project pro, int labourGradeId) {
    try {
      TypedQuery<EstimatedWorkPackageHours> query
          = em.createQuery("SELECT DISTINCT ew FROM EstimatedWorkPackageHours AS ew, "
          + "Project AS proj WHERE "
          + "proj.projectNumber = :proId "
          + "AND ew.workpackage MEMBER OF proj.workPackages "
          + "AND ew.labourGrade.labourGradeId = :lgId "
          + "ORDER BY ew.dateCreated DESC", EstimatedWorkPackageHours.class)
          .setParameter("proId", pro.getProjectNumber())
          .setParameter("lgId", labourGradeId)
          .setMaxResults(1);
      if (query.getResultList().size() > 0) {
        return query.getSingleResult();
      } else {
        return null;
      }
    } catch (PersistenceException ex) {
      return null;
    }
  }
  
  /**
   * Returns an EstimatedWorkPackageHours that is for the given parameters.
   * @param wp The WorkPackage that the BudgetedHours is in.
   * @param labourGradeId The LabourGrade of the BudgetedHours.
   * @param date The date to be searched.
   * @return A List of EstimatedWorkPackageHours, or null if one is not found.
   */
  public EstimatedWorkPackageHours find(WorkPackage wp, int labourGradeId, Date date) {
    try {
      TypedQuery<EstimatedWorkPackageHours> query
          = em.createQuery("SELECT ew FROM EstimatedWorkPackageHours AS ew WHERE "
          + "ew.labourGrade.labourGradeId = :lgId "
          + "AND ew.workpackage.workPackageId = :wpId "
          + "AND ew.date = :date "
          + "ORDER BY ew.labourGrade.labourGradeId ASC", EstimatedWorkPackageHours.class)
          .setParameter("wpId", wp.getWorkPackageId())
          .setParameter("lgId", labourGradeId)
          .setParameter("date", date);
      if (query.getResultList().size() > 0) {
        return query.getSingleResult();
      } else {
        return null;
      }
    } catch (PersistenceException ex) {
      return null;
    }
  }
  
  public void persist(EstimatedWorkPackageHours ew) {
    em.persist(ew);
  }
  
  public void merge(EstimatedWorkPackageHours ew) {
    em.merge(ew);
  }
  
  /**
   * Returns a List of EstimatedWorkPackageHours associated with the given WorkPackage
   * that have unique dates.  Used to make a list of dates with estimates.
   * @param wp The WorkPackage to be searched.
   * @return A List of EstimatedWorkPackageHours.
   */
  public List<EstimatedWorkPackageHours> getAllWithWorkPackageUniqueDate(WorkPackage wp) {
    TypedQuery<EstimatedWorkPackageHours> query
        = em.createQuery("SELECT ew FROM EstimatedWorkPackageHours AS ew "
        + "WHERE ew.workpackage = :wp GROUP BY ew.dateCreated "
        + "ORDER BY ew.dateCreated DESC", EstimatedWorkPackageHours.class)
        .setParameter("wp", wp);
    return query.getResultList();
  }
  
}
