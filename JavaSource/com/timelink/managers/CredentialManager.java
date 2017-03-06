package com.timelink.managers;

import com.timelink.ejbs.Credentials;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Dependent
@Stateless
public class CredentialManager {
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  /**
   * Returns Credentials that match the user name and password given,
   * or null if no matching Credentials are found.
   * 
   * @param username The user name of the credentials searched for.
   * @param password The password of the credentials searched for.
   * @return Credentials that match the given user name and password.
   */
  public Credentials find(String username, String password) {
    TypedQuery<Credentials> query = em.createQuery("SELECT c FROM Credentials AS c WHERE"
        + " c.username = :username AND c.password = :password", Credentials.class)
        .setParameter("username", username)
        .setParameter("password", password);
    try {
      return query.getSingleResult();
    } catch (NoResultException ex) {
      return null;
    }
  }
  
  /**
   * Adds credentials to the database.
   * 
   * @param cred The credentials to be added.
   */
  public void persist(Credentials cred) {
    em.persist(cred);
  }
  
  /**
   * Updates the credentials in the database.
   * 
   * @param cred The credentials to be updated.
   */
  public void merge(Credentials cred) {
    em.merge(cred);
  }
}
