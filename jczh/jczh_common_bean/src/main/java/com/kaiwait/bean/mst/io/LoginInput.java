package com.kaiwait.bean.mst.io;


import com.kaiwait.common.vo.json.server.BaseInputBean;

public class LoginInput extends BaseInputBean{
	
	private static final long serialVersionUID = 5085368117650161941L;
	
	private String userName;
	private String password;
	private String oldPassword;
	
	public String getUserName() {
		return userName;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
