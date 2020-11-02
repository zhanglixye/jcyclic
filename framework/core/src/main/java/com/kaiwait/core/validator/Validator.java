package com.kaiwait.core.validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaiwait.core.process.dynamic.parser.InterfaceDefinitionNotFoundException;

public interface Validator {
	String validate(String mappingPath, HttpServletRequest request, HttpServletResponse response) throws InterfaceDefinitionNotFoundException;
	

	String onInvalidRequest(String message, HttpServletRequest request, HttpServletResponse response);


}
