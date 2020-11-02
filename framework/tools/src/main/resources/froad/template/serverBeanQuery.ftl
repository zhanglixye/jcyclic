namespace java ${serverBeanPackage}

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/**
 * ${name}输入参数
 */
struct ${serverInputBeanName} {
	/** 客户端ID */
	1:string clientId;
	<#list inParamList as param>
	/** ${param.comment} */
	${param_index+2}:<#if param.required == false> optional </#if>string ${param.name};
	</#list>
}

<#if detailOutParam??>
/**
 * ${detailOutParam.comment}bean
 */
struct ${serverDetailOutputBeanName?cap_first} {
	<#list detailOutParam.detailParamList as param>
	/** ${param.comment} */
	${param_index+1}:string ${param.name};
	</#list>
}
</#if>
/**
 * ${name}输出参数
 */
struct ${serverOutputBeanName} {
	/** 结果信息 */
	1:Common.ResultVo resultVo;
	<#assign varIndex=2/>
	<#if paging>
	/** 分页信息 */
	2:Common.PageVo page;
	<#assign varIndex=varIndex+1/>
	</#if>
	<#list outParamList as param>
	/** ${param.comment} */
	${varIndex}:string ${param.name};
		<#assign varIndex=varIndex+1/>
	</#list>
	<#if detailOutParam??>
	/** ${detailOutParam.comment} */
	${varIndex}:list<${serverDetailOutputBeanName?cap_first}> ${detailOutParam.name}
	</#if>

}
