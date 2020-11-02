package com.kaiwait.transfer.file.executor;

import org.springframework.beans.BeanUtils;

import com.kaiwait.transfer.file.IFileTransfer;
import com.kaiwait.transfer.file.TransferSetting;

/**
 * 鏂囦欢浼犺緭澶勭悊鍣ㄥ伐鍘傜被
 * 
 *
 */
public class ExecutorFactory {
	/**
	 * 绂佹鍒涘缓瀹炰緥
	 */
	private ExecutorFactory() {

	}

	/**
	 * 鑾峰彇SFTP鏂囦欢浼犺緭澶勭悊鍣ㄥ疄渚�
	 * 
	 *            鏂囦欢浼犺緭鐨勫弬鏁拌瀹�
	 */
	public static IFileTransfer getSftpFileTransfterExector(TransferSetting setting) throws Exception{
		AbstractBaseExecutor transferExecutor = new SftpExecutor();
		BeanUtils.copyProperties(setting, transferExecutor);
		return transferExecutor;
	}
}
