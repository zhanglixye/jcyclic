/**
 * 
 */
package com.kaiwait.webclient.impl;

import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiwait.common.constants.NothingObject;
import com.kaiwait.common.constants.NullObject;
import com.kaiwait.common.exception.BusinessException;
import com.kaiwait.utils.common.InstanceUtils;
import com.kaiwait.utils.common.InstanceUtils.ReflectionException;
import com.kaiwait.webclient.IWebClient;
import com.kaiwait.webclient.WebClientFactory;
import com.kaiwait.webclient.dict.HttpContentCharset;
import com.kaiwait.webclient.dict.HttpStatus;
import com.kaiwait.webclient.exception.HttpTransferException;
import com.kaiwait.webclient.vo.json.BaseJsonObject;
import com.kaiwait.webclient.vo.json.MetaInfo;

/**
 * coreserver的Json服务的专用Json客户端
 * 
 * @author wanght
 *
 */
public class CoreJsonClient implements IWebClient {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	static{
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}


	/**
	 * 构造方法,不推荐使用.</br>
	 * 推荐使用WebClientFactory获取实例
	 * 
	 */
	@Deprecated
	public CoreJsonClient() {
		
	}
	
	/**
	 * 用UTF8编码,以Post方式请求coreserver的没有参数的Action方法</br>
	 * 注意:如果使用该方法调用ISingleFunctionAction的实例将永远返回400错误
	 * @param url 目标地址
	 * @param returnType 返回内容封装成哪种类型
	 * @return 将响应内容封装为returnType指定类型的对象实例
	 * @throws HttpTransferException 请求过程中发生的异常
	 */
	@Override
	public <T> T post(String url, Class<T> returnType) throws HttpTransferException {
		return post(url, new NothingObject(), returnType, null);
	}
	
	/**
	 * 用指定编码,以Post方式请求coreserver的没有参数的Action方法</br>
	 * 注意:如果使用该方法调用ISingleFunctionAction的实例将永远返回400错误
	 * @param url 目标地址
	 * @param returnType  返回内容封装成哪种类型
	 * @param charset 使用的编码
	 * @return 将响应内容封装为returnType指定类型的对象实例
	 * @throws HttpTransferException 请求过程中发生的异常
	 */
	@Override
	public <T> T post(String url, Class<T> returnType, HttpContentCharset charset) throws HttpTransferException {
		return post(url, new NothingObject(), returnType, charset);
	}
	
	/**
	 * 用指定编码,以Post方式请求coreserver的Action方法
	 * 
	 * @param url
	 *            目标地址
	 * @param contentObject
	 *            提交的对象,如果需要以null值绑定服务器端方法参数类型时,请使用new NullObject(方法参数类型)
	 * @param returnType
	 *            返回内容封装成哪种类型
	 * @return 将响应内容封装为returnType指定类型的对象实例
	 * @throws HttpTransferException
	 *             请求过程中发生的异常
	 */
	@Override
	public <T> T post(String url, Object contentObject, Class<T> returnType) throws HttpTransferException {
		return post(url, contentObject, returnType, null);
	}

	/**
	 * 用指定编码,以Post方式提交一个请求
	 * 
	 * @param url
	 *            目标地址
	 * @param contentObject
	 *            提交的对象
	 * @param returnType
	 *            返回内容封装成哪种类型
	 * @param charset
	 *            使用的编码
	 * @return 将响应内容封装为returnType指定类型的对象实例
	 * @throws HttpTransferException
	 *             请求过程中发生的异常
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T post(String url, Object contentObject, Class<T> returnType, HttpContentCharset charset)
			throws HttpTransferException {
		if (returnType == null) {
			throw new HttpTransferException(false, HttpStatus.BAD_REQUEST, "错误的请求,返回值类型不能为空");
		}
		BaseJsonObject baseJsonObject = BaseJsonObject.newInstance();
		if (contentObject != null) {
			if (contentObject instanceof NothingObject) {
				//没有提交的对象
				baseJsonObject.getMetaInfo().setDataType(null);
				baseJsonObject.setData(null);
			} else {
				//提交的对象不是null
				baseJsonObject.getMetaInfo().setDataType(contentObject.getClass().getName());
				baseJsonObject.setData(contentObject);
			}
		} else {
			//提交的对象是null
			baseJsonObject.getMetaInfo().setDataType(NullObject.class.getName());
			baseJsonObject.setData(null);
		}
		
		String requestStr;
		try {
			//请求对象转换为json字符串
			requestStr = OBJECT_MAPPER.writeValueAsString(baseJsonObject);
		} catch (Throwable e) {
			throw new HttpTransferException(false, HttpStatus.BAD_REQUEST, "对象序列化失败!", e);
		}
		
		// 发送请求
		String responseStr = post(url, requestStr, charset);

		// 得到了响应
		MetaInfo metaInfo;
		JsonNode readTree;
		try {
			// 将Json串转换为MetaInfo对象
			readTree = OBJECT_MAPPER.readTree(responseStr);
			JsonNode jsonMetaNode = readTree.get(BaseJsonObject.META_FIELD);
			String metaTextValue = jsonMetaNode.toString();
			metaInfo = OBJECT_MAPPER.readValue(metaTextValue, MetaInfo.class);
		} catch (Throwable e) {
			throw new HttpTransferException(true, HttpStatus.OK, "对象反序列化异常!", e);
		}
		switch (metaInfo.getResult()) {
		case OK:
			// 反序列化Data对象
			Object data =readReturnData(readTree,returnType);
			return (T) data;
		case BUSINESS_ERROR:
		case VAIDATE_ERROR:
			// 业务异常和校验异常相同处理
			// 反序列化Data对象
			Object ex = readReturnData(readTree, metaInfo.getDataType());
			throw (BusinessException) ex;
		case FORBIDDEN:
			throw new HttpTransferException(true, HttpStatus.FORBIDDEN, metaInfo.getMessage());
		case LOGIN:
			throw new HttpTransferException(true, HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, metaInfo.getMessage());
		case USE_CACHE:
			// 缓存机制,有待实现
			throw new HttpTransferException(true, HttpStatus.INTERNAL_SERVER_ERROR, "还未实现缓存机制,请与架构组联系");
		default:
			throw new HttpTransferException(true, HttpStatus.INTERNAL_SERVER_ERROR, "返回了未知的状态码");
		}
	}
	
	/**
	 * 从返回的JSon树中取得指定结点
	 * @param jsonStr JSon串
	 * @param key 结点名
	 * @param type Data的类型
	 * @return 反序列化后的Data
	 * @throws HttpTransferException 对象反序列化异常
	 */
	public <T> T readDataByKey(String jsonStr, String key, Class<T> type)
			throws HttpTransferException {
		try {
			JsonNode readTree = OBJECT_MAPPER.readTree(jsonStr);
			JsonNode jsonExNode = readTree.get(key);
			String exTextValue = jsonExNode.toString();
			return OBJECT_MAPPER.readValue(exTextValue, type);
		} catch (Throwable e) {
			throw new HttpTransferException(true, HttpStatus.OK, "对象反序列化异常!", e);
		}
	}

