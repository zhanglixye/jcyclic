package com.kaiwait.transfer.file.task;

import java.io.File;

/**
 * 压缩、解压缩处理接口
 *
 */
public interface ICompression {
	/**
	 * 压缩文件
	 */
	File compressFile(File srcFile, File destFolder) throws Exception;

	/**
	 * 解压缩文件
	 */
	File decompressionFile(File srcFile, File destFolder) throws Exception;
}
