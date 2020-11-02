package com.kaiwait.transfer.exception;

/**
 * 文件传输异常
 *
 */
public class TransferException extends Exception {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -4549353259031440828L;

	/**
	 * 构造函数
	 * 
	 *            错误消息
	 */
	public TransferException(String message) {
		super("文件传输过程中发生了异常:" + message);
	}

	/**
	 * 构造函数
	 * 
	 *            异常堆栈
	 */
	public TransferException(Throwable cause) {
		super("文件传输过程中发生了异常:", cause);
	}

	/**
	 * 构造函数
	 * 
	 *            错误消息
	 *            异常堆栈
	 */
	public TransferException(String message, Throwable cause) {
		super("文件传输过程中发生了异常:" + message, cause);
	}

}
