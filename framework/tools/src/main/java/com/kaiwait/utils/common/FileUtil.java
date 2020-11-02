package com.kaiwait.utils.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * 文件处理类
 * 
 *
 */
public class FileUtil {

	/**
	 * 功能：加载密钥文件
	 * 
	 */
	public static String loadKey(String keyfile) {
		BufferedReader br = null;
		InputStreamReader reader = null;
		InputStream fileInputStream = null;
		String keyString = null;
		try {
			fileInputStream = FileUtil.class.getClassLoader().getResourceAsStream(keyfile);
			reader = new InputStreamReader(fileInputStream);
			br = new BufferedReader(reader);
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
				}
			}
			keyString = sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return keyString;
	}

	/**
	 * 向文件中插入内容<br/>
	 * 注:将源文件内容与要插入的内容写入到一个临时文件中,写入完成后返回临时文件对象.需要手动删除.
	 * 
	 *            源文件
	 *            内容插入的位置(在文件中的字节数)
	 *            插入的内容
	 *             文件操作过程中的异常
	 */
	public static File insertFile(File srcFile, long insertIndex, byte[] insertBytes) throws IOException {
		// 取得源文件长度
		long srcFileLength = srcFile.length();
		if (insertIndex > srcFileLength) {
			insertIndex = srcFileLength;
		}
		// 设置缓冲
		byte[] byteBuffer = new byte[1024];
		// 设置临时文件
		String tmpFileSuffix = "_" + DateTimeUtil.getDateByFormat("yyyyMMddHHmmss", new Date());
		File tempFile = File.createTempFile(srcFile.getName(), tmpFileSuffix);
		// 写文件
		try (FileInputStream fis = new FileInputStream(srcFile); FileOutputStream fos = new FileOutputStream(tempFile);) {
			// 已经读取总字节数
			long readByteTotal = 0;
			// 一次读取的字节数
			int readBytes = 0;
			// 标识是否已经执行了插入
			boolean inserted = false;
			// 读写文件
			while ((readBytes = fis.read(byteBuffer)) >= 0) {
				if (readBytes > 0) {
					if (!inserted && (readBytes >= insertIndex - readByteTotal)) {
						// 文件没有执行过插入操作,并且要插入的内容在本次读取内容中间位置,开始执行插入操作
						// 计算插入位置,计算后的数值不会超过1024强转为int型
						int len = (int) (insertIndex - readByteTotal);
						// 将插入位置前的源文件的字节写入临时文件
						fos.write(byteBuffer, 0, len);
						// 将要插入的内容写入临时文件中
						fos.write(insertBytes);
						// 将插入位置后的源文件的字节写入临时文件
						fos.write(byteBuffer, len, readBytes - len);
						inserted = true;
					} else {
						// 要插入的内容不在本次读取内容之中,将源文件内容全部写入临时文件
						fos.write(byteBuffer, 0, readBytes);
					}
				}
				readByteTotal += readBytes;
			}
			if (!inserted) {
				// 源文件已经读完,还没有执行插入操作,将要插入的内容写入到临时文件的最后
				fos.write(insertBytes);
				inserted = true;
			}
			// 刷新写缓冲
			fos.flush();
		} catch (IOException e) {
			// 删除临时文件
			tempFile.delete();
			throw e;
		}
		return tempFile;
	}

	/**
	 * 将多个文件合并成一个
	 * 
	 *            要合并的文件对象
	 */
	public static File mergeFiles(byte[] fileSeparators, File... files) throws IOException {
		// 设置缓冲
		byte[] byteBuffer = new byte[1024];
		// 设置临时文件
		File tempFile = File.createTempFile("merge", "_tmp");

		if (files != null) {
			//打开输出流
			try (FileOutputStream fos = new FileOutputStream(tempFile);) {
				// 一次读取的字节数
				int readBytes = 0;
				int i=0;
				for (File file : files) {
					if (file != null && file.length() > 0) {
						if (i > 0 && fileSeparators != null && fileSeparators.length > 0) {
							//插入文件分隔符内容
							fos.write(fileSeparators);
						}
						i++;
						try (FileInputStream fis = new FileInputStream(file);) {
							while ((readBytes = fis.read(byteBuffer)) >= 0) {
								//将输入流内容写出到输出流
								fos.write(byteBuffer, 0, readBytes);
							}
						}
					}
				}
				fos.flush();
			}
		}
		return tempFile;
	}
	
	/**
	 * 用指定的内容创建新文件
	 */
	public static File createNewFile(String filePath, byte[] contents) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			File parentFile = file.getParentFile();
			if (parentFile != null) {
				parentFile.mkdirs();
			}
			file.createNewFile();
		}
		try(FileOutputStream fos = new FileOutputStream(file)) {
			if (contents != null) {
				fos.write(contents);
			} else {
				fos.write("".getBytes());
			}
			fos.flush();
		}
		return file;
	}
}
