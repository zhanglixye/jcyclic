/**
 * 
 */
package com.kaiwait.common.exception;


public class BaseRuntimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9052341491353981297L;
	private String errorCode;
	private Object data;

	public BaseRuntimeException() {
		
	}
	public BaseRuntimeException(String message) {
		super(message);
	}

	public BaseRuntimeException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BaseRuntimeException(String message, Object data) {
		super(message);
		this.data = data;
	}

	public BaseRuntimeException(String message, String errorCode, Object data) {
		super(message);
		this.errorCode = errorCode;
		this.data = data;
	}

	public BaseRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseRuntimeException(String message, Throwable cause, String errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public BaseRuntimeException(String message, Throwable cause, Object data) {
		super(message, cause);
		this.data = data;
	}

	public BaseRuntimeException(String message, Throwable cause, String errorCode, Object data) {
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
