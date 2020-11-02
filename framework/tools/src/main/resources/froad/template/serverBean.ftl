namespace java ${serverBeanPackage}

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/**
 * ${name}输入参数
 */
struct ${serverInputBeanName} {
	/** 客户端ID */
	1:string clientId;
	<#if type=="update" >
    /** 操作人 */
	2:string updateUser;
    </#if>
	<#list inParamList as param>
	/** ${param.comment} */
	<#if type=="update" >
	${param_index+3}:<#if param.required == false> optional </#if>string ${param.name};
	<#else>
	${param_index+2}:<#if param.required == false> optional </#if>string ${param.name};
	</#if>
	</#list>
}

<#if type == "query">
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
	<#list outParamList as param>
	/** ${param.comment} */
	${param_index+2}:string ${param.name};
	</#list>
	<#if detailOutParam??>
	/** ${detailOutParam.comment} */
	2:list<${serverDetailOutputBeanName?cap_first}> ${detailOutParam.name}
	</#if>
	/** 分页信息 */
	3:Common.PageVo page;
}
</#if>
