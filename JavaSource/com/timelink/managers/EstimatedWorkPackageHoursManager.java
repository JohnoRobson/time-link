package com.timelink.managers;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.timelink.ejbs.EstimatedWorkPackageHours;
import com.timelink.ejbs.WorkPackage;

@Dependent
@Stateless
public class EstimatedWorkPackageHoursManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  public EstimatedWorkPackageHours find(int id) {
    return em.find(EstimatedWorkPackageHours.class, id);
  }
  
  public void persist(EstimatedWorkPackageHours ew) {
    em.persist(ew);
  }
  
  public void merge(EstimatedWorkPackageHours ew) {
    em.merge(ew);
  }
  
  public List<EstimatedWorkPackageHours> getAllWithWorkPackage(WorkPackage wp) {
    TypedQuery<EstimatedWorkPackageHours> query
        = em.createQuery("SELECT ew FROM EstimatedWorkPackageHours AS ew "
        + "WHERE ew.workPackage = :wp", EstimatedWorkPackageHours.class)
        .setParameter("wp", wp);
    return query.getResultList();
  }
  
  /**
   * Returns a BudgetedHours that is for the given parameters.
   * @param wp The WorkPackage that the BudgetedHours is in.
   * @param labourGradeId The LabourGrade of the BudgetedHours.
   * @return A BudgetedHours, or null if one is not found.
   */
  public EstimatedWorkPackageHours find(WorkPackage wp, int labourGradeId, Date date) {
    try {
      TypedQuery<EstimatedWorkPackageHours> query = em.createQuery("SELECT ew FROM EstimatedWorkPackageHours AS ew WHERE "
          + "ew.labourGrade.labourGradeId = :lgId "
          + "AND ew.workPackage.workPackageId = :wpId "
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
  
  /**
   * Returns a BudgetedHours that is for the given parameters.
   * @param wp The WorkPackage that the BudgetedHours is in.
   * @param labourGradeId The LabourGrade of the BudgetedHours.
   * @return A BudgetedHours, or null if one is not found.
   */
  public List<EstimatedWorkPackageHours> find(WorkPackage wp, Date date) {
    try {
      TypedQuery<EstimatedWorkPackageHours> query = em.createQuery("SELECT ew FROM EstimatedWorkPackageHours AS ew WHERE "
          + "ew.workPackage.workPackageId = :wpId "
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
}
