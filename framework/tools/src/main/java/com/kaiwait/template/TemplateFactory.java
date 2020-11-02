package com.kaiwait.template;


/**
 * 通用模板工厂
 *
 */
public class TemplateFactory {
	/** 认的模板构造器*/
	private static final ITemplateBuilder DEFAULT_TEMPLATE_BUILDER=  new com.kaiwait.template.freemarker.TemplateBuilderImpl();
	
	/**
	 * 取得默认的模板构造器(当前是Freemarker实现)
	 */
	public static ITemplateBuilder getTemplateBuilder() {
		return DEFAULT_TEMPLATE_BUILDER;
	}
	
}