	/**
	 * 从返回的JSon树中取得Data结点
	 * @param readTree JSon树
	 * @param typeStr Data的类型
	 * @return 反序列化后的Data
	 * @throws HttpTransferException 对象反序列化异常
	 */
	private Object readReturnData(JsonNode readTree, String typeStr)throws HttpTransferException {
		Class<?> type;
		try {
			type = InstanceUtils.getClass(typeStr);
			return readReturnData(readTree, type);
		} catch (ReflectionException e) {
			throw new HttpTransferException(true, HttpStatus.OK, "对象反序列化异常!", e);
		}
	}
	
	/**
	 * 从返回的JSon树中取得Data结点
	 * @param readTree JSon树
	 * @param type Data的类型
	 * @return 反序列化后的Data
	 * @throws HttpTransferException 对象反序列化异常
	 */
	@SuppressWarnings("unchecked")
	private <T> T readReturnData(JsonNode readTree, Class<T> type)
			throws HttpTransferException {
		try {
			JsonNode jsonExNode = readTree.get(BaseJsonObject.DATA_FIELD);
			String exTextValue = jsonExNode.toString();
			if (type.equals(OriginalJsonStr.class)) {
				//原始报文原样返回
				return (T)new OriginalJsonStr(exTextValue);
			}
			return OBJECT_MAPPER.readValue(exTextValue, type);
		} catch (Throwable e) {
			throw new HttpTransferException(true, HttpStatus.OK, "对象反序列化异常!", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaiwait.webclient.IWebClient#post(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String post(String url, String content) throws HttpTransferException {
		return post(url, content, (HttpContentCharset) null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaiwait.webclient.IWebClient#post(java.lang.String,
	 * java.lang.String, com.kaiwait.webclient.HttpContentCharset)
	 */
	@Override
	public String post(String url, String content, HttpContentCharset charset) throws HttpTransferException {
		return post(url, content, charset, null);
	}
	
	/* (non-Javadoc)
	 * @see com.kaiwait.webclient.IWebClient#post(java.lang.String, java.lang.String, com.kaiwait.webclient.dict.HttpContentCharset, java.util.Map)
	 */
	@Override
	public String post(String url, String content, HttpContentCharset charset, Map<String, String> headers) throws HttpTransferException {
		return WebClientFactory.getJsonClient().post(url, content, charset, headers);
	}

	/*
	 * 还未实现,请与架构组联系
	 * 
	 * @see com.kaiwait.webclient.IWebClient#get(java.lang.String,
	 * java.lang.Object, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String url, Object contentObject, Class<T> returnType) throws HttpTransferException {
		return (T) get(url);
	}

	/*
	 * 还未实现,请与架构组联系
	 * 
	 * @see com.kaiwait.webclient.IWebClient#get(java.lang.String,
	 * java.lang.Object, java.lang.Class,
	 * com.kaiwait.webclient.dict.HttpContentCharset)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String url, Object contentObject, Class<T> returnType, HttpContentCharset charset)
			throws HttpTransferException {
		return (T) get(url);
	}

	/*
	 * 还未实现,请与架构组联系
	 * 
	 * @see com.kaiwait.webclient.IWebClient#get(java.lang.String)
	 */
	@Override
	public String get(String url) throws HttpTransferException {
		return get(url, HttpContentCharset.UTF8);
	}

	/*
	 * 还未实现,请与架构组联系
	 * 
	 * @see com.kaiwait.webclient.IWebClient#get(java.lang.String,
	 * com.kaiwait.webclient.dict.HttpContentCharset)
	 */
	@Override
	public String get(String url, HttpContentCharset charset) throws HttpTransferException {
		throw new HttpTransferException(false, HttpStatus.BAD_REQUEST, "还未实现get请求,请与架构组联系");
	}

}
