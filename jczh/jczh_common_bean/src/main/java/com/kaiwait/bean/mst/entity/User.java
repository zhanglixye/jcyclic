package com.kaiwait.bean.mst.entity;

import java.util.List;

import com.kaiwait.bean.mst.vo.CommonmstVo;
import com.kaiwait.bean.mst.vo.CompanyVo;

public class User {
	//登陆用户名
	private String loginName;
	//部门ID
	private String departCD;
	//英文名称
	private String nickNameEn;
	//用户编号，一般用于被操作的用户
	private String userCD;
	//email
	private String email;
	//密码
	private String pwd;
	//级别
	private String level;
	//名称
	private String nickname;
	//语言ID
	private String langtyp;
	//登陆者ID
	private String addUserCD;
	//公司ID
	private String companyCD;
	//更新着ID
	private String upUserCD;
	//添加者名称
	private String addUserName;
	//更新着名称
	private String upUserName;
	//添加时间
	private String addTime;
	//更新时间
	private String upTime;
	//删除FLG
	private String delFlg;
	//得意先flg
	private String clientFlg;
	//请求先
	private String payFlg;
	
	
	//共通表中语言list
	private List<CommonmstVo> langTypList;
	//共通表中部门list
	private List<CommonmstVo> departList;
	//公司表中所有有效的公司名称
	private List<CompanyVo> comapnyList;
	//该用户所有已参照的公司
	private List<CompanyVo> checkedComapnyList;
	//共通表中级别list
	private List<CommonmstVo> levelList;
	//用户列表
	private List<User> userList;
	//添加者历史列表
	private List<User> userHistoryByInsert;
	//更新着历史列表
	private List<User> userHistoryByUp;
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
	//以关联
	private List<Clmst> relevancedList;
	//未关联
	private List<Clmst> irrelevantList;
	
	private String memberID;
	 
	private List<PageNode> pageNodeList;
	private List<Role> roleList;
	
	private List<Role> userRoleList;
	
	private String roleNameByUser;
	private String roleNameByUserzc;
	private String roleNameByUserzt;
	private String roleNameByUserjp;
	private String roleNameByUseren;
	
	//货币换算code
	private String itmLD;
	//小数点位数
	private String pointNumber;
	//货币名称中文
	private String moneyzc;
	//货币名称英文
	private String moneyen;
	//货币名称繁体
	private String moneyzt;
	//货币名称日文
	private String moneyjp;
	
	private String companyName;
	private String accountDate;
	private String roleStr;
	private int sysLockFlg;
	private String sysLockMsg;
	private String dateCompanyZone;
	private int isOutFlg;
	private String colorV;
	private String timeZoneType;
	private String statusFlg;
	
	private String usercolor;
	private int lockFlg;
	
	
	public String getRoleNameByUserzc() {
		return roleNameByUserzc;
	}

	public void setRoleNameByUserzc(String roleNameByUserzc) {
		this.roleNameByUserzc = roleNameByUserzc;
	}

	public String getRoleNameByUserzt() {
		return roleNameByUserzt;
	}

	public void setRoleNameByUserzt(String roleNameByUserzt) {
		this.roleNameByUserzt = roleNameByUserzt;
	}

	public String getRoleNameByUserjp() {
		return roleNameByUserjp;
	}

	public void setRoleNameByUserjp(String roleNameByUserjp) {
		this.roleNameByUserjp = roleNameByUserjp;
	}

	public String getRoleNameByUseren() {
		return roleNameByUseren;
	}

	public void setRoleNameByUseren(String roleNameByUseren) {
		this.roleNameByUseren = roleNameByUseren;
	}

	public int getLockFlg() {
		return lockFlg;
	}

	public void setLockFlg(int lockFlg) {
		this.lockFlg = lockFlg;
	}

	public String getClientFlg() {
		return clientFlg;
	}

	public void setClientFlg(String clientFlg) {
		this.clientFlg = clientFlg;
	}

	public String getPayFlg() {
		return payFlg;
	}

	public void setPayFlg(String payFlg) {
		this.payFlg = payFlg;
	}

	public String getUsercolor() {
		return usercolor;
	}

	public void setUsercolor(String usercolor) {
		this.usercolor = usercolor;
	}

	public String getStatusFlg() {
		return statusFlg;
	}

	public void setStatusFlg(String statusFlg) {
		this.statusFlg = statusFlg;
	}

	public String getTimeZoneType() {
		return timeZoneType;
	}

