package com.plj.domain.request.sys;

import java.io.Serializable;

public class UpdatePasswordRequest implements Serializable
{
	private static final long serialVersionUID = -8783122243748032517L;
	
	private String oldPassword;
	
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
