package com.kaiwait.webservice.mst;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.mst.io.ClmstAddInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.ClmstService;

@Component("TaxTypeInit")
public class TaxTypeInit  implements SingleFunctionIF<ClmstAddInput> {

	@Resource
	private ClmstService clmstService;
	
	@Override
	public ClmstAddInput process(ClmstAddInput inputParam) {
	return clmstService.taxtypeInit(inputParam);	
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		return ClmstAddInput.class;
	}

	@Override
	public ValidateResult validate(ClmstAddInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}

}
