package com.kaiwait.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ExceptionUtil {
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

	@SuppressWarnings("unchecked")
	public static <T> T find(Throwable object, Class<T> type) {
		if (object == null || type == null) {
			return null;
		}
		if (object.getClass().equals(type)) {
			return (T) object;
		} else {
			while ((object = object.getCause()) != null) {
				if (object.getClass().equals(type)) {
					return (T) object;
				}
			}
		}
		return null;
	}
}
