/**
 * 
 */
package com.kaiwait.core.process.dynamic.vo;

import java.util.LinkedList;
import java.util.List;


/**
 * @author wings
 *
 */
public class InterfaceDefinition {
	/** 保存接口定义的文件路径*/
	private String definitionFilePath;
	/** 接口名*/
	private String name;
	/** 接口路径*/
	private String path;
	/** 接口根路径*/
	private String rootPath;
	/** 接口类型*/
	private InterfaceTypeEnum type;
	/** 调用方式*/
	private String method;
	/** 分页*/
	private boolean paging;
	/** 入参列表*/
	private List<ParameterDefinition> inParamList;
	/** 出参列表*/
	private List<ParameterDefinition> outParamList;
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
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the rootPath
	 */
	public String getRootPath() {
		return rootPath;
	}
	/**
	 * @param rootPath the rootPath to set
	 */
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	public boolean isPaging() {
		return paging;
	}
	public void setPaging(boolean paging) {
		this.paging = paging;
	}
	/**
	 * @return the type
	 */
	public InterfaceTypeEnum getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(InterfaceTypeEnum type) {
		this.type = type;
	}
	/**
	 * @return the inParamList
	 */
	public List<ParameterDefinition> getInParamList() {
		return inParamList;
	}
	/**
	 * @param inParamList the inParamList to set
	 */
	public void setInParamList(List<ParameterDefinition> inParamList) {
		this.inParamList = inParamList;
	}
	/**
	 * @return the outParamList
	 */
	public List<ParameterDefinition> getOutParamList() {
		return outParamList;
	}
	/**
	 * @param outParamList the outParamList to set
	 */
	public void setOutParamList(List<ParameterDefinition> outParamList) {
		this.outParamList = outParamList;
	}
	
	public ParameterDefinition addInParam(ParameterDefinition parameter) {
		if (this.inParamList == null) {
			this.inParamList = new LinkedList<ParameterDefinition>();
		}
		this.inParamList.add(parameter);
		return parameter;
	}
	public String getDefinitionFilePath() {
		return definitionFilePath;
	}
	public void setDefinitionFilePath(String definitionFilePath) {
		this.definitionFilePath = definitionFilePath;
	}
	
	
}
