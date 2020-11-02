package com.kaiwait.common.exception;

public class BusinessException extends BaseRuntimeException {


    /**
	 * 
	 */
	private static final long serialVersionUID = -4774127773438576720L;

	public BusinessException() {
    	
    }
	public BusinessException(String message, Object data) {
		super(message, data);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message, String errorCode, Object data) {
		super(message, errorCode, data);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message, String errorCode) {
		super(message, errorCode);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message, Throwable cause, Object data) {
		super(message, cause, data);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message, Throwable cause, String errorCode, Object data) {
		super(message, cause, errorCode, data);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message, Throwable cause, String errorCode) {
		super(message, cause, errorCode);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

   
}
