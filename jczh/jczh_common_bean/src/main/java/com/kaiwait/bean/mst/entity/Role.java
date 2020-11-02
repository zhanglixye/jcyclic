package com.kaiwait.bean.mst.entity;

import java.util.Date;
import java.util.List;

public class Role {
	private int roleID;
	private String roleName;
	private String roleNameen;
	private String roleNamezc;
	private String roleNamejp;
	private String roleNamezt;
	
	private int roleOrder;
	private int isOn;
	private int nodeID;
	private String nodeNamejp;
	private String nodeNamezc;
	private String nodeNameen;
	private String nodeNamezt;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getNodeNamejp() {
		return nodeNamejp;
	}
	public void setNodeNamejp(String nodeNamejp) {
		this.nodeNamejp = nodeNamejp;
	}
	public String getNodeNamezc() {
		return nodeNamezc;
	}
	public void setNodeNamezc(String nodeNamezc) {
		this.nodeNamezc = nodeNamezc;
	}
	public String getNodeNameen() {
		return nodeNameen;
	}
	public void setNodeNameen(String nodeNameen) {
		this.nodeNameen = nodeNameen;
	}
	public String getNodeNamezt() {
		return nodeNamezt;
	}
	public void setNodeNamezt(String nodeNamezt) {
		this.nodeNamezt = nodeNamezt;
	}
	private int nodeOrder;
	
	private List<Role> nodeList;
	private List<Role> nodeListByRole;
	private List<Role> roleList;
	
	private Date addDate;
	private String companyID;
	private String addUserID;
	private String nodeColor;
	private int companycd;
	private int isNameRole;
	private int lockFlg;
	
	
	public String getRoleNameen() {
		return roleNameen;
	}
	public void setRoleNameen(String roleNameen) {
		this.roleNameen = roleNameen;
	}
	public String getRoleNamezc() {
		return roleNamezc;
	}
	public void setRoleNamezc(String roleNamezc) {
		this.roleNamezc = roleNamezc;
	}
	public String getRoleNamejp() {
		return roleNamejp;
	}
	public void setRoleNamejp(String roleNamejp) {
		this.roleNamejp = roleNamejp;
	}
	public String getRoleNamezt() {
		return roleNamezt;
	}
	public void setRoleNamezt(String roleNamezt) {
		this.roleNamezt = roleNamezt;
	}
	public int getLockFlg() {
		return lockFlg;
	}
	public void setLockFlg(int lockFlg) {
		this.lockFlg = lockFlg;
	}
	public int getIsNameRole() {
		return isNameRole;
	}
	public void setIsNameRole(int isNameRole) {
		this.isNameRole = isNameRole;
	}
	public String getNodeColor() {
		return nodeColor;
	}
	public void setNodeColor(String nodeColor) {
		this.nodeColor = nodeColor;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getAddUserID() {
		return addUserID;
	}
	public void setAddUserID(String addUserID) {
		this.addUserID = addUserID;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public List<Role> getNodeListByRole() {
		return nodeListByRole;
	}
	public void setNodeListByRole(List<Role> nodeListByRole) {
		this.nodeListByRole = nodeListByRole;
	}
	public int getNodeOrder() {
		return nodeOrder;
	}
	public void setNodeOrder(int nodeOrder) {
		this.nodeOrder = nodeOrder;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public List<Role> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<Role> nodeList) {
		this.nodeList = nodeList;
	}
	public int getNodeID() {
		return nodeID;
	}
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}
	public int getIsOn() {
		return isOn;
	}
	public void setIsOn(int isOn) {
		this.isOn = isOn;
	}
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public int getRoleOrder() {
		return roleOrder;
	}
	public void setRoleOrder(int roleOrder) {
		this.roleOrder = roleOrder;
	}
	public int getCompanycd() {
		return companycd;
	}
	public void setCompanycd(int companycd) {
		this.companycd = companycd;
	}
	
}