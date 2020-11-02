package com.kaiwait.core.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiwait.common.exception.ValidateException;
import com.kaiwait.common.utils.StringUtil;
import com.kaiwait.common.vo.json.web.RequestMeta;
import com.kaiwait.common.vo.json.web.ResponseMeta;
import com.kaiwait.core.process.dynamic.parser.InterfaceDefinitionNotFoundException;
import com.kaiwait.core.utils.RequestUtil;
import com.kaiwait.core.validator.Validator;

/**
 * Http拦截器 - Http请求和返回结果处理
 * 
 * @ClassName: HttpInterceptor
 */
public class ValidateInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(ValidateInterceptor.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private Map<String, Validator> uriValidatorMapping;

	/**
	 * 最后执行，可用于释放资源
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception e)
			throws Exception {
	}

	/**
	 * 生成视图之前执行
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * Action之前执行
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestContorllerPath = RequestUtil.getRequestControllerPath(request);
		RequestUtil.setValidateIgnored(request, "没有为请求配置校验规则");
		if (uriValidatorMapping != null) {
			for (Entry<String, Validator> mapping : uriValidatorMapping.entrySet()) {
				if (requestContorllerPath.startsWith(mapping.getKey())) {
					RequestMeta requestMeta = RequestUtil.getRequestMeta(request);
					LOG.debug("[>>>,{}]validator invoke begin. request:[{}] rule:[{}] validator:[{}]", requestMeta.getRequestId(), requestContorllerPath, mapping.getKey(),
							mapping.getValue().getClass().getName());
					try {
						String result = mapping.getValue().validate(mapping.getKey(), request, response);
						if (StringUtil.isNotEmpty(result)) {
							LOG.debug("[<<<,{}]validate failed.", requestMeta.getRequestId());
							response.setContentType("application/json;charset=UTF-8");
							response.getWriter().write(result);
							response.flushBuffer();
							invalidateRequest("200", result, request);
							return false;
						} else {
							RequestUtil.setValidateIgnored(request, null);
							LOG.debug("[<<<,{}]validate successful.", requestMeta.getRequestId());
							return true;
						}
					} catch (InterfaceDefinitionNotFoundException e) {
						RequestUtil.setValidateIgnored(request, e.getMessage());
						LOG.warn("[<<<,{}]validate ignored.{}", requestMeta.getRequestId(), e.getMessage());
						return true;
					} catch (ValidateException e) {
						LOG.error("[err,{}]{}", requestMeta.getRequestId(), e.getMessage(), e);
						response.setStatus(HttpStatus.BAD_REQUEST.value());
						invalidateRequest("400", null, request);
						return false;
					}
				}
			}
		}
		return true;
	}
	
	
	
	public void invalidateRequest(String code, String result, HttpServletRequest request) throws IOException {
		long currentTimeMillis = System.currentTimeMillis();
		RequestMeta reqMeta = RequestUtil.getRequestMeta(request);
		if (reqMeta == null) {
			return;
		} else {
			ResponseMeta responseMeta = new ResponseMeta();
			responseMeta.setRequestId(reqMeta.getRequestId());
			responseMeta.setHttpStatusCode(code);
			responseMeta.setTimestamp(currentTimeMillis);
			responseMeta.setUseTimeMs(currentTimeMillis - reqMeta.getTimestamp());
			String responseMetaJsonStr = OBJECT_MAPPER.writeValueAsString(responseMeta);
			String responseContentJsonStr = result;
			StringBuilder sb = new StringBuilder(2048);
			sb.append("{\"respMeta\":");
			sb.append(responseMetaJsonStr);
			sb.append(",\"respContent\":");
			sb.append(responseContentJsonStr);
			sb.append("}");
			String responseMessage = sb.toString();
			LOG.debug("[<<<,{},{}]{}", reqMeta.getRequestId(), responseMessage.getBytes().length, responseMessage);
		}
	}

	public Map<String, Validator> getUriValidatorMapping() {
		return uriValidatorMapping;
	}

	public void setUriValidatorMapping(Map<String, Validator> uriValidatorMapping) {
		this.uriValidatorMapping = uriValidatorMapping;
	}

	
}
