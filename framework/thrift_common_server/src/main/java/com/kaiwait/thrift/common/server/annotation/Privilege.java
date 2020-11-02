/**
 * 
 */
package com.kaiwait.thrift.common.server.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
/**
 * @author Administrator
 *
 */
public @interface Privilege {
	/**
	 * 权限Key列表
	 * @return
	 */
	String[] keys();
	
	/**
	 * 权限匹配方式
	 * @return
	 */
	MatchEnum match() default MatchEnum.ANY;
}
