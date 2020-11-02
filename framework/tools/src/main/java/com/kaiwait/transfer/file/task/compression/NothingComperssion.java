package com.kaiwait.transfer.file.task.compression;

import java.io.File;

import com.kaiwait.transfer.file.task.ICompression;

/**
 * 什么也不做的压缩解压缩
 *
 */
public class NothingComperssion implements ICompression {

	
	/* (non-Javadoc)
	 */
	@Override
	public File compressFile(File srcFile, File destFile) {
		return srcFile;
	}

	/* (non-Javadoc)
	 */
	@Override
	public File decompressionFile(File srcFile, File destFile) {
		return srcFile;
	}

	

}
