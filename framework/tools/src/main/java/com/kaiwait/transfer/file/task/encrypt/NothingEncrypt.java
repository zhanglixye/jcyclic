package com.kaiwait.transfer.file.task.encrypt;

import java.io.File;

import com.kaiwait.transfer.file.task.IEncrypt;

/**
 * 什么也不做的加解密处理
 *
 */
public class NothingEncrypt implements IEncrypt {

	/* (non-Javadoc)
	 */
	@Override
	public File encryptFile(File srcFile, File destFile) {
		return srcFile;
	}

	/* (non-Javadoc)
	 */
	@Override
	public File decryptFile(File srcFile, File destFile) {
		return srcFile;
	}

}
