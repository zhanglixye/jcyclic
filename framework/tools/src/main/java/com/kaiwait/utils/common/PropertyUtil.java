package com.kaiwait.utils.common;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 读取配置文件的工具类<br/>
 * 该工具将加载classPath下所有的配置文件,之后加载%jety_CONFIG_HOME%环境变量设置的路径下所有的配置文件<br/>
 * 以配置文件在classPath中的相对路径 + 配置文件名 做为classPath配置文件命名空间<br/>
 * 以配置文件在%jety_CONFIG_HOME%中的相对路径 + 配置文件名 做为外部配置文件命名空间<br/>
 * classPath配置文件命名空间与外部配置文件命名空间冲突的场合,用外部配置文件命名空间中定义的值覆盖classPath配置文件命名空间的值<br/>
 * %jety_CONFIG_HOME%环境变量可以定义为系统环境变量或者JVM环境变量,两者可以同时存在,JVM环境变量优先<br/>
 * 
 * <pre>
 * 当classPath下、 系统环境变量指定目录下、 JVM环境变量指定目录下有相同命名空间的配置文件时,同名配置项目的设定顺序为:
 * 1)设置classPath下配置文件值
 * 2)用系统环境变量指定目录下配置文件值覆盖1）设定的值
 * 3)JVM环境变量指定目录下配置文件值覆盖2）设定的值
 * </pre>
 * 
 * 
 * 
 *
 */
public class PropertyUtil extends Properties {

	private static final Log LOGGER = LogFactory.getLog(PropertyUtil.class);

	/** 序列化ID */
	private static final long serialVersionUID = 5583030653502112371L;
	/** 定义配置文件保存路径的环境变量的名字 */
	public static final String CONFIG_HOME_ENV_NAME = "jety_CONFIG_HOME";
	/** 配置文件后缀 */
	public static final String CONFIG_FILE_SUFFIX = ".properties";
	/** 配置文件过滤器 */
	public static final FileFilter propertiesFilter = new FileFilter() {
		@Override
		public boolean accept(File file) {
			if (file.isDirectory() || file.getName().endsWith(CONFIG_FILE_SUFFIX)) {
				return true;
			} else {
				return false;
			}
		}
	};

	/** 保存属性文件的容器 */
	private static final Map<String, Map<String, String>> PROPERTY_MAP = new HashMap<String, Map<String, String>>();
	/** 方法没有实现的信息 */
	private static final String NOT_IMPLEMENTED_MESSAGE = "该方法没有实现,如需要使用该方法请与架构组联系";
	/** 系统设定文件的命名空间 */
	private static final String SYSTEM_SETTING_NAMESPACE = "system";
	/** 系统设定文件中用来定义应用名称的key */
	private static final String SYSTEM_SETTING_APP_NAME = "app_name";

	/**
	 * 禁止构造实例
	 */
	private PropertyUtil() {

	}

	/**
	 * 伪装成属性文件实例
	 * 
	 */
	public static Properties getInstance() {
		return new PropertyUtil();
	}

	/**
	 * 静态初始化
	 */
	static {
		init();
	}

	/**
	 * 初始化方法,程序启动时由静态初始化方法调用,实现单例模式.<br/>
	 * ※ 该方法没有合并至静态初始化方法中主要是为了让JUnit可以重复测试初始化过程,<br/>
	 * ※ 其它场合请确保不要调用该方法,否则会出现不确定的问题(主要是多线程的问题).<br/>
	 */
	protected static void init() {
		// 清空缓存
		PROPERTY_MAP.clear();

		// 加载classPath下所有配置文件.
		// URL systemResource = ClassLoader.getSystemResource("");
		URL systemResource = PropertyUtil.class.getResource("/");
		
		if (systemResource != null) {
			loadPropertiesFile(new File(systemResource.getFile()), null);
		}

		// 如果系统环境变量中定义了Properties文件保存路径,加载该路径下所有配置文件,如果与classPath下的配置文件发生冲突,将覆盖classPath下配置文件的值
		String property = getOSEnv(CONFIG_HOME_ENV_NAME);
		if (StringUtil.isNotEmpty(property)) {
			try {
				String appName = getAppName();
				//设置了应用名,以该名称作为外部配置文件的根目录
				property = property + "/" + appName;
			} catch (GetValueException e) {
				//没有设置应用名
			}
			// 加载该路径下所有文件.(应用于生产环境)
			LOGGER.info("开始加载系统环境变量" + CONFIG_HOME_ENV_NAME + "指定的外部配置文件");
			loadPropertiesFile(new File(property), null);
			LOGGER.info("系统环境变量" + CONFIG_HOME_ENV_NAME + "指定的外部配置文件加载结束");
		}

		// 如果JVM环境变量中定义了Properties文件保存路径,加载该路径下所有配置文件,
		// 如果与系统环境变量或者classPath下的配置文件发生冲突,将覆盖系统环境变量或者classPath下配置文件的值
		property = getVmEnv(CONFIG_HOME_ENV_NAME);
		if (StringUtil.isNotEmpty(property)) {
			try {
				String appName = getAppName();
				//设置了应用名,以该名称作为外部配置文件的根目录
				property = property + "/" + appName;
			} catch (GetValueException e) {
				//没有设置应用名
			}
			// 加载该路径下所有文件.(应用于生产环境)
			LOGGER.info("开始加载JVM环境变量" + CONFIG_HOME_ENV_NAME + "指定的外部配置文件");
			loadPropertiesFile(new File(property), null);
			LOGGER.info("JVM环境变量" + CONFIG_HOME_ENV_NAME + "指定的外部配置文件加载结束");
		}
	}

