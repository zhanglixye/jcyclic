package com.kaiwait.bean.mst.io;


import java.util.List;

import com.kaiwait.bean.mst.entity.Clmst;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class UserInfoInput extends BaseInputBean{
	
	private static final long serialVersionUID = 5085368117650161941L;
	//登陆名称
	private String loginName;
	//部门ID
	private String departCD;
	//英文名称
	private String nickNameEn;
	//用户编号
	private String userCD;
	private int userIDBysql;
	//email
	private String email;
	//密码
	private String pwd;
	//级别
	private String level;
	//姓名
	private String nickname;
	//语言ID
	private String langtyp;
	//删除flg
	private String delFlg;
	//操作者公司ID
	private String companyID;
	//用户列表和用户添加，页面初始化标识
	private String searchFLg;
	//公司ID字符串数组
	private String companyList;
	//以关联
	private List<Clmst> relevancedList;
	//未关联
	private List<Clmst> irrelevantList;
	
	private String memberID;
	private String roles;
	
	private String sClientID;
	private String divnm;
	private String pd;
	private String addDatetime;
	private String upDatetime;
	private String changeCompanyID;
	private String colorV;
	private String roleID;
	private String flag;
	private String colorv;
	private int lockFlg;
	private String fileData;
	private String pswUpdateTime;
	public String getPswUpdateTime() {
		return pswUpdateTime;
	}

	public void setPswUpdateTime(String pswUpdateTime) {
		this.pswUpdateTime = pswUpdateTime;
	}

	public String getFileData() {
		return fileData;
	}

	public void setFileData(String fileData) {
		this.fileData = fileData;
	}

	public int getLockFlg() {
		return lockFlg;
	}

	public void setLockFlg(int lockFlg) {
		this.lockFlg = lockFlg;
	}

	public String getColorv() {
		return colorv;
	}

	public void setColorv(String colorv) {
		this.colorv = colorv;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getColorV() {
		return colorV;
	}

	public void setColorV(String colorV) {
		this.colorV = colorV;
	}

	public String getChangeCompanyID() {
		return changeCompanyID;
	}

	public void setChangeCompanyID(String changeCompanyID) {
		this.changeCompanyID = changeCompanyID;
	}

	public String getAddDatetime() {
		return addDatetime;
	}

	public void setAddDatetime(String addDatetime) {
		this.addDatetime = addDatetime;
	}

	public String getUpDatetime() {
		return upDatetime;
	}

	public void setUpDatetime(String upDatetime) {
		this.upDatetime = upDatetime;
	}

	public String getDivnm() {
		return divnm;
	}

	public void setDivnm(String divnm) {
		this.divnm = divnm;
	}

	public String getsClientID() {
		return sClientID;
	}

	public void setsClientID(String sClientID) {
		this.sClientID = sClientID;
	}

	public int getUserIDBysql() {
		return userIDBysql;
	}

	public void setUserIDBysql(int userIDBysql) {
		this.userIDBysql = userIDBysql;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public List<Clmst> getRelevancedList() {
		return relevancedList;
	}

	public void setRelevancedList(List<Clmst> relevancedList) {
		this.relevancedList = relevancedList;
	}

	public List<Clmst> getIrrelevantList() {
		return irrelevantList;
	}

	public void setIrrelevantList(List<Clmst> irrelevantList) {
		this.irrelevantList = irrelevantList;
	}

	public String getCompanyList() {
		return companyList;
	}

	public void setCompanyList(String companyList) {
		this.companyList = companyList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSearchFLg() {
		return searchFLg;
	}

	public void setSearchFLg(String searchFLg) {
		this.searchFLg = searchFLg;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

    public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	

	public String getUserCD() {
		return userCD;
	}

	public void setUserCD(String userCD) {
		this.userCD = userCD;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

}