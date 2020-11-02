<#if type == "query">
import com.froad.thrift.vo.PageVo;
/**
 * ${name}
 * @param page 分页信息
 * @param clientId 客户端ID
<#list inParamList as param>
 * @param ${param.name} ${param.comment}
</#list>
 * @return 结果列表
 */
List<> ${path}(
	 PageVo page
	, String clientId
	<#list inParamList as param>
	, ${param.type} ${param.name}
	</#list>
);

<#else>
	/**
	 * ${name}
	 * @param clientId 客户端ID
	 * @param updateUser 当前操作者
	<#list inParamList as param>
	 * @param ${param.name} ${param.comment}
	</#list>
	 * @return ${name}处理结果
	 */
	ResultVo ${path}(
		 String clientId
		,String updateUser
		<#list inParamList as param>
		,${param.type} ${param.name}
		</#list>
	);
</#if>
