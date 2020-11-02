package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.ListColumnInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.ListColumnService;
import com.kaiwait.thrift.common.server.annotation.Privilege;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;

@Privilege(keys= {"70"}, match=MatchEnum.ANY)
@Component("ListColumnUpdate")

public class ListColumnUpdate implements SingleFunctionIF<ListColumnInput>{
	
	@Resource
	private ListColumnService ListColumnService;
	
	@Override
	public Object process(ListColumnInput inputParam) {
		return ListColumnService.ListColumnUpdateTx(inputParam);
		 
	}

	public ValidateResult validate(ListColumnInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return ListColumnInput.class;
	}


}
