package com.kaiwait.bean.jczh.entity;

import java.util.Date;
import java.util.List;

public class Role {
	private int roleID;
	private String roleNamezc;
	private String roleNamezt;
	private String roleNameen;
	private String roleNamejp;
	private int roleOrder;
	private int isOn;
	private int nodeID;
	private String nodeNamezc;
	private String nodeNamezt;
	private String nodeNameen;
	private String nodeNamejp;
	private int nodeOrder;
	
	private List<Role> nodeList;
	private List<Role> nodeListByRole;
	private List<Role> roleList;
	
	private Date addDate;
	private String companyID;
	private String addUserID;
	private String nodeColor;
	private int colorIndex;
	private String userName;
	private String memberID;
	private String departNamezc;
	private String departNameen;
	private String departNamejp;
	private String departNamezt;
	private String levelNamezc;
	private String levelNameen;
	private String levelNamejp;
	private String levelNamezt;
	private String companyFullName;
	private String roleNameListStrzc;
	private String roleNameListStrzt;
	private String roleNameListStren;
	private String roleNameListStrjp;
	private String nodeListStr;
	
	public String getNodeListStr() {
		return nodeListStr;
	}
	public void setNodeListStr(String nodeListStr) {
		this.nodeListStr = nodeListStr;
	}
	public String getRoleNameListStrzc() {
		return roleNameListStrzc;
	}
	public void setRoleNameListStrzc(String roleNameListStrzc) {
		this.roleNameListStrzc = roleNameListStrzc;
	}
	public String getRoleNameListStrzt() {
		return roleNameListStrzt;
	}
	public void setRoleNameListStrzt(String roleNameListStrzt) {
		this.roleNameListStrzt = roleNameListStrzt;
	}
	public String getRoleNameListStren() {
		return roleNameListStren;
	}
	public void setRoleNameListStren(String roleNameListStren) {
		this.roleNameListStren = roleNameListStren;
	}
	public String getRoleNameListStrjp() {
		return roleNameListStrjp;
	}
	public void setRoleNameListStrjp(String roleNameListStrjp) {
		this.roleNameListStrjp = roleNameListStrjp;
	}
	public String getRoleNamezc() {
		return roleNamezc;
	}
	public void setRoleNamezc(String roleNamezc) {
		this.roleNamezc = roleNamezc;
	}
	public String getRoleNamezt() {
		return roleNamezt;
	}
	public void setRoleNamezt(String roleNamezt) {
		this.roleNamezt = roleNamezt;
	}
	public String getRoleNameen() {
		return roleNameen;
	}
	public void setRoleNameen(String roleNameen) {
		this.roleNameen = roleNameen;
	}
	public String getRoleNamejp() {
		return roleNamejp;
	}
	public void setRoleNamejp(String roleNamejp) {
		this.roleNamejp = roleNamejp;
	}
	public String getNodeNamezc() {
		return nodeNamezc;
	}
	public void setNodeNamezc(String nodeNamezc) {
		this.nodeNamezc = nodeNamezc;
	}
	public String getNodeNamezt() {
		return nodeNamezt;
	}
	public void setNodeNamezt(String nodeNamezt) {
		this.nodeNamezt = nodeNamezt;
	}
	public String getNodeNameen() {
		return nodeNameen;
	}
	public void setNodeNameen(String nodeNameen) {
		this.nodeNameen = nodeNameen;
	}
	public String getNodeNamejp() {
		return nodeNamejp;
	}
	public void setNodeNamejp(String nodeNamejp) {
		this.nodeNamejp = nodeNamejp;
	}
	public String getDepartNamezc() {
		return departNamezc;
	}
	public void setDepartNamezc(String departNamezc) {
		this.departNamezc = departNamezc;
	}
	public String getDepartNameen() {
		return departNameen;
	}
	public void setDepartNameen(String departNameen) {
		this.departNameen = departNameen;
	}
	public String getDepartNamejp() {
		return departNamejp;
	}
	public void setDepartNamejp(String departNamejp) {
		this.departNamejp = departNamejp;
	}
	public String getDepartNamezt() {
		return departNamezt;
	}
	public void setDepartNamezt(String departNamezt) {
		this.departNamezt = departNamezt;
	}
	public String getLevelNamezc() {
		return levelNamezc;
	}
	public void setLevelNamezc(String levelNamezc) {
		this.levelNamezc = levelNamezc;
	}
	public String getLevelNameen() {
		return levelNameen;
	}
	public void setLevelNameen(String levelNameen) {
		this.levelNameen = levelNameen;
	}
	public String getLevelNamejp() {
		return levelNamejp;
	}
	public void setLevelNamejp(String levelNamejp) {
		this.levelNamejp = levelNamejp;
	}
	public String getLevelNamezt() {
		return levelNamezt;
	}
	public void setLevelNamezt(String levelNamezt) {
		this.levelNamezt = levelNamezt;
	}
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	
	public String getCompanyFullName() {
		return companyFullName;
	}
	public void setCompanyFullName(String companyFullName) {
		this.companyFullName = companyFullName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getColorIndex() {
		return colorIndex;
	}
	public void setColorIndex(int colorIndex) {
		this.colorIndex = colorIndex;
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
	
}