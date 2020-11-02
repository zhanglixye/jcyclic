/**
 * 
 */
package com.kaiwait.common.vo.json.server;

import java.util.Map;

import com.kaiwait.common.vo.json.base.MessageMeta;
import com.kaiwait.common.vo.json.base.Result;

public class OutputMeta extends MessageMeta {

	private Result result;

	private String message;

	private String dataType;

	private Map<String, String> attachInfo;

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

}
