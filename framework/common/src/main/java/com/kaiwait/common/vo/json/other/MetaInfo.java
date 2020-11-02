/**
 * 
 */
package com.kaiwait.common.vo.json.other;

import java.util.Map;

import com.kaiwait.common.vo.json.base.Result;

public class MetaInfo {
	
	private Result result;

	private String message;

	private String dataType;

	private Map<String, String> attachInfo;
	
	/** 用户标识*/
	private String custId;
	/** 机构标识，用于识别要访问那个机构的数据*/
	private String orgId;
	
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
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

}
