package com.kaiwait.transfer.file.task.encrypt;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kaiwait.transfer.file.task.IEncrypt;
import com.kaiwait.utils.common.CommandUtil;

/**
 * GPG加密
 * 
 *
 */
public class GpgEncrypt implements IEncrypt {
	public static final Log LOGGER = LogFactory.getLog(GpgEncrypt.class);

	/** 加密用的公钥的用户ID */
	private String userId;
	/** 解密用的私钥的密码 */
	private String password;

	/**
	 * 构造函数
	 * 
	 *            加密用的公钥的用户ID
	 *            解密用的私钥的密码
	 */
	public GpgEncrypt(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * com.com.kaiwait.transfer.file.task.IEncrypt#encryptFile(java.io.File,
	 * java.io.File)
	 */
	@Override
	public synchronized File encryptFile(File srcFile, File destFolder) throws Exception {
		// 加密文件保存路径
		StringBuilder encryptFilePathBuffer = new StringBuilder(128);
		encryptFilePathBuffer.append(destFolder.getAbsolutePath());
		encryptFilePathBuffer.append("/");
		encryptFilePathBuffer.append(srcFile.getName());

		// 如果加密输出文件已经存在,先删除它,否则调用gpg2加密时将弹出是否要覆盖的确认对话框并导致死循环
		File destFile = new File(encryptFilePathBuffer.toString());
		if (destFile.exists()) {
			destFile.delete();
		}
		// gpg2 -e -r USERID -o /tmp/xxx $FILE
		// tmp/xxx为输出文件的路径；$FILE为需要加密的文件；USERID是已经导入的公钥中标识的用户ID,可通过gpg2 –k来查看。
		StringBuilder commandBuffer = new StringBuilder(256);
		commandBuffer.append("gpg2 -e -r ");
		commandBuffer.append(userId);
		commandBuffer.append(" -o ");
		commandBuffer.append(encryptFilePathBuffer);
		commandBuffer.append(" ");
		commandBuffer.append(srcFile.getAbsolutePath());

		// 在指定目录下执行命令
		int execCommand = CommandUtil.execCommand(commandBuffer.toString(), null);
		if (execCommand != CommandUtil.RESULT_SUCCESS) {
			throw new Exception("文件加密失败");
		}
		return new File(encryptFilePathBuffer.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * com.com.kaiwait.transfer.file.task.IEncrypt#decryptFile(java.io.File,
	 * java.io.File)
	 */
	@Override
	public synchronized File decryptFile(File srcFile, File destFolder) throws Exception {
		//文件解密,递归处理所有子文件和子文件夹
		decrypt(srcFile, destFolder.getAbsolutePath());
		//返回解密后文件输出路径的文件对象
		return new File(destFolder.getAbsolutePath() + "/" + srcFile.getName());
	}

	/**
	 * 文件解密,递归处理所有子文件和子文件夹
	 * 
	 *            源文件
	 *            解密后文件输出路径
	 *             包含解密过程中所有异常
	 */
	private void decrypt(File srcFile, String destFolderPath) throws Exception {
		StringBuilder messageBuffer = new StringBuilder(512);
		messageBuffer.append("源文件路径:");
		messageBuffer.append(srcFile.getAbsolutePath());
		messageBuffer.append(", 目标路径:");
		messageBuffer.append(destFolderPath);
		messageBuffer.append("/");
		messageBuffer.append(srcFile.getName());

		if (srcFile.isFile()) {
			// 当前是文件,执行解密操作
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("文件解密开始," + messageBuffer.toString());
			}
			// 如果解密输出文件已经存在,先删除它,否则调用gpg2解密时将弹出是否要覆盖的确认对话框并导致死循环
			File destFile = new File(destFolderPath + "/" + srcFile.getName());
			if (destFile.exists()) {
				destFile.delete();
			}
			// gpg2 –d --passphrase 密码 –o /tmp/xxx $FILE
			// 此处密码为生成公钥时输入的密码；/tmp/xxx为输出文件的路径；$FILE为需要解密的文件；
			StringBuilder commandBuffer = new StringBuilder(128);
			commandBuffer.append("gpg2 --batch -d --passphrase ");
			commandBuffer.append(password);
			commandBuffer.append(" -o ");
			commandBuffer.append(destFolderPath);
			commandBuffer.append("/");
			commandBuffer.append(srcFile.getName());
			commandBuffer.append(" ");
			commandBuffer.append(srcFile.getAbsolutePath());
			// 执行gpg2解密
			int execCommand = CommandUtil.execCommand(commandBuffer.toString(), null);

			if (execCommand != CommandUtil.RESULT_SUCCESS) {
				// 解密命令执行失败
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("文件解密失败," + messageBuffer.toString());
				}
				throw new Exception("文件解密失败," + messageBuffer.toString());
			} else {
				// 解密命令执行成功
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("文件解密成功," + messageBuffer.toString());
				}
			}
		} else if (srcFile.isDirectory()) {
			// 当前是目录
			// 创建对应的解密目录
			File destFolder = new File(destFolderPath + "/" + srcFile.getName());
			if (!destFolder.exists()) {
				boolean mkdirs = destFolder.mkdirs();
				if (!mkdirs) {
					if (LOGGER.isErrorEnabled()) {
						LOGGER.error("文件解密失败,不能创建解密文件夹." + messageBuffer.toString());
					}
					throw new Exception("文件解密失败,不能创建解密文件夹." + messageBuffer.toString());
				}
			}
			// 递归处理目录内所有文件
			File[] listFiles = srcFile.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file : listFiles) {
					decrypt(file, destFolder.getAbsolutePath());
				}
			}
		}

	}

	/**
	 * 获取［加密用的公钥的用户ID］
	 * 
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置［加密用的公钥的用户ID］
	 * 
	 *            加密用的公钥的用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取［解密用的私钥的密码］
	 * 
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置［解密用的私钥的密码］
	 * 
	 *            解密用的私钥的密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
