package com.kaiwait.core.controller.dynamic;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiwait.common.vo.json.base.MessageWarp;
import com.kaiwait.common.vo.json.base.Result;
import com.kaiwait.common.vo.json.server.InputMeta;
import com.kaiwait.common.vo.json.server.OutputMeta;
import com.kaiwait.common.vo.json.web.WebOutputMeta;
import com.kaiwait.core.utils.RequestUtil;
import com.kaiwait.thrift.common.client.DynamicJsonServiceClient;


@Controller
@RequestMapping(value = "/jcUpCsvFile")

public class JcUploadCSVController {
	/**
	 * 
	 */
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final Logger LOG = LoggerFactory.getLogger(JcUploadCSVController.class);
	private static final String ERROR_RESPONSE_JSON = "{\"metaInfo\":{\"result\":\"SYSTEM_ERROR\",\"message\":\"系统异常\"},\"data\":null}";
	//private static final String SYSTEM_UPLOAD_ERROR = "{\"metaInfo\":{\"result\":\"UPLOAD_ERROR\",\"message\":\"upload file error\"},\"data\":null}";
	private static final String FILE_FORMAT_ERROR = "{\"metaInfo\":{\"result\":\"FILE_FORMAT_ERROR\",\"message\":\"upload file format error\"},\"data\":null}";
	private static final String REQUEST_METHOD_ERROR = "{\"metaInfo\":{\"result\":\"REQUEST_METHOD_ERROR\",\"message\":\"upload file method error\"},\"data\":null}";
	private static final String ERROR_LOGIN_JSON = "{\"metaInfo\":{\"result\":\"LOGIN_ERROR\",\"message\":\"系统异常\"},\"data\":null}";
	private static final String READ_FILE_JSON = "{\"metaInfo\":{\"result\":\"READ_FILE_ERROR\",\"message\":\"系统异常\"},\"data\":null}";

	//逻辑服务器后台负载均衡器地址 j-cyclic HC  ali
	//private String serverIP = "172.31.27.208";
	//逻辑服务器后台负载均衡器地址 j-cyclic.com.cn   HW  ali
	//private String serverIPlocal = "192.168.0.141";
	
	private String serverIPlocal = "localhost";
	
	@RequestMapping(value = "/{action}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> dispatcher(@PathVariable String action, HttpServletRequest request,@RequestParam("jcCsvFile") MultipartFile file) {
		String responseJson = null;
		HttpSession session = request.getSession(false);
		WebOutputMeta webOutputMeta = null;

		try {
			//RequestMeta requestMeta = RequestUtil.getRequestMeta(request);
			if (!ServletFileUpload.isMultipartContent(request)) {
				//不是Multipart类型,禁止访问
				LOG.error("请求类型不是Multipart类型,禁止访问");
				return onErrorRequest(request, "request method is error", null,REQUEST_METHOD_ERROR);
			}
			if( session == null)
			{
				return onErrorRequest(request, "没登陆", null,ERROR_LOGIN_JSON);
			}
			if (!file.isEmpty()) {
				String fileData = new String(file.getBytes(),"UTF-8").replaceAll("\r", "^");
				if(fileData.split("\\^").length > 500)
				{
					return onErrorRequest(request, "文件格式错误", null,FILE_FORMAT_ERROR);
				}else {
					InputMeta serverInputMeta = new InputMeta();
	    			serverInputMeta.setUserID((String)session.getAttribute("userID"));
	    			serverInputMeta.setCompanyID((String)session.getAttribute("companyID"));
	    			serverInputMeta.setRoleList((String)session.getAttribute("uNodeList"));
	    			serverInputMeta.setTargetServiceName(action);
	    			String metaInfoJsonStr = OBJECT_MAPPER.writeValueAsString(serverInputMeta);
	    			StringBuilder sb = new StringBuilder(file.getBytes().length + 256);
	    			sb.append("{\"");
	    			sb.append(MessageWarp.META_FIELD);
	    			sb.append("\":");
	    			sb.append(metaInfoJsonStr);
	    			sb.append(",\"");
	    			sb.append(MessageWarp.DATA_FIELD);
	    			sb.append("\":");
	    			sb.append("{\"fileData\":\""+fileData+"\"}");
	    			sb.append("}");
	    			//responseJson = DynamicJsonServiceClient.invoke(serverIP, 8081, 300000, sb.toString());
	    			//responseJson = DynamicJsonServiceClient.invoke(serverIPlocal, 9091, 300000, sb.toString());
	    			responseJson = DynamicJsonServiceClient.invoke(serverIPlocal, 8081, 120000, sb.toString());
				}
			}else {
				return onErrorRequest(request, "文件读取错误", null,READ_FILE_JSON);
			}
			
		} catch (Exception e) {
			return onErrorRequest(request, "调用Server过程中发生异常！", e,ERROR_RESPONSE_JSON);
		}
		
		
		try {
			OutputMeta serverOutputMeta;
			JsonNode readTree = OBJECT_MAPPER.readTree(responseJson);
			JsonNode jsonMetaNode = readTree.get(MessageWarp.META_FIELD);
			String metaTextValue = jsonMetaNode.toString();
			serverOutputMeta = OBJECT_MAPPER.readValue(metaTextValue, OutputMeta.class);
			switch(serverOutputMeta.getResult()) {
			case OK:
				webOutputMeta = new WebOutputMeta();
				webOutputMeta.setResult(Result.OK);
				webOutputMeta.setMessage(serverOutputMeta.getMessage());
				break;
			case VALIDATE_ERROR:
				webOutputMeta = new WebOutputMeta();
				webOutputMeta.setResult(Result.VALIDATE_ERROR);
				webOutputMeta.setMessage(serverOutputMeta.getMessage());
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
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> handleException(Exception ex,HttpServletRequest request)
	{
		if (ex instanceof MaxUploadSizeExceededException)
		{
			return onErrorRequest(request, "文件格式错误", null,FILE_FORMAT_ERROR); 
		}
		return null;
	}
	
	private ResponseEntity<String> onSuccessRequest(HttpServletRequest request, String jsonStr) {
		RequestUtil.setResponseJson(request, jsonStr);
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(jsonStr, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}
	private ResponseEntity<String> onErrorRequest(HttpServletRequest request, String message, Throwable e,String errFlgString) {
		if (e == null) {
			LOG.error("[err,{}]{}", "csv upload error log", message);
		} else {
			LOG.error("[err,{}]{}[{}]", "csv upload error log", message,e.getMessage(), e);
		}
		RequestUtil.setResponseJson(request, errFlgString);
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(errFlgString, httpHeaders,
				HttpStatus.OK);
		return responseEntity;
	}
	
}