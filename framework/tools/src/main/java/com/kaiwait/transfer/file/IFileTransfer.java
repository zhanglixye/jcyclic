package com.kaiwait.transfer.file;

import java.io.File;
import java.util.List;

import com.kaiwait.transfer.exception.TransferException;

/**
 * 文件传输接口
 * 
 *
 */
public interface IFileTransfer {
	/** 文件传输方式(scp) */
	String TRANSFER_TYPE_SCP = "SCP";
	/** 文件传输方式(sftp) */
	String TRANSFER_TYPE_SFTP = "SFTP";
	/** 文件传输方式(ftp) */
	String TRANSFER_TYPE_FTP = "FTP";
	/** 文件压缩方式(tar.gz) */
	String COMPRESSION_TYPE_TAR_GZ = "TARGZ";
	/** 文件压缩方式(tar) */
	String COMPRESSION_TYPE_TAR = "TAR";
	/** 文件压缩方式(zip) */
	String COMPRESSION_TYPE_ZIP = "ZIP";
	/** 文件加密方式(gpg) */
	String ENCRYPT_TYPE_GPG = "GPG";

	enum RemoteFileType {
		file, directory, other;
	}

	/**
	 * 上传文件
	 * 
	 *            需要上传的文件对象
	 *            远程文件的相对路径(相对于TransferSetting中配置的远程根目录)<br/>
	 *            如果传null值,将上传到根目录.
	 *             远程服务器无法连接或者文件上传过程中发生的异常
	 */
	void upload(File targetFile, String remoteFilePath) throws TransferException;

	/**
	 * 下载文件
	 * 
	 *            远程文件的相对路径(相对于TransferSetting中配置的远程根目录)<br/>
	 *            如果传null值,将返回远程根目录下所有文件.
	 *             远程服务器无法连接或者文件下载过程中发生的异常
	 */
	File download(String remoteFilePath) throws TransferException;

	/**
	 * 获取远程文件列表
	 * 
	 *            远程文件的相对路径(相对于TransferSetting中配置的远程根目录)<br/>
	 *            如果传null值,将返回远程根目录下所有文件.
	 *             远程服务器无法连接,或者remoteFilePath参数指定的路径不存在
	 */
	List<RemoteFile> getRemoteFileList(String remoteFilePath) throws TransferException;

	/**
	 * 确定远程文件是否存在
	 * 
	 *            远程文件的相对路径(相对于TransferSetting中配置的远程根目录)<br/>
	 *            如果传null值,将判断远程根目录.
	 *         false:remoteFilePath参数指定的远程文件不存在
	 */
	boolean remoteFileExist(String remoteFilePath);

	/**
	 * 取得传输类型
	 * 
	 */
	String getTransferType();

}
