package com.kaiwait.webclient;

import com.kaiwait.webclient.impl.JsonClient;
import com.kaiwait.webclient.impl.CoreJsonClient;

/**
 * @author wanght
 *
 */
public class WebClientFactory {
	@SuppressWarnings("deprecation")
	private static final JsonClient JSON_CLIENT = new JsonClient();
	@SuppressWarnings("deprecation")
	private static final CoreJsonClient coreserver_JSON_CLIENT = new CoreJsonClient();

	/**
	 * 返回标准JSON客户端
	 * @return 标准JSON客户端
	 */
	public static IWebClient getJsonClient() {
		return JSON_CLIENT;
	}
	
	/**
	 * 返回基于coreserver框架下JSON服务的JSON客户端
	 * @return 基于coreserverJSON客户端
	 */
	public static IWebClient getcoreserverJsonClient() {
		return coreserver_JSON_CLIENT;
	}
	
}
