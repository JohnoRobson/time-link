package com.timelink.managers;

import com.timelink.ejbs.AdminMessage;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Dependent
@Stateless
public class AdminMessageManager {

  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;

  public AdminMessage find(int adminMsgId) {
    return em.find(AdminMessage.class, adminMsgId);
  }

  public void persist(AdminMessage am) {
    em.persist(am);
  }

  /**
   * Returns all administrator messages.
   * @return A List of AdminMessage.
   */
  public List<AdminMessage> getAll() {
    TypedQuery<AdminMessage> query
        = em.createQuery("SELECT a FROM AdminMessage a", AdminMessage.class);
    return query.getResultList();
  }
}
