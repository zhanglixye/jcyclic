package com.kaiwait.transfer.file.task;

import java.io.File;

/**
 * 加解密接口
 *
 */
public interface IEncrypt {
	/**
	 * 文件加密
	 */
	File encryptFile(File srcFile, File destFolder) throws Exception;

	/**
	 * 文件解密
	 */
	File decryptFile(File srcFile, File destFolder) throws Exception;
}
