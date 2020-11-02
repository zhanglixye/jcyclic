package com.kaiwait.core.controller.single;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiwait.common.constants.NullObject;
import com.kaiwait.common.exception.BusinessException;
import com.kaiwait.common.exception.JsonReturnBusinessException;
import com.kaiwait.common.exception.ValidateException;
import com.kaiwait.common.utils.ExceptionUtil;
import com.kaiwait.common.utils.InstanceUtils;
import com.kaiwait.common.utils.StringUtil;
import com.kaiwait.common.vo.json.base.MessageWarp;
import com.kaiwait.common.vo.json.base.Result;
import com.kaiwait.common.vo.json.server.BaseInputBean;
import com.kaiwait.common.vo.json.server.InputMeta;
import com.kaiwait.common.vo.json.server.OutputMeta;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.core.utils.SpringUtil;

@Controller
@RequestMapping("/json")
public class JsonSingleFunctionController {
	private static final Logger LOG = LoggerFactory.getLogger(JsonSingleFunctionController.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@RequestMapping(value = "/{className}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> dispatcher(@PathVariable String className, HttpServletRequest request) {
		if (StringUtils.isEmpty(className)) {
			LOG.warn("Json请求的URI不正确！请求的类为空");
			return onBadRequest();
		}

		int contentLength = request.getContentLength();
		if (contentLength <= 0) {
			LOG.warn("获取Json请求内容时发生异常！，请求内容为空");
			return onBadRequest();
		}
		
		String jsonRequestContent;
		try (ServletInputStream inputStream = request.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream(contentLength);) {
			byte[] buffer = new byte[1024];
			int readTotal = 0;
			while (true) {
				int read = inputStream.read(buffer);
				if (read > 0) {
					readTotal += read;
					baos.write(buffer, 0, read);
					if (readTotal >= contentLength) {
						break;
					}
				} else if (read == 0) {
					Thread.sleep(500);
				} else {
					break;
				}
			}
			jsonRequestContent = new String(baos.toByteArray(), "UTF-8");
			LOG.debug("获取json请求内容成功:");
			LOG.debug(jsonRequestContent);
		} catch (Throwable e) {
			LOG.error("获取Json请求内容时发生异常！", e);
			return onBadRequest();
		}

		JsonNode readTree;
		InputMeta metaInfo;
		try {
			readTree = OBJECT_MAPPER.readTree(jsonRequestContent);
			JsonNode jsonMetaNode = readTree.get(MessageWarp.META_FIELD);
			String metaTextValue = jsonMetaNode.toString();
			metaInfo = OBJECT_MAPPER.readValue(metaTextValue, InputMeta.class);
		} catch (Throwable e) {
			LOG.warn("将Json串转换为对象时发生异常！", e);
			return onBadRequest();
		}
//		final String dataTypeStr = metaInfo.getOrgId();
//		if (StringUtil.isEmpty(dataTypeStr)) {
//			LOG.warn("调用SingleFunctionIF接口必需传递一个参数！");
//			return onBadRequest();
//		}
		final String orgId = metaInfo.getOrgId();
		if (StringUtil.isEmpty(orgId)) {
			LOG.warn("调用SingleFunctionIF接口必需传递机构标识！");
			return onBadRequest();
		}
		final String custId = metaInfo.getCustId();
		if (StringUtil.isEmpty(custId)) {
			LOG.warn("调用SingleFunctionIF接口必需传递用户标识！");
			return onBadRequest();
		}
		
		@SuppressWarnings("rawtypes")
		SingleFunctionIF instance;

		try {
			instance = SpringUtil.getBean(className, SingleFunctionIF.class);
		} catch (NoSuchBeanDefinitionException e) {
			LOG.warn("Spring上下文中没找到处理类：" + className, e);
			return onBadRequest();
		} catch (Throwable e) {
			LOG.error("实例化目标对象时发生异常！", e);
			return onErrorRequest();
		}

		BaseInputBean inputData = null;
//		if (!NullObject.class.getName().equals(dataTypeStr)) {
			try {
				JsonNode jsonDataNode = readTree.get(MessageWarp.DATA_FIELD);
				String dataTextValue = jsonDataNode.toString();
				Class<?> paramType = instance.getParamType();
				inputData = (BaseInputBean)OBJECT_MAPPER.readValue(dataTextValue, paramType);
				inputData.setCustId(custId);
				inputData.setOrgId(orgId);
			} catch (ClassCastException e) {
				LOG.error("SingleFunctionIF接口的入参必需是BaseInputBean的子类！", e);
				return onErrorRequest();
			} catch (Throwable e) {
				LOG.warn("将Json串转换为对象时发生异常！", e);
				return onBadRequest();
			}
//		} else {
//			inputData = null;
//		}
		
		Object returnValue = null;
		MessageWarp returnJsonObject = new MessageWarp();
		OutputMeta outputMeta = new OutputMeta();
		returnJsonObject.setMetaInfo(outputMeta);

		try {
			Method method = InstanceUtils.getFirstMethod(instance.getClass().getName(), SingleFunctionIF.VALIDATE_METHOD);
			ValidateResult validateResult = (ValidateResult)exec(instance, method, inputData);
			if (validateResult == null) {
				method = InstanceUtils.getFirstMethod(instance.getClass().getName(), SingleFunctionIF.PROCESS_METHOD);
				returnValue = exec(instance, method, inputData);
				
				outputMeta.setResult(Result.OK);
				if (returnValue != null) {
					outputMeta.setDataType(returnValue.getClass().getCanonicalName());
				}
				returnJsonObject.setData(returnValue);
			} else {
				//TODO 验证没通过
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
					outputMeta.setDataType(ValidateException.class.getName());
					returnJsonObject.setData(validateException);
					LOG.debug("数据有效性检查未通过！" + validateException.getMessage());
				} else {
					BusinessException businessException = ExceptionUtil.find(ex, BusinessException.class);
					if (businessException != null) {
						outputMeta.setResult(Result.BUSINESS_ERROR);
						outputMeta.setMessage(businessException.getMessage());
						outputMeta.setDataType(businessException.getClass().getName());
						returnJsonObject.setData(businessException);
						LOG.error("业务处理过程发生异常！");
					} else {
						LOG.error("执行Json请求时发生异常！");
						return onErrorRequest();
					}
				}
			}
		}

		try {
			String writeValueAsString = OBJECT_MAPPER.writeValueAsString(returnJsonObject);
			LOG.debug(writeValueAsString);
			return onSuccessRequest(writeValueAsString);
		} catch (Throwable e) {
			LOG.error("将执行结果对象转换为Json字符串时发生异常！", e);
			return onErrorRequest();
		}
	}

