package com.kaiwait.core.process.dynamic.vo;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class ParameterDefinition {
	/** 在定义文件中的行数*/
	private int defineLine;
	/** 参数名*/
	private String name;
	/** 参数注释*/
	private String comment;
	/** 参数类型(Object,Array,String)*/
	private ParameterTypeEnum type;
	/** 参数说明*/
	private String description;
	/** 是否必需*/
	private boolean required;
	/** 校验格式*/
	private ParameterFormatEnum format;
	/** 附加格式*/
	private String formatPattern;
	/** 校验长度*/
	private Integer length;
	/** 子参数列表*/
	private List<ParameterDefinition> detailParamList;
	/** 父节点,通过定义文件生成配置文件时做控制用*/
	@JsonIgnore
	private ParameterDefinition parent;
	/** 参数类型,保存参数对应的java类型.通过定义文件生成Bean时使用*/
	@JsonIgnore
	private String javaType;
	
	public ParameterDefinition() {
		
	}
	public ParameterDefinition(String name, String comment) {
		this(name, comment, ParameterTypeEnum.String);
	}
	public ParameterDefinition(String name, String comment, boolean required) {
		this(name, comment, ParameterTypeEnum.String, required, null);
	}
	public ParameterDefinition(String name, String comment, ParameterTypeEnum type) {
		this(name, comment, type, true);
	}
	public ParameterDefinition(String name, String comment, ParameterTypeEnum type, boolean required) {
		this(name, comment, type, required, null);
	}
	public ParameterDefinition(String name, String comment, ParameterTypeEnum type, boolean required, Integer length) {
		this(name, comment, type, required, length, null);
	}
	public ParameterDefinition(String name, String comment, ParameterTypeEnum type, boolean required, Integer length, ParameterFormatEnum format) {
		this(name, comment, type, required, length, format, null);
	}
	public ParameterDefinition(String name, String comment, ParameterTypeEnum type, boolean required, Integer length, ParameterFormatEnum format, String formatPattern) {
		this.name = name;
		this.comment = comment;
		this.type = type;
		this.required = required;
		this.length = length;
		this.format = format;
		this.formatPattern = formatPattern;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the type
	 */
	public ParameterTypeEnum getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(ParameterTypeEnum type) {
		this.type = type;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}
	/**
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}
	/**
	 * @return the format
	 */
	public ParameterFormatEnum getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(ParameterFormatEnum format) {
		this.format = format;
	}
	/**
	 * @return the formatPattern
	 */
	public String getFormatPattern() {
		return formatPattern;
	}
	/**
	 * @param formatPattern the formatPattern to set
	 */
	public void setFormatPattern(String formatPattern) {
		this.formatPattern = formatPattern;
	}
	/**
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(Integer length) {
		this.length = length;
	}
	public int getDefineLine() {
		return defineLine;
	}
	public void setDefineLine(int defineLine) {
		this.defineLine = defineLine;
	}
	public ParameterDefinition getParent() {
		return parent;
	}
	public void setParent(ParameterDefinition parent) {
		this.parent = parent;
	}
	
	
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	/**
	 * @return the detailParamList
	 */
	public List<ParameterDefinition> getDetailParamList() {
		return detailParamList;
	}
	/**
	 * @param detailParamList the detailParamList to set
	 */
	public void setDetailParamList(List<ParameterDefinition> detailParamList) {
		this.detailParamList = detailParamList;
	}
	
	public void addParam(ParameterDefinition parameter) {
		if (this.detailParamList == null) {
			this.detailParamList = new LinkedList<ParameterDefinition>();
		}
		this.detailParamList.add(parameter);
	}
	
}
