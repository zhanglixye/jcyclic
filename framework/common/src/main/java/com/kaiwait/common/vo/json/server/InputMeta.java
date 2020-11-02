/**
 * 
 */
package com.kaiwait.common.vo.json.server;


import com.kaiwait.common.vo.json.base.MessageMeta;

/**
 * @author wings
 *
 */
public class InputMeta extends MessageMeta {
	/**  调用端生成的请求的唯一标识*/
	private String requestId;
	/**  用户标识,如用户未登录,该值为空*/
	private String custId;
	
	/**  会话标识*/
	private String sessionId;
	/** 客户端IP*/
	private String remoteIp;
	/** 客户来源标识*/
	private String orgId;
	/** 要调用的目标服务名*/
	private String targetServiceName;
	/**用户ID*/
	private String userID;
	/**公司ID*/
	private String companyID;
	private String roleList;
	private String pageNodeList;
	
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
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getTargetServiceName() {
		return targetServiceName;
	}
	public void setTargetServiceName(String targetServiceName) {
		this.targetServiceName = targetServiceName;
	}

}
