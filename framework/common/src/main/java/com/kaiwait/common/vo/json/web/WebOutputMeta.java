/**
 * 
 */
package com.kaiwait.common.vo.json.web;

import com.kaiwait.common.vo.json.base.MessageMeta;
import com.kaiwait.common.vo.json.base.Result;

public class WebOutputMeta extends MessageMeta {

	private Result result;

	private String message;

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

}
