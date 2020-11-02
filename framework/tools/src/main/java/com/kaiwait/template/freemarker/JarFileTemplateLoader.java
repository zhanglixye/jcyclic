/**
 * 
 */
package com.kaiwait.template.freemarker;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import freemarker.cache.TemplateLoader;

/**
 * 支持从Jar文件中加载模板的模板加载器
 *
 */
public class JarFileTemplateLoader implements TemplateLoader {
	/** 模板容器*/
	private static Map<String, JarFileTemplate> TEMPLATE_MAP = new Hashtable<String, JarFileTemplate>() ; 

	/* (non-Javadoc)
	 */
	@Override
	public Object findTemplateSource(String name) throws IOException {
		final Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(name);
		if (!resources.hasMoreElements()) {
			return null;
		}
		if (TEMPLATE_MAP.containsKey(name)) {
			return TEMPLATE_MAP.get(name);
		} else {
			JarFileTemplate template = new JarFileTemplate(name);
			TEMPLATE_MAP.put(name, template);
			return template;
		}
	}

	/* (non-Javadoc)
	 */
	@Override
	public long getLastModified(Object templateSource) {
		
		return ((JarFileTemplate)templateSource).getLastModified();
	}

	/* (non-Javadoc)
	 */
	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		return ((JarFileTemplate)templateSource).getReader(encoding);
	}

	/* (non-Javadoc)
	 */
	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
		((JarFileTemplate)templateSource).closeTemplateSource();
	}

}
