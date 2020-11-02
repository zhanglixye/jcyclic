package ${controllerServicePackage};
import java.util.HashMap;
import java.util.Map;
import ${controllerInputBeanPackage}.${controllerInputBeanName};
<#if detailOutParam??>
import java.util.LinkedList;
import java.util.List;
import ${controllerOutputBeanPackage}.${controllerOutputBeanName};
</#if>
import ${serverInputBeanPackage}.${serverInputBeanName};
import ${serverOutputBeanPackage}.${serverOutputBeanName};
/**
 * @author wanght
 *
 */
@Service
public class ${controllerServiceName?cap_first} {
	/**
	 * ${name}
	 * @param clientId 客户端ID
	 <#if type=="update">
	 * @param updateUser 当前操作用户
	 </#if>
	 <#if inParamList?? && inParamList?size != 0 >
	 * @param reqVo 请求参数
	 </#if>
	 * @return 处理结果
	 * @throws TException 服务接口调用异常
	 */
	public Map<String, Object> ${path}(String clientId<#if type=="update">, String updateUser</#if><#if inParamList?? && inParamList?size != 0 >, ${controllerInputBeanName} reqVo</#if>) throws TException {
		//初始化处理结果 默认值是成功
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put(ResultEnum.CODE.getCode(), EnumTypes.success.getCode());
		resMap.put(ResultEnum.MESSAGE.getCode(), EnumTypes.success.getMessage());
		<#if type=="query">
		// 分页信息取得
		PageVo pageVo = PageUtil.getPageVo(reqVo);
		</#if>
		
		// 调用参数变换开始
		${serverInputBeanName} ${serverInputBeanName?uncap_first} = new ${serverInputBeanName}();
		${serverInputBeanName?uncap_first}.setClientId(clientId);// 客户端Id
		<#if type=="update">
		${serverInputBeanName?uncap_first}.setUpdateUser(updateUser);// 当前操作用户
		</#if>
		<#list inParamList as param>
			<#if param.required == false>
		if (reqVo.get${param.name?cap_first}() != null) {
			</#if>
		<#if param.required == false>	</#if>${serverInputBeanName?uncap_first}.set${param.name?cap_first}(<#if param.format?? && param.format == "Long" >Long.parseLong(</#if><#if param.format?? && param.format == "Integer" >Integer.parseInt(</#if>reqVo.get${param.name?cap_first}()<#if param.format??>)</#if>);//${param.comment}
			<#if param.required == false>
		}
			</#if>	
		</#list>
		// 调用参数变换结束	
		
		//调用后端服务接口
		<#if type=="query">
		${serverOutputBeanName} ${serverOutputBeanName?uncap_first} = ;
		LogCvt.info("${name}接口返回：" + JSON.toJSONString(${serverInputBeanName?uncap_first}));
		
		//判断后端服务接口调用结果
		if (!EnumTypes.success.getCode().equals(${serverOutputBeanName?uncap_first}.getResultVo().getResultCode())) {
			resMap.put(ResultEnum.CODE.getCode(), ${serverOutputBeanName?uncap_first}.getResultVo().getResultCode());
			resMap.put(ResultEnum.MESSAGE.getCode(), ${serverOutputBeanName?uncap_first}.getResultVo().getResultDesc());
			return resMap;
		}
			<#if detailOutParam??>

		//后端服务接口调用结果变换
		List<${controllerOutputBeanName}> resultList = new LinkedList<${controllerOutputBeanName}>();
		if (${serverOutputBeanName?uncap_first}.get${detailOutParam.name?cap_first}() != null) {
			for (${serverDetailOutputBeanName} ${serverDetailOutputBeanName?uncap_first} : ${serverOutputBeanName?uncap_first}.get${detailOutParam.name?cap_first}()) {
				${controllerOutputBeanName} ${controllerOutputBeanName?uncap_first} = new ${controllerOutputBeanName}();
				<#list detailOutParam.detailParamList as param>
				${controllerOutputBeanName?uncap_first}.set${param.name?cap_first}(${serverDetailOutputBeanName?uncap_first}.get${param.name?cap_first}());//${param.comment}
				</#list>
				resultList.add(${controllerOutputBeanName?uncap_first});
			}
		}
		//返回处理结果
		resMap.put("${detailOutParam.name?cap_first}", resultList);
		resMap.put("page", ${serverOutputBeanName?uncap_first}.getPage());
			</#if>
		<#else>
		ResultVo resultVo = ;
		LogCvt.info("${name}接口返回：" + JSON.toJSONString(resultVo));
		resMap.put(ResultEnum.CODE.getCode(), resultVo.getResultCode());
		resMap.put(ResultEnum.MESSAGE.getCode(), resultVo.getResultDesc());
		</#if>
		return resMap;
	}
}
