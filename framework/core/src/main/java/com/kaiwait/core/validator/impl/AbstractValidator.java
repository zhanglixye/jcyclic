/**
 * 
 */
package com.kaiwait.core.validator.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaiwait.core.validator.Validator;

/**
 * @author wings
 *
 */
public abstract class AbstractValidator implements Validator {

	public String onInvalidRequest(String message, HttpServletRequest request, HttpServletResponse response) {
		return "{\"metaInfo\":{\"result\":\"VALIDATE_FORMAT_ERROR\",\"message\":\""
				+ message + "\"},\"data\":null}";
	}

}
