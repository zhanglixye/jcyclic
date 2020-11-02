package com.kaiwait.common.exception;

import com.kaiwait.common.constants.HttpStatus;

public class HttpTransferException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1763343715938355861L;
	/** http状态码 */
	private HttpStatus httpStatus;
	/** 请求是否已经发出 */
	private boolean sent;

	/**
	 * 构造函数
	 * 
	 * @param sent
	 *            请求是否已经发出
	 * @param httpStatus
	 *            http状态码
	 * @param message
	 *            错误消息
	 * @param cause
	 *            异常堆栈
	 */
	public HttpTransferException(boolean sent, HttpStatus httpStatus, String message, Throwable cause) {
		super(message + "  请求是否已经发出:" + sent + "  Http状态码:" + httpStatus.stringValue(), cause);
		this.sent = sent;
		this.httpStatus = httpStatus;
	}

	/**
	 * 构造函数
	 * 
	 * @param sent
	 *            请求是否已经发出
	 * @param httpStatus
	 *            http状态码
	 * @param message
	 *            错误消息
	 */
	public HttpTransferException(boolean sent, HttpStatus httpStatus, String message) {
		super(message + "  请求是否已经发出:" + sent + "  Http状态码:" + httpStatus.stringValue());
		this.sent = sent;
		this.httpStatus = httpStatus;
	}

	/**
	 * 取得Http状态码
	 * 
	 * @return Http状态码
	 */
	public int getHttpStatusCode() {
		return httpStatus.intValue();
	}

	/**
	 * 取得Http状态码
	 * 
	 * @return Http状态码
	 */
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	public boolean isSent() {
		return sent;
	}

}
