package com.kaiwait.common.vo.json.server;

import java.io.Serializable;

public class BaseOutputBean implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -598923354771847461L;
	/** 响应码 */
	private String responseCode;
	/** 响应信息 */
	private String responseMsg;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
}
