package com.timelink.managers;

import com.timelink.ejbs.LabourGrade;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Dependent
public class LabourGradeManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  public List<LabourGrade> getAllLabourGrades() {
    return em.createQuery("SELECT lg FROM LabourGrade as lg", LabourGrade.class)
        .getResultList();
  }
}
