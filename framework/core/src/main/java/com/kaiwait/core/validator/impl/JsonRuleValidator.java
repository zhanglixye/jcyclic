/**
 * 
 */
package com.kaiwait.core.validator.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;
import com.kaiwait.common.exception.ValidateException;
import com.kaiwait.common.utils.StringUtil;
import com.kaiwait.core.process.dynamic.parser.InterfaceDefinitionNotFoundException;
import com.kaiwait.core.process.dynamic.parser.InterfaceDefinitionParse;
import com.kaiwait.core.process.dynamic.vo.InterfaceDefinition;
import com.kaiwait.core.process.dynamic.vo.ParameterDefinition;
import com.kaiwait.core.process.dynamic.vo.ParameterFormatEnum;
import com.kaiwait.core.process.dynamic.vo.ParameterTypeEnum;
import com.kaiwait.core.utils.RequestUtil;

/**
 * @author wings
 *
 */
public class JsonRuleValidator extends AbstractValidator {
	private static final Logger LOG = LoggerFactory.getLogger(JsonRuleValidator.class);
	private static final ConcurrentLinkedHashMap<String, InterfaceDefinition> INTERFACE_DEFINITION_CACHE = new
		    ConcurrentLinkedHashMap.Builder<String, InterfaceDefinition>()
		            .maximumWeightedCapacity(1000).
		            weigher(Weighers.singleton()).build();
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private InterfaceDefinitionParse ifDefParse;
	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaiwait.core.validator.Validator#validate(java.lang.String,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	public String validate(String mappingPath, HttpServletRequest request, HttpServletResponse response)
			throws InterfaceDefinitionNotFoundException {
		String requestContorllerPath = RequestUtil.getRequestControllerPath(request);
		InterfaceDefinition ifDef;
		if (INTERFACE_DEFINITION_CACHE.containsKey(requestContorllerPath)) {
			ifDef = INTERFACE_DEFINITION_CACHE.get(requestContorllerPath);
			if (ifDef == null) {
				throw new InterfaceDefinitionNotFoundException("无法获取请求" + requestContorllerPath + "的接口定义");
			}
		} else {
			ifDef = ifDefParse.getInterfaceDefinition(requestContorllerPath);
			INTERFACE_DEFINITION_CACHE.put(requestContorllerPath, ifDef);
		}
		String jsonRequestContent;
		try {
			byte[] buffer = RequestUtil.getRequestBody(request, true);
			jsonRequestContent = new String(buffer, "UTF-8");
		} catch (Throwable e) {
			throw new ValidateException("获取Json请求内容时发生异常！", e);
		}
		String validateResult = validate(ifDef, jsonRequestContent);
		if (StringUtil.isNotBlank(validateResult)) {
			LOG.debug("接口定义文件[{}]中定义的校验规则未通过:{}", ifDef.getDefinitionFilePath(), validateResult);
			return onInvalidRequest(validateResult, request, response);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected String validate(InterfaceDefinition ifDef, String jsonRequestContent) {
		HashMap<String, Object> parseObject;
		if (StringUtil.isBlank(jsonRequestContent)) {
			parseObject = null;
		} else {
			try {
				parseObject = OBJECT_MAPPER.readValue(jsonRequestContent, HashMap.class);
			} catch (Throwable e) {
				throw new ValidateException("解析Json请求内容时发生异常！json内容:" + jsonRequestContent, e);
			}
		}
		String validateResult = validate(ifDef.getInParamList(), parseObject);
		return validateResult;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String validate(List<ParameterDefinition> paramDefList, Map<String, Object> inputMap) {
		if (paramDefList == null) {
			return null;
		}
		for (ParameterDefinition paramDef : paramDefList) {
			Object object;
			if (ParameterTypeEnum.Object.equals(paramDef.getType())) {
				if(paramDef!= null && inputMap != null && paramDef.getName() != null && !paramDef.getName().equals("input") )
				{
					object = inputMap.get(paramDef.getName());
					if(object == null)
					{
						object = inputMap;
					}
				}else {
					object = inputMap;
				}
				
			} else {
				object = inputMap.get(paramDef.getName());
			}
			if (object == null || object.equals("")) {
				if (paramDef.isRequired()) {
					return paramDef.getComment() + "不能为空";
				} else {
					continue;
				}
			}
			switch (paramDef.getType()) {
			case Array:
				if (!(object instanceof List)) {
					return "参数[" + paramDef.getComment() + "]类型错误";
				}
				List<Map> paramList = (List<Map>) object;
				for (Map map : paramList) {
					String result = validate(paramDef.getDetailParamList(), map);
					if (StringUtil.isNotBlank(result)) {
						return result;
					}
				}

				break;
			case Object:
				if (!(object instanceof Map)) {
					return "参数[" + paramDef.getComment() + "]类型错误";
				}
				
				String result2 = validate(paramDef.getDetailParamList(),(Map<String, Object>) object);
				if (StringUtil.isNotBlank(result2)) {
					return result2;
				}

				break;
			case String:

				String value = object.toString();
				Integer length = paramDef.getLength();
				if(paramDef.getDescription().equals("notCheckPoint"))
				{
					DecimalFormat df = null;
					df=	(DecimalFormat)NumberFormat.getInstance();
					if(paramDef.getDescription().equals("notCheckPoint"))
					{
						df.applyPattern("####0.00####");
					}
					BigDecimal value1 = new BigDecimal(value);
				    String value2 = df.format(value1);
				    String valueNotPoint = value2.replace(".", "");
					if (length != null) {
						if (valueNotPoint.length() > length) {
							return "参数[" + paramDef.getComment() + "]长度超过限制,最多输入" + length + "个字符";
						}else {
							value = value2;
						}
					}
				}else {
					if (length != null) {
						if (value.length() > length) {
							return "参数[" + paramDef.getComment() + "]长度超过限制,最多输入" + length + "个字符";
						}
					}
				}
				
				ParameterFormatEnum format = paramDef.getFormat();
				if (format != null) {
					switch (format) {
					case Integer:
						try {
							Integer.parseInt(value);
						} catch (Exception e) {
							return "参数[" + paramDef.getComment() + "]类型错误,只能输入整数";
						}
						break;
					case Long:
						try {
							Long.parseLong(value);
						} catch (Exception e) {
							return "参数[" + paramDef.getComment() + "]类型错误,只能输入整数";
						}
						break;
					case BigDecimal:
						try {
							new BigDecimal(value);
						} catch (Exception e) {
							return "参数[" + paramDef.getComment() + "]类型错误,请输入正确的数值";
						}
						break;
					case Date:
						String formatPattern = paramDef.getFormatPattern();
						if (StringUtil.isNotEmpty(formatPattern)) {
							try {
								if (formatPattern.length() != value.length()) {
									return "参数[" + paramDef.getComment() + "]格式错误,必需输入" + formatPattern + "格式的正确的日期";
								}
								SimpleDateFormat sf = new SimpleDateFormat(formatPattern);
								sf.setLenient(false);
								sf.parse(value);
							} catch (Exception e) {
								return "参数[" + paramDef.getComment() + "]格式错误,必需输入" + formatPattern + "格式的正确的日期";
							}
						}
						break;
					case Regular:
						String regEx = paramDef.getFormatPattern();
						if (StringUtil.isNotEmpty(regEx)) {
							Pattern pattern = Pattern.compile(regEx);
							Matcher matcher = pattern.matcher(value);
							// 字符串是否与正则表达式相匹配
							if (!matcher.matches()) {
								return "参数[" + paramDef.getComment() + "]格式错误";
							}
						}
						break;
					case Enums:
						String enums = paramDef.getFormatPattern();
						if (StringUtil.isNotEmpty(enums)) {
							String[] enumList = enums.split(";");
							boolean validFlag = false;
							for(String enumValue : enumList) {
								if (enumValue.equals(value)) {
									validFlag = true;
									break;
								}
							}
							if (!validFlag) {
								return "参数[" + paramDef.getComment() + "]只能输入["+ enums +"]其中一个";
							}
						}
						break;
					default:
						break;
					}
				}
				break;
			default:
				return "参数错误";
			}
		}
		return null;
	}

	public InterfaceDefinitionParse getIfDefParse() {
		return ifDefParse;
	}

	public void setIfDefParse(InterfaceDefinitionParse ifDefParse) {
		this.ifDefParse = ifDefParse;
	}

}
