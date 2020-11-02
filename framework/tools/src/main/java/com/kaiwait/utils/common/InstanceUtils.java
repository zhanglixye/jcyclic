package com.kaiwait.utils.common;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */
public class InstanceUtils {
	/** 日志对象*/
	private static final Log LOG = LogFactory.getLog(ConfigParameterUtil.class);
	/** 单例容器<全类名,类实例>,需要做同步访问 */
	private static final Hashtable<String, Object> SINGLETON_INSTANCE_CONTAINER = new Hashtable<String, Object>();
	/** 类容器<全类名, 类> */
	private static final Hashtable<String, Class<?>> CLASS_CONTAINER = new Hashtable<String, Class<?>>();
	/** 类注解容器 <全类名, <注解类全类名,注解类>>*/
	private static final Hashtable<String, Hashtable<String, Annotation>> CLASS_ANNOTATION_CONTAINER = new Hashtable<String, Hashtable<String, Annotation>>();
	/** 方法容器<全类名.方法名.参数列表, 方法> */
	private static final Map<String, Method> METHOD_CONTAINER_BY_IDENTIFY = Collections.synchronizedMap(new HashMap<String, Method>());
	/** 方法容器<全类名, 方法> */
	private static final Hashtable<String, List<Method>> METHOD_CONTAINER_BY_CLASS = new Hashtable<String, List<Method>>();
	
	/** 方法注解容器<全类名.方法名.参数列表, <注解类全类名,注解类>> */
	private static final Hashtable<String, Hashtable<String, Annotation>> METHOD_ANNOTATION_CONTAINER = new Hashtable<String, Hashtable<String, Annotation>>();
	/** 属性容器<全类名, 属性列表> */
	private static final Hashtable<String, List<Field>> FIELD_CONTAINER = new Hashtable<String, List<Field>>();
	/** 属性注解容器 <全类名.属性名, <注解类全类名,注解类>>*/
	private static final Hashtable<String, Hashtable<String, Annotation>> FIELD_ANNOTATION_CONTAINER = new Hashtable<String, Hashtable<String, Annotation>>();

	/**
	 * 禁止实例化
	 */
	private InstanceUtils() {

	}

	/**
	 * 调用对象类的默认构造函数，获取对象实例
	 * 
	 *            全类名
	 *             包含实例化过程中发生的所有异常
	 */
	public static Object getInstance(String classFullName) throws Exception {
		Class<?> forName = Class.forName(classFullName);
		return forName.newInstance();
	}

	/**
	 * 调用对象类的默认构造函数，获取对象唯一实例
	 * 
	 *            全类名
	 *             包含实例化过程中发生的所有异常
	 */
	public static synchronized Object getSingletonInstance(String classFullName) throws Exception {
		if (SINGLETON_INSTANCE_CONTAINER.containsKey(classFullName)) {
			return SINGLETON_INSTANCE_CONTAINER.get(classFullName);
		} else {
			Object instance = getInstance(classFullName);
			SINGLETON_INSTANCE_CONTAINER.put(classFullName, instance);
			return instance;
		}
	}


	/**
	 * 获取类对象,有缓存
	 * 
	 *            全类名,如果使用class.getCanonicalName()指定类名,将无法反射内部类.
	 *             指定的类不存在
	 */
	public synchronized static Class<?> getClass(String classFullName) throws ReflectionException {
		if (!CLASS_CONTAINER.containsKey(classFullName)) {
			try {
				loadClass(classFullName);
			} catch (ClassNotFoundException e) {
				throw new ReflectionException("获取类对象失败", e);
			}
		}
		return CLASS_CONTAINER.get(classFullName);
	}
	
	/**
	 * 获取指定类上的注解
	 */
	public static Hashtable<String, Annotation> getClassAnnotation(Class<?> clazz) throws ReflectionException {
		//如果使用class.getCanonicalName(),将无法反射内部类.
		return getClassAnnotation(clazz.getName());
	}

