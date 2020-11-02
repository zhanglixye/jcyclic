package com.kaiwait.transfer.file.executor;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kaiwait.transfer.exception.TransferException;
import com.kaiwait.transfer.file.IFileTransfer;
import com.kaiwait.transfer.file.TransferParameter;
import com.kaiwait.transfer.file.task.ICompression;
import com.kaiwait.transfer.file.task.IEncrypt;
import com.kaiwait.transfer.file.task.compression.NothingComperssion;
import com.kaiwait.transfer.file.task.encrypt.NothingEncrypt;
import com.kaiwait.utils.common.DateTimeUtil;

/**
 * 文件传输入的共通基类,完成传输入前数据准备,传输后数据备份等基本功能
 * 
 *
 */
public abstract class AbstractBaseExecutor implements IFileTransfer {
	private static final Log LOGGER = LogFactory.getLog(AbstractBaseExecutor.class);

	/** 重试次数 */
	private Integer retryCount = 3;
	/** 重试间隔(秒) */
	private Integer retrySpan = 60;
	/** 连接超时时间(秒) */
	private Integer connectTimeout = 120;
	/** 传输超时时间(秒) */
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
	/** 是否需要备份源文件 */
	private boolean backSrcFile = true;
	/** 本地工作文件夹路径 */
	private String localWorkFolderPath;
	/** 文件压缩处理器实例, 不设定代表不需要压缩和解压缩 */
	private ICompression compressionProcesser = new NothingComperssion();
	/** 文件加密处理器实例,不设定代表不需要加密解密 */
	private IEncrypt encryptProcesser = new NothingEncrypt();

	/*
	 * (non-Javadoc)
	 * 
	 * java.lang.String)
	 */
	@Override
	public void upload(File targetFile, String remoteFilePath) throws TransferException {
		StringBuilder messageBuffer = new StringBuilder();
		messageBuffer.append("[传输方式:");
		messageBuffer.append(getTransferType());
		messageBuffer.append(" 本地文件:");
		messageBuffer.append(targetFile.getAbsolutePath());
		messageBuffer.append(" 远程地址:");
		messageBuffer.append(transferHostName);
		messageBuffer.append(":");
		messageBuffer.append(transferPort);
		messageBuffer.append("/");
		messageBuffer.append(remoteUploadFolderPath);
		messageBuffer.append("]");

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("文件上传开始," + messageBuffer.toString());
		}

