package com.kaiwait.core.process;

import com.kaiwait.common.constants.MsgCodeConstant;

public class ValidateResult {
	private String errorCode;
	private String errorMessage;
	public ValidateResult() {
		
	}
	public ValidateResult(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public static ValidateResult genSysErrorResult() {
		return new ValidateResult(MsgCodeConstant.ERR_SYS);
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
