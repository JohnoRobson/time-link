package com.timelink.ejbs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "admin_message")
public class AdminMessage implements Serializable {
	
	@Id
	@Column(name = "am_id")
	private int adminMessageId;
	
	@Column(name = "am_user_name")
	private String userName;
	
	@Column(name = "am_user_email")
	private String userEmail;
	
	@Column(name = "am_issue_title")
	private String title;
	
	@Column(name = "am_issue_content")
	private String contents;
	
	@Column(name = "am_isread")
	private boolean isRead;

	public int getAdminMessageId() {
		return adminMessageId;
	}

	public void setAdminMessageId(int adminMessageId) {
		this.adminMessageId = adminMessageId;
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

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	
	
}
