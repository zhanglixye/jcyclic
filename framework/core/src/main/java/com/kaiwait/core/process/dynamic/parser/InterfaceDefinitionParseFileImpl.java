/**
 * 
 */
package com.kaiwait.core.process.dynamic.parser;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiwait.core.process.dynamic.vo.InterfaceDefinition;

/**
 * @author wings
 *
 */
public class InterfaceDefinitionParseFileImpl implements InterfaceDefinitionParse {
	private static final Logger LOG = LoggerFactory.getLogger(InterfaceDefinitionParseFileImpl.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	/** 保存接口定义 文件的根目录*/
	private String definitionFileRootPath;

	
	/* (non-Javadoc)
	 * @see com.kaiwait.core.process.dynamic.parser.InterfaceDefinitionParse#getInterfaceDefinition(java.lang.String)
	 */
	@Override
	public InterfaceDefinition getInterfaceDefinition(String path) throws InterfaceDefinitionNotFoundException{
		String configFilePath = definitionFileRootPath + path + ".json";

		InputStream resourceAsStream = Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(configFilePath);
		if (resourceAsStream == null) {
			throw new InterfaceDefinitionNotFoundException("无法获取请求" + path + "的接口定义,请确认" +configFilePath  + "是否存在");
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"));) {
			String line;
			StringBuilder sb = new StringBuilder(2048);
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			InterfaceDefinition interfaceDefinition = OBJECT_MAPPER.readValue(sb.toString(), InterfaceDefinition.class);
			interfaceDefinition.setDefinitionFilePath(configFilePath);
			return interfaceDefinition;
		} catch (Exception e) {
			LOG.error("读取请求{}的接口定义文件{}发生异常", path, configFilePath, e);
			return null;
		}
	}

	public String getDefinitionFileRootPath() {
		return definitionFileRootPath;
	}

	public void setDefinitionFileRootPath(String definitionFileRootPath) {
		this.definitionFileRootPath = definitionFileRootPath;
	}

}