	public void setTimeZoneType(String timeZoneType) {
		this.timeZoneType = timeZoneType;
	}

	public String getColorV() {
		return colorV;
	}

	public void setColorV(String colorV) {
		this.colorV = colorV;
	}

	public int getIsOutFlg() {
		return isOutFlg;
	}

	public void setIsOutFlg(int isOutFlg) {
		this.isOutFlg = isOutFlg;
	}

	public String getDateCompanyZone() {
		return dateCompanyZone;
	}

	public void setDateCompanyZone(String dateCompanyZone) {
		this.dateCompanyZone = dateCompanyZone;
	}

	public String getSysLockMsg() {
		return sysLockMsg;
	}

	public void setSysLockMsg(String sysLockMsg) {
		this.sysLockMsg = sysLockMsg;
	}

	public int getSysLockFlg() {
		return sysLockFlg;
	}

	public void setSysLockFlg(int sysLockFlg) {
		this.sysLockFlg = sysLockFlg;
	}

	public String getRoleStr() {
		return roleStr;
	}

	public void setRoleStr(String roleStr) {
		this.roleStr = roleStr;
	}

	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}

	public List<PageNode> getPageNodeList() {
		return pageNodeList;
	}

	public void setPageNodeList(List<PageNode> pageNodeList) {
		this.pageNodeList = pageNodeList;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getItmLD() {
		return itmLD;
	}

	public void setItmLD(String itmLD) {
		this.itmLD = itmLD;
	}

	public String getPointNumber() {
		return pointNumber;
	}

	public void setPointNumber(String pointNumber) {
		this.pointNumber = pointNumber;
	}

	public String getMoneyzc() {
		return moneyzc;
	}

	public void setMoneyzc(String moneyzc) {
		this.moneyzc = moneyzc;
	}

	public String getMoneyen() {
		return moneyen;
	}

	public void setMoneyen(String moneyen) {
		this.moneyen = moneyen;
	}

	public String getMoneyzt() {
		return moneyzt;
	}

	public void setMoneyzt(String moneyzt) {
		this.moneyzt = moneyzt;
	}

	public String getMoneyjp() {
		return moneyjp;
	}

	public void setMoneyjp(String moneyjp) {
		this.moneyjp = moneyjp;
	}

	public String getRoleNameByUser() {
		return roleNameByUser;
	}

	public void setRoleNameByUser(String roleNameByUser) {
		this.roleNameByUser = roleNameByUser;
	}

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

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	
	
	public List<CompanyVo> getCheckedComapnyList() {
		return checkedComapnyList;
	}

	public void setCheckedComapnyList(List<CompanyVo> checkedComapnyList) {
		this.checkedComapnyList = checkedComapnyList;
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

	public List<User> getUserHistoryByInsert() {
		return userHistoryByInsert;
	}

	public void setUserHistoryByInsert(List<User> userHistoryByInsert) {
		this.userHistoryByInsert = userHistoryByInsert;
	}

	public List<User> getUserHistoryByUp() {
		return userHistoryByUp;
	}

	public void setUserHistoryByUp(List<User> userHistoryByUp) {
		this.userHistoryByUp = userHistoryByUp;
	}

	public String getAddUserName() {
		return addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public String getUpUserName() {
		return upUserName;
	}

	public void setUpUserName(String upUserName) {
		this.upUserName = upUserName;
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

	public List<CommonmstVo> getLangTypList() {
		return langTypList;
	}

	public void setLangTypList(List<CommonmstVo> langTypList) {
		this.langTypList = langTypList;
	}

	public List<CommonmstVo> getDepartList() {
		return departList;
	}

	public void setDepartList(List<CommonmstVo> departList) {
		this.departList = departList;
	}

	public List<CommonmstVo> getLevelList() {
		return levelList;
	}

	public void setLevelList(List<CommonmstVo> levelList) {
		this.levelList = levelList;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getUpTime() {
		return upTime;
	}

	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}

	

	public List<CompanyVo> getComapnyList() {
		return comapnyList;
	}

	public void setComapnyList(List<CompanyVo> comapnyList) {
		this.comapnyList = comapnyList;
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

	public String getUpUserCD() {
		return upUserCD;
	}

	public void setUpUserCD(String upUserCD) {
		this.upUserCD = upUserCD;
	}

	public String getAddUserCD() {
		return addUserCD;
	}

	public void setAddUserCD(String addUserCD) {
		this.addUserCD = addUserCD;
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

}