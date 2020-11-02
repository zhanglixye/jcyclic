namespace java ${serverBeanPackage}

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/**
 * ${name}输入参数
 */
struct ${serverInputBeanName} {
	/** 客户端ID */
	1:string clientId;
    /** 操作人 */
	2:string updateUser;
   /** 详细参数*/
   	3:list<${serverInputBeanName}DetailVo> recordList;
}

/**
 * ${name}详细参数
 */
struct ${serverInputBeanName}DetailVo {
<#list inParamList as param>
	/** ${param.comment} */
	${param_index+1}:${param.type?uncap_first} ${param.name};
</#list>
}
