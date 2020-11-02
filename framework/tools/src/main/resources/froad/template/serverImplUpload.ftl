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
	public ResultVo ${path}(${serverInputBeanName} ${serverInputBeanName?uncap_first}) throws TException {
		long startTime = System.currentTimeMillis();
		LogCvt.debug("${name}接口输入参数: clientId:" + ${serverInputBeanName?uncap_first}.getClientId() + " updateUser:" + ${serverInputBeanName?uncap_first}.getClientId());
		ResultVo result = new ResultVo();
		try {
			String clientId = ${serverInputBeanName?uncap_first}.getClientId();
			// 输入参数有效性校验
			List<${serverInputBeanName}DetailVo> recordList = ${serverInputBeanName?uncap_first}.getRecordList();
			if (recordList == null) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("${name}上传数据不能为空，请补充完整");
				return result;
			} else {
				LogCvt.debug("${name}数据条数:" + recordList.size());
			}
			int rowIndex = 1;
			for(${serverInputBeanName}DetailVo record : recordList) {
				<#list inParamList as param>
					<#if param.required >
				if (StringUtils.isEmpty(record.get${param.name?cap_first}())) {
					LogCvt.error("${name}失败，原因: 第" + rowIndex + "行[${param.comment}]不能为空");
					result.setResultCode(ResultCode.failed.getCode());
					result.setResultDesc("${name}失败，原因: 第" + rowIndex + "[行${param.comment}]不能为空");
					return result;
				}
					</#if>
					<#if param.format?? && param.format == "Long" >
				if (<#if param.required == false>StringUtils.isNotEmpty(record.get${param.name?cap_first}()) && </#if>!NumberUtils.isDigits(record.get${param.name?cap_first}())) {
					LogCvt.error("${name}失败，原因: 第" + rowIndex + "行[${param.comment}]类型不正确,必需输入整数");
					result.setResultCode(ResultCode.failed.getCode());
					result.setResultDesc("${name}失败，原因: 第" + rowIndex + "行[${param.comment}]类型不正确,必需输入整数");
					return result;
				}
					</#if>
					<#if param.format?? && param.format == "Integer" >
				if (<#if param.required == false>StringUtils.isNotEmpty(record.get${param.name?cap_first}()) && </#if>!NumberUtils.isDigits(record.get${param.name?cap_first}())) {
					LogCvt.error("${name}失败，原因: 第" + rowIndex + "行[${param.comment}]类型不正确,必需输入整数");
					result.setResultCode(ResultCode.failed.getCode());
					result.setResultDesc("${name}失败，原因: 第" + rowIndex + "行[${param.comment}]类型不正确,必需输入整数");
					return result;
				}
					</#if>
					<#if param.format?? && param.format == "Number" >
				if (<#if param.required == false>StringUtils.isNotEmpty(record.get${param.name?cap_first}()) && </#if>!NumberUtils.isNumber(record.get${param.name?cap_first}())) {
					LogCvt.error("${name}失败，原因: 第" + rowIndex + "行[${param.comment}]类型不正确,必需输入数值");
					result.setResultCode(ResultCode.failed.getCode());
					result.setResultDesc("${name}失败，原因: 第" + rowIndex + "行[${param.comment}]类型不正确,必需输入数值");
					return result;
				}
					</#if>
					<#if param.length??>
				if (<#if param.required == false>StringUtils.isNotEmpty(record.get${param.name?cap_first}()) && </#if>record.get${param.name?cap_first}().length() > ${param.length}) {
					LogCvt.error("${name}失败，原因: 第" + (i+1) + "行[${param.comment}]长度不正确,最多允许输入${param.length}个字符");
					resMap.put(ResultEnum.FAIL.getCode(), EnumTypes.illlegal.getCode());
					resMap.put(ResultEnum.MESSAGE.getCode(), "${name}失败，原因: 第" + (i+1) + "行[${param.comment}]长度不正确,最多允许输入${param.length}个字符");
					return resMap;
				}
					</#if>
				</#list>
				rowIndex++;
			}
			//调用Process
			
			result = ${serverName?uncap_first}Logic.${path}(
				 ${serverInputBeanName?uncap_first}.getClientId() //客户端ID
				,${serverInputBeanName?uncap_first}.getUpdateUser() //当前操作者
				,recordList
			);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc("${name}成功");
			return result;
		} catch (Exception e) {
			LogCvt.error("${name}失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("${name}失败，原因:" + e.getMessage());
			return result;
		} finally {
			long endTime = System.currentTimeMillis();
			LogCvt.debug("${name}耗时" + (endTime - startTime) + "--startTime:" + startTime + "---endTime:" + endTime);
		}
	}
}


