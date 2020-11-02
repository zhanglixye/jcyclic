/**
 * 
 */
package com.kaiwait.core.controller.dynamic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiwait.common.vo.json.base.MessageWarp;
import com.kaiwait.common.vo.json.base.Result;
import com.kaiwait.common.vo.json.server.InputMeta;
import com.kaiwait.common.vo.json.server.OutputMeta;
import com.kaiwait.common.vo.json.web.RequestMeta;
import com.kaiwait.common.vo.json.web.WebOutputMeta;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.core.utils.RequestUtil;
import com.kaiwait.thrift.common.client.DynamicJsonServiceClient;
import com.kaiwait.utils.common.StringUtil;

import redis.clients.jedis.Jedis;

/**
 * @author wings
 *
 */
@Controller
@RequestMapping(value = "/dynamicout")
public class DynamicOutPdfController {
	private static final Logger LOG = LoggerFactory.getLogger(DynamicDefJsonController.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final String ERROR_RESPONSE_JSON = "{\"metaInfo\":{\"result\":\"SYSTEM_ERROR\",\"message\":\"系统异常\"},\"data\":null}";
	private static final String ERROR_LOGIN_JSON = "{\"metaInfo\":{\"result\":\"LOGIN_ERROR\",\"message\":\"系统异常\"},\"data\":null}";
	private static final String ERROR_REQUESTID_JSON = "{\"metaInfo\":{\"result\":\"REQUESTID_ERROR\",\"message\":\"系统异常\"},\"data\":null}";
	private static final String ERROR_REQUESTNAME_JSON = "{\"metaInfo\":{\"result\":\"REQUESTNAME_ERROR\",\"message\":\"系统异常\"},\"data\":null}";
	private static final String SYSTEM_LOCK_JSON = "{\"metaInfo\":{\"result\":\"SYSTEM_LOCK\",\"message\":\"系统异常\"},\"data\":null}";
	private static final String SYSTEM_UNLOCK_JSON = "{\"metaInfo\":{\"result\":\"SYSTEM_UNLOCK\",\"message\":\"系统异常\"},\"data\":null}";
	private String serverIP = "J-CYCLIC-LOGIC-NLB-18f2e0738305ee21.elb.cn-north-1.amazonaws.com.cn";
	//private String serverIP = "52.80.142.196";
	//private String serverIP = "localhost";
	//服务器IP地址
    private static String ADDR = "aec-j-cyclic.2g3uxh.ng.0001.cnn1.cache.amazonaws.com.cn";
    //端口
    private static int PORT = 6379;
    
	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	@RequestMapping(value = "/{action}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> dispatcher(@PathVariable String action, HttpServletRequest request) {
		String validateIgnoredMsg = RequestUtil.getValidateIgnored(request);
		if(!action.equals("logout"))
		{
			if (StringUtil.isNotEmpty(validateIgnoredMsg)) {
				return onBadRequest(request, "动态接口调用被禁止,原因:" + validateIgnoredMsg, null);
			}
		}
//		if (SpringUtil.containsBean(action)) {
//			return dynamicInvokeLocalService(action, request);
//		} else {
//			 return dynamicInvokeRestfulService(action,request);
//		}
		return dynamicInvokeThriftJsonService(action, request);
	}
	private ResponseEntity<String> dynamicInvokeThriftJsonService(String action, HttpServletRequest request) {
		//调用Server端
		@SuppressWarnings("resource")
		Jedis jedis = new Jedis(ADDR, PORT, 100000);
		String companySysLock = "";
		String responseJson = null;
		HttpSession session = request.getSession(false);
		WebOutputMeta webOutputMeta = null;
		try {
			byte[] requestBody = RequestUtil.getRequestBody(request, false);
			RequestMeta requestMeta = RequestUtil.getRequestMeta(request);
			companySysLock = "systemlock"+requestMeta.getCompanyID();
			System.out.println("companySysLock-----------------------"+companySysLock);
			if(jedis != null && jedis.get(companySysLock) != null && !action.equals("login"))
			{
				if((boolean)jedis.get(companySysLock).equals("lock"))
				{
					Boolean flg = RequestUtil.getSysLockFlg((String) session.getAttribute("roleStr"));
					if(flg)
					{
						return onErrorRequest(request, "system is locked...", null,SYSTEM_LOCK_JSON);
					}
				}else {
					if(action.equals("monthCheckOut"))
					{
						return onErrorRequest(request, "system is locked...", null,SYSTEM_UNLOCK_JSON);
					}
				}
			}
			
			/*
			if(session != null && session.getAttribute("systemlock") != null && !action.equals("login"))
			{
				if((boolean)session.getAttribute("systemlock").equals("lock"))
				{
					Boolean flg = RequestUtil.getSysLockFlg((String) session.getAttribute("roleStr"));
					if(flg)
					{
						return onErrorRequest(request, "system is locked...", null,SYSTEM_LOCK_JSON);
					}
				}else {
					if(action.equals("monthCheckOut"))
					{
						return onErrorRequest(request, "system is locked...", null,SYSTEM_UNLOCK_JSON);
					}
				}
			}
			*/
			if(!action.equals("login") && !action.equals("changePwd") && session == null)
			{
				
				return onErrorRequest(request, "没登陆", null,ERROR_LOGIN_JSON);
			}
			if(!action.equals("login") && !action.equals("changePwd") && session != null)
			{
				if(!session.getAttribute("requestID").equals(request.getHeader("requestID")))
				{
					session.invalidate();
					return onErrorRequest(request, "request is error", null,ERROR_REQUESTID_JSON);
				}
			}
			
			if(action.equals("logout") && session != null)
			{
				session.invalidate();
				webOutputMeta = new WebOutputMeta();
				webOutputMeta.setResult(Result.OK);
				webOutputMeta.setMessage("退出登录");
				StringBuilder sb = new StringBuilder();
				sb.append("{\"");
				sb.append(MessageWarp.META_FIELD);
				sb.append("\":");
				sb.append(OBJECT_MAPPER.writeValueAsString(webOutputMeta));
				sb.append("}");
				return onSuccessRequest(request, sb.toString());
			}else {
				InputMeta serverInputMeta = new InputMeta();
				serverInputMeta.setOrgId(requestMeta.getClientId());
				serverInputMeta.setRemoteIp(requestMeta.getServerIp());
				serverInputMeta.setRequestId(requestMeta.getRequestId());
				serverInputMeta.setSessionId(requestMeta.getSessionId());
				serverInputMeta.setCustId(requestMeta.getCustId());
				serverInputMeta.setUserID(requestMeta.getUserID());
				serverInputMeta.setCompanyID(requestMeta.getCompanyID());
				serverInputMeta.setRoleList(requestMeta.getRoleList());
				serverInputMeta.setTargetServiceName(action);
				serverInputMeta.setPageNodeList(requestMeta.getPageNodeList());
				String metaInfoJsonStr = OBJECT_MAPPER.writeValueAsString(serverInputMeta);
				StringBuilder sb = new StringBuilder(requestBody.length + 256);
				sb.append("{\"");
				sb.append(MessageWarp.META_FIELD);
				sb.append("\":");
				sb.append(metaInfoJsonStr);
				sb.append(",\"");
				sb.append(MessageWarp.DATA_FIELD);
				sb.append("\":");
				sb.append(new String(requestBody, "UTF-8"));
				sb.append("}");
				if(action.equals("changeColorByUser") && session != null)
				{
					JsonNode rt = OBJECT_MAPPER.readTree(sb.toString());
					session.removeAttribute("colorV");
					session.setAttribute("colorV",rt.get(MessageWarp.DATA_FIELD).path("colorV").textValue());
				}
				//判断请求头中的custID是否有值
				if(!StringUtil.isEmpty(requestMeta.getCustId()))
				{
					System.out.println("serverIP------------"+serverIP);
					switch (requestMeta.getCustId()) {
						case MessageWarp.REQUEST_MST_NAME:
							responseJson = DynamicJsonServiceClient.invoke(serverIP, 8081, 300000, sb.toString());
							break;
						case MessageWarp.REQUEST_JCZH_NAME:
							responseJson = DynamicJsonServiceClient.invoke(serverIP, 8082, 300000, sb.toString());
							break;
						case MessageWarp.REQUEST_JCJP_NAME:
							responseJson = DynamicJsonServiceClient.invoke(serverIP, 8083, 300000, sb.toString());
							break;
						default :
							return onErrorRequest(request, "requestName is error", null,ERROR_REQUESTNAME_JSON);
					}
				}else {
					return onErrorRequest(request, "requestName is error", null,ERROR_REQUESTNAME_JSON);
				}
			}
		} catch (Throwable e) {
			return onErrorRequest(request, "调用Server过程中发生异常！", e,ERROR_RESPONSE_JSON);
		}
		//转换结果
		try {
			
			OutputMeta serverOutputMeta;
			JsonNode readTree = OBJECT_MAPPER.readTree(responseJson);
			JsonNode jsonMetaNode = readTree.get(MessageWarp.META_FIELD);
			RequestMeta serverInputMeta = RequestUtil.getRequestMeta(request);
			String rsInUserInfo = "";
			
			if((action.equals("login") || action.equals("changeCompany")) && !readTree.get(MessageWarp.DATA_FIELD).isNull())
			{
				session = request.getSession();
				session.setAttribute("requestID", serverInputMeta.getRequestId());
				session.setAttribute("companyID", readTree.get(MessageWarp.DATA_FIELD).path("companyCD").textValue());
				session.setAttribute("userID",  readTree.get(MessageWarp.DATA_FIELD).path("userCD").textValue());
				session.setAttribute("client_id", "1");
				session.setAttribute("itmLD", readTree.get(MessageWarp.DATA_FIELD).path("itmLD").textValue());
				session.setAttribute("pointNumber", readTree.get(MessageWarp.DATA_FIELD).path("pointNumber").textValue());
				session.setAttribute("moneyzc", readTree.get(MessageWarp.DATA_FIELD).path("moneyzc").textValue());
				session.setAttribute("moneyen", readTree.get(MessageWarp.DATA_FIELD).path("moneyen").textValue());
				session.setAttribute("moneyzt", readTree.get(MessageWarp.DATA_FIELD).path("moneyzt").textValue());
				session.setAttribute("moneyjp", readTree.get(MessageWarp.DATA_FIELD).path("moneyjp").textValue());
				
				session.setAttribute("departNamezc", readTree.get(MessageWarp.DATA_FIELD).path("departNamezc").textValue());
				session.setAttribute("departNameen", readTree.get(MessageWarp.DATA_FIELD).path("departNameen").textValue());
				session.setAttribute("departNamezt", readTree.get(MessageWarp.DATA_FIELD).path("departNamezt").textValue());
				session.setAttribute("departNamejp", readTree.get(MessageWarp.DATA_FIELD).path("departNamejp").textValue());
				
				session.setAttribute("departCD", readTree.get(MessageWarp.DATA_FIELD).path("departCD").textValue());
				session.setAttribute("roleStr", readTree.get(MessageWarp.DATA_FIELD).path("roleStr").textValue());
				session.setAttribute("dateCompanyZone", readTree.get(MessageWarp.DATA_FIELD).path("dateCompanyZone").textValue());
				session.setAttribute("timeZoneType", readTree.get(MessageWarp.DATA_FIELD).path("timeZoneType").textValue());
				session.setAttribute("colorV", readTree.get(MessageWarp.DATA_FIELD).path("colorV").textValue());
				
				if(readTree.get(MessageWarp.DATA_FIELD).path("sysLockFlg").toString().equals("1"))
				{
					jedis.set(companySysLock, "lock");
					//session.setAttribute("systemlock", "lock");
				}else {
					jedis.set(companySysLock, "unlock");
					//session.setAttribute("systemlock", "unlock");
				}
				JsonNode nodeListNode = readTree.get(MessageWarp.DATA_FIELD).path("roleList");
				String nodeListStr = OBJECT_MAPPER.writeValueAsString(nodeListNode);
				session.setAttribute("uNodeList", nodeListStr.toString());
				
				JsonNode pageListNode = readTree.get(MessageWarp.DATA_FIELD).path("pageNodeList");
				String pageListStr = OBJECT_MAPPER.writeValueAsString(pageListNode);
				session.setAttribute("pageNodeList", pageListStr.toString());
				
				JsonNode userCompanyList = readTree.get(MessageWarp.DATA_FIELD).path("comapnyList");
				String userCompanyListStr = OBJECT_MAPPER.writeValueAsString(userCompanyList);
				session.setAttribute("userCompanyList", userCompanyListStr.toString());
				
			}
			
			if(!action.equals("login") && !action.equals("changePwd"))
			{
				session.removeAttribute("requestID");
				session.setAttribute("requestID", serverInputMeta.getRequestId());
			}
			
			String metaTextValue = jsonMetaNode.toString();
			serverOutputMeta = OBJECT_MAPPER.readValue(metaTextValue, OutputMeta.class);
			String lockStatus = "";
			switch(serverOutputMeta.getResult()) {
			case OK:
				if(action.equals("systemlock") && jedis != null)
				{
					if(readTree.get(MessageWarp.DATA_FIELD).intValue() == 0)
					{
						//session.removeAttribute("systemlock");
						//session.setAttribute("systemlock", "unlock");
						jedis.set(companySysLock, "unlock");
						lockStatus = "unlock";
					}else {
						//session.removeAttribute("systemlock");
						//session.setAttribute("systemlock", "lock");
						jedis.set(companySysLock, "lock");
						lockStatus = "lock";
					}
				}
				if(lockStatus.equals("") && session != null)
				{
					lockStatus = (String)jedis.get(companySysLock);
				}
				webOutputMeta = new WebOutputMeta();
				webOutputMeta.setResult(Result.OK);
				webOutputMeta.setMessage(serverOutputMeta.getMessage());
				
				if(!action.equals("login") && session != null)
				{
					String colorV = (String)session.getAttribute("colorV");
					String dateCompanyZone = DateUtil.getNewTime(DateUtil.getDateForNow(DateUtil.dateTimeFormat), Integer.valueOf(serverInputMeta.getTimeZoneType())); 
					rsInUserInfo = "{\"userID\":\""+serverInputMeta.getUserID()+"\",\"companyID\":\""+serverInputMeta.getCompanyID()+"\",\"clientID\":\""+serverInputMeta.getCustId()+"\",\"requestID\":\""+serverInputMeta.getRequestId()+
									"\",\"itmLD\":\""+serverInputMeta.getItmLD()+"\",\"pointNumber\":\""+serverInputMeta.getPointNumber()+"\",\"moneyzc\":\""+serverInputMeta.getMoneyzc()+"\",\"moneyen\":\""+serverInputMeta.getMoneyen()+
									"\",\"departNamezc\":\""+serverInputMeta.getDepartNamezc()+"\",\"departNamejp\":\""+serverInputMeta.getDepartNamejp()+"\",\"departNamezt\":\""+serverInputMeta.getDepartNamezt()+"\",\"departNameen\":\""+serverInputMeta.getDepartNameen()+
									"\",\"moneyzt\":\""+serverInputMeta.getMoneyzt()+"\",\"moneyjp\":\""+serverInputMeta.getMoneyjp()+"\",\"departCD\":\""+serverInputMeta.getDepartCD()+"\",\"uNodeList\":"+serverInputMeta.getRoleList()+
									",\"pageNodeList\":"+serverInputMeta.getPageNodeList()+",\"userCompanyList\":"+serverInputMeta.getUserCompanyList()+",\"dateCompanyZone\":\""+dateCompanyZone+"\",\"lockStatus\":\""+lockStatus+
									"\",\"colorV\":\""+colorV+"\",\"timeZoneType\":\""+serverInputMeta.getTimeZoneType()+"\"}";
				}
				if(action.equals("login") && session != null)
				{
					rsInUserInfo = "{\"requestID\":\""+serverInputMeta.getRequestId()+"\"}";
				}
				break;
			case VALIDATE_ERROR:
				webOutputMeta = new WebOutputMeta();
				webOutputMeta.setResult(Result.VALIDATE_ERROR);
				webOutputMeta.setMessage(serverOutputMeta.getMessage());
				if(!action.equals("login") && session != null)
				{
					rsInUserInfo = "{\"requestID\":\""+serverInputMeta.getRequestId()+"\"}";
				}
				break;
			case SYSTEM_ERROR:
				return onErrorRequest(request, "Server端系统异常！", null,ERROR_RESPONSE_JSON);
			case BUSINESS_ERROR:
				return onErrorRequest(request, "Server端业务异常！", null,ERROR_RESPONSE_JSON);
			default:
				break;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("{\"");
			sb.append(MessageWarp.META_FIELD);
			sb.append("\":");
			sb.append(OBJECT_MAPPER.writeValueAsString(webOutputMeta));
			
			if(!rsInUserInfo.equals(""))
			{
				sb.append(",\"");
				sb.append("userInfo");
				sb.append("\":");
				sb.append(rsInUserInfo);
			}
			
			sb.append(",\"");
			sb.append(MessageWarp.DATA_FIELD);
			sb.append("\":");
			sb.append(readTree.get(MessageWarp.DATA_FIELD).toString());
			sb.append("}");
			return onSuccessRequest(request, sb.toString());
		} catch (Throwable e) {
			return onErrorRequest(request, "转换Server端调用结果时发生异常！", e,ERROR_RESPONSE_JSON);
		}

	}
/*
	private ResponseEntity<String> dynamicInvokeLocalService(String action, HttpServletRequest request) {
		try {
			Object bean = SpringUtil.getBean(action);
			//TODO 方法定位需要修改
			Method method = InstanceUtils.getFirstMethod(bean.getClass(), "process");
			byte[] requestBody = RequestUtil.getRequestBody(request, false);
			RequestMeta requestMeta = RequestUtil.getRequestMeta(request);
			Class<?>[] parameterTypes = method.getParameterTypes();
			Class paramType = parameterTypes[0];
			BaseInputBean inputBean = (BaseInputBean) OBJECT_MAPPER.readValue(requestBody, paramType);
			inputBean.setClientId(requestMeta.getClientId());
			inputBean.setCustId(requestMeta.getCustId());
			inputBean.setRequestId(requestMeta.getRequestId());
			BaseOutputBean outputBean = (BaseOutputBean) method.invoke(bean, inputBean);
			String responseJson = OBJECT_MAPPER.writeValueAsString(outputBean);
			return onSuccessRequest(request,responseJson);
		} catch (Throwable e) {
			return onErrorRequest(request,"本地服务异常！",e);
		}
	}

	private ResponseEntity<String> dynamicInvokeRestfulService(String action, HttpServletRequest request) {
		try {
			RequestMeta requestMeta = RequestUtil.getRequestMeta(request);
			byte[] requestBody = RequestUtil.getRequestBody(request, false);
			InputMeta serverInputMeta = new InputMeta();
			serverInputMeta.setClientId(requestMeta.getClientId());
			serverInputMeta.setCustId(requestMeta.getCustId());
			serverInputMeta.setRemoteIp(requestMeta.getServerIp());
			serverInputMeta.setRequestId(requestMeta.getRequestId());
			serverInputMeta.setSessionId(requestMeta.getSessionId());
			serverInputMeta.setTargetServiceName(action);
			String metaInfoJsonStr = OBJECT_MAPPER.writeValueAsString(serverInputMeta);
			String bodyJsonStr = new String(requestBody, "UTF-8");
			StringBuilder sb = new StringBuilder(requestBody.length + 256);
			sb.append("{\"");
			sb.append(MessageWarp.META_FIELD);
			sb.append("\":");
			sb.append(metaInfoJsonStr);
			sb.append(",\"");
			sb.append(MessageWarp.DATA_FIELD);
			sb.append("\":");
			sb.append(bodyJsonStr);
			sb.append("}");
			IWebClient jsonClient = WebClientFactory.getJsonClient();
			String responseJson = jsonClient.post("http://localhost:8080/test", sb.toString());
			return onSuccessRequest(request,responseJson);
		} catch (Throwable e) {
			return onErrorRequest(request, "调用Server过程中发生异常！", e);
		}
	}
*/
	private ResponseEntity<String> onSuccessRequest(HttpServletRequest request, String jsonStr) {
		RequestUtil.setResponseJson(request, jsonStr);
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(jsonStr, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}
	/*
	private ResponseEntity<String> onErrorRequest(HttpServletRequest request, String message, Throwable e) {
		RequestMeta requestMeta = RequestUtil.getRequestMeta(request);
		if (e == null) {
			LOG.error("[err,{}]{}", requestMeta.getRequestId(), message);
		} else {
			LOG.error("[err,{}]{}[{}]", requestMeta.getRequestId(), message,e.getMessage(), e);
		}
		RequestUtil.setResponseJson(request, ERROR_RESPONSE_JSON);
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(ERROR_RESPONSE_JSON, httpHeaders,
				HttpStatus.OK);
		return responseEntity;
	}
	*/
	/*
	private ResponseEntity<String> onLoginErrorRequest(HttpServletRequest request, String message, Throwable e) {
		RequestMeta requestMeta = RequestUtil.getRequestMeta(request);
		if (e == null) {
			LOG.error("[err,{}]{}", requestMeta.getRequestId(), message);
		} else {
			LOG.error("[err,{}]{}[{}]", requestMeta.getRequestId(), message,e.getMessage(), e);
		}
		RequestUtil.setResponseJson(request, ERROR_LOGIN_JSON);
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(ERROR_LOGIN_JSON, httpHeaders,
				HttpStatus.OK);
		return responseEntity;
	}
	*/
	private ResponseEntity<String> onErrorRequest(HttpServletRequest request, String message, Throwable e,String errFlgString) {
		RequestMeta requestMeta = RequestUtil.getRequestMeta(request);
		if (e == null) {
			LOG.error("[err,{}]{}", requestMeta.getRequestId(), message);
		} else {
			LOG.error("[err,{}]{}[{}]", requestMeta.getRequestId(), message,e.getMessage(), e);
		}
		RequestUtil.setResponseJson(request, errFlgString);
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(errFlgString, httpHeaders,
				HttpStatus.OK);
		return responseEntity;
	}
	/*
	private ResponseEntity<String> onReuestIDErrorRequest(HttpServletRequest request, String message, Throwable e) {
		RequestMeta requestMeta = RequestUtil.getRequestMeta(request);
		if (e == null) {
			LOG.error("[err,{}]{}", requestMeta.getRequestId(), message);
		} else {
			LOG.error("[err,{}]{}[{}]", requestMeta.getRequestId(), message,e.getMessage(), e);
		}
		RequestUtil.setResponseJson(request, ERROR_REQUESTID_JSON);
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(ERROR_REQUESTID_JSON, httpHeaders,
				HttpStatus.OK);
		return responseEntity;
	}
	*/
	/*
	private ResponseEntity<String> onReuestNameErrorRequest(HttpServletRequest request, String message, Throwable e) {
		RequestMeta requestMeta = RequestUtil.getRequestMeta(request);
		if (e == null) {
			LOG.error("[err,{}]{}", requestMeta.getRequestId(), message);
		} else {
			LOG.error("[err,{}]{}[{}]", requestMeta.getRequestId(), message,e.getMessage(), e);
		}
		RequestUtil.setResponseJson(request, ERROR_REQUESTNAME_JSON);
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(ERROR_REQUESTNAME_JSON, httpHeaders,
				HttpStatus.OK);
		return responseEntity;
	}
	*/
	private ResponseEntity<String> onBadRequest(HttpServletRequest request, String message, Throwable e) {
		RequestMeta requestMeta = RequestUtil.getRequestMeta(request);
		if (e == null) {
			LOG.error("[err,{}]{}", requestMeta.getRequestId(), message);
		} else {
			LOG.error("[err,{}]{}[{}]", requestMeta.getRequestId(), message,e.getMessage(), e);
		}
//		RequestUtil.setResponseJson(request, ERROR_RESPONSE_JSON);
		RequestUtil.setResponseHttpStatusCode(request, "404");
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>("", httpHeaders,
				HttpStatus.NOT_FOUND);
		return responseEntity;
	}
}
