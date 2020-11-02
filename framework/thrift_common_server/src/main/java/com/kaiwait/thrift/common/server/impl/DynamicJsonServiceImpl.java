/**
 * 
 */
package com.kaiwait.thrift.common.server.impl;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiwait.common.constants.LogConstant;
import com.kaiwait.common.constants.NullObject;
import com.kaiwait.common.exception.BusinessException;
import com.kaiwait.common.exception.JsonReturnBusinessException;
import com.kaiwait.common.exception.ValidateException;
import com.kaiwait.common.utils.ExceptionUtil;
import com.kaiwait.common.utils.InstanceUtils;
import com.kaiwait.common.utils.InstanceUtils.ReflectionException;
import com.kaiwait.common.utils.StringUtil;
import com.kaiwait.common.vo.json.base.MessageWarp;
import com.kaiwait.common.vo.json.base.Result;
import com.kaiwait.common.vo.json.server.BaseInputBean;
import com.kaiwait.common.vo.json.server.InputMeta;
import com.kaiwait.common.vo.json.server.OutputMeta;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.core.utils.SpringUtil;
import com.kaiwait.thrift.common.server.DynamicJsonService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;

/**
 * @author wings
 *
 */
public class DynamicJsonServiceImpl implements DynamicJsonService.Iface {
	private static final Logger LOG = LoggerFactory.getLogger(DynamicJsonServiceImpl.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final String THIS_METOD = DynamicJsonServiceImpl.class.getCanonicalName() + ".invoke";
	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Override
	public String invoke(String jsonMsg) throws TException {
		long beginTime = System.currentTimeMillis();
		if (LOG.isDebugEnabled()) {
			LOG.debug(">>>>>>{} param:{}", THIS_METOD, jsonMsg);
		} else {
			LOG.info(">>>>>>{}", THIS_METOD);
		}
		try {
			if (StringUtil.isEmpty(jsonMsg)) {
				return "";
			}
			JsonNode readTree;
			InputMeta metaInfo;
			try {
				readTree = OBJECT_MAPPER.readTree(jsonMsg);
				JsonNode jsonMetaNode = readTree.get(MessageWarp.META_FIELD);
				String metaTextValue = jsonMetaNode.toString();
				metaInfo = OBJECT_MAPPER.readValue(metaTextValue, InputMeta.class);
				MDC.put(LogConstant.REQEUST_ID, metaInfo.getRequestId());
			} catch (Throwable e) {
				LOG.warn("将Json串转换为对象时发生异常！", e);
				throw new TException(e);
			}
			final String orgId = metaInfo.getOrgId();
			// if (StringUtil.isEmpty(orgId)) {
			// LOG.warn("调用SingleFunctionIF接口必需传递机构标识！");
			// throw new TException("调用SingleFunctionIF接口必需传递机构标识！");
			// }
			final String custId = metaInfo.getCustId();
			// if (StringUtil.isEmpty(custId)) {
			// LOG.warn("调用SingleFunctionIF接口必需传递用户标识！");
			// throw new TException("调用SingleFunctionIF接口必需传递用户标识！");
			// }
			final String companyID = metaInfo.getCompanyID();
			final String userID = metaInfo.getUserID();
			
			@SuppressWarnings("rawtypes")
			SingleFunctionIF instance;
			String className = metaInfo.getTargetServiceName();
			if (!SpringUtil.containsBean(className)) {
				LOG.warn("Spring上下文中没找到处理类：" + className);
				return onErrorRequest(metaInfo, beginTime);
			}
			try {
				instance = SpringUtil.getBean(className, SingleFunctionIF.class);
			} catch (Throwable e) {
				LOG.error("实例化目标对象时发生异常！", e);
				return onErrorRequest(metaInfo, beginTime);
			}
			
			// 权限控制
			try {
				Privilege privilegeAnnotation = InstanceUtils.getClassAnnotation(instance.getClass(), Privilege.class);
				boolean hasPrivilege = true;
				if (privilegeAnnotation != null) {
					String[] keys = privilegeAnnotation.keys();
					if (keys != null && keys.length > 0) {
						// 获取当前用户所拥有的权限key
						List<String> userPrivileges = getRoleIdList(metaInfo);
						//检查用户权限
						for(String privilegeKey :keys) {
							if (userPrivileges.contains(privilegeKey)) {
								if (privilegeAnnotation.match().equals(MatchEnum.ANY)) {
									hasPrivilege = true;
									break;
								}
							} else {
								hasPrivilege = false;
								if (privilegeAnnotation.match().equals(MatchEnum.ALL)) {
									break;
								}
							}
						}
						if (!hasPrivilege) {
							return onPrivilegeException(metaInfo, beginTime);
						}
					}
					
					
				}
			} catch (ReflectionException e) {
				LOG.error("获取目标对象的权限注解时发生异常！", e);
				return onErrorRequest(metaInfo, beginTime);
			}
			
			BaseInputBean inputData = null;
			try {
				JsonNode jsonDataNode = readTree.get(MessageWarp.DATA_FIELD);
				LOG.debug(">>>>>>userNameaaaaaaa", THIS_METOD,readTree.get(MessageWarp.DATA_FIELD) );
				String dataTextValue = jsonDataNode.toString();
				Class<?> paramType = instance.getParamType();
				inputData = (BaseInputBean) OBJECT_MAPPER.readValue(dataTextValue, paramType);
				inputData.setCustId(custId);
				inputData.setOrgId(orgId);
				inputData.setCompanyID(companyID);
				inputData.setUserID(userID);
				inputData.setRequestId(metaInfo.getRequestId());
				
			} catch (ClassCastException e) {
				LOG.error("SingleFunctionIF接口的入参必需是BaseInputBean的子类！", e);
				return onErrorRequest(metaInfo, beginTime);
			} catch (Throwable e) {
				LOG.warn("将Json串转换为对象时发生异常！", e);
				return onErrorRequest(metaInfo, beginTime);
			}

			Object returnValue = null;
			MessageWarp returnJsonObject = new MessageWarp();
			OutputMeta outputMeta = new OutputMeta();
			returnJsonObject.setMetaInfo(outputMeta);

			try {
				Method method = InstanceUtils.getFirstMethod(instance.getClass().getName(),
						SingleFunctionIF.VALIDATE_METHOD);
				
				ValidateResult validateResult = (ValidateResult) exec(metaInfo, instance, method, inputData);
				if (validateResult == null) {
					method = InstanceUtils.getFirstMethod(instance.getClass().getName(),
							SingleFunctionIF.PROCESS_METHOD);
					returnValue = exec(metaInfo, instance, method, inputData);

					outputMeta.setResult(Result.OK);
					if (returnValue != null) {
						outputMeta.setDataType(returnValue.getClass().getCanonicalName());
					}
					returnJsonObject.setData(returnValue);
				} else {
					// TODO 验证没通过
					outputMeta.setResult(Result.VALIDATE_ERROR);
					outputMeta.setMessage(validateResult.getErrorMessage());
					outputMeta.setDataType(NullObject.class.getName());
				}
			} catch (Throwable ex) {
				JsonReturnBusinessException jsonReturnBusinessException = ExceptionUtil.find(ex,
						JsonReturnBusinessException.class);
				if (jsonReturnBusinessException != null) {
					outputMeta.setResult(Result.OK);
					outputMeta.setMessage(jsonReturnBusinessException.getMessage());
					final Object returnData = jsonReturnBusinessException.getData();
					returnJsonObject.setData(returnData);
					outputMeta.setDataType(returnData.getClass().getName());
				} else {
					ValidateException validateException = ExceptionUtil.find(ex, ValidateException.class);
					if (validateException != null) {
						outputMeta.setResult(Result.VALIDATE_ERROR);
						outputMeta.setMessage(validateException.getMessage());
						outputMeta.setDataType(NullObject.class.getName());
						LOG.info("数据有效性检查未通过！" + validateException.getMessage());
					} else {
						BusinessException businessException = ExceptionUtil.find(ex, BusinessException.class);
						if (businessException != null) {
							outputMeta.setResult(Result.BUSINESS_ERROR);
							outputMeta.setMessage("业务处理过程发生异常！");
							outputMeta.setDataType(NullObject.class.getName());
							LOG.error("业务处理过程发生异常！", businessException);
						} else {
							LOG.error("执行Json请求时发生异常！");
							return onErrorRequest(metaInfo, beginTime);
						}
					}
				}
			}

			try {
				String writeValueAsString = OBJECT_MAPPER.writeValueAsString(returnJsonObject);
				if (LOG.isDebugEnabled()) {
					LOG.debug("<<<<<<{} useTime:{}ms return:{}", THIS_METOD, System.currentTimeMillis() - beginTime,
							writeValueAsString);
				} else {
					LOG.info("<<<<<<{} useTime:{}ms", THIS_METOD, System.currentTimeMillis() - beginTime);
				}
				return writeValueAsString;
			} catch (Throwable e) {
				LOG.error("将执行结果对象转换为Json字符串时发生异常！", e);
				return onErrorRequest(metaInfo, beginTime);
			}
		} finally {
			MDC.clear();
		}
	}

	/**
	 * 获取用户所拥有的所有权限
	 * @param metaInfo
	 * @return 用户所拥有的所有权限id列表
	 */
	private List<String> getRoleIdList(InputMeta metaInfo)  {
		List<String> userPrivileges = new LinkedList<String>();
		String roleListString = metaInfo.getRoleList();
		if (StringUtil.isNotBlank(roleListString)) {
			try {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> roleList = OBJECT_MAPPER.readValue(roleListString,List.class);
				for(Map<String, Object> role : roleList) {
					Object nodeId = role.get("nodeID");
					if (nodeId != null) {
						userPrivileges.add(nodeId.toString());
					}
				}
			} catch( Exception e) {
				LOG.error("转换用户权限发生异常", e);
			} 
		}
		return userPrivileges;
	}

	@SuppressWarnings("rawtypes")
	private Object exec(InputMeta metaInfo, SingleFunctionIF targetInstance, Method method, Object inputData)
			throws Throwable {

		final String classFullName = targetInstance.getClass().getCanonicalName();
		long beginTime = System.currentTimeMillis();
		if (LOG.isDebugEnabled()) {
			LOG.debug(">>>{}.{} param:{}", classFullName, method.getName(),
					OBJECT_MAPPER.writeValueAsString(inputData));
		} else {
			LOG.info(">>>{}.{}", classFullName, method.getName());
		}
		Object returnValue = null;
		// try {
		// 执行方法
		returnValue = method.invoke(targetInstance, inputData);
		// } catch (Throwable throwable) {
		// LOG.error(throwable.getMessage());
		// }
		if (LOG.isDebugEnabled()) {
			LOG.debug("<<<{}.{} useTime:{}ms return:{}", classFullName, method.getName(),
					System.currentTimeMillis() - beginTime, OBJECT_MAPPER.writeValueAsString(returnValue));
		} else {
			LOG.info("<<<{}.{} useTime:{}ms", classFullName, method.getName(),
					System.currentTimeMillis() - beginTime);
		}
		return returnValue;
	}

	private String onErrorRequest(InputMeta metaInfo, long beginTime) throws TException {
		MessageWarp returnJsonObject = new MessageWarp();
		OutputMeta outputMeta = new OutputMeta();
		returnJsonObject.setMetaInfo(outputMeta);
		outputMeta.setResult(Result.SYSTEM_ERROR);
		String responseMetaJsonStr;
		try {
			responseMetaJsonStr = OBJECT_MAPPER.writeValueAsString(returnJsonObject);
			if (LOG.isDebugEnabled()) {
				LOG.debug("[<<<<<<{} useTime:{}ms return:{}", THIS_METOD, System.currentTimeMillis() - beginTime,
						responseMetaJsonStr);
			} else {
				LOG.info("[<<<<<<{} useTime:{}ms", THIS_METOD, System.currentTimeMillis() - beginTime);
			}
			return responseMetaJsonStr;
		} catch (JsonProcessingException e) {
			throw new TException(e);
		}
	}
	
	private String onPrivilegeException(InputMeta metaInfo, long beginTime) throws TException {
		MessageWarp returnJsonObject = new MessageWarp();
		OutputMeta outputMeta = new OutputMeta();
		returnJsonObject.setMetaInfo(outputMeta);
		outputMeta.setResult(Result.VALIDATE_ERROR);
		outputMeta.setResult(Result.VALIDATE_ERROR);
		outputMeta.setMessage("没有执行该操作的权限");
		outputMeta.setDataType(NullObject.class.getName());
		LOG.info("权限检查未通过！" );
		String responseMetaJsonStr;
		try {
			responseMetaJsonStr = OBJECT_MAPPER.writeValueAsString(returnJsonObject);
			if (LOG.isDebugEnabled()) {
				LOG.debug("[<<<<<<{} useTime:{}ms return:{}", THIS_METOD, System.currentTimeMillis() - beginTime,
						responseMetaJsonStr);
			} else {
				LOG.info("[<<<<<<{} useTime:{}ms", THIS_METOD, System.currentTimeMillis() - beginTime);
			}
			return responseMetaJsonStr;
		} catch (JsonProcessingException e) {
			throw new TException(e);
		}
	}

}
