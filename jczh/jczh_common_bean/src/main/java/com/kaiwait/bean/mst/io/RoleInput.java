package com.kaiwait.bean.mst.io;


import java.util.List;

import com.kaiwait.bean.mst.entity.Role;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class RoleInput extends BaseInputBean{
	
	private static final long serialVersionUID = 5085368117650161941L;
	
	private String roleName;
	private int roleOrder;
	private int isOn;
	private String roleID;
	
	private List<Role> roleList;

	
	
	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getRoleOrder() {
		return roleOrder;
	}

	public void setRoleOrder(int roleOrder) {
		this.roleOrder = roleOrder;
	}

	public int getIsOn() {
		return isOn;
	}

	public void setIsOn(int isOn) {
		this.isOn = isOn;
	}
	
	
}