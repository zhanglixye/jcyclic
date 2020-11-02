package com.kaiwait.webservice.jczh;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.LendtrnInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.LendtrnService;


@Component("LendtrnDelete")

public class LendtrnDelete implements SingleFunctionIF<LendtrnInput>{
	
	@Resource
	private LendtrnService LendtrnService;
	@Resource
	private CommonMethedService CommonMethedService;
	
	@Override
	public Object process(LendtrnInput inputParam) {
		return LendtrnService.LendtrnDeleteTx(inputParam);
		 
	}

	public ValidateResult validate(LendtrnInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return LendtrnInput.class;
	}


}
