package com.kaiwait.core.interceptor;

import java.lang.reflect.Field;

import org.apache.ibatis.binding.MapperProxy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiwait.common.dao.BaseMapper;

/**
 * 日志拦截器
 *
 */
public class ServiceLogging {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(ServiceLogging.class);

	/**
	 * 方法返回
	 * 
	 * @param jp
	 *            切入点,包含了被代理的方法信息
	 */
	public void doAfter(JoinPoint jp) {
		
	}

	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		long beginTime = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder(100);
		sb.append(">>>");
		sb.append(getTargetObjectName(pjp.getTarget()));
		sb.append(".");
		sb.append(pjp.getSignature().getName());
		logger.info(sb.toString());
		
		if (logger.isDebugEnabled()) {
			Object[] args = pjp.getArgs();
			if (args != null) {
				sb.setLength(0);
				int index = 0;
				for (Object arg : args) {
					sb.setLength(0);
					sb.append("\tparams[");
					sb.append(index++);
					sb.append("] type[");
					sb.append(arg == null ? "null" : arg.getClass().getName());
					sb.append("] value[");
					sb.append(convertObjectToString(arg));
					sb.append("]");
					logger.debug(sb.toString());
				}
			}
		}
		Object retVal = pjp.proceed();
		if (logger.isDebugEnabled()) {
			sb.setLength(0);
			if (retVal != null) {
				sb.append("return type[");
				sb.append(retVal.getClass().getName());
				sb.append("] value[");
				sb.append(convertObjectToString(retVal));
				sb.append("]");
			} else {
				sb.append("return null");
			}
			logger.debug(sb.toString());
		}
		
		sb.setLength(0);
		sb.append("<<<");
		sb.append(getTargetObjectName(pjp.getTarget()));
		sb.append(".");
		sb.append(pjp.getSignature().getName());
		sb.append(" useTime:");
		sb.append(System.currentTimeMillis() - beginTime);
		sb.append("ms");
		logger.info(sb.toString());
		return retVal;
	}

	public void doBefore(JoinPoint jp) {
		
	}

	/**
	 * 异常发生
	 * 
	 * @param jp
	 *            切入点,包含了被代理的方法
	 * @param ex
	 *            发生的异常
	 */
	public void doThrowing(JoinPoint jp, Throwable ex) {
		if (logger.isErrorEnabled()) {
			StringBuilder sb = new StringBuilder(100);
			sb.append("error->");
			sb.append(getTargetObjectName(jp.getTarget()));
			sb.append(".");
			sb.append(jp.getSignature().getName());
			logger.error(sb.toString(), ex);
		}
	}

	/**
	 * 取得代理对象的原始类型名
	 * 
	 * @param proxy
	 *            代理对象
	 * @return 原始类型名
	 */
	public static String getTargetObjectName(Object proxy) {

		if (proxy instanceof BaseMapper) {
			// Mapper的代理
			Field h;
			try {
				h = proxy.getClass().getSuperclass().getDeclaredField("h");
				h.setAccessible(true);
				@SuppressWarnings("rawtypes")
				MapperProxy mapperProxy = (MapperProxy) h.get(proxy);
				Field mapperInterface = MapperProxy.class.getDeclaredField("mapperInterface");
				mapperInterface.setAccessible(true);
				Object object = mapperInterface.get(mapperProxy);

				return object.toString();
			} catch (Throwable e) {
				return proxy.getClass().getName();
			}
		}
		if (!AopUtils.isAopProxy(proxy)) {
			// 不是代理类
			return proxy.getClass().getName();
		} else {
			// AOP代理
			return AopUtils.getTargetClass(proxy).getClass().getName();
		}

	}

	private String convertObjectToString(Object o) {
		if (o == null) {
			return "null";
		} else if (o.getClass().isPrimitive()) {
			return String.valueOf(o);
		} else if (o instanceof String) {
			return (String) o;
		} else {
			try {
				return OBJECT_MAPPER.writeValueAsString(o);
			} catch (JsonProcessingException e) {
				return "对象转换时发生了异常,不能输出该对象的字符串表示值.";
			}
		}
	}
}
