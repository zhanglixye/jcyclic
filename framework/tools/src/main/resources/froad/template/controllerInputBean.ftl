package ${controllerInputBeanPackage};
<#if type=="query">
import com.froad.cbank.coremodule.module.normal.bank.vo.BaseVo;
</#if>
public class ${controllerInputBeanName}<#if type=="query"> extends BaseVo</#if> {
	<#list inParamList as param>
	 /** ${param.comment} */
	  public ${param.type} ${param.name?uncap_first};
	</#list>
	<#list inParamList as param>
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
