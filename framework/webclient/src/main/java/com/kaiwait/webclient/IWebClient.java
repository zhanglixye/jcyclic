package com.kaiwait.webclient;

import java.util.Map;

import com.kaiwait.webclient.dict.HttpContentCharset;
import com.kaiwait.webclient.exception.HttpTransferException;

/**
 * 发送Web请求的客户端工具
 * @author wanght
 *
 */
public interface IWebClient {
	/**
	 * 用UTF8编码,以Post方式请求指定的URL,没有请求内容
	 * @param url 目标地址
	 * @param returnType 返回内容封装成哪种类型
	 * @return 将响应内容封装为returnType指定类型的对象实例
	 * @throws HttpTransferException 请求过程中发生的异常
	 */
	<T> T post(String url, Class<T> returnType) throws HttpTransferException;
	
	/**
	 * 用指定编码,以Post方式请求指定的URL,没有请求内容
	 * @param url 目标地址
	 * @param returnType  返回内容封装成哪种类型
	 * @param charset 使用的编码
	 * @return 将响应内容封装为returnType指定类型的对象实例
	 * @throws HttpTransferException 请求过程中发生的异常
	 */
	<T> T post(String url, Class<T> returnType, HttpContentCharset charset) throws HttpTransferException;

	/**
	 * 用UTF8编码,以Post方式提交一个请求
	 * @param url 目标地址
	 * @param contentObject 提交的对象
	 * @param returnType 返回内容封装成哪种类型
	 * @return 将响应内容封装为returnType指定类型的对象实例
	 * @throws HttpTransferException 请求过程中发生的异常
	 */
	<T> T post(String url, Object contentObject, Class<T> returnType) throws HttpTransferException;

	/**
	 * 用指定编码,以Post方式提交一个请求
	 * @param url 目标地址
	 * @param contentObject 提交的对象
	 * @param returnType  返回内容封装成哪种类型
	 * @param charset 使用的编码
	 * @return 将响应内容封装为returnType指定类型的对象实例
	 * @throws HttpTransferException 请求过程中发生的异常
	 */
	<T> T post(String url, Object contentObject, Class<T> returnType, HttpContentCharset charset) throws HttpTransferException;
	
	/**
	 * 用UTF8编码,以Post方式提交一个请求
	 * @param url 目标地址
	 * @param content 提交的内容
	 * @return 响应内容
	 * @throws HttpTransferException 请求过程中发生的异常
	 */
	String post(String url, String content) throws HttpTransferException;
	
	/**
	 * 用指定编码,以Post方式提交一个请求
	 * @param url 目标地址
	 * @param content 提交的内容
	 * @param charset 使用的编码
	 * @return 响应内容
	 * @throws HttpTransferException 请求过程中发生的异常
	 */
	String post(String url, String content, HttpContentCharset charset) throws HttpTransferException;
	
	
	/**
	 * 用指定编码,以Post方式提交一个请求
	 * @param url 目标地址
	 * @param content 提交的内容
	 * @param charset 使用的编码
	 * @param headers 请求头
	 * @return 响应内容
	 * @throws HttpTransferException 请求过程中发生的异常
	 */
	String post(String url, String content, HttpContentCharset charset, Map<String, String> headers) throws HttpTransferException;
	
	
	/**
	 * 用UTF8编码,以get方式提交一个请求
	 * @param url 目标地址
	 * @param contentObject 提交的对象
	 * @param returnType 返回内容封装成哪种类型
	 * @return 将响应内容封装为returnType指定类型的对象实例
	 * @throws HttpTransferException 请求过程中发生的异常
	 */
	<T> T get(String url, Object contentObject, Class<T> returnType) throws HttpTransferException;

	/**
	 * 用指定编码,以get方式提交一个请求
	 * @param url 目标地址
	 * @param contentObject 提交的对象
	 * @param returnType  返回内容封装成哪种类型
	 * @param charset 使用的编码
	 * @return 将响应内容封装为returnType指定类型的对象实例
	 * @throws HttpTransferException 请求过程中发生的异常
	 */
	<T> T get(String url, Object contentObject, Class<T> returnType, HttpContentCharset charset) throws HttpTransferException;
	
	/**
	 * 用UTF8编码,以get方式提交一个请求
	 * @param url 目标地址
	 * @return 响应内容
	 * @throws HttpTransferException 请求过程中发生的异常
	 */
	String get(String url) throws HttpTransferException;
	
	/**
	 * 用指定编码,以get方式提交一个请求
	 * @param url 目标地址
	 * @param charset 使用的编码
	 * @return 响应内容
	 * @throws HttpTransferException 请求过程中发生的异常
	 */
	String get(String url, HttpContentCharset charset) throws HttpTransferException;
	
}