	/**
	 * 获取指定类上的注解
	 */
	public static Hashtable<String, Annotation> getClassAnnotation(String classFullName) throws ReflectionException {
		if (CLASS_ANNOTATION_CONTAINER.containsKey(classFullName)) {
			return CLASS_ANNOTATION_CONTAINER.get(classFullName);
		} else {
			final Class<?> clazz = getClass(classFullName);
			final Annotation[] annotations = clazz.getAnnotations();
			Hashtable<String, Annotation> annotationMap = new Hashtable<String, Annotation>(annotations.length);
			
			for (Annotation annotation : annotations) {
				annotationMap.put(annotation.annotationType().getCanonicalName(), annotation);
			}
			
			CLASS_ANNOTATION_CONTAINER.put(classFullName, annotationMap);
			return annotationMap;

		}
	}
	
	/**
	 * 获取指定类上指定类型的注解
	 */
	public static <T> T getClassAnnotation(Class<?> clazz, Class<T> annotationType) throws ReflectionException {
		//如果使用class.getCanonicalName(),将无法反射内部类.
		return getClassAnnotation(clazz.getName(), annotationType);
	}
	/**
	 * 获取指定类上指定类型的注解
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getClassAnnotation(String classFullName, Class<T> annotationType) throws ReflectionException {
		final Hashtable<String, Annotation> classAnnotation = getClassAnnotation(classFullName);
		return (T)classAnnotation.get(annotationType.getCanonicalName());
	}
	
	/**
	 * 获取方法列表,有缓存
	 */
	public static List<Method> getMethodList(Class<?> clazz) throws ReflectionException {
		return getMethodList(clazz.getName());
	}

	/**
	 *  获取方法列表,有缓存
	 */
	public static List<Method> getMethodList(String classFullName) throws ReflectionException {
		try {
			loadClass(classFullName);
			return METHOD_CONTAINER_BY_CLASS.get(classFullName);
		} catch (Throwable e) {
			throw new ReflectionException("获取类" + classFullName + "的方法列表时发生异常!", e);
		}
	}
	
	/**
	 * 获取方法,有缓存
	 * 
	 *            类
	 *            方法名
	 *            方法参数
	 *             指定的方法不存在或者指定的类不存在
	 */
	public static Method getMethod(Class<?> clazz, String methodName, Object... params)
			throws ReflectionException {
		return getMethod(clazz.getName(), methodName, params);
		
	}
	