		try {
			LOGGER.debug("创建工作目录开始");
			TransferParameter transferParameter = new TransferParameter();
			makeUploadWorkDir(transferParameter);
			LOGGER.debug("创建工作目录结束");

			if (isBackSrcFile()) {
				LOGGER.debug("备份源文件开始");
				backupUpdateFile(targetFile, transferParameter);
				LOGGER.debug("备份源文件结束");
			}

			LOGGER.debug("文件压缩开始");
			File compressFile = compressFile(targetFile, transferParameter);
			LOGGER.debug("文件压缩结束");

			LOGGER.debug("文件加密开始");
			File encryptFile = encryptFile(compressFile, transferParameter);
			LOGGER.debug("文件加密结束");

			LOGGER.debug("文件传输开始");
			updateFile(encryptFile, remoteFilePath, transferParameter);
			LOGGER.debug("文件传输结束");

			LOGGER.info("文件上传成功");
		} catch (TransferException e) {
			LOGGER.error("文件上传失败," + messageBuffer.toString(), e);
			throw e;
		} catch (Throwable e) {
			LOGGER.error("文件上传失败," + messageBuffer.toString(), e);
			throw new TransferException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * com.com.kaiwait.transfer.file.IFileTransfer#download(java.lang.String)
	 */
	@Override
	public File download(String remoteFilePath) throws TransferException {
		StringBuilder messageBuffer = new StringBuilder();
		messageBuffer.append("[传输方式:");
		messageBuffer.append(getTransferType());
		messageBuffer.append(" 远程地址:");
		messageBuffer.append(transferHostName);
		messageBuffer.append(":");
		messageBuffer.append(transferPort);
		messageBuffer.append("/");
		messageBuffer.append(remoteDownloadFolderPath);
		messageBuffer.append("/");
		messageBuffer.append(remoteFilePath);
		messageBuffer.append("]");

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("文件下载开始," + messageBuffer.toString());
		}

		try {
			LOGGER.debug("创建工作目录开始");
			TransferParameter transferParameter = new TransferParameter();
			makeDownloadWorkDir(transferParameter);
			LOGGER.debug("创建工作目录结束");

			LOGGER.debug("文件传输开始");
			File downloadFile = downloadFile(remoteFilePath, transferParameter);
			LOGGER.debug("文件传输结束");

			if (isBackSrcFile()) {
				LOGGER.debug("备份源文件开始");
				backupDownloadFile(downloadFile, transferParameter);
				LOGGER.debug("备份源文件结束");
			}

			LOGGER.debug("文件解密开始");
			File decryptFile = decryptFile(downloadFile, transferParameter);
			LOGGER.debug("文件解密结束");

			LOGGER.debug("文件解压开始");
			File decompressFile = decompressionFile(decryptFile, transferParameter);
			LOGGER.debug("文件解压结束");

			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("文件下载成功,文件保存路径:" + decompressFile.getAbsolutePath());
			}
			return decompressFile;

		} catch (TransferException e) {
			LOGGER.error("文件下载失败," + messageBuffer.toString(), e);
			throw e;
		} catch (Throwable e) {
			LOGGER.error("文件下载失败," + messageBuffer.toString(), e);
			throw new TransferException(e);
		}
	}

	/**
	 * 文件上传
	 * 
	 *            源文件
	 *            远程文件的相对路径(相对于TransferSetting中配置的远程根目录)<br/>
	 *            如果传null值,将上传到根目录.
	 *             包含了上传过程中可能会发生的所有异常
	 */
	protected abstract void updateFile(File srcFile, String remoteFilePath, TransferParameter transferParameter) throws TransferException;

	/**
	 * 文件下载
	 * 
	 *            远程目标文件路径
	 *             包含了下载过程中可能会发生的所有异常
	 */
	protected abstract File downloadFile(String targetFilePath, TransferParameter transferParameter) throws TransferException;

	/**
	 * 上传前备份源文件
	 * 
	 *            源文件
	 *             包含了备份过程中可能会发生的所有异常
	 */
	private void backupUpdateFile(File srcFile, TransferParameter transferParameter) throws TransferException {
		try {
			FileUtils.copyFileToDirectory(srcFile, transferParameter.localUploadBackWorkFolder);
		} catch (Throwable e) {
			throw new TransferException(e);
		}
	}

	/**
	 * 下载后备份源文件
	 * 
	 *            源文件
	 *             包含了备份过程中可能会发生的所有异常
	 */
	private void backupDownloadFile(File srcFile, TransferParameter transferParameter) throws TransferException {
		try {
			if (srcFile.isDirectory()) {
				FileUtils.copyDirectoryToDirectory(srcFile, transferParameter.localDownloadBackWorkFolder);
			} else {
				FileUtils.copyFileToDirectory(srcFile, transferParameter.localDownloadBackWorkFolder);
			}
		} catch (Throwable e) {
			throw new TransferException(e);
		}
	}

	/**
	 * 文件压缩
	 * 
	 *            源文件
	 *             包含了压缩过程中可能会发生的所有异常
	 */
	private File compressFile(File srcFile, TransferParameter transferParameter) throws TransferException {
		try {
			return compressionProcesser.compressFile(srcFile, transferParameter.localCompressionWorkFolder);
		} catch (Throwable e) {
			throw new TransferException("文件压缩失败", e);
		}
	}

	/**
	 * 文件解压缩
	 * 
	 *            压缩文件
	 *             包含了解压缩过程中可能会发生的所有异常
	 */
	private File decompressionFile(File srcFile, TransferParameter transferParameter) throws TransferException {
		try {
			return compressionProcesser.decompressionFile(srcFile, transferParameter.localDecompressionWorkFolder);
		} catch (Throwable e) {
			throw new TransferException("文件解压缩失败", e);
		}
	}

	/**
	 * 文件加密
	 * 
	 *            源文件
	 *             包含了加密过程中可能会发生的所有异常
	 */
	private File encryptFile(File srcFile, TransferParameter transferParameter) throws TransferException {
		try {
			return encryptProcesser.encryptFile(srcFile, transferParameter.localEncryptWorkFolder);
		} catch (Throwable e) {
			throw new TransferException("文件加密失败", e);
		}
	}

	/**
	 * 文件解密
	 * 
	 *            需要解密的文件
	 *             包含了解密过程中可能会发生的所有异常
	 */
	private File decryptFile(File srcFile, TransferParameter transferParameter) throws TransferException {
		try {
			return encryptProcesser.decryptFile(srcFile, transferParameter.localDecryptWorkFolder);
		} catch (Throwable e) {
			throw new TransferException("文件解密失败", e);
		}
	}

	/**
	 * 构建上传用工作目录<br/>
	 * root <--用户在外部配置文件中指定的根目录<br/>
	 * |----upload 固定值<br/>
	 * |-------yyyyMMddHHmmssms 当前系统时间<br/>
	 * |----------bak 保存备份文件<br/>
	 * |----------compression 保存压缩文件<br/>
	 * |----------encrypt 保存加密文件<br/>
	 * |-------yyyyMMddHHmmssms_0001 当前系统时间，重复时0001~9999编号<br/>
	 * |----------bak 保存备份文件<br/>
	 * |----------compression 保存压缩文件<br/>
	 * |----------encrypt 保存加密文件<br/>
	 * 
	 * 
	 *             包含了创建过程中发生的所有异常
	 */
	private synchronized void makeUploadWorkDir(TransferParameter transferParameter) throws TransferException {

		try {
			String workDirName = DateTimeUtil.getDateByFormat("yyyyMMddHHmmss", new Date());
			transferParameter.localUploadWorkFolder = new File(this.localWorkFolderPath + "/upload/" + workDirName);
			int i = 1;
			while (transferParameter.localUploadWorkFolder.exists()) {
				if (i > 9999) {
					throw new TransferException("创建工作目录失败");
				}
				i++;
				transferParameter.localUploadWorkFolder = new File(workDirName + "_" + String.format("%04d", i));
			}
			if (!transferParameter.localUploadWorkFolder.mkdirs()) {
				throw new TransferException("创建工作目录失败");
			}
			transferParameter.localUploadBackWorkFolder = new File(transferParameter.localUploadWorkFolder.getAbsolutePath() + "/bak");
			if (!transferParameter.localUploadBackWorkFolder.mkdirs()) {
				throw new TransferException("创建备份目录失败");
			}
			transferParameter.localCompressionWorkFolder = new File(transferParameter.localUploadWorkFolder.getAbsolutePath() + "/compression");
			if (!transferParameter.localCompressionWorkFolder.mkdirs()) {
				throw new TransferException("创建压缩目录失败");
			}
			transferParameter.localEncryptWorkFolder = new File(transferParameter.localUploadWorkFolder.getAbsolutePath() + "/encrypt");
			if (!transferParameter.localEncryptWorkFolder.mkdirs()) {
				throw new TransferException("创建加密目录失败");
			}
		} catch (TransferException e) {
			transferParameter.localUploadWorkFolder = null;
			throw e;
		} catch (Throwable e) {
			transferParameter.localUploadWorkFolder = null;
			throw new TransferException(e);
		}

	}

	/**
	 * 构建下载用工作目录<br/>
	 * root <--用户在外部配置文件中指定的根目录<br/>
	 * |----download 固定值<br/>
	 * |--------yyyyMMddHHmmssms 当前系统时间<br/>
	 * |------------bak 保存备份文件<br/>
	 * |------------decompression 保存解压缩文件<br/>
	 * |------------decrypt 保存解密文件<br/>
	 * |--------yyyyMMddHHmmssms_0001 当前系统时间，重复时0001~9999编号<br/>
	 * |------------bak 保存备份文件<br/>
	 * |------------decompression 保存解压缩文件<br/>
	 * |------------decrypt 保存解密文件<br/>
	 * 
	 */
	private synchronized void makeDownloadWorkDir(TransferParameter transferParameter) throws TransferException {

		try {
			String workDirName = DateTimeUtil.getDateByFormat("yyyyMMddHHmmss", new Date());
			transferParameter.localDownloadWorkFolder = new File(this.localWorkFolderPath + "/download/" + workDirName);
			int i = 0;
			while (transferParameter.localDownloadWorkFolder.exists()) {
				if (i > 9999) {
					throw new TransferException("创建工作目录失败");
				}
				i++;
				transferParameter.localDownloadWorkFolder = new File(workDirName + "_" + String.format("%04d", i));
			}
			if (!transferParameter.localDownloadWorkFolder.mkdirs()) {
				throw new TransferException("创建工作目录失败");
			}
			transferParameter.localDownloadBackWorkFolder = new File(transferParameter.localDownloadWorkFolder.getAbsolutePath() + "/bak");
			if (!transferParameter.localDownloadBackWorkFolder.mkdirs()) {
				throw new TransferException("创建备份目录失败");
			}
			transferParameter.localDecompressionWorkFolder = new File(transferParameter.localDownloadWorkFolder.getAbsolutePath() + "/decompression");
			if (!transferParameter.localDecompressionWorkFolder.mkdirs()) {
				throw new TransferException("创建解压缩目录失败");
			}
			transferParameter.localDecryptWorkFolder = new File(transferParameter.localDownloadWorkFolder.getAbsolutePath() + "/decrypt");
			if (!transferParameter.localDecryptWorkFolder.mkdirs()) {
				throw new TransferException("创建解密目录失败");
			}
		} catch (TransferException e) {
			transferParameter.localDownloadWorkFolder = null;
			throw e;
		} catch (Throwable e) {
			transferParameter.localDownloadWorkFolder = null;
			throw new TransferException(e);
		}
	}

	/**
	 * 取得[是否需要备份源文件]
	 * 
	 */
	public boolean isBackSrcFile() {
		return backSrcFile;
	}

	/**
	 * 设置[是否需要备份源文件]
	 * 
	 *            true:需要备份源文件,false:不需要备份源文件
	 */
	public void setBackSrcFile(boolean backSrcFile) {
		this.backSrcFile = backSrcFile;
	}

	/**
	 * 取得[重试次数]
	 * 
	 */
	public Integer getRetryCount() {
		return retryCount;
	}

	/**
	 * 设置[重试次数]
	 * 
	 *            重试次数
	 */
	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	/**
	 * 取得[重试间隔(秒)]
	 * 
	 */
	public Integer getRetrySpan() {
		return retrySpan;
	}

	/**
	 * 设置[重试间隔(秒)]
	 * 
	 *            重试间隔(秒)
	 */
	public void setRetrySpan(Integer retrySpan) {
		this.retrySpan = retrySpan;
	}

	/**
	 * 取得[连接超时时间(秒)]
	 * 
	 */
	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * 设置[连接超时时间(秒)]
	 * 
	 *            连接超时时间(秒)
	 */
	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * 取得[传输超时时间(秒)]
	 * 
	 */
	public Integer getTransferTimeout() {
		return transferTimeout;
	}

	/**
	 * 设置[传输超时时间(秒)]
	 * 
	 *            传输超时时间(秒)
	 */
	public void setTransferTimeout(Integer transferTimeout) {
		this.transferTimeout = transferTimeout;
	}

	/**
	 * 取得[远程主机名]
	 * 
	 */
	public String getTransferHostName() {
		return transferHostName;
	}

	/**
	 * 设置[远程主机名]
	 * 
	 *            远程主机名
	 */
	public void setTransferHostName(String transferHostName) {
		this.transferHostName = transferHostName;
	}

	/**
	 * 取得[远程端口]
	 * 
	 */
	public Integer getTransferPort() {
		return transferPort;
	}

	/**
	 * 设置[远程端口]
	 * 
	 *            远程端口
	 */
	public void setTransferPort(Integer transferPort) {
		this.transferPort = transferPort;
	}

	/**
	 * 取得[远程登录用户名]
	 * 
	 */
	public String getTransferUserName() {
		return transferUserName;
	}

	/**
	 * 设置[远程登录用户名]
	 * 
	 *            远程登录用户名
	 */
	public void setTransferUserName(String transferUserName) {
		this.transferUserName = transferUserName;
	}

	/**
	 * 取得[远程登录密码]
	 * 
	 */
	public String getTransferPassword() {
		return transferPassword;
	}

	/**
	 * 设置[远程登录密码]
	 * 
	 *            远程登录密码
	 */
	public void setTransferPassword(String transferPassword) {
		this.transferPassword = transferPassword;
	}

	/**
	 * 取得[远程上传文件夹路径]
	 * 
	 */
	public String getRemoteUploadFolderPath() {
		return remoteUploadFolderPath;
	}

	/**
	 * 设置[远程上传文件夹路径]
	 * 
	 *            远程上传文件夹路径
	 */
	public void setRemoteUploadFolderPath(String remoteUploadFolderPath) {
		this.remoteUploadFolderPath = remoteUploadFolderPath;
	}

	/**
	 * 取得[远程下载文件夹路径]
	 * 
	 */
	public String getRemoteDownloadFolderPath() {
		return remoteDownloadFolderPath;
	}

	/**
	 * 设置[远程下载文件夹路径]
	 * 
	 *            远程下载文件夹路径
	 */
	public void setRemoteDownloadFolderPath(String remoteDownloadFolderPath) {
		this.remoteDownloadFolderPath = remoteDownloadFolderPath;
	}

	/**
	 * 取得[本地工作文件夹路径]
	 * 
	 */
	public String getLocalWorkFolderPath() {
		return localWorkFolderPath;
	}

	/**
	 * 设置[本地工作文件夹路径]
	 * 
	 *            本地工作文件夹路径
	 */
	public void setLocalWorkFolderPath(String localWorkFolderPath) {
		this.localWorkFolderPath = localWorkFolderPath;
	}

	/**
	 * 取得[文件压缩方式, 不设定代表不需要压缩和解压缩]
	 * 
	 */
	public ICompression getCompressionProcesser() {
		return compressionProcesser;
	}

	/**
	 * 设置[文件压缩方式]
	 * 
	 *            文件压缩方式
	 */
	public void setCompressionProcesser(ICompression compressionProcesser) {
		this.compressionProcesser = compressionProcesser;
	}

	/**
	 * 取得[加密方式,不设定代表不需要加密解密]
	 * 
	 */
	public IEncrypt getEncryptProcesser() {
		return encryptProcesser;
	}

	/**
	 * 设置[加密方式,不设定代表不需要加密解密]
	 * 
	 *            加密方式
	 */
	public void setEncryptProcesser(IEncrypt encryptProcesser) {
		this.encryptProcesser = encryptProcesser;
	}

}
