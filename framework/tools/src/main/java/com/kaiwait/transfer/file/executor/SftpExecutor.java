
package com.kaiwait.transfer.file.executor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.SFTPv3DirectoryEntry;
import ch.ethz.ssh2.SFTPv3FileAttributes;
import ch.ethz.ssh2.SFTPv3FileHandle;
import com.kaiwait.thread.task.IFutureTask;
import com.kaiwait.transfer.exception.TransferException;
import com.kaiwait.transfer.file.IFileTransfer;
import com.kaiwait.transfer.file.RemoteFile;
import com.kaiwait.transfer.file.TransferParameter;
import com.kaiwait.utils.common.StringUtil;
import com.kaiwait.utils.future.impl.FutureTaskHandler;

/**
 * SFTP 方式上传下载文件
 * 
 *
 */
public class SftpExecutor extends AbstractBaseExecutor {
	/** 日志 */
	private static final Log LOGGER = LogFactory.getLog(SftpExecutor.class);
	/** 秒转换为毫秒 */
	private static final int MILLISECONDS = 1000;

	/*
	 * (non-Javadoc)
	 * 
	 * com.com.kaiwait.transfer.file.IFileTransfer#getRemoteFileList(java.lang.
	 * String)
	 */
	@Override
	public List<RemoteFile> getRemoteFileList(String remoteFilePath) throws TransferException {
		String remotePath = getRemoteDownloadFolderPath();
		if (StringUtil.isNotEmpty(remoteFilePath)) {
			remotePath = remotePath + "/" + remoteFilePath;
		}
		Connection conn = null;
		SFTPv3Client sftpClient = null;
		try {
			// 连接远程服务器
			conn = getConnection();
			sftpClient = new SFTPv3Client(conn);
			SFTPv3FileAttributes lstat = sftpClient.lstat(remotePath);
			if (lstat.isDirectory()) {
				// 取得目录下所有文件
				List<SFTPv3DirectoryEntry> ls = sftpClient.ls(remotePath);
				List<RemoteFile> fileNameList = new ArrayList<RemoteFile>(ls.size());
				// 遍历处理每个文件
				for (SFTPv3DirectoryEntry entry : ls) {
					if (".".equals(entry.filename) || "..".equals(entry.filename)) {
						// 跳过当前文件夹和上级文件夹
						continue;
					} else {
						int type = RemoteFile.TYPE_OTHER;
						if (entry.attributes.isDirectory()) {
							type = RemoteFile.TYPE_DIRECTORY;
						} else if (entry.attributes.isRegularFile()) {
							type = RemoteFile.TYPE_FILE;
						}
						fileNameList.add(new RemoteFile(entry.filename, type));
					}
				}
				return fileNameList;
			} else {
				return null;
			}
		} catch (Throwable e) {
			throw new TransferException(e);
		} finally {
			if (sftpClient != null) {
				sftpClient.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * com.com.kaiwait.transfer.file.IFileTransfer#remoteFileExist(java.lang.
	 * String)
	 */
	@Override
	public boolean remoteFileExist(String remoteFilePath) {
		String remotePath = getRemoteDownloadFolderPath();
		if (StringUtil.isNotEmpty(remoteFilePath)) {
			remotePath = remotePath + "/" + remoteFilePath;
		}
		Connection conn = null;
		SFTPv3Client sftpClient = null;
		try {
			// 连接远程服务器
			conn = getConnection();
			sftpClient = new SFTPv3Client(conn);
			sftpClient.lstat(remotePath);
			sftpClient.close();
			return true;
		} catch (Throwable e) {
			return false;
		} finally {
			if (sftpClient != null) {
				sftpClient.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public String getTransferType() {
		return IFileTransfer.TRANSFER_TYPE_SFTP;
	}


	/* (non-Javadoc)
	 */
	@Override
	protected void updateFile(File targetFile, String remoteFilePath, TransferParameter transferParameter) throws TransferException {
		// 连接远程服务器
		Connection conn = getConnection();
		SFTPv3Client sftpClient = null;
		try {
			sftpClient = new SFTPv3Client(conn);

			SFTPUploadThread uploadThreadInstance = new SFTPUploadThread(sftpClient, targetFile, getRemoteUploadFolderPath(), remoteFilePath);
			// 开始上传
			LOGGER.debug("启动上传线程");
			new FutureTaskHandler<String>().submit(uploadThreadInstance, getTransferTimeout(), TimeUnit.SECONDS);
			LOGGER.debug("上传线程结束");
		} catch (TimeoutException e) {
			// 上传文件超时
			throw new TransferException("文件上传超时", e);
		} catch (Throwable e) {
			throw new TransferException(e);
		} finally {
			if (sftpClient != null) {
				sftpClient.close();
			}
			conn.close();
		}

	}

	/* (non-Javadoc)
	 */
	@Override
	protected File downloadFile(String targetFilePath, TransferParameter transferParameter) throws TransferException {
		// 连接远程服务器
		Connection conn = getConnection();
		SFTPv3Client sftpClient = null;
		try {
			sftpClient = new SFTPv3Client(conn);
			final SftpDownloadThread downloadThreadInstance = new SftpDownloadThread(sftpClient, getRemoteDownloadFolderPath() + "/" + targetFilePath, transferParameter.getLocalDownloadWorkFolder());
			// 开始下载
			LOGGER.debug("启动下载线程");
			new FutureTaskHandler<String>().submit(downloadThreadInstance, getTransferTimeout(), TimeUnit.SECONDS);
			LOGGER.debug("下载线程结束");
		} catch (TimeoutException e) {
			// 下载文件结束或者超时
			throw new TransferException("文件下载超时", e);
		} catch (Throwable e) {
			throw new TransferException(e);
		} finally {
			if (sftpClient != null) {
				sftpClient.close();
			}
			conn.close();
		}
		return new File(transferParameter.getLocalDownloadWorkFolder() + "/" + FilenameUtils.getName(targetFilePath));
	}

	/**
	 * 文件上传用线程类<br>
	 * 
	 *
	 */
	public static class SFTPUploadThread implements IFutureTask<String> {
		/** Sftp客户端 */
		private SFTPv3Client sftpClient;
		/** 本地文件 */
		private File localFile;
		/** 远程目标文件夹 */
		private String remoteTargetDirectory;
		/** 远程文件的相对路径(相对于远程根目录) */
		private String remoteFilePath;
		/** 字节流缓冲大小 */
		int transferBufferSize = 1024;

		/**
		 * 构造函数
		 * 
		 *            Sftp客户端
		 *            本地文件
		 *            远程目标文件夹
		 */
		public SFTPUploadThread(SFTPv3Client sftpClient, File localFile, String remoteTargetDirectory, String remoteFilePath) {
			this.sftpClient = sftpClient;
			this.localFile = localFile;
			this.remoteTargetDirectory = remoteTargetDirectory;
			this.remoteFilePath = remoteFilePath;
		}

		/*
		 * (non-Javadoc)
		 * 
		 */
		@Override
		public String call() throws Exception {
			SFTPv3FileHandle fileHandle = null;
			try (FileInputStream fileInputStream = new FileInputStream(localFile);) {
				if (StringUtil.isNotEmpty(remoteFilePath)) {
					if (StringUtil.isNotEmpty(remoteTargetDirectory)) {
						remoteTargetDirectory = remoteTargetDirectory + "/" + remoteFilePath;
					} else {
						remoteTargetDirectory = remoteFilePath;
					}
				}
				if (StringUtil.isNotEmpty(remoteTargetDirectory)) {
					try {
						sftpClient.mkdir(remoteTargetDirectory, 0755);
					} catch (Throwable e) {

					}
				}
				// 在服务器上创建新文件并以读写方式打开,如果文件已经存在则截断
				fileHandle = sftpClient.createFileTruncate(remoteTargetDirectory + "/" + localFile.getName());
				// 将本地文件写入服务器
				int readLength = 0;
				int writedLength = 0;
				byte[] transferBuffer = new byte[transferBufferSize];
				while ((readLength = fileInputStream.read(transferBuffer)) >= 0) {
					sftpClient.write(fileHandle, writedLength, transferBuffer, 0, readLength);
					writedLength += readLength;
				}
			} catch (Throwable e) {
				throw new TransferException(e);
			} finally {
				if (fileHandle != null) {
					try {
						sftpClient.closeFile(fileHandle);
					} catch (IOException e) {
						LOGGER.warn("资源释放失败");
					}
				}
			}
			return "success";
		}

	}

	/**
	 * 文件下载用线程<br>
	 * 
	 *
	 */
	public static class SftpDownloadThread implements IFutureTask<String> {
		/** SFTP客户端 */
		private SFTPv3Client sftpClient;
		/** 本地文件夹 */
		private File localDownloadWorkFolder;
		/** 远程目标文件 */
		private String remoteTargetFilePath;
		/** 字节流缓冲大小 */
		int transferBufferSize = 1024;

		/**
		 * 构造函数
		 * 
		 *            SFTP客户端
		 *            远程目标文件路径
		 *            本地保存文件夹
		 */
		public SftpDownloadThread(SFTPv3Client sftpClient, String remoteTargetFilePath, File localDownloadWorkFolder) {
			this.sftpClient = sftpClient;
			this.localDownloadWorkFolder = localDownloadWorkFolder;
			this.remoteTargetFilePath = remoteTargetFilePath;
		}

		/*
		 * (non-Javadoc)
		 * 
		 */
		@Override
		public String call() throws Exception {
			try {
				// 下载文件
				download(remoteTargetFilePath, localDownloadWorkFolder.getAbsolutePath());
				return "success";
			} catch (Throwable e) {
				throw new TransferException(e);
			}
		}

		/**
		 * 下载文件
		 * 
		 *            远程文件路径
		 *            本地文件路径
		 *             包含下载过程中所有的异常
		 */
		private void download(String remoteFilePath, String localFilePath) throws Exception {
			SFTPv3FileAttributes fileAttributes = sftpClient.lstat(remoteFilePath);
			if (fileAttributes.isDirectory()) {
				// 下载对象是目录,创建本地目录并递归处理
				String localDirPath = localFilePath + "/" + FilenameUtils.getName(remoteFilePath);
				File localDir = new File(localDirPath);
				if (!localDir.exists()) {
					boolean mkdirs = localDir.mkdirs();
					if (!mkdirs) {
						throw new Exception("创建本地文件夹[" + localDirPath + "]失败");
					}
				} else if (!localDir.isDirectory()) {
					throw new Exception("创建本地文件夹[" + localDirPath + "]失败, 存在同名文件.");
				}
				// 取得目录下所有文件
				List<SFTPv3DirectoryEntry> ls = sftpClient.ls(remoteFilePath);
				// 遍历处理每个文件
				for (SFTPv3DirectoryEntry entry : ls) {
					if (".".equals(entry.filename) || "..".equals(entry.filename)) {
						// 跳过当前文件夹和上级文件夹
						continue;
					}
					// 递归处理文件夹的对象
					download(remoteFilePath + "/" + entry.filename, localDirPath);
				}
			} else if (fileAttributes.isRegularFile()) {
				// 下载对象是文件,创建本地文件
				String fileName = FilenameUtils.getName(remoteFilePath);
				File localFile = new File(localFilePath + "/" + fileName);
				if (!localFile.exists()) {
					localFile.createNewFile();
				}
				SFTPv3FileHandle fileHandle = null;
				try (FileOutputStream fileOutputStream = new FileOutputStream(localFile);) {
					// 在服务器上以只读方式打开文件
					fileHandle = sftpClient.openFileRO(remoteFilePath);
					// 将远程文件写入本地
					int readLength = 0;
					int readLengthTotal = 0;
					byte[] transferBuffer = new byte[transferBufferSize];
					while ((readLength = sftpClient.read(fileHandle, readLengthTotal, transferBuffer, 0, transferBuffer.length)) >= 0) {
						readLengthTotal += readLength;
						fileOutputStream.write(transferBuffer, 0, readLength);
					}
					fileOutputStream.flush();
				} finally {
					if (fileHandle != null) {
						try {
							sftpClient.closeFile(fileHandle);
						} catch (IOException e) {
							LOGGER.warn("资源释放失败");
						}
					}
				}
			} else {
				// 下载对象不是正常文件(可能是符号链接等),不处理
				LOGGER.warn(remoteFilePath + "不是正常文件,跳过");
			}
		}
	}

	/**
	 * 连接远程服务器
	 * 
	 *             连接失败超过指定次数时,抛出该异常
	 */
	private Connection getConnection() throws TransferException {
		StringBuilder messageBuffer = new StringBuilder();
		// 连接远程服务器
		Connection conn = new Connection(super.getTransferHostName(), super.getTransferPort());
		int errorCount = 0;
		while (true) {
			try {
				// 尝试连接
				if (LOGGER.isDebugEnabled()) {
					messageBuffer.setLength(0);
					messageBuffer.append("尝试第");
					messageBuffer.append(errorCount + 1);
					messageBuffer.append("次连接");
					LOGGER.debug(messageBuffer.toString());
				}
				conn.connect(null, getConnectTimeout() * MILLISECONDS, getConnectTimeout() * MILLISECONDS);
				final boolean connected = conn.authenticateWithPassword(getTransferUserName(), getTransferPassword());
				if (connected) {
					// 连接成功
					LOGGER.debug("连接成功");
					return conn;
				} else {
					// 连接远程服务器失败,本次连接终止,间隔指定时间后再重试连接
					throw new IOException();
				}
			} catch (SocketTimeoutException e) {
				// 连接远程服务器超时,远程服务器无应答
				errorCount++;
				if (errorCount >= getRetryCount()) {
					throw new TransferException("连接远程服务器失败,远程服务器无应答", e);
				} else {
					messageBuffer.setLength(0);
					messageBuffer.append("第");
					messageBuffer.append(errorCount);
					messageBuffer.append("次连接远程服务器失败,间隔");
					messageBuffer.append(getRetrySpan());
					messageBuffer.append("秒后将尝试再次连接");
					LOGGER.warn(messageBuffer);
				}
			} catch (IOException e) {
				// 连接远程服务器失败
				errorCount++;
				if (errorCount >= getRetryCount()) {
					throw new TransferException("连接远程服务器失败,远程服务器无应答", e);
				} else {
					messageBuffer.setLength(0);
					messageBuffer.append("第");
					messageBuffer.append(errorCount);
					messageBuffer.append("次连接远程服务器超时,间隔");
					messageBuffer.append(getRetrySpan());
					messageBuffer.append("秒后将尝试再次连接");
					LOGGER.warn(messageBuffer);
				}
			}
			try {
				// 间隔指定时间后,再重试
				Thread.sleep(getRetrySpan() * MILLISECONDS);
			} catch (InterruptedException e) {
				// 间隔时间到,继续尝试下一次
			}
		}
	}
}
