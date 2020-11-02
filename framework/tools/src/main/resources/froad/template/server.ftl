include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "${serverName}Vo.thrift"
namespace java ${serverPackage}

service ${serverName}Service extends BizMonitor.BizMonitorService {
	/** ${name}接口*/
	<#if type == "update" || type == "upload">
	Common.ResultVo ${path}(1:${serverName}Vo.${serverInputBeanName} ${serverInputBeanName?uncap_first});
	<#else>
	${serverName}Vo.${serverOutputBeanName} ${path}(1:${serverName}Vo.${serverInputBeanName} ${serverInputBeanName?uncap_first}<#if paging>, 2:Common.PageVo pageVo</#if>);
	</#if>
}
