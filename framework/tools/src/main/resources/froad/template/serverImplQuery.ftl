package ${serverPackage}.impl;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.vo.ResultVo;
import ${serverPackage}.${serverName}Service;
/**
 *
 */
public class ${serverName}ServiceImpl extends BizMonitorBaseService implements ${serverName}Service.Iface {

	public ${serverName}ServiceImpl(String name, String version) {
		super(name,version);
	} 

	public ${serverOutputBeanName} ${path}(${serverInputBeanName} ${serverInputBeanName?uncap_first}<#if paging>, PageVo pageVo</#if>) throws TException {
		long startTime = System.currentTimeMillis();
		LogCvt.debug("${name}接口输入参数,${serverInputBeanName?uncap_first}:" + JSON.toJSONString(${serverInputBeanName?uncap_first})<#if paging> + ",pageVo:" + JSON.toJSONString(pageVo)</#if>);
		ResultVo result = new ResultVo();
		${serverOutputBeanName} ${serverOutputBeanName?uncap_first} = new ${serverOutputBeanName}();
		${serverOutputBeanName?uncap_first}.setResultVo(result);
		try {
		<#if paging>
			Page<> page = new Page<>();
			BeanUtils.copyProperties(page, pageVo);
		</#if>
			String clientId = ${serverInputBeanName?uncap_first}.getClientId();
			// 输入参数有效性校验
			<#list inParamList as param>
				<#if param.required >
			if (StringUtils.isEmpty(${serverInputBeanName?uncap_first}.get${param.name?cap_first}())) {
				LogCvt.error("${name}失败，原因: ${param.comment}不能为空");
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("${name}失败，原因: ${param.comment}不能为空");
				return ${serverOutputBeanName?uncap_first};
			}
				</#if>
				<#if param.format?? && param.format == "Long" >
					<#if param.required == false>
			if (StringUtils.isNotEmpty(${serverInputBeanName?uncap_first}.get${param.name?cap_first}()) && 
					<#else>
			if (
					</#if>
			 !NumberUtils.isDigits(${serverInputBeanName?uncap_first}.get${param.name?cap_first}())) {
				LogCvt.error("${name}失败，原因: ${param.comment}类型不正确,必需输入整数");
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("${name}失败，原因: ${param.comment}类型不正确,必需输入整数");
				return ${serverOutputBeanName?uncap_first};
			}
				</#if>
				<#if param.format?? && param.format == "Integer" >
					<#if param.required == false>
			if (StringUtils.isNotEmpty(${serverInputBeanName?uncap_first}.get${param.name?cap_first}()) && 
					<#else>
			if (
					</#if>
			 !NumberUtils.isDigits(${serverInputBeanName?uncap_first}.get${param.name?cap_first}())) {
				LogCvt.error("${name}失败，原因: ${param.comment}类型不正确,必需输入整数");
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("${name}失败，原因: ${param.comment}类型不正确,必需输入整数");
				return ${serverOutputBeanName?uncap_first};
			}
				</#if>
			</#list>
			
			//调用Process
			List<> queryList = ${serverName?uncap_first}Logic.${path}(
				 page //分页信息
				,clientId //客户端ID
			<#list inParamList as param>
				,${serverInputBeanName?uncap_first}.get${param.name?cap_first}() //${param.comment}
			</#list>
			);
			<#if detailOutParam??>
				
			//结果变换
			List<${serverDetailOutputBeanName}> ${detailOutParam.name} = new LinkedList<${serverDetailOutputBeanName}>();
			for ( item : queryList) {
				${serverDetailOutputBeanName?cap_first} ${serverDetailOutputBeanName} = new ${serverDetailOutputBeanName?cap_first}();
			<#list detailOutParam.detailParamList as param>
				${serverDetailOutputBeanName}.set${param.name?cap_first}(item.get${param.name?cap_first}());//${param.comment}
			</#list>
				${detailOutParam.name}.add(${serverDetailOutputBeanName});
			}
			${serverOutputBeanName?uncap_first}.set${detailOutParam.name?cap_first}(${detailOutParam.name});
			</#if>
			<#if paging>
			pageVo.setPageCount(page.getPageCount());
			pageVo.setTotalCount(page.getTotalCount());
			if (pageVo.getPageCount() > pageVo.getPageNumber()) {
				pageVo.setHasNext(true);
			} else {
				pageVo.setHasNext(false);
			}
			${serverOutputBeanName?uncap_first}.setPage(pageVo);
			</#if>
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc("${name}成功");
			return ${serverOutputBeanName?uncap_first};
		} catch (Exception e) {
			LogCvt.error("${name}失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("${name}失败，原因:" + e.getMessage());
			<#if type == "update">
			return result;
			<#else>
			return ${serverOutputBeanName?uncap_first};
			</#if>
		} finally {
			long endTime = System.currentTimeMillis();
			LogCvt.debug("${name}耗时" + (endTime - startTime) + "--startTime:" + startTime + "---endTime:" + endTime);
		}
	}
}


