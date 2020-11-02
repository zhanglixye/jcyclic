/**
 * 
 */
package com.kaiwait.webclient.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiwait.utils.common.InstanceUtils;
import com.kaiwait.webclient.IWebClient;
import com.kaiwait.webclient.dict.HttpContentCharset;
import com.kaiwait.webclient.dict.HttpStatus;
import com.kaiwait.webclient.exception.HttpTransferException;

/**
 * Json客户端
 * 
 * @author wanght
 *
 */
public class JsonClient implements IWebClient {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(JsonClient.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final int TIMEOUT_MS = 30 * 1000;

	/**
	 * 构造方法,不推荐使用.</br>
	 * 推荐使用WebClientFactory获取实例
	 * 
	 */
	@Deprecated
	public JsonClient() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaiwait.webclient.IWebClient#post(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T post(String url, Class<T> returnType) throws HttpTransferException {
		return (T) post(url, null, returnType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaiwait.webclient.IWebClient#post(java.lang.String, java.lang.Class,
	 * com.kaiwait.webclient.dict.HttpContentCharset)
	 */
	@Override
	public <T> T post(String url, Class<T> returnType, HttpContentCharset charset) throws HttpTransferException {
		return (T) post(url, null, returnType, charset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaiwait.webclient.IWebClient#post(java.lang.String, java.lang.Object,
	 * java.lang.Class)
	 */
	@Override
	public <T> T post(String url, Object contentObject, Class<T> returnType) throws HttpTransferException {
		return (T) post(url, contentObject, returnType, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaiwait.webclient.IWebClient#post(java.lang.String, java.lang.Object,
	 * java.lang.Class, com.kaiwait.webclient.dict.HttpContentCharset)
	 */
	@Override
	public <T> T post(String url, Object contentObject, Class<T> returnType, HttpContentCharset charset)
			throws HttpTransferException {
		if (returnType == null) {
			throw new HttpTransferException(false, HttpStatus.BAD_REQUEST, "错误的请求,返回值类型不能为空");
		}
		String inputStr = null;
		if (contentObject != null) {
			// 序列化
			inputStr = serializeToString(contentObject, charset);
		}
		// 发送请求
		String responseContent = post(url, inputStr, charset);
		try {
			// 反序列化
			return OBJECT_MAPPER.readValue(responseContent, returnType);
		} catch (IOException e) {
			throw new HttpTransferException(true, HttpStatus.OK, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaiwait.webclient.IWebClient#post(java.lang.String, java.lang.String)
	 */
	@Override
	public String post(String url, String content) throws HttpTransferException {
		return post(url, content, (HttpContentCharset) null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaiwait.webclient.IWebClient#post(java.lang.String, java.lang.String,
	 * com.kaiwait.webclient.HttpContentCharset)
	 */
	@Override
	public String post(String url, String content, HttpContentCharset charset) throws HttpTransferException {
		return post(url, content, charset, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaiwait.webclient.IWebClient#post(java.lang.String, java.lang.String,
	 * com.kaiwait.webclient.dict.HttpContentCharset, java.util.Map)
	 */
	@Override
	public String post(String url, String content, HttpContentCharset charset, Map<String, String> headers)
			throws HttpTransferException {

		byte[] resultContent = HttpClient.post(url, content, charset, TIMEOUT_MS, headers);
		try {
			if (charset != null) {
				return new String(resultContent, charset.name());
			} else {
				return new String(resultContent);
			}
		} catch (UnsupportedEncodingException e) {
			throw new HttpTransferException(true, HttpStatus.OK, "构造结果异常", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaiwait.webclient.IWebClient#get(java.lang.String, java.lang.Object,
	 * java.lang.Class)
	 */
	@Override
	public <T> T get(String url, Object contentObject, Class<T> returnType) throws HttpTransferException {

		return get(url, contentObject, returnType, HttpContentCharset.UTF8);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaiwait.webclient.IWebClient#get(java.lang.String, java.lang.Object,
	 * java.lang.Class, com.kaiwait.webclient.dict.HttpContentCharset)
	 */
	@Override
	public <T> T get(String url, Object contentObject, Class<T> returnType, HttpContentCharset charset)
			throws HttpTransferException {
		if (returnType == null) {
			throw new HttpTransferException(false, HttpStatus.BAD_REQUEST, "错误的请求,返回值类型不能为空");
		}
		if (charset == null) {
			charset = HttpContentCharset.UTF8;
		}
		// 序列化
		StringBuilder urlBuffer = new StringBuilder(url);
		if (contentObject != null) {
			String serializeUrlParams = serializeToUrlParam(contentObject, charset);
			if (!url.contains("?")) {
				urlBuffer.append("?");
			} else {
				urlBuffer.append("&");
			}
			urlBuffer.append(serializeUrlParams);
		}
		// 发送请求,得到结果
		String resultStr = get(urlBuffer.toString(), charset);
		try {
			// 反序列化
			return OBJECT_MAPPER.readValue(resultStr, returnType);
		} catch (IOException e) {
			throw new HttpTransferException(true, HttpStatus.OK, e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaiwait.webclient.IWebClient#get(java.lang.String)
	 */
	@Override
	public String get(String url) throws HttpTransferException {
		return get(url, HttpContentCharset.UTF8);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaiwait.webclient.IWebClient#get(java.lang.String,
	 * com.kaiwait.webclient.dict.HttpContentCharset)
	 */
	@Override
	public String get(String url, HttpContentCharset charset) throws HttpTransferException {
		if (charset == null) {
			charset = HttpContentCharset.UTF8;
		}
		byte[] result = HttpClient.get(url, TIMEOUT_MS);
		String resultString;
		try {
			resultString = new String(result, charset.name());
			return resultString;
		} catch (UnsupportedEncodingException e) {
			throw new HttpTransferException(true, HttpStatus.OK, "构造结果异常", e);
		}

	}

	/**
	 * 序列化
	 * 
	 * @param contentObject
	 *            需要序列化的对象实例
	 * @param charset
	 *            使用的字符集
	 * @return 序列化后的字符串表示
	 * @throws HttpTransferException
	 *             序列化过程中出现的异常
	 */
	private String serializeToString(Object contentObject, HttpContentCharset charset) throws HttpTransferException {
		String inputStr = "";
		if (contentObject != null) {
			try {
				if (charset == null) {
					inputStr = OBJECT_MAPPER.writeValueAsString(contentObject);
				} else {
					byte[] writeValueAsBytes = OBJECT_MAPPER.writeValueAsBytes(contentObject);
					inputStr = new String(writeValueAsBytes, charset.name());
				}
			} catch (JsonProcessingException | UnsupportedEncodingException e) {
				throw new HttpTransferException(false, HttpStatus.BAD_REQUEST, e.getMessage(), e);
			}
		}
		return inputStr;
	}

	/**
	 * 将对象序列化成URL参数表示
	 * 
	 * @param contentObject
	 *            对象
	 * @param charset
	 *            字符集
	 * @return 对象的URL参数表示
	 * @throws HttpTransferException
	 */
	private String serializeToUrlParam(Object contentObject, HttpContentCharset charset) throws HttpTransferException {
		try {
			StringBuilder sb = new StringBuilder();
			List<Field> fields = InstanceUtils.getFields(contentObject.getClass().getName());
			// 取得当前类的所有属性
			for (Field field : fields) {
				Object fieldValue = InstanceUtils.getFieldValue(contentObject, field, Object.class);
				if (sb.length() > 0) {
					sb.append("&");
				}
				String key = field.getName();
				sb.append(URLEncoder.encode(key, charset.name()));
				sb.append("=");
				String value = OBJECT_MAPPER.writeValueAsString(fieldValue);
				if (value.startsWith("\"")) {
					value = value.substring(1);
				}
				if (value.endsWith("\"")) {
					value = value.substring(0, value.length() - 1);
				}
				sb.append(URLEncoder.encode(value, charset.name()));
			}
			// 取得所有父类的所有属性
			List<Field> parentFields = InstanceUtils.getParentFields(contentObject.getClass().getName(), true);
			for (Field field : parentFields) {
				Object fieldValue = InstanceUtils.getFieldValue(contentObject, field, Object.class);
				if (sb.length() > 0) {
					sb.append("&");
				}
				String key = field.getName();
				sb.append(URLEncoder.encode(key, charset.name()));
				sb.append("=");
				String value = OBJECT_MAPPER.writeValueAsString(fieldValue);
				sb.append(URLEncoder.encode(value, charset.name()));
			}
			return sb.toString();
		} catch (Throwable e) {
			if (e instanceof HttpTransferException) {
				throw (HttpTransferException) e;
			} else {
				throw new HttpTransferException(false, HttpStatus.BAD_REQUEST, "对象序列化失败", e);
			}
		}
	}

}
