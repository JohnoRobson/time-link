package com.timelink.controllers;

import com.timelink.ejbs.AdminMessage;
import com.timelink.managers.AdminMessageManager;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

@SuppressWarnings("serial")
@SessionScoped
@Named("AdminMessageController")
public class AdminMessageController implements Serializable {

  @Inject AdminMessageManager am;

  private String userName;
  private String userEmail;
  private String title;
  private String content;
  private boolean isRead;

  public List<AdminMessage> getAdList() {
    return am.getAll();
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean isRead() {
    return isRead;
  }

  public void setRead(boolean isRead) {
    this.isRead = isRead;
  }
  
  /**
   * Saves the current user issue.
   */
  public void saveUserIssue() {

    AdminMessage newMessage = new AdminMessage();

    newMessage.setUserName(userName);
    newMessage.setUserEmail(userEmail);
    newMessage.setTitle(title);
    newMessage.setContents(content);
    newMessage.setRead(true);

    try {
      am.persist(newMessage);
      RequestContext.getCurrentInstance().reset("form:panel");
    } catch (Exception ex) {
      return;
    }
  }



}
