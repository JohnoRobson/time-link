package com.timelink.managers;

import com.timelink.ejbs.BudgetedProjectWorkDays;
import com.timelink.ejbs.Project;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Dependent
@Stateless
public class BudgetedProjectWorkDaysManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  public BudgetedProjectWorkDays find(int id) {
    return em.find(BudgetedProjectWorkDays.class, id);
  }
  
  /**
   * Returns all BudgetedProjectWorkDays associated with the given project.
   * @param pro The project that the hours belong to.
   * @return A List of BudgetedProjectWorkDays.
   */
  public List<BudgetedProjectWorkDays> find(Project pro) {
    TypedQuery<BudgetedProjectWorkDays> query
        = em.createQuery("SELECT ph FROM BudgetedProjectWorkDays AS ph "
        + "WHERE ph.project.projectNumber = :proId", BudgetedProjectWorkDays.class)
        .setParameter("proId", pro.getProjectNumber());
    return query.getResultList();
  }
  
  /**
   * Returns a BudgetedProjectWorkDays with the specified Project and labourGradeId.
   * @param pro The project that the hour belongs to.
   * @param labourGradeId The labourGradeId that the project has.
   * @return A BudgetedProjectWorkDays.
   */
  public BudgetedProjectWorkDays find(Project pro, int labourGradeId) {
    TypedQuery<BudgetedProjectWorkDays> query
        = em.createQuery("SELECT ph FROM BudgetedProjectWorkDays AS ph "
        + "WHERE ph.project.projectNumber = :proId AND "
        + "ph.labourGrade.labourGradeId = :lgId", BudgetedProjectWorkDays.class)
        .setParameter("proId", pro.getProjectNumber())
        .setParameter("lgId", labourGradeId);
    return query.getSingleResult();
  }
  
  public void persist(BudgetedProjectWorkDays ph) {
    em.persist(ph);
  }
  
  public void merge(BudgetedProjectWorkDays ph) {
    em.merge(ph);
  }
}