	@SuppressWarnings("rawtypes")
	private Object exec(SingleFunctionIF targetInstance, Method method, Object inputData) throws Throwable {
		
		final String classFullName = targetInstance.getClass().getCanonicalName();
		
	
		LOG.debug(">>>" + classFullName + "." + method.getName());
		
		Object returnValue = null;
//		try {
			//执行方法
			returnValue = method.invoke(targetInstance, inputData);
//		} catch (Throwable throwable) {
//			LOG.error(throwable.getMessage());
//		}
		LOG.debug("<<<" + classFullName + "." + method.getName());
		
		return returnValue;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void execValidate(SingleFunctionIF targetInstance, Object inputData) {
		final String classFullName = targetInstance.getClass().getCanonicalName();
		LOG.debug(">>>" + classFullName + "." + SingleFunctionIF.VALIDATE_METHOD);
		targetInstance.validate(inputData);
		LOG.debug("<<<" + classFullName + "." + SingleFunctionIF.VALIDATE_METHOD);
	}

	private ResponseEntity<String> onBadRequest() {
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>("", httpHeaders, HttpStatus.BAD_REQUEST);
		return responseEntity;
	}

	private ResponseEntity<String> onErrorRequest() {
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>("", httpHeaders,
				HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}

	private ResponseEntity<String> onSuccessRequest(String jsonStr) {
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(jsonStr, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}
}
