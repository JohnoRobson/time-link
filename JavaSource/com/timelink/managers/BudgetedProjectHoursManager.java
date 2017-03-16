package com.timelink.managers;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.timelink.ejbs.BudgetedProjectHours;
import com.timelink.ejbs.Project;

@Dependent
@Stateless
public class BudgetedProjectHoursManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  public BudgetedProjectHours find(int id) {
    return em.find(BudgetedProjectHours.class, id);
  }
  
  public void persist(BudgetedProjectHours ph) {
    em.persist(ph);
  }
  
  public void merge(BudgetedProjectHours ph) {
    em.merge(ph);
  }
  
  public List<BudgetedProjectHours> find(Project pro) {
    TypedQuery<BudgetedProjectHours> query = em.createQuery("SELECT ph FROM BudgetedProjectHours AS ph "
        + "WHERE ph.project.projectNumber = :proId", BudgetedProjectHours.class)
        .setParameter("proId", pro.getProjectNumber());
    return query.getResultList();
  }
  
  public BudgetedProjectHours find(Project pro, int labourGradeId) {
    TypedQuery<BudgetedProjectHours> query = em.createQuery("SELECT ph FROM BudgetedProjectHours AS ph "
        + "WHERE ph.project.projectNumber = :proId AND "
        + "ph.labourGrade.labourGradeId = :lgId", BudgetedProjectHours.class)
        .setParameter("proId", pro.getProjectNumber())
        .setParameter("lgId", labourGradeId);
    return query.getSingleResult();
  }
}