	/**
	 * 加载指定的配置文件,如果配置项目已经存在,用当前值覆盖已经存在的值
	 * 
	 *            要加载的配置文件
	 *            类路径缓冲对象
	 *             加载过程中发生的异常
	 */
	private static synchronized void loadPropertiesFile(File propertiesFile, StringBuilder classPathBuffer)
			throws LoadPropertiesException {
		String absolutePath = propertiesFile.getAbsolutePath();
		try {
			if (!propertiesFile.exists()) {
				// 文件不存在,退出
				return;
			}
			if (propertiesFile.isDirectory()) {
				// 是文件夹,构造包名
				if (classPathBuffer != null) {
					if (classPathBuffer.length() > 0) {
						classPathBuffer.append(".");
					}
					classPathBuffer.append(propertiesFile.getName());
				} else {
					classPathBuffer = new StringBuilder();
				}
				// 遍历目录下所有Properties文件
				for (File file : propertiesFile.listFiles(propertiesFilter)) {
					// 递归处理文件和文件夹
					loadPropertiesFile(file, new StringBuilder(classPathBuffer));
				}
			} else {
				LOGGER.info("开始加载配置文件:" + absolutePath);
				// 是Properties文件,加载属性
				Properties properties = new Properties();
				try (FileInputStream inStream = new FileInputStream(propertiesFile);) {
					properties.load(inStream);
				}
				// 得到当前Properties文件的相对类路径
				if (classPathBuffer != null) {
					if (classPathBuffer.length() > 0) {
						classPathBuffer.append(".");
					}
					classPathBuffer.append(FilenameUtils.getBaseName(propertiesFile.getName()));
				} else {
					classPathBuffer = new StringBuilder();
				}
				// 缓存配置内容Map
				Map<String, String> propertiesMap;
				String namespace = classPathBuffer.toString();
				LOGGER.info("namespace is [" + namespace + "]" );
				if (PROPERTY_MAP.containsKey(namespace)) {
					propertiesMap = PROPERTY_MAP.get(namespace);
				} else {
					propertiesMap = new HashMap<String, String>();
					PROPERTY_MAP.put(namespace, propertiesMap);
				}
				// 将配置内容加载到Map
				for (Entry<Object, Object> entry : properties.entrySet()) {
					String keyStr = entry.getKey().toString();
					String valueStr = entry.getValue().toString();
					propertiesMap.put(keyStr, valueStr);
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("\t" + keyStr + "=" + valueStr);
					} else {
						LOGGER.info("\t" + keyStr + "=" + "******");
					}
				}
				LOGGER.info("加载配置文件结束:" + absolutePath);
			}
		} catch (Throwable e) {
			throw new LoadPropertiesException("加载配置文件失败! 错误文件:" + absolutePath, e);
		}
	}

	/**
	 * 获取当前应用的名称,该名称将被用于确定外部配置文件的根路径.<br/>
	 * 应该在当前应用的CLASSPATH根下放置system.properties文件,在该文件中设置 app_name=当前应用的名称
	 * 
	 *             当设定值不存在时,抛出该异常
	 */
	public static String getAppName() throws GetValueException {
		return get(SYSTEM_SETTING_NAMESPACE, SYSTEM_SETTING_APP_NAME);
	}

	/**
	 * 取得配置文件的值
	 * 
	 *            命名空间
	 *            key
	 *             当设定值不存在时,抛出该异常
	 */
	public static String get(String namespace, String key) throws GetValueException {
		if (getNameSpace(namespace).containsKey(key)) {
			return getNameSpace(namespace).get(key);
		} else {
			throw new GetValueException(String.format("设定值不存在,[%s.%s]", namespace, key));
		}
	}

	/**
	 * 取得配置文件的值
	 * 
	 *            需使用 ('命名空间' + '#' + 'key')的形式<br/>
	 *            例:com.com.kaiwait.web.config#remoteAddress
	 *             当设定值不存在时,抛出该异常
	 */
	public static String get(String key) throws GetValueException {
		if (StringUtil.isNotEmpty(key)) {
			String[] split = key.split("#");
			if (split != null && split.length == 2) {
				return get(split[0], split[1]);
			} else {
				throw new GetValueException(String.format("格式不正确, 需使用 ('命名空间' + '#' + 'key')的形式做为Key读取配置文件"));
			}
		} else {
			throw new GetValueException(String.format("不可以使用空值做为Key读取配置文件"));
		}
	}

	/**
	 * 获取操作系统环境变量
	 * 
	 *            key
	 *             获取操作系统环境变量时发生异常
	 */
	public static String getOSEnv(String key) {
		Map<String, String> envMap;
		try {
			envMap = System.getenv();
		} catch (Throwable e) {
			throw new GetValueException("获取操作系统环境变量时发生异常, key:" + key, e);
		}
		return envMap.get(key);
	}

	/**
	 * 获取JVM环境变量
	 * 
	 *            key
	 *             获取JVM环境变量时发生异常
	 */
	public static String getVmEnv(String key) throws GetValueException {
		String property;
		try {
			property = System.getProperty(key);
		} catch (Throwable e) {
			throw new GetValueException("获取JVM环境变量时发生异常, key:" + key, e);
		}
		return property;
	}

	/**
	 * 取得配置文件的值
	 * 
	 *            命名空间
	 *            key
	 *             当设定值不存在时,抛出该异常
	 *             转换异常
	 */
	public static Integer getInt(String namespace, String key) throws GetValueException, NumberFormatException {
		String value = get(namespace, key);
		return Integer.parseInt(value);
	}

	/**
	 * 取得命名空间下所有设定值
	 * 
	 *            命名空间
	 *             命名空间不存在时,抛出该异常
	 */
	public static Map<String, String> getNameSpace(String namespace) throws GetValueException {
		if (PROPERTY_MAP.containsKey(namespace)) {
			return PROPERTY_MAP.get(namespace);
		} else {
			throw new GetValueException(String.format("配置文件不存在,[%s]", namespace));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public String getProperty(String key) {
		return get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public String getProperty(String key, String defaultValue) {
		try {
			return get(key);
		} catch (Throwable e) {
			return defaultValue;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public synchronized Object setProperty(String key, String value) {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public synchronized void load(Reader reader) throws IOException {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public synchronized void load(InputStream inStream) throws IOException {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public void save(OutputStream out, String comments) {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public void store(Writer writer, String comments) throws IOException {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public void store(OutputStream out, String comments) throws IOException {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public synchronized void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * java.lang.String)
	 */
	@Override
	public void storeToXML(OutputStream os, String comment) throws IOException {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void storeToXML(OutputStream os, String comment, String encoding) throws IOException {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public Enumeration<?> propertyNames() {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public Set<String> stringPropertyNames() {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public void list(PrintStream out) {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public void list(PrintWriter out) {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	/**
	 * 加载配置文件异常
	 * 
	 *
	 */
	public static class LoadPropertiesException extends RuntimeException {

		/** 序列化ID */
		private static final long serialVersionUID = 1L;

		/**
		 * 构造函数
		 * 
		 *            异常信息
		 */
		public LoadPropertiesException(String message) {
			super(message);
		}

		/**
		 * 构造函数
		 * 
		 *            异常信息
		 *            异常堆栈
		 */
		public LoadPropertiesException(String message, Throwable cause) {
			super(message, cause);
		}

	}

	/**
	 * 取得配置值异常
	 * 
	 *
	 */
	public static class GetValueException extends RuntimeException {

		/** 序列化ID */
		private static final long serialVersionUID = 1L;

		/**
		 * 构造函数
		 * 
		 *            异常信息
		 */
		public GetValueException(String message) {
			super(message);
		}

		/**
		 * 构造函数
		 * 
		 *            异常信息
		 *            异常堆栈
		 */
		public GetValueException(String message, Throwable cause) {
			super(message, cause);
		}

	}
}
