package com.kaiwait.template.freemarker;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 支持从Jar文件中加载的模板
 *
 */
public class JarFileTemplate {
	/** 模板标识*/
	private String id;
	/** 模板的最后修改时间戳*/
	private Long lastModified;
	/** 模板输入流*/
	private Reader reader;

	/**
	 * 构造函数
	 */
	public JarFileTemplate(String id) {
		this.id = id;
		this.lastModified = System.currentTimeMillis();
	}

	/**
	 * 取得模板输入流
	 */
	public Reader getReader(String encoding) throws IOException {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(id);
		if (in != null) {
			reader = new InputStreamReader(in, encoding);
			return reader;
		} else {
			return null;
		}
	}

	/**
	 * 清理模板资源,关闭模板输入流
	 */
	public void closeTemplateSource() throws IOException {
		if (reader != null) {
			reader.close();
		}
	}

	/**
	 * 获取模板的最后修改时间戳
	 */
	public long getLastModified() {
		return this.lastModified;
	}

}
