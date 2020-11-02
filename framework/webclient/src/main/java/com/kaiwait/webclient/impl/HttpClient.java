package com.kaiwait.webclient.impl;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.kaiwait.webclient.WebClientFactory;
import com.kaiwait.webclient.dict.HttpContentCharset;
import com.kaiwait.webclient.dict.HttpStatus;
import com.kaiwait.webclient.exception.HttpTransferException;

public class HttpClient {
	public static byte[] get(String url, int timeoutMs) throws HttpTransferException {
		boolean sent = false;
		HttpStatus httpStatus = HttpStatus.UNKNOW_ERROR;
		try (CloseableHttpClient httpCilent = HttpClients.createDefault();) {
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeoutMs) // 设置连接超时时间
					.setConnectionRequestTimeout(timeoutMs) // 设置请求超时时间
					.setSocketTimeout(timeoutMs).setRedirectsEnabled(true)// 默认允许自动重定向
					.build();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setConfig(requestConfig);
			sent = true;
			HttpResponse httpResponse = httpCilent.execute(httpGet);
			httpStatus = HttpStatus.valueOf(httpResponse.getStatusLine().getStatusCode());
			if (httpStatus == HttpStatus.OK) {
				return EntityUtils.toByteArray(httpResponse.getEntity());// 获得返回的结果
			} else {
				throw new HttpTransferException(sent, httpStatus, httpResponse.getStatusLine().toString());
			}
		} catch (Exception e) {
			throw new HttpTransferException(sent, httpStatus, e.getMessage(), e);
		}
	}

	public static byte[] post(String url, String content, HttpContentCharset charset, int timeoutMs,
			Map<String, String> headers) throws HttpTransferException {
		boolean sent = false;
		HttpStatus httpStatus = HttpStatus.UNKNOW_ERROR;
		// 获取可关闭的 httpCilent
		try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
			// 配置超时时间
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeoutMs)
					.setConnectionRequestTimeout(timeoutMs).setSocketTimeout(timeoutMs).setRedirectsEnabled(true)
					.build();

			HttpPost httpPost = new HttpPost(url);
			// 设置超时时间
			httpPost.setConfig(requestConfig);
			httpPost.setHeader("content-type", "application/json" + (charset == null ? "":";charset=" + charset.name()));
			if (headers != null) {
				for(Entry<String, String> entry : headers.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			StringEntity stringEntity = new StringEntity(content);
			// 设置post求情参数
			httpPost.setEntity(stringEntity);
			sent = true;
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			httpStatus = HttpStatus.valueOf(httpResponse.getStatusLine().getStatusCode());
			if (httpStatus == HttpStatus.OK) {
				return EntityUtils.toByteArray(httpResponse.getEntity());
			} else {
				throw new HttpTransferException(sent, httpStatus, httpResponse.getStatusLine().toString());
			}
		} catch (Exception e) {
			if (e instanceof HttpTransferException) {
				throw (HttpTransferException)e;
			} else {
				throw new HttpTransferException(sent, httpStatus, e.getMessage(), e);
			}
		}
	}
	
	public static void main(String[] args) throws HttpTransferException {
		WebClientFactory.getJsonClient().post("http://localhost:8080/front/main/dynamic/test", "{\"orderNo\":\"abc\"}");
	}
}
