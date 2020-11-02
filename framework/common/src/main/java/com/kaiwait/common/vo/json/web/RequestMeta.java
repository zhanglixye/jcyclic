/**
 * 
 */
package com.kaiwait.common.vo.json.web;


/**
 * @author wings
 *
 */
public class RequestMeta {
	/**  服务端生成的请求的唯一标识*/
	private String requestId;
	/**  用户标识,如用户未登录,该值为空*/
	private String custId;
	/**  会话标识*/
	private String sessionId;
	/** 服务器IP*/
	private String serverIp;
	/** 客户端IP*/
	private String remoteIp;
	/** 消息到达的时间戳*/
	private Long timestamp;
	/** 客户来源标识*/
	private String clientId;
	/** 客户标签*/
	private String clientTag;
	/** 客户端控件ID*/
	private String buttonId;
	/** 请求的Uri路径*/
	private String reqUri;
	/**用户ID*/
	private String userID;
	/**公司ID*/
	private String companyID;
	
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
	
	private String departNamezc;
	private String departNameen;
	private String departNamezt;
	private String departNamejp;
	
	//部门ID
	private String departCD;
	
	private String roleList;
	
	private String pageNodeList;
	
	private String userCompanyList;
	private String systemlock;
	private String dateCompanyZone;
	private String timeZoneType;
	
	
	
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
	public String getDepartNamezt() {
		return departNamezt;
	}
	public void setDepartNamezt(String departNamezt) {
		this.departNamezt = departNamezt;
	}
	public String getDepartNamejp() {
		return departNamejp;
	}
	public void setDepartNamejp(String departNamejp) {
		this.departNamejp = departNamejp;
	}
	public String getTimeZoneType() {
		return timeZoneType;
	}
	public void setTimeZoneType(String timeZoneType) {
		this.timeZoneType = timeZoneType;
	}
	public String getDateCompanyZone() {
		return dateCompanyZone;
	}
	public void setDateCompanyZone(String dateCompanyZone) {
		this.dateCompanyZone = dateCompanyZone;
	}
	public String getSystemlock() {
		return systemlock;
	}
	public void setSystemlock(String systemlock) {
		this.systemlock = systemlock;
	}
	public String getUserCompanyList() {
		return userCompanyList;
	}
	public void setUserCompanyList(String userCompanyList) {
		this.userCompanyList = userCompanyList;
	}
	public String getPageNodeList() {
		return pageNodeList;
	}
	public void setPageNodeList(String pageNodeList) {
		this.pageNodeList = pageNodeList;
	}
	public String getRoleList() {
		return roleList;
	}
	public void setRoleList(String roleList) {
		this.roleList = roleList;
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
	public String getDepartCD() {
		return departCD;
	}
	public void setDepartCD(String departCD) {
		this.departCD = departCD;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientTag() {
		return clientTag;
	}
	public void setClientTag(String tag) {
		this.clientTag = tag;
	}
	public String getButtonId() {
		return buttonId;
	}
	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}
	public String getReqUri() {
		return reqUri;
	}
	public void setReqUri(String reqUri) {
		this.reqUri = reqUri;
	}

}
