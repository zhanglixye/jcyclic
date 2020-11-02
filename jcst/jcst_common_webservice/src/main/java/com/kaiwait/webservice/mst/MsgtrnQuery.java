package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.MsgtrnInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.MsgtrnService;

@Component("MsgtrnQuery")

public class MsgtrnQuery implements SingleFunctionIF<MsgtrnInput>{
	
	@Resource
	private MsgtrnService MsgtrnService;
	
	@Override
	public Object process(MsgtrnInput inputParam) {
		
		return MsgtrnService.searchMsgtrnList(inputParam);
	}

	public ValidateResult validate(MsgtrnInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return MsgtrnInput.class;
	}


}
