package com.kaiwait.transfer.file.task.compression;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import com.kaiwait.transfer.file.task.ICompression;
import com.kaiwait.utils.common.CommandUtil;
import com.kaiwait.utils.common.StringUtil;

/**
 * gzip压缩解压缩
 * 
 *
 */
public class TarGzComperssion implements ICompression {

	/** 压缩后文件名,如果不设定该属性,压缩后的文件名与源文件名相同 */
	private String destFileName;

	/*
	 * (non-Javadoc)
	 * 
	 * com.com.kaiwait.transfer.file.task.ICompression#compressFile(java.io.
	 * File, java.io.File)
	 */
	@Override
	public File compressFile(File srcFile, File destFolder) throws Exception {
		StringBuilder commandBuffer = new StringBuilder(256);
		// 生成压缩文件路径
		StringBuilder comperssFilePath = new StringBuilder(128);
		comperssFilePath.append(destFolder.getAbsolutePath());
		comperssFilePath.append("/");
		if (StringUtil.isNotEmpty(destFileName)) {
			comperssFilePath.append(destFileName);
		} else {
			comperssFilePath.append(FilenameUtils.getBaseName(srcFile.getName()));
			comperssFilePath.append(".tar.gz");
		}
		// 构造压缩命令 tar -zcvf $comperssFilePath $srcFileName
		commandBuffer.append("tar -zcvf ");
		commandBuffer.append(" ");
		commandBuffer.append(comperssFilePath);
		commandBuffer.append(" ");
		commandBuffer.append(srcFile.getName());
		// 命令执行目录
		String commandExecDir = FilenameUtils.getFullPath(srcFile.getAbsolutePath());

		// 在指定目录下执行命令
		int execCommand = CommandUtil.execCommand(commandBuffer.toString(), commandExecDir);
		if (execCommand != CommandUtil.RESULT_SUCCESS) {
			throw new Exception("文件压缩失败");
		}
		return new File(comperssFilePath.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * com.com.kaiwait.transfer.file.task.ICompression#decompressionFile(java.io
	 * .File, java.io.File)
	 */
	@Override
	public File decompressionFile(File srcFile, File destFolder) throws Exception {
		// 构造解压缩命令 tar -zxvf  -C $decomperssFilePath $srcFileName
		StringBuilder commandBuffer = new StringBuilder(128);
		commandBuffer.append("tar -zxvf ");
		commandBuffer.append(srcFile.getAbsolutePath());
		commandBuffer.append(" -C ");
		commandBuffer.append(destFolder.getAbsolutePath());
		int execCommand = CommandUtil.execCommand(commandBuffer.toString(), null);
		if (execCommand != CommandUtil.RESULT_SUCCESS) {
			throw new Exception("文件解压缩失败");
		}
		return destFolder;
	}

	/**
	 * 取得压缩后文件名
	 * 
	 */
	public String getDestFileName() {
		return destFileName;
	}

	/**
	 * 设置压缩后文件名
	 * 
	 *            压缩后文件名
	 */
	public void setDestFileName(String destFileName) {
		this.destFileName = destFileName;
	}

}
