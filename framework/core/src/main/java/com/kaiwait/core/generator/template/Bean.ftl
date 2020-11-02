package com.froad.system.bean.${rootPath}.${path};
public class ${paramDef.name?cap_first} {
	<#list paramDef.detailParamList as param>
	 /** ${param.comment} */
	  private ${param.javaType?cap_first} ${param.name?uncap_first};
	</#list>
	<#list paramDef.detailParamList as param>
	/**
	 * 取得${param.comment}
	 * @return ${param.comment}
	 */
	public ${param.javaType?cap_first} get${param.name?cap_first}() {
		return ${param.name?uncap_first};
	}
	/**
	 * 设置${param.comment}
	 * @param name ${param.comment}
	 */
	public void set${param.name?cap_first}(${param.javaType?cap_first} ${param.name?uncap_first}) {
		this.${param.name?uncap_first} = ${param.name?uncap_first};
	}
	</#list>
}
