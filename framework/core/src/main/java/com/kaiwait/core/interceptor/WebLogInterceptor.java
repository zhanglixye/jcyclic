package com.kaiwait.core.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiwait.common.vo.json.web.RequestMeta;
import com.kaiwait.common.vo.json.web.ResponseMeta;
import com.kaiwait.core.utils.RequestUtil;
import com.kaiwait.utils.common.StringUtil;

/**
 * Http拦截器 - Http请求和返回结果处理
 * 
 * @ClassName: HttpInterceptor
 */
public class WebLogInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(WebLogInterceptor.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	/**
	 * 最后执行，可用于释放资源
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception e)
			throws Exception {
		if (e == null) {
			return;
		}
		RequestMeta reqMeta = (RequestMeta) RequestUtil.getRequestMeta(request);
		if (reqMeta == null) {
			return;
		} else {
			long currentTimeMillis = System.currentTimeMillis();
			ResponseMeta responseMeta = new ResponseMeta();
			responseMeta.setRequestId(reqMeta.getRequestId());
			responseMeta.setHttpStatusCode("500");
			responseMeta.setTimestamp(currentTimeMillis);
			responseMeta.setUseTimeMs(currentTimeMillis - reqMeta.getTimestamp());
			String responseMetaJsonStr = OBJECT_MAPPER.writeValueAsString(responseMeta);
			String responseContentJsonStr = null;
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

	/**
	 * 生成视图之前执行
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		long currentTimeMillis = System.currentTimeMillis();
		RequestMeta reqMeta = (RequestMeta) RequestUtil.getRequestMeta(request);
		if (reqMeta == null) {
			return;
		} else {
			
			ResponseMeta responseMeta = new ResponseMeta();
			responseMeta.setRequestId(reqMeta.getRequestId()); 
			String responseHttpStatusCode = RequestUtil.getResponseHttpStatusCode(request);
			responseMeta.setHttpStatusCode(responseHttpStatusCode == null ? "200" : responseHttpStatusCode);
			responseMeta.setTimestamp(currentTimeMillis);
			responseMeta.setUseTimeMs(currentTimeMillis - reqMeta.getTimestamp());
			String responseMetaJsonStr = OBJECT_MAPPER.writeValueAsString(responseMeta);
			String responseContentJsonStr;
			String responseJson = RequestUtil.getResponseJson(request);
			if (responseJson == null) {
				responseContentJsonStr = OBJECT_MAPPER.writeValueAsString(modelAndView);
			} else {
				responseContentJsonStr = responseJson;
				JsonNode readTree = OBJECT_MAPPER.readTree(responseJson);
				HttpSession session = request.getSession(false);
				if(readTree.get("metaInfo").get("result").toString().equals("SYSTEM_ERROR") && session != null)
				{
					session.invalidate();
				}
			}
			
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

	/**
	 * Action之前执行
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!"application/json".equals(request.getHeader("Content-Type"))) {
			//不是Json请求,跳过
			return true;
		}
		//请求的元数据
		RequestMeta reqMeta = getRequestMeta(request);
		
		String reqMetaJsonStr = OBJECT_MAPPER.writeValueAsString(reqMeta);
		
		//请求的内容
		String reqContentJsonStr = getReqContent(request);
		if (reqContentJsonStr.length() == 0) {
			reqContentJsonStr = null;
		}
		
		//http请求参数
		Map<String, Object> paraValuesMap = RequestUtil.getParaValuesMap(request);
		String reqParamJsonStr = OBJECT_MAPPER.writeValueAsString(paraValuesMap);
		
		StringBuilder sb = new StringBuilder(2048);
		sb.append("{\"reqMeta\":");
		sb.append(reqMetaJsonStr);
		sb.append(",\"reqContent\":");
		sb.append(reqContentJsonStr);
		sb.append(",\"reqParam\":");
		sb.append(reqParamJsonStr);
		sb.append("}");
		String reqMessage = sb.toString();
		LOG.debug("[>>>,{},{}]{}", reqMeta.getRequestId(), reqMessage.getBytes().length, reqMessage);
		RequestUtil.setRequestMeta(request, reqMeta);
		return true;

	}

	private RequestMeta getRequestMeta(HttpServletRequest request) throws JsonProcessingException {
		//消息到达的时间戳
		long timestamp = System.currentTimeMillis();
		RequestMeta requestMeta = new RequestMeta();
		//服务端生成的请求的唯一标识
		String requestId = UUID.randomUUID().toString();
		requestMeta.setRequestId(requestId);
		//用户标识,如用户未登录,该值为空
		String custId = getCustId(request);
		requestMeta.setCustId(custId);
		//用户ID
		String userID = getUserID(request);
		requestMeta.setUserID(userID);
		//公司ID
		String companyID = getCompanyID(request);
		requestMeta.setCompanyID(companyID);
		//会话ID
		String sessionId = getSessionId(request);
		requestMeta.setSessionId(sessionId);
		//服务器Ip
		String serverIp = getServerIp();
		requestMeta.setServerIp(serverIp);
		//客户端IP
		String remoteIp = getRemoteIp(request);
		requestMeta.setRemoteIp(remoteIp);
		//消息到达的时间戳
		requestMeta.setTimestamp(timestamp);
		//客户来源标识
		String clientId = getClientId(request);
		requestMeta.setClientId(clientId);
		
		
		String itmLD = getUserInfo(request,"itmLD");
		requestMeta.setItmLD(itmLD);
		String pointNumber = getUserInfo(request,"pointNumber");
		requestMeta.setPointNumber(pointNumber);
		String moneyzc = getUserInfo(request,"moneyzc");
		requestMeta.setMoneyzc(moneyzc);
		String moneyen = getUserInfo(request,"moneyen");
		requestMeta.setMoneyen(moneyen);
		String moneyzt = getUserInfo(request,"moneyzt");
		requestMeta.setMoneyzt(moneyzt);
		String moneyjp = getUserInfo(request,"moneyjp");
		requestMeta.setMoneyjp(moneyjp);
		
		String departNamezc = getUserInfo(request,"departNamezc");
		requestMeta.setDepartNamezc(departNamezc);
		String departNameen = getUserInfo(request,"departNameen");
		requestMeta.setDepartNameen(departNameen);
		String departNamezt = getUserInfo(request,"departNamezt");
		requestMeta.setDepartNamezt(departNamezt);
		String departNamejp = getUserInfo(request,"departNamejp");
		requestMeta.setDepartNamejp(departNamejp);
		
		String departCD = getUserInfo(request,"departCD");
		requestMeta.setDepartCD(departCD);
		
		String roleList = getUserInfo(request,"uNodeList");
		requestMeta.setRoleList(roleList);
		
		String pageNodeList = getUserInfo(request,"pageNodeList");
		requestMeta.setPageNodeList(pageNodeList);
		
		String userCompanyList = getUserInfo(request,"userCompanyList");
		requestMeta.setUserCompanyList(userCompanyList);
		
		String systemlock = getUserInfo(request,"systemlock");
		requestMeta.setSystemlock(systemlock);
		
		String dateCompanyZone = getUserInfo(request,"dateCompanyZone");
		requestMeta.setDateCompanyZone(dateCompanyZone);
		
		String timeZoneType = getUserInfo(request,"timeZoneType");
		requestMeta.setTimeZoneType(timeZoneType);
		
		
		//客户标签
		String tag = getTag(request);
		requestMeta.setClientTag(tag);
		//客户端控件ID
		String buttonId = getButtonId(request);
		requestMeta.setButtonId(buttonId);
		//请求的Uri路径
		requestMeta.setReqUri(getRequestUri(request));
		return requestMeta;
	}

	private String getRequestUri(HttpServletRequest request) {
		return RequestUtil.getRequestUri(request);
	}

	private String getReqContent(HttpServletRequest request) throws IOException {
		byte[] requestBody = RequestUtil.getRequestBody(request, true);
		return new String(requestBody, "UTF-8");
	}

	private String getButtonId(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getTag(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getClientId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return (String)session.getAttribute("client_id");
		} else {
			return null;
		}
	}

	private String getRemoteIp(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	private String getServerIp() {
		// TODO Auto-generated method stub
		return null;
	}

	private String getSessionId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return session.getId();
		} else {
			return null;
		}
	}

	private String getCustId(HttpServletRequest request) {
		//edit by wangyan 2018.05.22
		/* 增加从请求头中获取custID，在action中作为条件，判断需要调用哪个处理服务
		 * 在MessageWarp中新增两个常量，与前台传递此参数做条件匹配
		HttpSession session = request.getSession(false);
		if (session != null) {
			return (String)session.getAttribute("client_id");
		} else {
			return null;
		}
		*/
		if(StringUtil.isNotEmpty(request.getHeader("requestName")) && !request.getHeader("requestName").equals(" "))
		{
			return request.getHeader("requestName");
		}else {
			return null;
		}
	}
	//增加获取用户ID by  wangyan  20180507
	private String getUserID(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return (String)session.getAttribute("userID");
		} else {
			return null;
		}
	}
	//增加获取公司ID by  wangyan  20180507
	private String getCompanyID(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return (String)session.getAttribute("companyID");
		} else {
			return null;
		}
	}
	//增加获取公司ID by  wangyan  20180507
	private String getUserInfo(HttpServletRequest request,String attName) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return (String)session.getAttribute(attName);
		} else {
			return null;
		}
	}
}
