/**
 * 
 */
package com.kaiwait.template.freemarker;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import com.kaiwait.template.ITemplateBuilder;

/**
 * 基于Freemarker的模板构造器
 *
 */
public class TemplateBuilderImpl implements ITemplateBuilder {
	/**
	 * 参数设置
	 */
	private static final Configuration CONFIGURATION;
	
	static {
		try {
			CONFIGURATION = new Configuration();
			CONFIGURATION.setTemplateLoader(new JarFileTemplateLoader());
			CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		} catch (Throwable e) {
			throw new RuntimeException("模板库初始化失败", e);
		}
	}

	/* (non-Javadoc)
	 */
	public String build(String templatePath, Object data) throws Exception {
		return build(templatePath, data, DEFAULT_CHARSET);
	}

	/* (non-Javadoc)
	 */
	@Override
	public String build(String templatePath, Object data, String charSet) throws Exception {
		Template temp = CONFIGURATION.getTemplate(templatePath);
		try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
				Writer writer = new OutputStreamWriter(stream, charSet)) {
			temp.process(data, writer);
			return stream.toString(charSet);
		}
	}

}
