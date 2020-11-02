package com.kaiwait.utils.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * 异常工具类
 * 
 *
 */
public class ExceptionUtil {
	/**
	 * 获取异常堆栈信息的字符串表示
	 * 
	 *            异常对象
	 */
	public static String getStackTrace(Throwable e) {

		try (ByteArrayOutputStream out = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(out);) {
			ps.print("[异常类型]:");
			ps.print(e.getClass().getName());
			ps.println();
			ps.println();

			ps.print("[异常信息]:");
			ps.print(e.getMessage());
			ps.println();

			e.printStackTrace(ps);
			ps.flush();
			out.flush();
			return out.toString();
		} catch (IOException e1) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 判断两个异常是否相似(同类型异常或者源异常对象中包含目标异常)
	 * 
	 *            比较的源异常对象
	 *            比较的目标异常对象
	 */
	public static boolean like(Throwable object, Class<?> type) {
		if (object.getClass().equals(type)) {
			return true;
		} else {
			while ((object = object.getCause()) != null) {
				if (object.getClass().equals(type)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 在源异常对象的异常堆栈中查找目标异常
	 * 
	 *            源异常对象
	 *            目标异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T find(Throwable object, Class<T> type) {
		if (object == null || type == null) {
			return null;
		}
		if (object.getClass().equals(type)) {
			return (T)object;
		} else {
			while ((object = object.getCause()) != null) {
				if (object.getClass().equals(type)) {
					return (T)object;
				}
			}
		}
		return null;
	}
}
