package com.kaiwait.core.process;

public interface SingleFunctionIF<T> {
	String PROCESS_METHOD = "process";
	String VALIDATE_METHOD = "validate";

	Object process(T inputParam);
	
	ValidateResult validate(T inputParam);
	
	@SuppressWarnings("rawtypes")
	Class getParamType();

}
