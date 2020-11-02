package com.kaiwait.transfer.file;

import com.kaiwait.transfer.file.task.ICompression;
import com.kaiwait.transfer.file.task.IEncrypt;
import com.kaiwait.transfer.file.task.compression.NothingComperssion;
import com.kaiwait.transfer.file.task.encrypt.NothingEncrypt;

/**
 * 文件传输用的设定
 * 
 *
 */
public class TransferSetting {

	/** 重试次数,默认3次 */
	private Integer retryCount = 3;
	/** 重试间隔(秒)，默认60秒 */
	private Integer retrySpan = 60;
	/** 连接超时时间(秒) ，默认120秒*/
	private Integer connectTimeout = 120;
	/** 传输超时时间(秒)，默认360秒 */
	private Integer transferTimeout = 360;
	/** 远程主机名 */
	private String transferHostName;
	/** 远程端口 */
	private Integer transferPort;
	/** 远程登录用户名 */
	private String transferUserName;
	/** 远程登录密码 */
	private String transferPassword;
	/** 远程上传文件夹路径 */
	private String remoteUploadFolderPath;
	/** 远程下载文件夹路径 */
	private String remoteDownloadFolderPath;
	/** 是否需要备份源文件，默认true */
	private boolean backSrcFile = true;
	/** 本地工作文件夹路径 */
	private String localWorkFolderPath;
	
	/** 文件压缩处理器类, 不设定代表不需要压缩和解压缩 */
	private ICompression compressionProcesser = new NothingComperssion();
	
	/** 文件加密处理器类的全路径,不设定代表不需要加密解密 */
	private IEncrypt encryptProcesser = new NothingEncrypt();

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public Integer getRetrySpan() {
		return retrySpan;
	}

	public void setRetrySpan(Integer retrySpan) {
		this.retrySpan = retrySpan;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getTransferTimeout() {
		return transferTimeout;
	}

	public void setTransferTimeout(Integer transferTimeout) {
		this.transferTimeout = transferTimeout;
	}

	public String getTransferHostName() {
		return transferHostName;
	}

	public void setTransferHostName(String transferHostName) {
		this.transferHostName = transferHostName;
	}

	public Integer getTransferPort() {
		return transferPort;
	}

	public void setTransferPort(Integer transferPort) {
		this.transferPort = transferPort;
	}

	public String getTransferUserName() {
		return transferUserName;
	}

	public void setTransferUserName(String transferUserName) {
		this.transferUserName = transferUserName;
	}

	public String getTransferPassword() {
		return transferPassword;
	}

	public void setTransferPassword(String transferPassword) {
		this.transferPassword = transferPassword;
	}

	public String getRemoteUploadFolderPath() {
		return remoteUploadFolderPath;
	}

	public void setRemoteUploadFolderPath(String remoteUploadFolderPath) {
		this.remoteUploadFolderPath = remoteUploadFolderPath;
	}

	public String getRemoteDownloadFolderPath() {
		return remoteDownloadFolderPath;
	}

	public void setRemoteDownloadFolderPath(String remoteDownloadFolderPath) {
		this.remoteDownloadFolderPath = remoteDownloadFolderPath;
	}

	public boolean isBackSrcFile() {
		return backSrcFile;
	}

	public void setBackSrcFile(boolean backSrcFile) {
		this.backSrcFile = backSrcFile;
	}

	public String getLocalWorkFolderPath() {
		return localWorkFolderPath;
	}

	public void setLocalWorkFolderPath(String localWorkFolderPath) {
		this.localWorkFolderPath = localWorkFolderPath;
	}

	public ICompression getCompressionProcesser() {
		return compressionProcesser;
	}

	public void setCompressionProcesser(ICompression compressionProcesser) {
		this.compressionProcesser = compressionProcesser;
	}

	public IEncrypt getEncryptProcesser() {
		return encryptProcesser;
	}

	public void setEncryptProcesser(IEncrypt encryptProcesser) {
		this.encryptProcesser = encryptProcesser;
	}

}
