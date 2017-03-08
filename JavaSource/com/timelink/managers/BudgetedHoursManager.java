package com.timelink.managers;


import com.timelink.ejbs.BudgetedHours;
import com.timelink.ejbs.WorkPackage;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

@Dependent
@Stateless
public class BudgetedHoursManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  public BudgetedHours find(int plannedHoursId) {
    return em.find(BudgetedHours.class, plannedHoursId);
  }
  
  public BudgetedHours find(WorkPackage wp, int labourGradeId) {
    try {
      TypedQuery<BudgetedHours> query = em.createQuery("SELECT bh FROM BudgetedHours AS bh WHERE "
          + "bh.labourGrade.labourGradeId = :lgId "
          + "AND bh IN :plhrs", BudgetedHours.class)
          .setParameter("plhrs", wp.getPlannedHours())
          .setParameter("lgId", labourGradeId);
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
   * Adds hours to the database.
   * @param hours The Hours to be added to the database.
   */
  public void persist(BudgetedHours hours) {
    em.persist(hours);
  }
  
  /**
   * Updates an Hours in the database with hours.
   * @param hours The Hours object that will update the database.
   */
  public void merge(BudgetedHours hours) {
    em.merge(hours);
  }
  
  /**
   * Removes a BudgetedHours from the database.
   * @param hours The BudgetedHours to be removed.
   */
  public void remove(BudgetedHours hours) {
    em.remove(hours);
  }
}