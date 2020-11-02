package com.kaiwait.common.vo.json.web;

public class ResponseMeta {
	/**  服务端生成的请求的唯一标识*/
	private String requestId;
	/** 消息响应的时间戳*/
	private Long timestamp;
	/** 总耗时(毫秒)*/
	private Long useTimeMs;
	/** 响应码(00:正常,01:服务异常,02:权限异常,03:校验异常)*/
	private String httpStatusCode;
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Long getUseTimeMs() {
		return useTimeMs;
	}
	public void setUseTimeMs(Long useTimeMs) {
		this.useTimeMs = useTimeMs;
	}
	public String getHttpStatusCode() {
		return httpStatusCode;
	}
	public void setHttpStatusCode(String responseCode) {
		this.httpStatusCode = responseCode;
	}

	
}