	/**
	 * 获取方法,有缓存<br/>
	 * 获取方式:<br/>
	 * 1)生成方法标识时,空值用com.com.kaiwait.utils.common.InstanceUtils$Null来代表数据类型,当参数值列表中包含有null值的时候将无法通过方法标识直接获取到匹配的方法<br/>
	 * 2)通过方法标识无法获取匹配的方法的时候,再次通过方法名加参数个数加参数类型进行比较的方式检查是否存在匹配的方法<br/>
	 * 3)有多个匹配的方法时抛出反射异常<br/>
	 *            全类名
	 *            方法名
	 *            方法参数
	 *             指定的方法不存在或者指定的类不存在或者有多个匹配的方法
	 */
	public static Method getMethod(String classFullName, String methodName, Object... params)
			throws ReflectionException {

		String methodIdentify = makeMethodIdentify(classFullName, methodName, params);

		try {
			loadClass(classFullName);
		} catch (ClassNotFoundException e) {
			throw new ReflectionException("试图获取一个不存在的方法:" + methodIdentify, e);
		}

		synchronized (METHOD_CONTAINER_BY_IDENTIFY) {
			if (METHOD_CONTAINER_BY_IDENTIFY.containsKey(methodIdentify)) {
				Method cachedMethod = METHOD_CONTAINER_BY_IDENTIFY.get(methodIdentify);
				if(cachedMethod != null) {
					//通过方法标识直接命中了匹配的方法
					return cachedMethod;
				} else {
					//通过方法标识无法获取匹配的方法,通过方法名加参数个数的方式依然无法获取匹配的方法
					throw new ReflectionException("试图获取一个不存在的方法:" + methodIdentify);
				}
			} else {
				//二次查找
				//通过方法标识无法获取匹配的方法的时候,再次通过方法名加参数个数的方式检查是否存在匹配的方法
				final Class<?> clazz = getClass(classFullName);
				final Method[] declaredMethods = clazz.getDeclaredMethods();
				if (declaredMethods != null) {
					for(Method method : declaredMethods) {
						boolean matched = true;
						
						if (method.getName().equals(methodName)) {
							final Class<?>[] parameterTypes = method.getParameterTypes();
							if (parameterTypes != null && params != null) {
								if (parameterTypes.length == params.length) {
									//参数个数相同,依次比较参数类型
									for(int i=0; i< parameterTypes.length; i++ ) {
										if (params[i] != null) {
											if (!parameterTypes[i].equals(params[i].getClass()) && !(parameterTypes[i]).isAssignableFrom(params[i].getClass())) {
												//参数类型不匹配
												matched = false;
												break;
											}
										} else {
											//参数值是null
											if (parameterTypes[i].isPrimitive()) {
												//参数类型是基本数据类型,不匹配
												matched = false;
												break;
											}
										}
									}
									
								} else {
									//参数个数不匹配
									matched = false;
								}
							} else {
								if (parameterTypes == null && params == null) {
									//匹配无参数方法
									matched = true;
								} else {
									//参数个数不匹配
									matched = false;
								}
							}
						} else {
							//方法名不匹配
							matched = false;
						}
						
						if (matched) {
							if (METHOD_CONTAINER_BY_IDENTIFY.containsKey(methodIdentify)) {
								//同时匹配多个重载方法的场合,使用哪个都不正确
								//清除方法缓存
								METHOD_CONTAINER_BY_IDENTIFY.put(methodIdentify, null);
								//停止查找
								break;
							} else {
								//匹配到一个方法,放入方法缓存
								METHOD_CONTAINER_BY_IDENTIFY.put(methodIdentify, method);
								//继续查找是否可以同时匹配多个重载方法
							}
						}
					}
				}
				
				//再次检查是否匹配到方法
				Method cachedMethod = METHOD_CONTAINER_BY_IDENTIFY.get(methodIdentify);
				if (cachedMethod == null) {
					//无法正确的匹配到唯一一个方法
					if (!METHOD_CONTAINER_BY_IDENTIFY.containsKey(methodIdentify)) {
						//缓存不匹配的结果到方法缓存,避免再次执行二次查找
						METHOD_CONTAINER_BY_IDENTIFY.put(methodIdentify, null);
					}
					throw new ReflectionException("试图获取一个不存在的方法:" + methodIdentify);
				} else {
					//通过二次查找,匹配到唯一一个方法
					return cachedMethod;
				}
			}
		}

	}
	
	/**
	 * 用方法名获取实例中第一个方法,有缓存(当有多个同名的重载方法的情况下,只反回第一个)
	 * 
	 *            类
	 *            方法名
	 *             指定的方法不存在或者指定的类不存在
	 */
	public static Method getFirstMethod(Class<?> clazz, String methodName) throws ReflectionException {
		return getFirstMethod(clazz.getName(), methodName);
	}

