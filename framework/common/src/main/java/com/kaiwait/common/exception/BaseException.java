/**
 * 
 */
package com.kaiwait.common.exception;


public class BaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4577948674048554850L;
	private String errorCode;
	private Object data;

	public BaseException() {
		
	}
	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BaseException(String message, Object data) {
		super(message);
		this.data = data;
	}

	public BaseException(String message, String errorCode, Object data) {
		super(message);
		this.errorCode = errorCode;
		this.data = data;
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message, Throwable cause, String errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public BaseException(String message, Throwable cause, Object data) {
		super(message, cause);
		this.data = data;
	}

	public BaseException(String message, Throwable cause, String errorCode, Object data) {
		super(message, cause);
		this.data = data;
		this.errorCode = errorCode;
	}

	public Object getData() {
		return data;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
