package com.kaiwait.core.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.kaiwait.common.vo.json.web.RequestMeta;

public class RequestUtil {
	public static byte[] getRequestBody(HttpServletRequest request, boolean resetInputStream) throws IOException {
		int contentLength = request.getContentLength();
		ServletInputStream inputStream = request.getInputStream();
		byte[] body = new byte[contentLength];
		if (contentLength == 0) {
			return body;
		}
		int readTotal = 0;
		boolean markFlag = false;
		if (resetInputStream && inputStream.markSupported()) {
			inputStream.mark(1024 * 1024);
			markFlag = true;
		}
		while (true) {
			int read = inputStream.read(body, readTotal, contentLength - readTotal);
			if (read > 0) {
				readTotal += read;
				if (readTotal >= contentLength) {
					break;
				}
			} else if (read == 0) {
				Thread.yield();
			} else {
				break;
			}
		}
		if (resetInputStream && markFlag) {
			inputStream.reset();
		}
		if (resetInputStream == false) {
			inputStream.close();
		}
		return body;

	}

	public static boolean isJsonRequest(HttpServletRequest request) {
		return "application/json".equals(request.getHeader("Content-Type"));
	}

	public static boolean isPostRequest(HttpServletRequest request) {
		String method = request.getMethod();
		return "POST".equals(method);
	}

	public static String getRequestControllerPath(HttpServletRequest request) {
		// TODO 验证没有contextPath
		String contextPath = request.getContextPath();
		int contextPathLength = 0;
		if (!"/".equals(contextPath)) {
			contextPathLength += contextPath.length();
		}
		// TODO 验证没有servletPath
		String servletPath = request.getServletPath();
		if (!"/".equals(servletPath)) {
			contextPathLength += servletPath.length();
		}
		if (contextPathLength > 0) {
			return request.getRequestURI().substring(contextPathLength);
		} else {
			return request.getRequestURI();
		}
	}

	public static String getRequestUri(HttpServletRequest request) {
		return request.getRequestURI();
	}

	public static String[] getRequestControllerPathLevelArray(HttpServletRequest request) {
		String requestContorllerPath = getRequestControllerPath(request);
		if (requestContorllerPath.startsWith("/")) {
			requestContorllerPath.substring(1);
		}
		return requestContorllerPath.split("/");
	}

	/**
	 * 方法描述：将HttpServletRequest请求所有参数打印
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2015年3月26日 下午4:44:20
	 */
	public static Map<String, Object> getParaValuesMap(HttpServletRequest request) {
		Map<String, Object> dataReq = new HashMap<String, Object>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues == null || paramValues.length == 0) {
				dataReq.put(paramName, null);
			} else if (paramValues.length == 1) {
				dataReq.put(paramName, paramValues[0]);
			} else {
				dataReq.put(paramName, paramValues);
			}
		}
		return dataReq;
	}

	public static RequestMeta getRequestMeta(HttpServletRequest request) {
		return (RequestMeta) request.getAttribute("requestMeta");
	}

	public static void setRequestMeta(HttpServletRequest request, RequestMeta requestMeta) {
		request.setAttribute("requestMeta", requestMeta);
	}

	public static void setResponseJson(HttpServletRequest request, String responseJson) {
		request.setAttribute("responseJson", responseJson);
	}

	public static String getResponseJson(HttpServletRequest request) {
		return (String) request.getAttribute("responseJson");
	}
	
	public static void setValidateIgnored(HttpServletRequest request, String ignoredMsg) {
		request.setAttribute("ValidateIgnored", ignoredMsg);
	}
	
	public static String getValidateIgnored(HttpServletRequest request) {
		return (String)request.getAttribute("ValidateIgnored");
	}
	
	public static String getResponseHttpStatusCode(HttpServletRequest request) {
		return (String) request.getAttribute("ResponseHttpStatusCode");
	}
	
	public static void setResponseHttpStatusCode(HttpServletRequest request, String responseHttpStatusCode) {
		 request.setAttribute("ResponseHttpStatusCode", responseHttpStatusCode);
	}
	public static boolean getSysLockFlg(String roleStr) {
		Boolean flg = true;
		String[] roleList = roleStr.split(",");
		for(int i = 0;i < roleList.length;i++)
		{
			if(Integer.valueOf(roleList[i]) < 5 && Integer.valueOf(roleList[i]) > 0)
			{
				flg = false;
				break;
			}
		}
		return flg;
	}
}
