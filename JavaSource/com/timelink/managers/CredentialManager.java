package com.timelink.managers;

import com.timelink.ejbs.Credentials;
import com.timelink.ejbs.CredentialsId;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
@Stateless
public class CredentialManager {
  //TODO change this to match the schema when we get it.
  @PersistenceContext(unitName = "timesheet-jpa") EntityManager em;
  
  //TODO make this class work.
  
  /**
   * Returns Credentials that match the user name and password given,
   * or null if no matching Credentials are found.
   * 
   * @param username The user name of the credentials searched for.
   * @param password The password of the credentials searched for.
   * @return Credentials that match the given user name and password.
   */
  public Credentials find(String username, String password) {
    em.find(Credentials.class, new CredentialsId(username, password));
    return null;
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