	/**
	 * 用方法名获取实例中第一个方法,有缓存(当有多个同名的重载方法的情况下,只反回第一个)
	 * 
	 *            全类名
	 *            方法名
	 *             指定的方法不存在或者指定的类不存在
	 */
	public static Method getFirstMethod(String classFullName, String methodName) throws ReflectionException {

		String methodIdentify = makeMethodIdentify(classFullName, methodName, GetFirstMethodIdentify.class);

		if (METHOD_CONTAINER_BY_IDENTIFY.containsKey(methodIdentify)) {
			return METHOD_CONTAINER_BY_IDENTIFY.get(methodIdentify);
		} else {
			final Class<?> clazz = getClass(classFullName);
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					METHOD_CONTAINER_BY_IDENTIFY.put(methodIdentify, method);
					return method;
				}
			}
			throw new ReflectionException("试图获取一个不存在的方法:" + classFullName + "." + methodName);
		}
	}

	/**
	 * 获取方法上所有注解,有缓存
	 * 
	 *            方法名
	 *            方法参数
	 *             指定的方法不存在或者指定的类不存在
	 */
	public static Hashtable<String, Annotation> getMethodAnnotation(Class<?> clazz, String methodName,
			Object... params) throws ReflectionException {
		return getMethodAnnotation(clazz.getName(), methodName, params);
	}
	/**
	 * 获取方法上所有注解,有缓存
	 * 
	 *            全类名
	 *            方法名
	 *            方法参数
	 *             指定的方法不存在或者指定的类不存在
	 */
	public static Hashtable<String, Annotation> getMethodAnnotation(String classFullName, String methodName,
			Object... params) throws ReflectionException {
		String methodIdentify = makeMethodIdentify(classFullName, methodName, params);
		if (METHOD_ANNOTATION_CONTAINER.containsKey(methodIdentify)) {
			return METHOD_ANNOTATION_CONTAINER.get(methodIdentify);
		} else {
			Hashtable<String, Annotation> annotationMap = new Hashtable<String, Annotation>();
			final Method method = getMethod(classFullName, methodName, params);
			final Annotation[] annotationArray = method.getAnnotations();
			if (annotationArray != null) {
				for (Annotation annotation : annotationArray) {
					annotationMap.put(annotation.annotationType().getCanonicalName(), annotation);
				}
			}
			METHOD_ANNOTATION_CONTAINER.put(methodIdentify, annotationMap);
			return annotationMap;
		}
	}
	
	/**
	 * 获取方法上指定的注解,有缓存
	 * 
	 *            注解类型
	 * 
	 *            方法名
	 *            方法参数
	 *             指定的方法不存在或者指定的类不存在
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T getMethodAnnotation(Class<T> annotationClass, Class<?> clazz, String methodName,
			Object... params) throws ReflectionException {
		final Hashtable<String, Annotation> methodAnnotationMap = getMethodAnnotation(clazz.getName(), methodName,
				params);
		return (T) methodAnnotationMap.get(annotationClass.getCanonicalName());
	}
	

	/**
	 * 获取方法上指定的注解,有缓存
	 * 
	 *            注解类型
	 * 
	 *            全类名
	 *            方法名
	 *            方法参数
	 *             指定的方法不存在或者指定的类不存在
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T getMethodAnnotation(Class<T> annotationClass, String classFullName, String methodName,
			Object... params) throws ReflectionException {
		final Hashtable<String, Annotation> methodAnnotationMap = getMethodAnnotation(classFullName, methodName,
				params);
		return (T) methodAnnotationMap.get(annotationClass.getCanonicalName());
	}

	/**
	 * 获取指定类的所有属性,有缓存
	 * 
	 *            全类名
	 *             类不存在
	 */
	public static List<Field> getFields(String classFullName) throws ReflectionException {
		synchronized (FIELD_CONTAINER) {
			if (FIELD_CONTAINER.containsKey(classFullName)) {
				return FIELD_CONTAINER.get(classFullName);
			} else {
				List<Field> fieldList = new ArrayList<Field>();
				Class<?> clazz = getClass(classFullName);
				getFields(clazz, fieldList, false);
				FIELD_CONTAINER.put(classFullName, fieldList);
				return fieldList;
			}
		}
	}

	/**
	 * 获取指定类的父类的所有属性,有缓存
	 * 
	 *            全类名
	 *            是否递归获取父类属性(true:递归获取所有父类的所有属性, false:不递归,只获取当前类的直接父类的属性)
	 *             类不存在
	 */
	public static List<Field> getParentFields(String classFullName, boolean recursion) throws ReflectionException {
		Class<?> clazz = getClass(classFullName);
		if (Object.class.equals(clazz) || Object.class.equals(clazz.getSuperclass())) {
			// 自身或者父类是Object,直接返回空List
			return new ArrayList<Field>(0);
		}
		Class<?> superclass = clazz.getSuperclass();
		if (recursion) {
			// 递归父类属性,获取所有父类的属性
			List<Field> fieldList = new ArrayList<Field>();
			while (!Object.class.equals(superclass) && superclass != null) {
				fieldList.addAll(getFields(superclass.getName()));
				// 取得父类的父类
				superclass = superclass.getSuperclass();
			}
			return fieldList;
		} else {
			// 不递归父类属性,直接返回直接父类的所有属性
			return getFields(superclass.getName());
		}
	}
	
	/**
	 * 获取指定类的指定属性上的注解
	 */
	public static Hashtable<String, Annotation> getFieldAnnotation(Class<?> clazz, String fieldName) throws ReflectionException {
		return getFieldAnnotation(clazz.getName(), fieldName);
	}

	/**
	 * 获取指定类的指定属性上的注解
	 */
	public static Hashtable<String, Annotation> getFieldAnnotation(String classFullName, String fieldName) throws ReflectionException {
		final List<Field> fields = getFields(classFullName);
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				final String key = classFullName + "." + fieldName;
				if (FIELD_ANNOTATION_CONTAINER.containsKey(key)) {
					return FIELD_ANNOTATION_CONTAINER.get(key);
				} else {
					final Annotation[] annotations = field.getAnnotations();
					Hashtable<String, Annotation> fieldAnnotationMap = new Hashtable<String, Annotation>(
							annotations.length);
					
					for (Annotation annotation : annotations) {
						fieldAnnotationMap.put(annotation.annotationType().getCanonicalName(), annotation);
					}
					
					FIELD_ANNOTATION_CONTAINER.put(key, fieldAnnotationMap);
					return fieldAnnotationMap;
				}
			}
		}
		throw new ReflectionException(classFullName + "." + fieldName + "不存在!");
	}
	
	/**
	 * 获取指定类的指定属性上指定类型的注解
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFieldAnnotation(Class<?> clazz, String fieldName, Class<T> annotationClass) throws ReflectionException {
		final Hashtable<String, Annotation> fieldAnnotation = getFieldAnnotation(clazz, fieldName);
		return (T)fieldAnnotation.get(annotationClass.getCanonicalName());
	}
	
	/**
	 * 获取指定类的指定属性上指定类型的注解
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFieldAnnotation(String classFullName, String fieldName, Class<T> annotationClass) throws ReflectionException {
		final Hashtable<String, Annotation> fieldAnnotation = getFieldAnnotation(classFullName, fieldName);
		return (T)fieldAnnotation.get(annotationClass.getCanonicalName());
	}

	/**
	 * 取得属性值,包含父类属性值
	 * 
	 *            对象实例
	 *            属性名
	 *            返回值类型,不允许为空,不允许基本数据类型.
	 *             返回值类型为空、返回值类型是基本数据类型、自身和父类中都没有找到属性、反射异常
	 */
	public static <T> T getFieldValue(Object instance, String fieldName, Class<T> valueType)
			throws ReflectionException {
		// 取得自身属性
		String canonicalName = instance.getClass().getName();
		List<Field> fields = getFields(canonicalName);
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				// 命中属性,返回属性值
				return getFieldValue(instance, field, valueType);
			}
		}
		// 没有命中属性,检查父类属性
		Class<?> superclass = instance.getClass().getSuperclass();
		while (superclass != null && !superclass.equals(Object.class)) {
			List<Field> superFields = getFields(superclass.getName());
			for (Field field : superFields) {
				if (field.getName().equals(fieldName)) {
					// 命中属性,返回属性值
					return getFieldValue(instance, field, valueType);
				}
			}
			// 取父类的父类
			superclass = superclass.getSuperclass();
		}
		// 自身和父类中都没有命中属性,抛出异常
		throw new ReflectionException("试图获取一个不存在的属性" + canonicalName + "." + fieldName);
	}

	/**
	 * 取得属性值
	 * 
	 *            对象实例
	 *            属性
	 *            返回值类型,不允许为空,不允许基本数据类型.
	 *             返回值类型为空、返回值类型是基本数据类型、反射异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Object instance, Field field, Class<T> valueType) throws ReflectionException {
		if (valueType == null) {
			throw new ReflectionException("必需指定返回值类型");
		}
		if (valueType.isPrimitive()) {
			throw new ReflectionException("返回值类型不可以是基本数据类型,请使用基本数据类型所对应的封装类型.");
		}

		try {

			Object value = null;

			Class<?> type = field.getType();
			if (type.isPrimitive()) {
				// 处理基本数据类型
				if (boolean.class.equals(type)) {
					// boolean类型
					value = new Boolean(field.getBoolean(instance));
				} else if (byte.class.equals(type)) {
					// byte类型
					value = new Byte(field.getByte(instance));
				} else if (char.class.equals(type)) {
					// char类型
					value = new Character(field.getChar(instance));
				} else if (short.class.equals(type)) {
					// short类型
					value = new Short(field.getShort(instance));
				} else if (int.class.equals(type)) {
					// int类型
					value = new Integer(field.getInt(instance));
				} else if (long.class.equals(type)) {
					// long类型
					value = new Long(field.getLong(instance));
				} else if (float.class.equals(type)) {
					// float类型
					value = new Float(field.getFloat(instance));
				} else if (double.class.equals(type)) {
					// double类型
					value = new Double(field.getDouble(instance));
				} 
				return (T) value;
			} else {
				// 对象类型
				return (T) field.get(instance);
			}
		} catch (Throwable e) {
			throw new ReflectionException("获取属性值失败", e);
		}
	}

	/**
	 * 设定对象实例的属性值
	 * 
	 *            对象实例
	 *            属性名
	 *            属性值
	 *             包含设置过程中发生的所有异常
	 */
	public static void setFieldValue(Object instance, String fieldName, Object fieldValue) throws Exception {
		final String classFullName = instance.getClass().getCanonicalName();
		final List<Field> fields = getFields(classFullName);
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				field.setAccessible(true);
				field.set(instance, fieldValue);
				return;
			}
		}
		throw new Exception(classFullName + "." + fieldName + "不存在!");
	}

	/**
	 * 设定对象实例的属性值(推荐使用setFieldValue方法)
	 * 
	 *            对象实例
	 *            属性名
	 *            属性值
	 *             包含设置过程中发生的所有异常
	 */
	@Deprecated
	public static void setPropertyValue(Object instance, String propertyName, Object propertyValue) throws Exception {
		Field declaredField = instance.getClass().getDeclaredField(propertyName);
		declaredField.setAccessible(true);
		declaredField.set(instance, propertyValue);
	}

	/**
	 * 执行指定的方法,方法对象、类对象、类实例全部使用单例缓存
	 * 被执行的方法的参数必需声明为具体对象类型或者基本数据类型,如果被执行的方法的参数声明为Object,调用该方法将会抛出异常
	 * 
	 *            类
	 *            方法名
	 *            参数
	 *             指定的类不存在或者指定的方法不存在或者方法执行过程中发生了异常
	 */
	public static void executeVoidMethodUseSingletonInstanceCache(Class<?> clazz, String methodName, Object... params) throws ReflectionException {
		executeVoidMethodUseSingletonInstanceCache(clazz.getName(), methodName, params);
	}
	/**
	 * 执行指定的方法,方法对象、类对象、类实例全部使用单例缓存
	 * 被执行的方法的参数必需声明为具体对象类型或者基本数据类型,如果被执行的方法的参数声明为Object,调用该方法将会抛出异常
	 * 
	 *            全类名
	 *            方法名
	 *            参数
	 *             指定的类不存在或者指定的方法不存在或者方法执行过程中发生了异常
	 */
	public static void executeVoidMethodUseSingletonInstanceCache(String classFullName, String methodName, Object... params)
			throws ReflectionException {
		try {
			Method method = getMethod(classFullName, methodName, params);
			method.setAccessible(true);
			method.invoke(getSingletonInstance(classFullName), params);
		} catch (Throwable e) {
			throw new ReflectionException("反射调用方法异常:" + makeMethodIdentify(classFullName, methodName, params), e);
		}
	}
	
	/**
	 * 执行指定的方法,方法对象、类对象、类实例全部使用单例缓存</br>
	 * 被执行的方法的参数必需声明为具体对象类型或者基本数据类型,如果被执行的方法的参数声明为Object,调用该方法将会抛出异常
	 * 
	 *            全类名
	 *            方法名
	 *            参数
	 *             指定的类不存在或者指定的方法不存在或者方法执行过程中发生了异常
	 */
	public static <T> T executeMethodUseSingletonInstanceCache(Class<T> returnType, Class<?> clazz, String methodName, Object... params)
			throws ReflectionException {
		return executeMethodUseSingletonInstanceCache(returnType, clazz.getName(), methodName, params);
	}
	/**
	 * 执行指定的方法,方法对象、类对象、类实例全部使用单例缓存
	 * 被执行的方法的参数必需声明为具体对象类型或者基本数据类型,如果被执行的方法的参数声明为Object,调用该方法将会抛出异常
	 * 
	 *            全类名
	 *            方法名
	 *            参数
	 *             指定的类不存在或者指定的方法不存在或者方法执行过程中发生了异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T executeMethodUseSingletonInstanceCache(Class<T> returnType, String classFullName, String methodName, Object... params)
			throws ReflectionException {
		try {
			Method method = getMethod(classFullName, methodName, params);
			method.setAccessible(true);
			return (T)method.invoke(getSingletonInstance(classFullName), params);
		} catch (Throwable e) {
			throw new ReflectionException("反射调用方法异常:" + makeMethodIdentify(classFullName, methodName, params), e);
		}
	}

	/**
	 * 判断对象类型是不是虚拟机内置对象.
	 */
	public static boolean isJdkInternalObject(Class<?> type) {
		if (type == null) {
			return true;
		} else if (type.isPrimitive()) {
			return true;
		} else if (type.getName().startsWith("java.") || type.getName().startsWith("javax.")) {
			return true;
		}
		return false;
	}

	/** 
     * 从指定的package中获取所有的Class 
     *  
     */  
	public static Set<Class<?>> getClassesByPackage(String packageStr) throws Throwable {

		// class类的集合
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		if (StringUtil.isEmpty(packageStr)) {
			return classes;
		}
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageName = packageStr;
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的所有文件
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					LOG.info("file类型的扫描:" + filePath);
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						LOG.info("jar类型的扫描:" + jar.getName());
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx).replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class") && !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(packageName.length() + 1, name.length() - 6);
										try {
											// 添加到classes
											//classes.add(Class.forName(packageName + '.' + className));//forName会触发static方法
											classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
										} catch (ClassNotFoundException e) {
											LOG.error("获取指定的包下的类[" + packageName + '.' + className + "]发生错误,找不到此类的.class文件", e);
											throw e;
										}
									}
								}
							}
						}
					} catch (IOException e) {
						LOG.error("获取指定的包下的类发生错误,从jar包获取文件出错", e);
						throw e;
					}
				}
			}
		} catch (IOException e) {
			LOG.error("获取指定的包下的类发生IO异常", e);
			throw e;
		}

		return classes;
	}
    
    /** 
     * 以文件的形式来获取包下的所有Class 
     *  
     */  
	private static void findAndAddClassesInPackageByFile(String packageName, String packagePath,
			final boolean recursive, Set<Class<?>> classes) throws Throwable {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			LOG.info("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive,
						classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					// 添加到集合中去
					// classes.add(Class.forName(packageName + '.' + className));// forName会触发static方法，没有使用classLoader的load干净
					classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					LOG.error("获取指定的包下的类[" + packageName + '.' + className + "]发生错误,找不到此类的.class文件", e);
					throw e;
				}
			}
		}
	}
	
	/**
	 * 加载类,有缓存
	 * 
	 *            全类名
	 *             指定的类不存在
	 */
	private synchronized static void loadClass(String classFullName) throws ClassNotFoundException {
		List<Method> methodList = new ArrayList<Method>();
		//填充类容器
		if (CLASS_CONTAINER.containsKey(classFullName)) {
			//类已经加载过,直接返回
			return;
		}
		Class<?> clazz = Class.forName(classFullName);
		CLASS_CONTAINER.put(classFullName, clazz);
		//取得所有方法
		Method[] methods = clazz.getDeclaredMethods();
		if (methods != null) {
			for (Method method : methods) {
				methodList.add(method);
			}
		}

		//填充方法容器
		for (Method method : methodList) {
			String methodIdentify = makeMethodIdentify(classFullName, method.getName(),
					(Object[]) method.getParameterTypes());
			synchronized (METHOD_CONTAINER_BY_IDENTIFY) {
				METHOD_CONTAINER_BY_IDENTIFY.put(methodIdentify, method);
			}
		}
		
		//填充方法容器
		synchronized (METHOD_CONTAINER_BY_CLASS) {
			METHOD_CONTAINER_BY_CLASS.put(classFullName, methodList);
		}
	}

	/**
	 * 获取指定类的所有属性,有缓存
	 * 
	 *            类对象
	 *            所有属性(包含 public,private,protected)
	 *            是否递归父类属性(true:递归, false:不递归)
	 */
	private static void getFields(Class<?> clazz, List<Field> fieldList, boolean recursion) {
		Field[] declaredFields = clazz.getDeclaredFields();
		if (declaredFields != null) {
			for (Field field : declaredFields) {
				field.setAccessible(true);
				fieldList.add(field);
			}
		}
		if (recursion) {
			Class<?> superclass = clazz.getSuperclass();
			if (superclass != null && !superclass.equals(Object.class)) {
				getFields(superclass, fieldList, recursion);
			}
		}
	}

	/**
	 * 生成方法标识字符串<br/>
	 * 当方法的参数值包含null值的场合,由于无法获取参数的实际数据类型,将使用com.com.kaiwait.utils.common.InstanceUtils$Null来代表数据类型,这将导致生成的方法标识不能与实际的类方法匹配.<br/>
	 * 所有使用该方法生成的标识字符串定位实际的类方法的处理都需要在匹配不成功的时候,需通过参数个数再次确认是否存在匹配的方法,以防止错误的方法定位.<br/>
	 * 
	 * 
	 *            全类名
	 *            方法名
	 *            方法参数
	 */
	@SuppressWarnings("rawtypes")
	private static String makeMethodIdentify(String classFullName, String methodName, Object... params) {
		StringBuilder sb = new StringBuilder();
		sb.setLength(0);
		sb.append(classFullName);
		sb.append(".");
		sb.append(methodName);
		sb.append("(");
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object param : params) {
				if (i > 0) {
					sb.append(",");
				}
				if (param instanceof Class) {
					final Class paramClass = (Class) param;
					if (paramClass.equals(int.class)) {
						sb.append(Integer.class.getName());
					} else if (paramClass.equals(long.class)) {
						sb.append(Long.class.getName());
					} else if (paramClass.equals(byte.class)) {
						sb.append(Byte.class.getName());
					} else if (paramClass.equals(boolean.class)) {
						sb.append(Boolean.class.getName());
					} else if (paramClass.equals(char.class)) {
						sb.append(Character.class.getName());
					} else if (paramClass.equals(short.class)) {
						sb.append(Short.class.getName());
					} else if (paramClass.equals(float.class)) {
						sb.append(Float.class.getName());
					} else if (paramClass.equals(double.class)) {
						sb.append(Double.class.getName());
					} else {
						//基本数据类型以外
						sb.append(paramClass.getName());
					}
				} else {
					//参数值判定
					if (param == null) {
						//注意:参数为null将无法正确定位方法
						sb.append(Null.class.getName());
					} else {
						sb.append(param.getClass().getName());
					}
				}
				i++;
			}
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 反射调用异常
	 * 
	 *
	 */
	public static class ReflectionException extends Exception {

		/** 序列化ID */
		private static final long serialVersionUID = -6916732895958930551L;

		/**
		 * 构造函数
		 * 
		 *            异常信息
		 *            异常堆栈
		 */
		public ReflectionException(String message, Throwable cause) {
			super(message, cause);
		}

		/**
		 * 构造函数
		 * 
		 *            异常信息
		 */
		public ReflectionException(String message) {
			super(message);
		}

	}

	/**
	 * 为了给 用方法名获取实例中第一个方法 做参数标识用
	 * 
	 *
	 */
	private class GetFirstMethodIdentify {

	}
	
	/** 
	 * 标识Null值的对象
	 *
	 */
	private class Null {
		
	}

}
