package com.kaiwait.core.process.dynamic.parser;

import com.kaiwait.core.process.dynamic.vo.InterfaceDefinition;

public interface InterfaceDefinitionParse {
	InterfaceDefinition getInterfaceDefinition(String path) throws InterfaceDefinitionNotFoundException;

}
