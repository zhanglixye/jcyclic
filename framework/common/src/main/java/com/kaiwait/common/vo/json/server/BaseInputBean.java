package com.kaiwait.common.vo.json.server;

import java.io.Serializable;
import java.util.Map;

public class BaseInputBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1428236028040710930L;

	/** 扩展用*/
	private Map<String, String> attachInfo;
	/** 用户标识 */
	private String custId;
	/** 机构标识，用于识别要访问那个机构的数据 */
	private String orgId;
	/** 请求的唯一标识,用于区分每个请求调用*/
	private String requestId;
	/**用户ID*/
	private String userID;
	/**公司ID*/
	private String companyID;
	
	
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
	public Map<String, String> getAttachInfo() {
		return attachInfo;
	}
	public void setAttachInfo(Map<String, String> attachInfo) {
		this.attachInfo = attachInfo;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
