package com.kaiwait.webservice.jczh;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.io.CostInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.jczh.CostService;


@Component("listTitleUp")
public class ListTitleUp implements SingleFunctionIF<CostInput>{
	
	@Resource
	private CostService costService;
	
	@Override
	public Object process(CostInput inputParam) {
		return costService.updateTitleUpTx(inputParam.getListTitleMsg());
	}

	public ValidateResult validate(CostInput inputParam) {		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return CostInput.class;
	}


}
