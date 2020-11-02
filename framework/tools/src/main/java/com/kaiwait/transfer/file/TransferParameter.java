package com.kaiwait.transfer.file;

import java.io.File;

public class TransferParameter {
	/** 本地上传工作目录根 */
	public File localUploadWorkFolder;
	/** 本地上传备份目录 */
	public File localUploadBackWorkFolder;
	/** 本地压缩用目录 */
	public File localCompressionWorkFolder;
	/** 本地加密用目录 */
	public File localEncryptWorkFolder;
	/** 本地下载工作目录根 */
	public File localDownloadWorkFolder;
	/** 本地下载备份目录 */
	public File localDownloadBackWorkFolder;
	/** 本地解压缩目录 */
	public File localDecompressionWorkFolder;
	/** 本地解密用目录 */
	public File localDecryptWorkFolder;
	
	/**
	 * 取得[本地上传工作目录根]
	 * 
	 */
	public File getLocalUploadWorkFolder() {
		return localUploadWorkFolder;
	}

	/**
	 * 取得[本地上传备份目录]
	 * 
	 */
	public File getLocalUploadBackWorkFolder() {
		return localUploadBackWorkFolder;
	}

	/**
	 * 取得[本地压缩用目录]
	 * 
	 */
	public File getLocalCompressionWorkFolder() {
		return localCompressionWorkFolder;
	}

	/**
	 * 取得[本地加密用目录]
	 * 
	 */
	public File getLocalEncryptWorkFolder() {
		return localEncryptWorkFolder;
	}

	/**
	 * 取得[本地下载工作目录根]
	 * 
	 */
	public File getLocalDownloadWorkFolder() {
		return localDownloadWorkFolder;
	}

	/**
	 * 取得[本地下载备份目录]
	 * 
	 */
	public File getLocalDownloadBackWorkFolder() {
		return localDownloadBackWorkFolder;
	}

	/**
	 * 取得[本地解压缩目录]
	 * 
	 */
	public File getLocalDecompressionWorkFolder() {
		return localDecompressionWorkFolder;
	}

	/**
	 * 取得[本地解密用目录]
	 * 
	 */
	public File getLocalDecryptWorkFolder() {
		return localDecryptWorkFolder;
	}
}
