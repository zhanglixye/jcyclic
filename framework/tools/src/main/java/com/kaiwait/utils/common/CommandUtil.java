package com.kaiwait.utils.common;

import java.io.File;
import java.io.IOException;

/**
 * 运行外部命令的共通工具类
 *
 */
public class CommandUtil {
	/** 命令成功执行的返回值*/
	public static final int RESULT_SUCCESS = 0;

	/**
	 * 运行外部命令
	 * 
	 *            命令行
	 *            运行时相对路径,命令需保存在该路径下,可以为null
	 *             命令执行过程中可能会发生的异常
	 * 
	 */

	public static int execCommand(String commandLine, String libPath) throws IOException {
		int returnValue = 1;
		File dir = null;
		if (libPath != null && !"".equals(libPath)) {
			dir = new File(libPath);
		}
		Process process = Runtime.getRuntime().exec(commandLine, null, dir);
		try {
			// 等待命令执行完成
			returnValue = process.waitFor();
		} catch (InterruptedException e) {
			// 命令执行完成时,抛出该异常使得主线程结束等待
			// 该异常不需要处理
		}
		return returnValue;

	}

}
