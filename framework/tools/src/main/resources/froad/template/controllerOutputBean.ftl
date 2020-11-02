package ${controllerOutputBeanPackage};

public class ${controllerOutputBeanName} {
	<#list detailOutParam.detailParamList as param>
	/** ${param.comment} */
	public ${param.type} ${param.name?uncap_first};
	</#list>

	<#list detailOutParam.detailParamList as param>
	/**
	 * 取得${param.comment}
	 * @return ${param.comment}
	 */
	public ${param.type} get${param.name?cap_first}() {
		return ${param.name?uncap_first};
	}
	/**
	 * 设置${param.comment}
	 * @param name ${param.comment}
	 */
	public void set${param.name?cap_first}(String ${param.name?uncap_first}) {
		this.${param.name?uncap_first} = ${param.name?uncap_first};
	}
	</#list>
}
