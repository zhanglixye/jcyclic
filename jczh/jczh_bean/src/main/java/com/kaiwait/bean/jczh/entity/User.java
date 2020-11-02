package com.kaiwait.bean.jczh.entity;

import java.util.List;




public class User {
	//部门ID
	private String departCD;
	//英文名称
	private String nickNameEn;
	//用户编号，一般用于被操作的用户
	private String userCD;
	//级别
	private String level;
	//名称
	private String nickname;
	//语言ID
	private String langtyp;
	//公司ID
	private String companyCD;
	//用户列表
	private List<User> userList;
	//级别中文名称
	private String levelzc;
	//级别英文名称
	private String levelen;
	//级别繁体名称
	private String levelzt;
	//级别日文名称
	private String leveljp;
	//部门中文名称
	private String departNamezc;
	private String departNameen;
	private String departNamezt;
	private String departNamejp;
	//语言中文名称
	private String langtypzc;
	private String langtypen;
	private String langtypzt;
	private String langtypjp;

	
	 

	private List<Role> roleList;
	
	private List<Role> userRoleList;
	
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	private String companyName;
	
	
	

	public List<Role> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<Role> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String getLevelzc() {
		return levelzc;
	}

	public void setLevelzc(String levelzc) {
		this.levelzc = levelzc;
	}

	public String getLevelzt() {
		return levelzt;
	}

	public void setLevelzt(String levelzt) {
		this.levelzt = levelzt;
	}

	public String getDepartNamezc() {
		return departNamezc;
	}

	public void setDepartNamezc(String departNamezc) {
		this.departNamezc = departNamezc;
	}

	public String getDepartNamezt() {
		return departNamezt;
	}

	public void setDepartNamezt(String departNamezt) {
		this.departNamezt = departNamezt;
	}

	public String getLangtypzc() {
		return langtypzc;
	}

	public void setLangtypzc(String langtypzc) {
		this.langtypzc = langtypzc;
	}

	public String getLangtypzt() {
		return langtypzt;
	}

	public void setLangtypzt(String langtypzt) {
		this.langtypzt = langtypzt;
	}

	public String getLevelen() {
		return levelen;
	}

	public void setLevelen(String levelen) {
		this.levelen = levelen;
	}


	public String getLeveljp() {
		return leveljp;
	}

	public void setLeveljp(String leveljp) {
		this.leveljp = leveljp;
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


	public String getLangtypen() {
		return langtypen;
	}

	public void setLangtypen(String langtypen) {
		this.langtypen = langtypen;
	}

	public String getLangtypjp() {
		return langtypjp;
	}

	public void setLangtypjp(String langtypjp) {
		this.langtypjp = langtypjp;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	

	public String getDepartCD() {
		return departCD;
	}

	public void setDepartCD(String departCD) {
		this.departCD = departCD;
	}
	
	public String getNickNameEn() {
		return nickNameEn;
	}

	public void setNickNameEn(String nickNameEn) {
		this.nickNameEn = nickNameEn;
	}


	public String getCompanyCD() {
		return companyCD;
	}

	public void setCompanyCD(String companyCD) {
		this.companyCD = companyCD;
	}

	public String getUserCD() {
		return userCD;
	}

	public void setUserCD(String userCD) {
		this.userCD = userCD;
	}


	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLangtyp() {
		return langtyp;
	}

	public void setLangtyp(String langtyp) {
		this.langtyp = langtyp;
	}

}