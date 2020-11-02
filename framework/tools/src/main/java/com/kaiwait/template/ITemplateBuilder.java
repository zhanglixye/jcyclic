/**
 * 
 */
package com.kaiwait.template;

/**
 * 模板构造器接口
 *
 */
public interface ITemplateBuilder {
	/** 默认字符集*/
	String DEFAULT_CHARSET = "UTF-8";
	/**
	 * 绑定模板
	 */
	String build(String templatePath, Object data, String charSet) throws Exception;
	/**
	 * 使用默认字符集,绑定模板
	 */
	String build(String templatePath, Object data) throws Exception;
}
