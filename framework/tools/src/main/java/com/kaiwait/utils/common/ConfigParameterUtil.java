package com.kaiwait.utils.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kaiwait.utils.common.PropertyUtil.GetValueException;


/**
 * 定义外部配置参数的常量类 通过在具体类的静态初始化方法中调用该类的init方法,将属性文件值与常量定义值进行自动匹配.<br>
 * 前提:<br>
 * 1)常量类的包名与属性文件所在的包名相同<br>
 * 2)常量类的类名与属性文件的文件名相同<br>
 * 3)常量类的属性名与属性文件的Key相同<br>
 * 4)常量类的静态初始化方法应该写成如下形式<br>
 * <code>
 * 	static {
 * 		ConfigParameter.init(常量类.class);
 *  }
 * </code>
 * 注意:<br>
 * 1)由于反射机制的限制,不能支持基本数据类型的自动匹配,基本数据类型的常值将一直保持初始值.<br>
 * 2)如果相应资源文件不存在,或者与非基本类型的常量同名的设置值在资源文件中不存在,将会抛出运行时异常.<br>
 * 3)如果包含了(Integer,String)以外的非基本数据类的常量定义,将会抛出运行时异常.<br>
 * 4)其它非正常情况将抛出运行时异常<br>
 * 
 *
 */
public class ConfigParameterUtil {
	private static final Log LOGGER = LogFactory.getLog(ConfigParameterUtil.class);

	public static void init(Class<?> clazz) {
		try {
			String namespace = clazz.getName();
			// 取得所有Public属性
			Field[] fields = clazz.getFields();
			// 将Public属性与Properties文件进行匹配
			for (Field field : fields) {
				// 取得字段数据类型
				Class<?> type = field.getType();
				// 类型判定
				if (type.isPrimitive()) {
					// 不支持基本数据类型的映射
					continue;
				}
				// 去除final修饰符的影响，将字段设为可修改的
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
				// 取得字段名
				String fieldName = field.getName();
				// 取得字段名对应的属性值
				try{
				String propertyValue = PropertyUtil.get(namespace, fieldName);
				// 类型识别
				if (type.equals(Integer.class)) {
					// Integer类型
					field.set(null, Integer.parseInt(propertyValue));
				} else if (type.equals(String.class)) {
					// String类型
					field.set(null, propertyValue);
				} else {
					throw new RuntimeException("不支持的对象类型:" + type.getName());
				}
				} catch(GetValueException e) {
					LOGGER.info(e.getMessage());
				}
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

}
