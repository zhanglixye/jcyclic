package com.kaiwait.transfer.file;

/**
 * 描述远程文件的对象
 * 
 *
 */
public class RemoteFile {
	/** 文件 */
	public static final int TYPE_FILE = 0;
	/** 目录 */
	public static final int TYPE_DIRECTORY = 1;
	/** 符号链接等其它类型 */
	public static final int TYPE_OTHER = 2;

	/** 文件名 */
	private String name;
	/** 文件类型 */
	private int type;

	/**
	 * 构造函数
	 * 
	 *            文件名
	 *            文件类型
	 */
	public RemoteFile(String name, int type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * 是否是文件夹
	 * 
	 */
	public boolean isDirectory() {
		return this.type == TYPE_DIRECTORY;
	}

	/**
	 * 是否是文件
	 * 
	 */
	public boolean isFile() {
		return this.type == TYPE_FILE;
	}

	/**
	 * 是否是符号链接等其它类型
	 * 
	 */
	public boolean isOther() {
		return this.type == TYPE_OTHER;
	}

	/**
	 * 取得文件名
	 * 
	 */
	public String getName() {
		return this.name;
	}

}
